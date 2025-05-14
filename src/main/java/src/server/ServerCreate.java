package src.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCreate implements Runnable{
    public void run() {
        int serverPort = 12345;
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("Servidor Java iniciado na porta " + serverPort);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Aguarda por uma conexão do cliente (ESP)
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

                // Criar uma thread para lidar com cada cliente
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Dados recebidos do ESP: " + inputLine);
                    // Aqui você processaria os dados recebidos do ESP
                    // Por exemplo, você poderia separar os valores:
                    String[] sensorValues = inputLine.split(",");
                    if (sensorValues.length == 2) {
                        try {
                            float value1 = Float.parseFloat(sensorValues[0]);
                            float value2 = Float.parseFloat(sensorValues[1]);
                            // Faça algo com os valores recebidos (armazenar, exibir, etc.)
                            System.out.println("Valor 1: " + value1 + ", Valor 2: " + value2);
                        } catch (NumberFormatException e) {
                            System.err.println("Erro ao converter valores: " + inputLine);
                        }
                    }
                }
                System.out.println("Cliente desconectado: " + clientSocket.getInetAddress().getHostAddress());
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

