package common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
        
    @FXML 
    private TextField username;
    @FXML 
    private PasswordField password;
    
    public static String id;
  
    
    @FXML
    private void loginHandling(ActionEvent event) throws IOException {
            
        try{
                Class.forName("org.sqlite.JDBC");
                Connection conn2 = DriverManager.getConnection("jdbc:sqlite:db.sqlite");

                String query11 = "SELECT CustomerID FROM Customer where Username = '"+ username.getText() +"' ";
                PreparedStatement pst8 = conn2.prepareStatement(query11);
                ResultSet rs8 = pst8.executeQuery();

                while(rs8.next()){
                    id = rs8.getString("CustomerID");
                    
                }

                pst8.close();
                rs8.close();

            }catch(Exception e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        
            if (isValid())
            {
                
                //System.out.println(id);
                
                 try{
                     //User userID= new User(id);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML.fxml"));
            
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Plookify");
            stage.setScene(new Scene(root1));  
            ((Node)(event.getSource())).getScene().getWindow().hide();
            stage.show();
          }catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
                 
            }
            else
            {
                System.out.println("Incorrect Username/Password");
            }
    }
    
    private boolean isValid()
    {
        boolean let_in = false;

        Connection c = null;
        Statement stmt = null;
        try {
            
            c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            c.setAutoCommit(false);
            
            stmt = c.createStatement();
            
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Customer WHERE Username= " + "'" + username.getText() + "'" 
            + " AND Password= " + "'" + password.getText() + "'");
            
            while ( rs.next() ) {
                 if (rs.getString("username") != null && rs.getString("password") != null) { 
                    rs.getString("username");
                    rs.getString("password");
                     let_in = true;
                 }  
            }
            rs.close();
            stmt.close();
            c.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            return let_in;
        
    }
    
    public void signup(ActionEvent event){
        try{
                   
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/account/RegisterFXML.fxml"));
            
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Plookify");
            stage.setScene(new Scene(root1));  

            stage.show();
          }catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
    }
    
}