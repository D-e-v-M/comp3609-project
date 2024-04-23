import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.Image;

public class Fireball extends Thread {

    private GamePanel panel;
    private int x;
    private int y;
    private int width;
    private int height;

    private int dx;
    private int dy;

    private Rectangle2D.Double fireball;

    private Color backgroundColour;
    private Dimension dimension;
    private Wizard wizard;

    private Image fireImage;

    public Fireball(GamePanel p, int xPos, int yPos) {
        panel = p;
        dimension = panel.getSize();
        backgroundColour = panel.getBackground();
        x = xPos;
        y = yPos;

        dx = 10;
        dy = 30;

        width = 50;
        height = 50;

        // this.wizard = wizard;
        fireImage = ImageManager.loadImage("images/fireball.png");
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(fireImage, x, y, width, height, null);
    }

    public boolean move() {
        if (!panel.isVisible())
            return false;

        y = y - dy;

        if (y <= 0) {
            panel.shootFireball();

            y = 350;
            // x = wizard.getX();

            return false;
        }

        return true;
    }

    public void setX(int x) {
        if (y == 350 || y <= 0)
            this.x = x;
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
    }

}