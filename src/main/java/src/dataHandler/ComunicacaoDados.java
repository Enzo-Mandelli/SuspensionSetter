package src.dataHandler;

import src.server.Var;

import java.io.IOException;

public class ComunicacaoDados {
    public static String[] recebe(){
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
    public static void enviaDados(String[] mensagem){
        String mensagemUnicaLinha = "";
        for (String s : mensagem) {
            mensagemUnicaLinha += s;
        }
        Var.out.println(mensagemUnicaLinha);
    }

}
