
package src.Interface;
import processing.core.PApplet;
import processing.core.PImage;
import src.Interface.imagens.TextBox;

public class Main extends PApplet {
    int largura = 1200;
    int altura = 600;
    int alturaImagem = 158;
    int larguraImagem = 432;
    int pXTextBox[] = {462, 740};
    int pyTextBox[] = {206-20, 393};
    int larguraTxt = 40;
    int alturaTxt = 20;
    byte indexCaixa = 0;
    PImage imgCentral;
    PImage defaultImage;
    PImage fr;
    PImage fl;
    PImage rl;
    PImage rr;
    String debuga = "x: " + mouseX + " y: " + mouseY;

    public static void main(String[] args) {
        PApplet.main("src.Interface.Main");
    }

    TextBox frTxt;
    TextBox flTxt;
    TextBox rrTxt;
    TextBox rlTxt;

    @Override
    public void settings() {
        size(largura, altura);
        frTxt = new TextBox(this,larguraTxt, alturaTxt, pXTextBox[0], pyTextBox[0], 16, "angulo");
        flTxt = new TextBox(this,larguraTxt, alturaTxt, pXTextBox[0], pyTextBox[1], 16, "angulo");
        rrTxt = new TextBox(this,larguraTxt, alturaTxt, pXTextBox[1], pyTextBox[0], 16, "angulo");
        rlTxt = new TextBox(this,larguraTxt, alturaTxt, pXTextBox[1], pyTextBox[1], 16, "angulo");
        flTxt.offset = (flTxt.offset*-1)+alturaTxt+10;
        rlTxt.offset = (rlTxt.offset*-1)+alturaTxt+10;
    }



    @Override
    public void setup() {
        rr = loadImage("src/main/java/src/Interface/imagens/RL.png");
        fr = loadImage("src/main/java/src/Interface/imagens/FL.png");
        fl = loadImage("src/main/java/src/Interface/imagens/FR.png");
        rl = loadImage("src/main/java/src/Interface/imagens/RR.png");
        imgCentral = defaultImage = loadImage("src/main/java/src/Interface/imagens/default.png");

    }

    @Override
    public void draw() {
        background(50,50,50);
        debug();
        image(imgCentral, ((largura/2)- (larguraImagem/2)), ((altura/2)-(alturaImagem/2)));
        frTxt.display();
        flTxt.display();
        rrTxt.display();
        rlTxt.display();
        gerenciadorImagem();

    }

    void gerenciadorImagem(){
        if(frTxt.isMouseOver()){
            imgCentral = fr;
        }else if(flTxt.isMouseOver()){
            imgCentral = fl;
        }else if(rlTxt.isMouseOver()){
            imgCentral = rl;
        }else if(rrTxt.isMouseOver()){
            imgCentral = rr;
        }else {
            imgCentral = defaultImage;
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
        if (key == BACKSPACE) {
            if (frTxt.text.length() > 0) {
                //aqui uma forma desnecessariamente complicada de apagar o ultimo caracter escrito
                String[] palavraFatiada = frTxt.text.split(""); // divide a String em letras
                String palavraRemontada = "";
                for(int i = 0; i < palavraFatiada.length-1; i++){ // ignora a ultima letra assim a deletando da frase
                    palavraRemontada = palavraRemontada + palavraFatiada[i];
                }
                frTxt.text = palavraRemontada;
            }
        }else if (key != ESC) {
            frTxt.text = frTxt.text + key;
        }
    }

}