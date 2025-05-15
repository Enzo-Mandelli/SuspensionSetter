
package src.Interface;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import src.server.ServerCreate;
import src.server.Var;

public class Main extends PApplet {

    int largura = 960;
    byte posEsp32 = Var.posEsp32; //o index na arraylist do esp32 desejado
    int altura = 600;
    int alturaImagem = 158;
    int larguraImagem = 432;
    int pXTextBox[] = {462-240, 740-240};
    int pyTextBox[] = {194, 384};
    int larguraTxt = 40;
    int alturaTxt = 20;
    int posXEncoder = 516-240;
    int posYEncoder = 350;
    byte indexCaixa = 0;
    PImage imgCentral;
    PImage defaultImage;
    PImage fr;
    PImage fl;
    PImage rl;
    PImage rr;
    String debuga = "x: " + mouseX + " y: " + mouseY;
    int[] backgroundColor = {0,0,0};
    int[] textColor = {255,255,255};
    public static ServerCreate servidor = new ServerCreate();
    TelaInicializando unecessaryLoading = new TelaInicializando(this, backgroundColor, textColor, altura,largura);
    public static Thread thread = new Thread(servidor);


    public static void main(String[] args) {
        PApplet.main("src.Interface.Main");
        thread.run();
    }

    TextBox frTxt;//1
    TextBox flTxt;//2
    TextBox rrTxt;//3
    TextBox rlTxt;//4
    public PFont fonte;
    public TextBox encoderL;
    public TextBox encoderR;
    public String acelerometro = "";
    public TextBox alturaFR;
    public TextBox alturaFL;
    public TextBox alturaRR;
    public TextBox alturaRL;
    public String alturaFMeio = "";
    public String alturaRMeio = "";
    public String sensorDistFL = "";
    public String sensorDistFr = "";
    public String sensorDistRL = "";
    public String sensorDistRR = "";
    int[] corSist = {0,255,0};
    String sistemaOn = "Sistema ON";

    @Override
    public void settings() {
        size(largura, altura);
        frTxt = new TextBox(this,larguraTxt, alturaTxt, pXTextBox[0], pyTextBox[0], 16, "angulo");
        flTxt = new TextBox(this,larguraTxt, alturaTxt, pXTextBox[0], pyTextBox[1], 16, "angulo");
        rrTxt = new TextBox(this,larguraTxt, alturaTxt, pXTextBox[1], pyTextBox[0], 16, "angulo");
        rlTxt = new TextBox(this,larguraTxt, alturaTxt, pXTextBox[1], pyTextBox[1], 16, "angulo");
        encoderL = new TextBox(this,larguraTxt, alturaTxt, posXEncoder, posYEncoder, 16, "encoder");
        encoderR = new TextBox(this,larguraTxt, alturaTxt, posXEncoder, posYEncoder-120, 16, "encoder");
        alturaFR = new TextBox(this,larguraTxt, alturaTxt, 320-240, posYEncoder-120, 16, "Altura");
        alturaFL = new TextBox(this,larguraTxt, alturaTxt, 320-240, posYEncoder, 16, "Altura");
        alturaRR = new TextBox(this,larguraTxt, alturaTxt, 820-240, posYEncoder-120, 16, "Altura");
        alturaRL = new TextBox(this,larguraTxt, alturaTxt, 820-240, posYEncoder, 16, "Altura");
        flTxt.offset = (flTxt.offset*-1)+alturaTxt+10;
        rlTxt.offset = (rlTxt.offset*-1)+alturaTxt+10;
        encoderR.offset = (encoderR.offset*-1) + alturaTxt + 10;
        alturaFL.offset = (alturaFL.offset*-1) + alturaTxt+10;
        alturaRL.offset = (alturaRL.offset*-1) + alturaTxt+10;
    }



    @Override
    public void setup() {
        rr = loadImage("src/main/java/src/Interface/resources/RL.png");
        fr = loadImage("src/main/java/src/Interface/resources/FL.png");
        fl = loadImage("src/main/java/src/Interface/resources/FR.png");
        rl = loadImage("src/main/java/src/Interface/resources/RR.png");
        imgCentral = defaultImage = loadImage("src/main/java/src/Interface/resources/default.png");
        fonte = createFont("src/main/java/src/Interface/resources/fonteUltrakill.ttf", 16);
    }

    @Override
    public void draw() {
        textFont(fonte);
        if(!unecessaryLoading.concluido){
            unecessaryLoading.display();
            if(!unecessaryLoading.check1 && unecessaryLoading.conectado)unecessaryLoading.printaPontosCPU();
            if(unecessaryLoading.check1)unecessaryLoading.printaPontosGPU();
        }else {
            updateDados();

            background(0, 0, 0);
            background(0, 0, 0);
            image(imgCentral, (((largura - 240) / 2) - (larguraImagem / 2)), ((altura / 2) - (alturaImagem / 2)));
            display();
            if (!mouseOver()) gerenciadorImagens(indexCaixa);
            fill(corSist[0], corSist[1], corSist[2]);
            textSize(50);
            text(sistemaOn, (largura / 2) - 400, 80);
            fill(0, 0, 0);
            stroke(255);
            rect(960 - 240, 0, largura, altura);
            rect(960 - 240, 40, largura, altura);
            textSize(20);
            fill(255);
            text("Dados adicionais", 965 - 240, 35);
            textSize(16);
            text("altura media frente: " + alturaFMeio, 965 - 240, 65);
            text("altura media atras: " + alturaRMeio, 965 - 240, 95);
            text("Acelerometro: " + acelerometro, 965 - 240, 120);
            text("sensor distancia fr: " + sensorDistFr + "mm", 965 - 240, 145);
            text("sensor distancia rr: " + sensorDistRR + "mm", 965 - 240, 170);
            text("sensor distancia fl: " + sensorDistFL + "mm", 965 - 240, 195);
            text("sensor distancia rl: " + sensorDistRL + "mm", 965 - 240, 220);

        }

    }
    public void onOff(boolean on){
        if (on) {
            corSist[0] = 0;
            corSist[1] = 255;
            corSist[2] = 0;
            sistemaOn = "Sistema ON";
        }else{
            corSist[0] = 255;
            corSist[1] = 0;
            corSist[2] = 0;
            sistemaOn = "Sistema OFF";
        }
    }

