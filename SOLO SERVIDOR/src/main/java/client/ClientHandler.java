package client;

import controller.ServerFormController;
import javafx.application.Platform;
import server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler {
    private Socket socket;
    private List<ClientHandler> clients;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String clientName;

    public ClientHandler(Socket socket, List<ClientHandler> clients, String clientName) { // Ajustar constructor
        try {
            this.socket = socket;
            this.clients = clients;
            this.clientName = clientName;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                while (socket.isConnected()) {
                    String msg = dataInputStream.readUTF();
                    if (msg.startsWith("Servidor:"))
                        Platform.runLater(() -> ServerFormController.receiveMessage(msg.substring(9))); // Remover el prefijo
                    else {
                        for (ClientHandler client : clients) {
                            if (client != this) {
                                client.dataOutputStream.writeUTF(msg);
                                client.dataOutputStream.flush();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
