package src.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    public String inputLine = "";
    private Socket clientSocket;
    public void itens(){
        Var.dados.add(inputLine);
    }


    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            Var.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Var.out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while ((inputLine = Var.in.readLine()) != null) {
                System.out.println("Dados recebidos do ESP: " + inputLine);
                // Aqui você processaria os dados recebidos do ESP
                // Por exemplo, você poderia separar os valores:
                String[] sensorValues = inputLine.split(",");
                if (sensorValues.length == 2) {
                    try {
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


