/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import social.*;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import org.sqlite.SQLiteConfig;
import javafx.stage.Modality;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author fahad
 */
public class FriendRequestWindowController implements Initializable {
    
    Connection conn;
    PreparedStatement rpst = null;    
    Statement rcstmt = null;
    ResultSet rrs = null;
    
    PreparedStatement rpst1 = null;    
    Statement rcstmt1 = null;
    ResultSet rrs1 = null;
    
    PreparedStatement rpst2 = null;    
    Statement rcstmt2 = null;
    ResultSet rrs2 = null;
    
    PreparedStatement rpst3 = null;    
    Statement rcstmt3 = null;
    ResultSet rrs3 = null;
    
    PreparedStatement rpst4 = null;    
    Statement rcstmt4 = null;
    ResultSet rrs4 = null;
    
    @FXML
    private TableView FriendRequesttableView;
    @FXML
    private TableColumn senderid;
    @FXML
    private TableColumn username;
    @FXML
    private TableColumn message;
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
        message.setCellValueFactory(new PropertyValueFactory<FriendTableData, String>("Message"));
        
        

        try {
            SQLiteConfig config = new SQLiteConfig();                       
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");                        
            rcstmt = conn.createStatement();
            rrs = conn.createStatement().executeQuery("SELECT Customer.CustomerID, Customer.Username, FriendRelationship.message FROM Customer INNER JOIN FriendRelationship ON Customer.CustomerID=FriendRelationship.senderID WHERE status = 1 AND recieverID="+ Controller.id);
                       
            while (rrs.next()) {
                FriendTableData nt = new FriendTableData();
                nt.ID.set(rrs.getString("CustomerID"));
                nt.Username.set(rrs.getString("Username"));
                nt.Message.set(rrs.getString("message"));
                
                dataNotes.add(nt);                
            }            
            FriendRequesttableView.setItems(dataNotes);
            rcstmt.close();
            rrs.close();      
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        
        
        
    }
    
    @FXML
    private Button DeclineReq;
    
    @FXML
    public void DeclineRequestButton(ActionEvent event){
        
       
        try {
            FriendRequesttableView.getItems().size();
            FriendRequesttableView.getSelectionModel();
            FriendTableData selectedid2 = (FriendTableData) FriendRequesttableView.getSelectionModel().getSelectedItem();
            
            String rquery3 = "UPDATE FriendRelationship SET status= 3 WHERE senderID='"+selectedid2.getID()+"' AND recieverID="+ Controller.id ;                       
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");                        
            PreparedStatement rpst3 = conn.prepareStatement(rquery3);
            rpst3.executeUpdate();        
            rpst3.close();
              
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }  
        
        ObservableList<FriendTableData> productSelected, allItems;
        allItems = FriendRequesttableView.getItems();
        productSelected = FriendRequesttableView.getSelectionModel().getSelectedItems();
        productSelected.forEach(allItems::remove);
    }
        
        @FXML
    private Button AcceptReq;
    
    public void AcceptRequestButton(ActionEvent event){
                
                Component frame1 = null;
                //Message confirming
                JOptionPane.showMessageDialog(frame1,
                "You Just Added a Friend",
                "New Friend",
                JOptionPane.PLAIN_MESSAGE);
        
        try {
            
            FriendRequesttableView.getItems().size();
            FriendRequesttableView.getSelectionModel();
            FriendTableData selectedid2 = (FriendTableData) FriendRequesttableView.getSelectionModel().getSelectedItem();
            
            String rquery1 = "UPDATE FriendRelationship SET status= 2 WHERE senderID='"+selectedid2.getID()+"' AND recieverID="+ Controller.id ;                       
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");                        
            PreparedStatement rpst1 = conn.prepareStatement(rquery1);
            rpst1.executeUpdate();        
            rpst1.close();
             
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        } 
        
        try{
            String rquery2 = "INSERT INTO FriendList (FriendListID, CustomerID, SubscribedID) VALUES (?, ?, ?)";                       
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");                        
            rpst2 = conn.prepareStatement(rquery2);
                      
            FriendRequesttableView.getItems().size();
            FriendRequesttableView.getSelectionModel();
            FriendTableData selectedid3 = (FriendTableData) FriendRequesttableView.getSelectionModel().getSelectedItem();
            
            rpst2.setString(1,selectedid3.getID());
            rpst2.setString(2, Controller.id);
            rpst2.setString(3,selectedid3.getID());
            rpst2.addBatch();
            
            rpst2.executeBatch();
            rpst2.close();
        
            } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        } 
        
        try{
            String rquery4 = "INSERT INTO FriendList (FriendListID, CustomerID, SubscribedID) VALUES (?, ?, ?)";                       
            conn = DriverManager.getConnection("jdbc:sqlite:db.sqlite");                        
            rpst4 = conn.prepareStatement(rquery4);
                      
            FriendRequesttableView.getItems().size();
            FriendRequesttableView.getSelectionModel();
            FriendTableData selectedid3 = (FriendTableData) FriendRequesttableView.getSelectionModel().getSelectedItem();
            
            rpst4.setString(1,Controller.id);
            rpst4.setString(2, selectedid3.getID());
            rpst4.setString(3,Controller.id);
            rpst4.addBatch();
            
            rpst4.executeBatch();
            rpst4.close();
        
            } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        } 

                ObservableList<FriendTableData> productSelected2, allItems2;
                allItems2 = FriendRequesttableView.getItems();
                productSelected2 = FriendRequesttableView.getSelectionModel().getSelectedItems();
                productSelected2.forEach(allItems2::remove);
                
                
                
    }
    
    
    
          
      
     
}

