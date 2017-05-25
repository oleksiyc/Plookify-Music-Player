package player;


import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.sqlite.SQLiteConfig;
//import javafx.scene.media.Media;
public class Controller implements Initializable{
    
    Connection conn;
    PreparedStatement pst = null;    
    Statement cstmt = null;
    ResultSet rs = null;
       
    PreparedStatement pst2 = null;    
    Statement cstmt2 = null;
    ResultSet rs2 = null;
        
    PreparedStatement pst3 = null;    
    Statement cstmt3 = null;
    ResultSet rs3 = null;
        
    PreparedStatement pst4 = null;    
    Statement cstmt4 = null;
    ResultSet rs4 = null;
        
    PreparedStatement pst5 = null;    
    Statement cstmt5 = null;
    ResultSet rs5 = null;
    
    PreparedStatement pst6 = null;    
    Statement cstmt6 = null;
    ResultSet rs6 = null;
    
    PreparedStatement pst7 = null;    
    Statement cstmt7 = null;
    ResultSet rs7 = null;
    
    PreparedStatement pst12 = null;    
    Statement cstmt12 = null;
    ResultSet rs12 = null;
    
    @FXML
    private ListView<String> playlistNames;
    @FXML
    private ListView<String> listSongs;
    @FXML
    private Button newPlaylist;
    @FXML
    private Button addNowPlaying;
    @FXML
    private Button addSearchPlaylist;
    @FXML
    private Button addSearchExistingPlaylist;
    //@FXML
    /*private Button play;
    @FXML
    private Button pause;
    @FXML
    private Button rewind;
    @FXML
    private Button fastforward; */
    //@FXML
    //private Button nowPlaying; 
    
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn trackid;
    @FXML
    private TableColumn trackname;
    @FXML
    private TableColumn artist;
    @FXML
    private TableColumn genre;
    @FXML
    private TableColumn duration;
    @FXML
    private TextField searchArtist;
    @FXML
    private Label playlistLabel;
    @FXML
    private Label privateLabel;
    @FXML
    private Label searchResults;
    
    
    @FXML
    private ImageView play;
    @FXML
    private ImageView nowPlaying;
    @FXML
    private MediaView mv;
    @FXML
    private MediaPlayer mp;
    @FXML
    private Media me;
       
    ObservableList <TableData> dataNotes; 
   
