import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.Image;
import java.util.Random;

public class DragonFireball extends Thread {

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
    private SoundManager soundManager;
    private HeartPanel heartPanel;

    private Image fireImage;
    Random random;

    public DragonFireball(GamePanel p, int xPos, int yPos, Wizard wizard, HeartPanel heartPanel) {
        panel = p;
        dimension = panel.getSize();
        backgroundColour = panel.getBackground();
        x = xPos;
        y = yPos;

        dx = 10;
        dy = 30;

        width = 50;
        height = 50;
        random = new Random();

        this.wizard = wizard;
        this.heartPanel = heartPanel;
        soundManager = SoundManager.getInstance();
        fireImage = ImageManager.loadImage("images/fireball.png");
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(fireImage, x, y, width, height, null);
    }

    public boolean move() {
        if (!panel.isVisible())
            return false;

        x = x + dx;
        y = y + dy;

        int height = panel.getHeight();

        boolean wizardCollision = collidesWithWizard();

        if (wizardCollision) {
            soundManager.playClip("fireBallHit", false);
            Troll.lives--;
            heartPanel.loseHearts();
            y = 500;
        }

        return true;
    }

    public void setLocation(int yval) {
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        x = random.nextInt(panelWidth - width);
        // y= random.nextInt(panelHeight-height);
        y = yval;
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public boolean collidesWithWizard() {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double wizardRect = wizard.getBoundingRectangle();

        return myRect.intersects(wizardRect);
    }

}