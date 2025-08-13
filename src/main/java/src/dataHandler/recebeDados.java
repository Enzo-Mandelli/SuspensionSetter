package src.dataHandler;

import src.server.Var;

import java.io.IOException;

public class recebeDados {
    public String[] recebe(){
        String mensagem;
        String[] dadosSeparados;
        try {
            mensagem = Var.in.readLine();
            dadosSeparados = mensagem.split(",");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dadosSeparados;
    }
    public envia
}
