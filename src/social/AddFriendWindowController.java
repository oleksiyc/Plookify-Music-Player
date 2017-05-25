/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social;

import common.Controller;
import java.awt.Component;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.sqlite.SQLiteConfig;

/**
 * FXML Controller class
 *
 * @author fahad
 */
public class AddFriendWindowController implements Initializable {
    
    Connection conn;
    PreparedStatement fpst = null;    
    Statement fcstmt = null;
    ResultSet frs = null;
    
    PreparedStatement rpst1 = null;    
    Statement rcstmt1 = null;
    ResultSet rrs1 = null;
    
    @FXML
    private TableView FriendtableView;
    @FXML
    private TableColumn username;
    @FXML
    private TableColumn id;
    
    
    ObservableList <FriendTableData> dataNotes; 
   
    //final ObservableList<String> Flist= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        friendtableView();
        
    }    
    @FXML
    public void friendtableView(){
        dataNotes = FXCollections.observableArrayList();
    
        id.setCellValueFactory(new PropertyValueFactory<FriendTableData, String>("ID"));
        username.setCellValueFactory(new PropertyValueFactory<FriendTableData, String>("Username"));
        
        

        try {
            SQLiteConfig config = new SQLiteConfig();                       
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");                        
            fcstmt = conn.createStatement();
            frs = conn.createStatement().executeQuery("SELECT DISTINCT Customer.CustomerID, Username FROM Customer INNER JOIN Subscribed ON Customer.CustomerID=Subscribed.SubscribedID WHERE Paid='1' AND Discoverable='1' AND Customer.CustomerID!='"+Controller.id+"' EXCEPT SELECT Customer.CustomerID, Customer.Username FROM Customer INNER JOIN FriendRelationship ON Customer.CustomerID=FriendRelationship.recieverID INNER JOIN Subscribed ON Customer.CustomerID=Subscribed.SubscribedID WHERE Subscribed.Paid='1' AND Customer.Discoverable='1' AND Customer.CustomerID!='"+Controller.id+"' EXCEPT SELECT CustomerID,Username  FROM Customer INNER JOIN FriendRelationship ON FriendRelationship.senderID=Customer.CustomerID WHERE FriendRelationship.status='2' AND FriendRelationship.recieverID='"+Controller.id+"'");
                       
            while (frs.next()) {
                FriendTableData nt = new FriendTableData();
                nt.ID.set(frs.getString("Customer.CustomerID"));
                nt.Username.set(frs.getString("Username"));
                
                dataNotes.add(nt);                
            }            
            FriendtableView.setItems(dataNotes);
            fcstmt.close();
            frs.close();      
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        
        
    }
    
    @FXML
    private Button SendFriendRequest;
    
    /**
     *
     * @param event
     */
    @FXML
    public void SendFriendRequestButton(ActionEvent event){
        
        Component frame = null; 
        //Confirmation Message 
        JOptionPane.showMessageDialog(frame,
            "Friend Request Sent Sucessfully",
            "Adding New People",
        JOptionPane.PLAIN_MESSAGE);
       
        try {
            String rquery1 = "INSERT INTO FriendRelationship (senderID, recieverID,  status, message) VALUES (?, ?, ?, 'would like to add you as a friend' )";                       
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");                        
            rpst1 = conn.prepareStatement(rquery1);
                      
            FriendtableView.getItems().size();
            FriendtableView.getSelectionModel();
            FriendTableData selectedid2 = (FriendTableData) FriendtableView.getSelectionModel().getSelectedItem();
            
            rpst1.setString(1,Controller.id);
            rpst1.setString(2,selectedid2.getID());
            rpst1.setInt(3,1);
            rpst1.addBatch();
            
            rpst1.executeBatch();
            rpst1.close();
                      
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }  
        
                ObservableList<FriendTableData> productSelected, allItems;
                allItems = FriendtableView.getItems();
                productSelected = FriendtableView.getSelectionModel().getSelectedItems();
                productSelected.forEach(allItems::remove);
        
    }
    
   

}
