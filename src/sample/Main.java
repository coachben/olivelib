package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/dashboard.fxml"));

        Scene scene = new Scene(root, 900, 680);
        scene.getStylesheets().add(Main.class.getResource( "/css/bootstrap3.css").toExternalForm());

        primaryStage.setTitle("Olive Branch Book Library Software");
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
