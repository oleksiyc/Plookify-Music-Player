package account;

/**
 * @author Marjana
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        //classname.main(args); (multiple main methods)
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try{
        Parent root = FXMLLoader.load(getClass().getResource("/account/RegisterFXML.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Plookify");
        primaryStage.setScene(scene);
        primaryStage.show();} 
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