    void updateDados(){
        if(Var.dados.size() > 0) {
            String[] itens = Var.dados.get(posEsp32).split(",");
            encoderL.text = itens[0];
            encoderR.text = itens[1];
            acelerometro = itens[7];
            alturaFR.text = itens[2];
            alturaFL.text = itens[3];
            alturaRR.text = itens[4];
            alturaRL.text = itens[5];
            String sensorDistFL = itens[8];
            String sensorDistFr = itens[9];
            String sensorDistRL = itens[10];
            String sensorDistRR = itens[11];

            try {
                alturaFMeio = String.valueOf((Integer.valueOf(itens[2]) + Integer.valueOf(itens[3]) / 2));
                alturaRMeio = String.valueOf((Integer.valueOf(itens[4]) + Integer.valueOf(itens[5]) / 2));
            } catch (Exception e) {
                alturaFMeio = "NaN";
                alturaRMeio = "NaN";
            }
        }


        //Ordem texto
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

    public boolean checaEnviaDados(){
        boolean valido = true;
        try{
            int aux = Integer.valueOf(alturaFR.text);
            aux = Integer.valueOf(alturaFL.text);
            aux = Integer.valueOf(alturaRR.text );
            aux = Integer.valueOf(alturaRL.text);
        } catch (Exception e) {
            valido = false;
        }
        return valido;
    }

    void display(){
        frTxt.display();
        flTxt.display();
        rrTxt.display();
        rlTxt.display();
        encoderL.display();
        encoderR.display();
        alturaFR.display();
        alturaRR.display();
        alturaFL.display();
        alturaRL.display();
    }

    boolean mouseOver(){
        boolean estaSobCaixa = true;
        int indexCaixa = this.indexCaixa;
        if(frTxt.isMouseOver()){
            indexCaixa = 1;
        }else if(flTxt.isMouseOver()){
            indexCaixa = 2;
        }else if(rlTxt.isMouseOver()){
            indexCaixa = 3;
        }else if(rrTxt.isMouseOver()) {
            indexCaixa = 4;
        }else{
            indexCaixa = 0;
            estaSobCaixa = false;
        }
        gerenciadorImagens(indexCaixa);
        return estaSobCaixa;
    }

    void gerenciadorImagens(int indexCaixa){
        switch (indexCaixa){
            case 1:
                imgCentral = fr;
                break;
            case 2:
                imgCentral = fl;
                break;
            case 3:
                imgCentral = rl;
                break;
            case 4:
                imgCentral = rr;
                break;
            default:
                imgCentral = defaultImage;
        }
    }


    @Override
    public void mouseClicked() {
        if(frTxt.isMouseOver()){
            indexCaixa = 1;
        }else if(flTxt.isMouseOver()){
            indexCaixa = 2;
        }else if(rlTxt.isMouseOver()){
            indexCaixa = 3;
        }else if(rrTxt.isMouseOver()){
            indexCaixa = 4;
        }else{
            indexCaixa = 0;
        }

    }

    void debug(){
        debuga = "x: " + mouseX + " y: " + mouseY;
        textSize(20);
        fill(255);
        text(debuga, largura/4, altura/4);
        line(0,mouseY,largura, mouseY);
        line(mouseX,0,mouseX, altura);
    }


    public void keyPressed() { // metodo que detecta digitação
        String text = "";
        switch (indexCaixa){
            case 1:
                text = frTxt.text;
                break;
            case 2:
                text = flTxt.text;
                break;
            case 3:
                text = rlTxt.text;
                break;
            case 4:
                text = rrTxt.text;
                break;
            default:
                text = "";
                break;
        }
        if (key == BACKSPACE) {
            if (text.length() > 0) {
                //aqui uma forma desnecessariamente complicada de apagar o ultimo caracter escrito
                String[] palavraFatiada = text.split(""); // divide a String em letras
                String palavraRemontada = "";
                for(int i = 0; i < palavraFatiada.length-1; i++){ // ignora a ultima letra assim a deletando da frase
                    palavraRemontada = palavraRemontada + palavraFatiada[i];
                }
                text = palavraRemontada;
            }
        }else if (key != ESC) {
            text = text + key;
        }else if(key == ESC){
            indexCaixa = 0;
            gerenciadorImagens(indexCaixa);
        }
        switch (indexCaixa){
            case 1:
                frTxt.text = text;
                break;
            case 2:
                 flTxt.text = text;
                break;
            case 3:
                rlTxt.text = text;
                break;
            case 4:
                rrTxt.text = text;
                break;

        }
    }

}