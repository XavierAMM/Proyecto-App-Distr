package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.Socket;


public class ClientFormController {
    public AnchorPane pane;
    public ScrollPane scrollPain;
    public VBox vBox;
    public JFXTextField txtMsg;
    public Text txtLabel;
    public JFXButton emojiButton;

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String clientName = "Client";

    public void initialize(){
        txtLabel.setText(clientName);

        new Thread(() -> {
            try {
                socket = new Socket("localhost", 3001);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                dataOutputStream.writeUTF(clientName); 
                dataOutputStream.flush();

                System.out.println("Servidor: Cliente conectado");
                dataOutputStream.writeUTF("Servidor: "+clientName + " ha entrado en el chat!");
                dataOutputStream.flush();

                while (socket.isConnected()) {
                    String receivingMsg = dataInputStream.readUTF();
                    receiveMessage(receivingMsg, ClientFormController.this.vBox);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void shutdown() throws IOException {
        try {
            dataOutputStream.writeUTF("Servidor: "+clientName + " ha dejado el chat!");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void txtMsgOnAction(ActionEvent actionEvent) {
        sendButtonOnAction(actionEvent);
    }

    public void sendButtonOnAction(ActionEvent actionEvent) {
        sendMsg(txtMsg.getText());
    }

    private void sendMsg(String msgToSend) {
        if (!msgToSend.isEmpty()){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));

            HBox hBoxName = new HBox();
            hBoxName.setAlignment(Pos.CENTER_RIGHT);
            Text textName = new Text(" Tú" + " dices:");
            TextFlow textFlowName = new TextFlow(textName);
            hBoxName.getChildren().add(textFlowName);

            Text text = new Text(msgToSend);
            text.setStyle("-fx-font-size: 14");
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-background-color: #D3D3D3; -fx-background-radius: 5px");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(0, 0, 0));

            hBox.getChildren().add(textFlow);

            vBox.getChildren().add(hBoxName);
            vBox.getChildren().add(hBox);


            try {
                dataOutputStream.writeUTF(clientName + "-" + msgToSend);
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            txtMsg.clear();

        }
    }

    public static void receiveMessage(String msg, VBox vBox) throws IOException {

        String name = msg.split("-")[0];
        String msgFromServer = msg.split("-")[1];

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        HBox hBoxName = new HBox();
        hBoxName.setAlignment(Pos.CENTER_LEFT);
        Text textName = new Text(" " + name + " dice:");
        TextFlow textFlowName = new TextFlow(textName);
        hBoxName.getChildren().add(textFlowName);

        Text text = new Text(msgFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: #ADD8E6; -fx-background-radius: 5px");
        textFlow.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.color(0,0,0));

        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBoxName);
                vBox.getChildren().add(hBox);
            }
        });
    }
    public void setClientName(String name) {
        clientName = name;
    }
}
