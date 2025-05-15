package src.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerCreate implements Runnable {
    public int serverPort = Var.serverPort;
    public ServerSocket socket;
    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("Servidor Java iniciado na porta " + serverPort);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Aguarda por uma conexão do cliente (ESP)
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

                // Criar uma thread para lidar com cada cliente
                Var.clientes.add(new Thread(new ClientHandler(clientSocket)));
                Var.clientes.getLast().start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}