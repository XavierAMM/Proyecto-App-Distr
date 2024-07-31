package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class ServerLauncher extends Application {

    public static void main(String[] args) {
        // Imprimir todas las variables de entorno
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.println(envName + ": " + env.get(envName));
        }

        // Añadir un log para JAVA_HOME
        System.out.println("JAVA_HOME: " + System.getenv("JAVA_HOME"));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ServerForm.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Servidor");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
