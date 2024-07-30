package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerLauncher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ServerForm.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Servidor");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
