package server;

import client.ClientHandler;
import controller.ServerFormController;
import javafx.application.Platform;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private static Server server;

    private List<ClientHandler> clients = new ArrayList<>();

    private Server() throws IOException {
        serverSocket = new ServerSocket(3001);
    }

    public static Server getInstance() throws IOException {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public void makeSocket() {
        while (!serverSocket.isClosed()) {
            try {
                socket = serverSocket.accept();
                DataInputStream tempInput = new DataInputStream(socket.getInputStream());
                String clientName = tempInput.readUTF();

                ClientHandler clientHandler = new ClientHandler(socket, clients, clientName);
                clients.add(clientHandler);

                System.out.println("Cliente aceptado: " + clientName + " " + socket.toString());
                Platform.runLater(() -> ServerFormController.receiveMessage("Nuevo cliente conectado: " + clientName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
