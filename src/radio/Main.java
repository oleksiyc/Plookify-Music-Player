/* Author: Rakshindh


*/

package radio;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        //classname.main(args); (multiple main methods)
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        primaryStage.setTitle("Plookify");
        primaryStage.getIcons().add(new Image("file:/Users/Gowthaman/Desktop/NBProjects/SE8/Plookify/src/common/icon.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}


