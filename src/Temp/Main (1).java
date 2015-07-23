import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Alexey on 06.11.2014.
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("MainGUI.fxml"));
        primaryStage.setTitle("Converter 2.0");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }

    public static void main(String[] args) throws Exception {

        //DataBase dataBase = new DataBase();

        //XmlSAXParser parser = new XmlSAXParser(dataBase);
        //parser.parse();
        launch(args);
    }
}