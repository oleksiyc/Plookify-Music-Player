package radio;


import javax.swing.JFrame;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import radio.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyEvent;
import radio.TableData;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.sqlite.SQLiteConfig;

public class Controller implements Initializable{

    private static String id;
    
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
    
    Connection conn2;
    PreparedStatement pst8 = null;    
    Statement cstmt8 = null;
    ResultSet rs8 = null;
    
    PreparedStatement pst9 = null;    
    Statement cstmt9 = null;
    ResultSet rs9 = null;
    
    PreparedStatement pst10 = null;    
    Statement cstmt10 = null;
    ResultSet rs10 = null;
    
    PreparedStatement pst14 = null;    
    Statement cstmt14 = null;
    ResultSet rs14 = null;
    
    @FXML
    private ListView<String> playlistNames;
    @FXML
    private ListView<String> listSongs;
    @FXML
    private Button addSearchPlaylist;
    @FXML
    private Button Generate;
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
    private ListView<String> genreget;   

       
    ObservableList <TableData> dataNotes; 
   
    final ObservableList<String> list= FXCollections.observableArrayList();
    final ObservableList<String> list2= FXCollections.observableArrayList();
    final ObservableList<String> list3= FXCollections.observableArrayList();
    
    String getGenre;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playlistList();
        tableView();
        contextMenus();        
        contextMenuTable();     
        
        searchArtist.setOnKeyPressed(new EventHandler<KeyEvent>() {
           
            @Override
            public void handle(KeyEvent me) {
                try{
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");


                String query14 = "SELECT Genre FROM TrackList WHERE Artist = '"+ searchArtist.getText() +"' LIMIT 1 ";
               pst14 = conn.prepareStatement(query14);
               rs14 = pst14.executeQuery();

                while(rs14.next()){
                    getGenre = rs14.getString("Genre");
                    
                }

                pst14.close();
                rs14.close();

            }catch(Exception e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
    }
});
        
    }  
    
    public void Generate (ActionEvent Event){
        {
//            try {
        
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
//            
//            String fquery3 = "SELECT PaymentType FROM Customer where Customer.Customerid="+ Controller.id; 
//            pst4 = conn.prepareStatement(fquery3);
//            rs4 = pst4.executeQuery();
                       
//            if(rs4.getInt("PaymentType")==1){
 
            dataNotes = FXCollections.observableArrayList();

        trackname.setCellValueFactory(new PropertyValueFactory<radio.TableData, String>("TrackName"));
        artist.setCellValueFactory(new PropertyValueFactory<radio.TableData, String>("Artist"));
        genre.setCellValueFactory(new PropertyValueFactory<radio.TableData, String>("Genre"));
        duration.setCellValueFactory(new PropertyValueFactory<radio.TableData, String>("Duration"));
        
        
//

        try {
            SQLiteConfig config = new SQLiteConfig();                       
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite"); 
            
            cstmt3 = conn.createStatement();
            rs3 = conn.createStatement().executeQuery("SELECT * FROM TrackList WHERE Genre = '"+getGenre+"' ORDER BY RANDOM() LIMIT 10");
            
                       
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
                
//            } else if(rs4.getInt("PaymentType")==0){
//                JFrame frame = new JFrame("Test");
//                JOptionPane.showMessageDialog(frame,
//                "You must be a Subscription Custumer.");
//            } 
            
                       
//            pst4.close();
//            rs4.close(); 
                  
//            } catch(Exception e){
//                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//                System.exit(0);
//                }
                    
    

        
        
        
        }
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
        addSearchPlaylist.setVisible(false);
        Generate.setVisible(false);

        searchResults.setVisible(false);
        if(! searchArtist.isFocused()){
                playlistLabel.setVisible(true);
            searchResults.setVisible(false);
            privateLabel.setVisible(false);
            }

        
        if(! listSongs.getSelectionModel().isEmpty()){
        playlistNames.getSelectionModel().clearSelection();
        }
    }
    
    
    public void contextMenus(){
        playlistNames.setCellFactory(lv -> {
        ListCell<String> cell = new ListCell<>();
        ContextMenu contextMenu = new ContextMenu();  
        
        
        
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
        addSearchPlaylist.setVisible(false);
        Generate.setVisible(false);
        
        if(playlistNames.getSelectionModel().getSelectedIndex() == 0 ){
            privateLabel.setVisible(false);
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
                if(search.getArtist().toLowerCase().contains(lowerCaseFilter)){
                return true;
                }//else if(search.getTrackName().toLowerCase().contains(newValue)){
                //return true;
                //} else if(search.getGenre().toLowerCase().contains(newValue)){
                //return true;
                //}
                return false;
            });
            addSearchPlaylist.setVisible(true);
            Generate.setVisible(true);

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
                    
        contextMenu2.getItems().addAll(deleteItem2);
                       
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
            privateLabel.setText("Type: Private");
            } else if(rs6.getInt("Private")==0){
            privateLabel.setText("Type: Friend");
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
    
        

}  
    



