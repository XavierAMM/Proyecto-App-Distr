package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public JFXTextField txtName;

    public void initialize(){

    }

    public void logInButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (!txtName.getText().isEmpty()&&txtName.getText().matches("[A-Za-z0-9]+")){
            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ClientForm.fxml"));

            ClientFormController controller = new ClientFormController();
            controller.setClientName(txtName.getText());
            fxmlLoader.setController(controller);

            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle(txtName.getText());
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.setOnCloseRequest(windowEvent -> {
                try {
                    controller.shutdown();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            primaryStage.show();
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            txtName.clear();
        }else{
            new Alert(Alert.AlertType.ERROR, "No se ha ingresado un usuario").show();
        }
    }
}
