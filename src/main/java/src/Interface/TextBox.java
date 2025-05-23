package src.Interface;
import processing.core.PApplet;
import processing.core.PFont;

public class TextBox extends PApplet  {
    public String text = "";

    String texto = "";
    boolean mouseOver = false;
    int textColor = 255;
    int x;
    int y;
    int largura;
    int altura;
    int tamanhoTexto;
    PApplet parent;
    public int offset = -10;
    int offsetX = -4;
    public TextBox(PApplet p, int largura, int altura, int x, int y, int tamanhoTexto, String texto){
        this.parent = p;
        this.largura = largura;
        this.altura = altura;
        this.x = x;
        this.y = y;
        this.tamanhoTexto = tamanhoTexto;
        this.texto = texto;
    }

    public boolean isMouseOver() {
        return parent.mouseX > x && parent.mouseX < (x + largura) && parent.mouseY > y && parent.mouseY < (y + altura);
    }



    public void display() { // Renomeado para deixar claro que desenha
        parent.stroke(255);
        parent.fill(0);
        parent.rect(x, y, largura, altura, altura / 10);
        parent.fill(textColor);
        parent.textSize(tamanhoTexto);// Use PApplet.constante
        parent.text(text, x-offsetX, y+(altura/2)+3);
        parent.text(texto, x+(offsetX), y+(offset));
    }
}