    final ObservableList<String> list= FXCollections.observableArrayList();
          
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playlistList();
        tableView();
        contextMenus();        
        contextMenuTable();    
        //play();
//        String path = new File("/common/Sorry.mp3").getAbsolutePath();
//            me = new Media(new File(path).toURI().toString());
//            mp = new MediaPlayer(me);
//            mv.setMediaPlayer(mp);
    }  
    
    @FXML
    public void newPlaylist(ActionEvent event){
        
        TextInputDialog dialog = new TextInputDialog("New Playlist");
        dialog.setTitle("New Playlist");
        dialog.setHeaderText("Create a new Playlist");
        dialog.setContentText("Please enter the playlist name:");
        Optional<String> result = dialog.showAndWait();
        
        TextInputDialog dialog2 = new TextInputDialog("");
        dialog2.setTitle("New Playlist");
        dialog2.setHeaderText("Create a new Playlist");
        dialog2.setContentText("Private(1) or Friend(0):");
        Optional<String> result2 = dialog2.showAndWait();
        try{
            
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            conn.setAutoCommit(false);
            if(result.isPresent()){
                String query2 = "INSERT INTO playlist VALUES(null" +",'" +  result.get()  + "','"  + result2.get() +"');";
                
                cstmt = conn.createStatement();
                
                cstmt.executeUpdate(query2);
            }
            cstmt.close();
            conn.commit();
            conn.close();
                       
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            
        }       
        refresh();
    }
    
    public void refresh(){
        list.clear();
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            
            String query = "SELECT PlaylistName FROM playlist";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while(rs.next()){
                playlistNames.setItems(list);
                list.add(rs.getString("PlaylistName"));
            }
            pst.close();
            rs.close();
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    @FXML
    public void tableView(){
    dataNotes = FXCollections.observableArrayList();

        trackid.setCellValueFactory(new PropertyValueFactory<TableData, String>("TrackID"));
        trackname.setCellValueFactory(new PropertyValueFactory<TableData, String>("TrackName"));
        artist.setCellValueFactory(new PropertyValueFactory<TableData, String>("Artist"));
        genre.setCellValueFactory(new PropertyValueFactory<TableData, String>("Genre"));
        duration.setCellValueFactory(new PropertyValueFactory<TableData, String>("Duration"));

        try {
            SQLiteConfig config = new SQLiteConfig();                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            cstmt2 = conn.createStatement();
            rs2 = conn.createStatement().executeQuery("SELECT TrackID, TrackName, Artist, Genre, Duration FROM tracklist");
                       
            while (rs2.next()) {
                TableData nt = new TableData();
                nt.trackid.set(rs2.getString("TrackID"));
                nt.trackname.set(rs2.getString("TrackName"));
                nt.artist.set(rs2.getString("Artist"));
                nt.genre.set(rs2.getString("Genre"));
                nt.duration.set(rs2.getString("Duration"));
                dataNotes.add(nt);                
            }            
            tableView.setItems(dataNotes);
            cstmt2.close();
            rs2.close();      
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        searchArtist();
        listSongs.getSelectionModel().select(0);
        playlistLabel.setText(listSongs.getSelectionModel().getSelectedItem());
        addNowPlaying.setVisible(false);
        addSearchPlaylist.setVisible(false);
        addSearchExistingPlaylist.setVisible(false);
        searchResults.setVisible(false);
        if(! searchArtist.isFocused()){
                playlistLabel.setVisible(true);
            searchResults.setVisible(false);
            addNowPlaying.setVisible(false);
            }
        
        if(! listSongs.getSelectionModel().isEmpty()){
        playlistNames.getSelectionModel().clearSelection();
        }
    }
    
    @FXML 
    public void viewPlaylist(MouseEvent arg0) {
        
        dataNotes = FXCollections.observableArrayList();

        trackname.setCellValueFactory(new PropertyValueFactory<TableData, String>("TrackName"));
        artist.setCellValueFactory(new PropertyValueFactory<TableData, String>("Artist"));
        genre.setCellValueFactory(new PropertyValueFactory<TableData, String>("Genre"));
        duration.setCellValueFactory(new PropertyValueFactory<TableData, String>("Duration"));

        try {
            SQLiteConfig config = new SQLiteConfig();                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            cstmt3 = conn.createStatement();
            rs3 = conn.createStatement().executeQuery("SELECT tracklist.TrackID, tracklist.TrackName , tracklist.Artist, tracklist.Genre, "
                    + "tracklist.Duration FROM tracklist INNER JOIN playlistsongs ON  tracklist.TrackID = playlistsongs.Tracks "
                    + "INNER JOIN playlist ON playlist.PlaylistID=playlistsongs.Playlist WHERE playlist.PlaylistID= "+playlistNames.getSelectionModel().getSelectedIndex()+" ");
                       
            while (rs3.next()) {
                TableData nt = new TableData();
                nt.trackid.set(rs3.getString("TrackID"));
                nt.trackname.set(rs3.getString("TrackName"));
                nt.artist.set(rs3.getString("Artist"));
                nt.genre.set(rs3.getString("Genre"));
                nt.duration.set(rs3.getString("Duration"));
                dataNotes.add(nt);                
            }            
            tableView.setItems(dataNotes);
            cstmt3.close();
            rs3.close(); 
              
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }       
        playlistLabel();
        privateLabel();
        privateLabel.setVisible(true);
        addNowPlaying.setVisible(true);
        addSearchPlaylist.setVisible(false);
        addSearchExistingPlaylist.setVisible(false);
        
        if(playlistNames.getSelectionModel().getSelectedIndex() == 0 ){
            privateLabel.setVisible(false);
        }
        
        if(playlistNames.getSelectionModel().getSelectedIndex() == 0 ){
            addNowPlaying.setVisible(false);
        }
        
        if(! playlistNames.getSelectionModel().isEmpty()){
            listSongs.getSelectionModel().clearSelection();
        }
        
        searchResults.setVisible(false);
        if(! searchArtist.isFocused()){
                playlistLabel.setVisible(true);
            searchResults.setVisible(false);
            }
               
    }
    
    @FXML
    public void searchArtist(){
         
         FilteredList<TableData> filteredData = new FilteredList<>(dataNotes, p -> true);
         searchArtist.setOnKeyReleased(e -> { 
         searchArtist.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super TableData>) search->{
                if(newValue == null || newValue.isEmpty()){
                return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(search.getArtist().toLowerCase().contains(newValue)){
                return true;
                }else if(search.getTrackName().toLowerCase().contains(newValue)){
                return true;
                } else if(search.getGenre().toLowerCase().contains(newValue)){
                return true;
                }
                return false;
            });
            addSearchPlaylist.setVisible(true);
            addSearchExistingPlaylist.setVisible(true);

            if(searchArtist.getText() ==null){
            playlistLabel.setVisible(true);
            searchResults.setVisible(false);
            } else{
            playlistLabel.setVisible(false);
            searchResults.setVisible(true);
            }
            
            if(searchArtist.isFocused()){
                listSongs.getSelectionModel().clearSelection();
                playlistNames.getSelectionModel().clearSelection();
                addNowPlaying.setVisible(false);
            }
                       
        });
        SortedList<TableData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
        });
        
    }
    
    public void playlistList(){
    try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            
            String query = "SELECT PlaylistName FROM playlist";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while(rs.next()){
                playlistNames.setItems(list);
                list.add(rs.getString("PlaylistName"));
            }
            pst.close();
            rs.close();
            
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }   
    
    }
    
    public void contextMenus(){
        playlistNames.setCellFactory(lv -> {
        ListCell<String> cell = new ListCell<>();
        ContextMenu contextMenu = new ContextMenu();  
        
        if(playlistNames.getSelectionModel().getSelectedIndex() == 0){
            contextMenu.hide();
        }
        
        MenuItem renamePlaylist = new MenuItem();
        
        renamePlaylist.textProperty().bind(Bindings.format("Rename", cell.itemProperty()));
        renamePlaylist.setOnAction(event -> {
            String item = cell.getItem();
            
            TextInputDialog dialog3 = new TextInputDialog("");
        dialog3.setTitle("New Playlist Name");
        dialog3.setHeaderText("Rename the playlist");
        dialog3.setContentText("New playlist name: ");
        Optional<String> result3 = dialog3.showAndWait(); 
        
        try {
            String query4 = "UPDATE playlist SET PlaylistName = '" + result3.get()+ "' WHERE PlaylistID = " +playlistNames.getSelectionModel().getSelectedIndex()+"";                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            pst3 = conn.prepareStatement(query4);
            pst3.executeUpdate();
                       
            pst3.close();
            
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        
        refresh();
        
        
        });
        
        MenuItem deletePlaylist = new MenuItem();
        
        deletePlaylist.textProperty().bind(Bindings.format("Delete", cell.itemProperty()));
        deletePlaylist.setOnAction(event -> {
         try {
            String query3 = "DELETE FROM Playlist WHERE PlaylistID= " + playlistNames.getSelectionModel().getSelectedIndex()+"";                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            pst3 = conn.prepareStatement(query3);
            pst3.executeUpdate();
                       
            pst3.close();
            
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
         
         refresh();
        });
        
        MenuItem privatePlaylist = new MenuItem();
        
        privatePlaylist.textProperty().bind(Bindings.format("Make Private", cell.itemProperty()));
        privatePlaylist.setOnAction(event -> {
            
        try {
            String query7 = "UPDATE playlist SET Private = '1' WHERE PlaylistID = " +playlistNames.getSelectionModel().getSelectedIndex()+"";                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            pst5 = conn.prepareStatement(query7);
            pst5.executeUpdate();
                       
            pst5.close();
            privateLabel();
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
                
        });
        
        MenuItem publicPlaylist = new MenuItem();
        
        publicPlaylist.textProperty().bind(Bindings.format("Make Public", cell.itemProperty()));
        publicPlaylist.setOnAction(event -> {
            
        try {
            String query7 = "UPDATE playlist SET Private = '0' WHERE PlaylistID = " +playlistNames.getSelectionModel().getSelectedIndex()+"";                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            pst5 = conn.prepareStatement(query7);
            pst5.executeUpdate();
                       
            pst5.close();
            privateLabel();
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
       
               
        });
                    
        contextMenu.getItems().addAll(renamePlaylist, deletePlaylist, privatePlaylist, publicPlaylist);
        
        cell.textProperty().bind(cell.itemProperty());
        
        cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty)->{
            if(isNowEmpty){
            cell.setContextMenu(null);
            } else{
                cell.setContextMenu(contextMenu);
            }
            
        });
        return cell;
        
    });
        
        
    }
   
    @FXML 
    public void listSongs(MouseEvent arg0) {
        tableView();
        privateLabel.setVisible(false);
        
        }
    
    public void contextMenuTable(){
        tableView.setRowFactory(lv -> {
        TableRow<TableData> cell = new TableRow<>();
        ContextMenu contextMenu2 = new ContextMenu();               
        
        MenuItem deleteItem2 = new MenuItem();
                
        deleteItem2.textProperty().bind(Bindings.format("Delete", cell.itemProperty()));
        deleteItem2.setOnAction(event -> {
         try {
             TableData selectedid = (TableData) tableView.getSelectionModel().getSelectedItem();
            String query6 = "DELETE FROM playlistsongs WHERE Tracks= " + selectedid.getTrackID()+" AND Playlist= "+ playlistNames.getSelectionModel().getSelectedIndex()+"";                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            pst4 = conn.prepareStatement(query6);
            pst4.executeUpdate();
                       
            pst4.close();
            
            
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        
        refreshPlaylistList();
        
        });
        
        MenuItem addNowPlaying = new MenuItem();
                
        addNowPlaying.textProperty().bind(Bindings.format("Add to Now Playing", cell.itemProperty()));
        addNowPlaying.setOnAction(event -> {
         try {
             TableData selectedid = (TableData) tableView.getSelectionModel().getSelectedItem();
            String query12 = "INSERT INTO playlistsongs (Playlist, Tracks) VALUES (0, "+ selectedid.getTrackID() +")";                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            pst12 = conn.prepareStatement(query12);
            pst12.executeUpdate();
                       
            pst12.close();
            
            
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        
        //refreshPlaylistList();
        
        });
                    
        contextMenu2.getItems().addAll(deleteItem2, addNowPlaying);
                       
        cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty)->{
            if(isNowEmpty){
            cell.setContextMenu(null);
            } else{
                cell.setContextMenu(contextMenu2);
            }
            
        });
        return cell;
    });
               
    }
    
    @FXML
    public void playlistLabel() {
        playlistLabel.setText(playlistNames.getSelectionModel().getSelectedItem());            
    }
        
        @FXML
        public void privateLabel() {
            try {
                 
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            
            String query7 = "SELECT PlaylistID, PlaylistName, Private FROM playlist WHERE PlaylistName= '" + playlistNames.getSelectionModel().getSelectedItem() +"'"; 
            pst6 = conn.prepareStatement(query7);
            rs6 = pst6.executeQuery();
                       
            if(rs6.getInt("Private")==1){
            privateLabel.setText("Private");
            } else if(rs6.getInt("Private")==0){
            privateLabel.setText("Friend");
            } 
            
                       
            pst6.close();
            rs6.close(); 
                  
            } catch(Exception e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
                }
                    
        }
        
        public void refreshPlaylistList(){
        dataNotes.clear();
        try {
            SQLiteConfig config = new SQLiteConfig();                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            cstmt3 = conn.createStatement();
            rs3 = conn.createStatement().executeQuery("SELECT tracklist.TrackID, tracklist.TrackName , tracklist.Artist, tracklist.Genre, "
                    + "tracklist.Duration FROM tracklist INNER JOIN playlistsongs ON  tracklist.TrackID = playlistsongs.Tracks "
                    + "INNER JOIN playlist ON playlist.PlaylistID=playlistsongs.Playlist WHERE playlist.PlaylistID= "+playlistNames.getSelectionModel().getSelectedIndex()+" ");
                       
            while (rs3.next()) {
                TableData nt = new TableData();
                nt.trackid.set(rs3.getString("TrackID"));
                nt.trackname.set(rs3.getString("TrackName"));
                nt.artist.set(rs3.getString("Artist"));
                nt.genre.set(rs3.getString("Genre"));
                nt.duration.set(rs3.getString("Duration"));
                dataNotes.add(nt);                
            }            
            tableView.setItems(dataNotes);
            cstmt3.close();
            rs3.close(); 
              
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        }
        
        @FXML
    public void addNowPlaying(ActionEvent event){    
        
        try {
             
            String query8 = "INSERT INTO playlistsongs (Playlist, Tracks) VALUES (?, ?)";                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            pst7 = conn.prepareStatement(query8);
                      
            for(int i=0; i<tableView.getItems().size(); i++){
            tableView.getSelectionModel().select(i);
            TableData selectedid2 = (TableData) tableView.getSelectionModel().getSelectedItem();
            pst7.setInt(1, 0);
            pst7.setString(2, selectedid2.getTrackID());
            pst7.addBatch();
            }
            pst7.executeBatch();
            
            pst7.close();
                      
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    @FXML
    public void addSearchPlaylist(ActionEvent event){    
        TextInputDialog dialog = new TextInputDialog("New Playlist");
        dialog.setTitle("New Playlist");
        dialog.setHeaderText("Create a new Playlist");
        dialog.setContentText("Please enter the playlist name:");
        Optional<String> result = dialog.showAndWait();
        
        TextInputDialog dialog2 = new TextInputDialog("");
        dialog2.setTitle("New Playlist");
        dialog2.setHeaderText("Create a new Playlist");
        dialog2.setContentText("Private(1) or Friend(0):");
        Optional<String> result2 = dialog2.showAndWait();
        try{
            
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");
            conn.setAutoCommit(false);
            if(result.isPresent()){
                String query2 = "INSERT INTO playlist VALUES(null" +",'" +  result.get()  + "','"  + result2.get() +"');";
                
                cstmt = conn.createStatement();
                
                cstmt.executeUpdate(query2);
            }
            cstmt.close();
            conn.commit();
            conn.close();
                       
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            
        }       
        refresh();
        
        try {
             
            String query9 = "INSERT INTO playlistsongs (Playlist, Tracks) VALUES (?, ?)";                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            pst7 = conn.prepareStatement(query9);
                      
            for(int i=0; i<tableView.getItems().size(); i++){
            tableView.getSelectionModel().select(i);
            TableData selectedid2 = (TableData) tableView.getSelectionModel().getSelectedItem();
            playlistNames.getSelectionModel().selectLast();
            pst7.setInt(1, playlistNames.getSelectionModel().getSelectedIndex());
            pst7.setString(2, selectedid2.getTrackID());
            pst7.addBatch();
            }
            pst7.executeBatch();
            
            pst7.close();
                      
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    @FXML
    public void addSearchExistingPlaylist(ActionEvent event){    

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Now Playing", list);
        dialog.setTitle("Choose Playlist");
        dialog.setHeaderText("Choose a Playlist");
        dialog.setContentText("Choose Playlist:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            try {
             
            String query9 = "INSERT INTO playlistsongs (Playlist, Tracks) VALUES (?, ?)";                       
            conn = DriverManager.getConnection("jdbc:sqlite:Database.sqlite");                        
            pst7 = conn.prepareStatement(query9);
                      
            for(int i=0; i<tableView.getItems().size(); i++){
            tableView.getSelectionModel().select(i);
            playlistNames.getSelectionModel().select(result.get());
            TableData selectedid2 = (TableData) tableView.getSelectionModel().getSelectedItem();
            pst7.setInt(1, playlistNames.getSelectionModel().getSelectedIndex());
            pst7.setString(2, selectedid2.getTrackID());
            pst7.addBatch();
            }
            pst7.executeBatch();
            
            pst7.close();
                      
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        }
               
    }
    
//    @FXML
//        public void play(MouseEvent event){
////            URL resource = getClass().getResource("common/Sorry.mp3");
////            Media media = new Media(resource.toString());
////            //Media media = new Media(Paths.get("/common/sorry.mp3").toUri().toString());
////            MediaPlayer mediaPlayer = new MediaPlayer(media);
////            mediaPlayer.play();
//            
//            
//         //Media media = new Media(Paths.get("/src/common/sorry.mp3").toUri().toString());
//         //  player = new MediaPlayer(media);
//           
//         //player.play();
//           
//            
//         //final URL resource = getClass().getResource("/src/common/sorry.mp3");
//         //final Media media = new Media(resource.toString());
//         //final MediaPlayer mediaPlayer = new MediaPlayer(media);
//         //mediaPlayer.play();
//           
//           
//           
//        }
    
    
    
        //public void play(){
        //Image PlayButtonImage = new Image("common/play.png");
        //ImageView imageViewPlay = new ImageView(PlayButtonImage);
        //play.setGraphic(imageViewPlay);
        
        //Image PauseButtonImage = new Image("common/pause.png");
        //ImageView imageViewPause = new ImageView(PauseButtonImage);
        //pause.setGraphic(imageViewPause);
        
        //Image RewindButtonImage = new Image("common/rewind.png");
        //ImageView imageViewRewind = new ImageView(RewindButtonImage);
        //rewind.setGraphic(imageViewRewind);
        
        //Image FastForwardButtonImage = new Image("common/fastforward.png");
        //ImageView imageViewFastForward = new ImageView(FastForwardButtonImage);
        //fastforward.setGraphic(imageViewFastForward);
        
        //Image NowPlayingButtonImage = new Image("common/nowplaying.png");
        //ImageView imageViewNowPlaying = new ImageView(NowPlayingButtonImage);
        //nowPlaying.setGraphic(imageViewNowPlaying);
                
        //}
        
//    
//    @FXML
//    public void play(ActionEvent event){
//
//
//
//        mp.play();
            
        
        //}
        
        @FXML
        public void nowPlaying(MouseEvent arg0){
        
        playlistNames.getSelectionModel().select(0);
        listSongs.getSelectionModel().clearSelection();
        refreshPlaylistList();
        playlistLabel();
        privateLabel.setVisible(false);
        addNowPlaying.setVisible(false);
        } 

               
}  
    



