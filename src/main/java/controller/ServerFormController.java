package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.Server;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ServerFormController {
    public VBox vBox;
    public ScrollPane scrollPain;
    public AnchorPane pane;

    private Server server;
    private static VBox staticVBox;

    public void initialize(){
        staticVBox = vBox;
        receiveMessage("Servidor iniciado");
        vBox.heightProperty().addListener((observableValue, oldValue, newValue) -> scrollPain.setVvalue((Double) newValue));
        new Thread(() -> {
            try {
                server = Server.getInstance();
                server.makeSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void receiveMessage(String msgFromClient){
        Platform.runLater(() -> {
            if (staticVBox != null) {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5,5,5,10));

                Text text = new Text(msgFromClient);
                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle("-fx-font-weight: bold; -fx-background-radius: 5px");
                textFlow.setPadding(new Insets(5,10,5,10));
                text.setFill(Color.color(0,0,0));

                hBox.getChildren().add(textFlow);

                staticVBox.getChildren().add(hBox);
            }
        });
    }

}
