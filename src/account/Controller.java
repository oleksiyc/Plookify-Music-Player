package account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @author Marjana
 */
public class Controller {
    
    Connection connection;
    PreparedStatement preparedStatement2=null;
    ResultSet  rs2=null;
    
    
    @FXML
    private AnchorPane registerPage;
    @FXML
    private Label label;
    @FXML
    private TextField fullname;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField address1;
    @FXML
    private TextField address2;
    @FXML
    private TextField town;
    @FXML
    private TextField postcode;
    @FXML
    private RadioButton subscribed;
    @FXML
    private RadioButton paypal;
    @FXML
    private RadioButton card;
    @FXML
    private ChoiceBox devices;
    @FXML
    private Button submit;
    @FXML
    private Button clearData;
    
    public void customerDetails(ActionEvent event){
//        if(event.getSource() == clearData){
//            label.setText("You cleared the data");
//        } else if(event.getSource() == submit){
//            label.setText("You submit the data");
//        }        
        
         if(event.getSource()== submit){
             
            String s1 = fullname.getText();
            String s2 = username.getText();
            String s3 = password.getText();
            String s4 = email.getText();
            String s5 = address1.getText();
            String s6 = address2.getText();
            String s7 = town.getText();
            String s8 = postcode.getText();
            String s9;
            if(subscribed.isSelected()){
            s9="1";
            }else{
            s9="0";
            }
            String s10 = null;
            if(paypal.isSelected()){
            s10="PayPal";
            }else if(card.isSelected()){
            s10="Card";
            }
            
             
            try{
            Class.forName("org.sqlite.JDBC");

                connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");    
                 String query9 = "insert into Customer  (FullName, Username, Password, Email, AddressLine1, AddressLine2, Town, Postcode, Subscription, PaymentType, Devices) values('"+s1+"' , '"+s2+"' , '"+s3+"' , '"+s4+"' , '"+s5+"' ,   '"+s6+"',   '"+s7+"',   '"+s8+"' , '"+s9+"' ,  '"+s10+"' ,  '1'  )";                       
                               
            Statement stmt = connection.createStatement();

                 stmt.executeUpdate(query9);
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        } else if(event.getSource()==clearData){
           username.setText("");
           password.setText("");
           fullname.setText("");
           email.setText("");
           address1.setText("");
           address2.setText("");
           town.setText("");
           postcode.setText(""); 
   
    }   
   
         
//         
//    public void logOut(ActionEvent event){
//        try{
//            ((Node)event.getSource()).getScene().getWindow().hide();
//            Stage primaryStage = new Stage();
//            FXMLLoader loader = new FXMLLoader();
//            Parent root = loader.load(getClass().getResource("LoginFXML.fxml").openStream());
//            Scene scene = new Scene(root);
//            primaryStage.setScene(scene);
//            primaryStage.setTitle("Plookify");
//            primaryStage.show();
//        }catch(Exception e){
//        }
//    }
//   
//    public void deleteAccount(ActionEvent event){
//        try {
//            Class.forName("org.sqlite.JDBC");
//            connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
//            statement = connection.createStatement();
//            String queryDelete = "DELETE * FROM customer WHERE username=...";
//            statement.executeUpdate(queryDelete);
//        } catch (SQLException ex) {   
//        
//    }
         
         
    }
}
    
    
    
    
