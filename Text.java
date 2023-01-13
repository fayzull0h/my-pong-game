import java.awt.*;

public class Text {
    public String text;
    public Font font;
    public int x, y;

    public Text(String text, Font font, int x, int y) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
    }

    public Text(int text, Font font, int x, int y) {
        this.text = "" + text;
        this.font = font;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(font);
        g2.drawString(text, x, y);
    }
}
