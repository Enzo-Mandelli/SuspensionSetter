package src.server;

import java.io.IOException;

public class dataFilter {
    public static boolean encoderR;
    public static boolean encoderL;
    public static String alturaFR;
    public static String alturaFL;
    public static String alturaRR;
    public static String alturaRL;
    public static String acelerometro;
    public static String[] parametros = new String[13];

    public void preencheArray(){
        String text = "";
        try {
            text = Var.in.readLine();
            dataFilter.parametros = text.split(",");
        } catch (Exception e) {
            System.out.println("erro no preenchimento da array");
        }
    }

    public void setParametros(){
        for(int i = 0; i < parametros.length; i++){
            try{
                switch (i) {
                    case 0:
                        if (parametros[i].equals("true")) {
                            encoderR = true;
                        } else {
                            encoderR = false;
                        }
                        break;
                    case 1:
                        if (parametros[i].equals("true")) {
                            encoderL = true;
                        } else {
                            encoderL = false;
                        }
                        break;
                    case 2:
                        alturaFL = parametros[i];
                        break;
                    case 3:
                        alturaFR = parametros[i];
                        break;
                    case 4:
                        alturaRR = parametros[i];
                        break;
                    case 5:
                        alturaRL = parametros[i];
                        break;
                }
            }catch (Exception e){
                System.out.println(i);
            }
        }
    }

            /*
        0 EncoderR
        1 EncoderL
        2 AlturaFR
        3 AlturaFL
        4 AlturaRR
        5 AlturaRL
        6 Acelerometro
        8 distFL
        9 distFR
        10 distRl
        11 distRR
         */

}