import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.Image;

public class Goblin {

    private JPanel panel;
    private HeartPanel heartPanel;

    private int x;
    private int y;

    private int width;
    private int height;

    private int originalX;
    private int originalY;

    private int dx; // increment to move along x-axis
    private int dy; // increment to move along y-axis

    private Color backgroundColour;
    private Dimension dimension;

    private Random random;

    private Wizard wizard;
    private Fireball fireball;
    private SpikeManager spikeManager;

    private SoundManager soundManager;
    private Image goblinImage;
    private int count = 0;

    public Goblin(JPanel p, int xPos, int yPos, Wizard wizard, Fireball fireball, HeartPanel heartPanel,
            SpikeManager spikeManager) {
        panel = p;
        this.heartPanel = heartPanel;
        dimension = panel.getSize();
        backgroundColour = panel.getBackground();

        width = 50;
        height = 45;

        random = new Random();

        x = xPos;
        y = yPos;

        setLocation();

        dx = 5;
        dy = 5;

        this.wizard = wizard;
        this.fireball = fireball;
        this.spikeManager = spikeManager;

        goblinImage = ImageManager.loadImage("images/goblin.png");
        soundManager = SoundManager.getInstance();
    }

    public void setLocation() {
        int panelWidth = panel.getWidth();
        x = random.nextInt(panelWidth - width);
        y = 10;
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(goblinImage, x, y, width, height, null);

    }

    public void move() {
        if (!panel.isVisible())
            return;

        x = x + dx;
        y = y + dy;
        if (x < 0) {
            x = 0;
            dx = 5;
        }

        if (x + width > dimension.width) {
            x = dimension.width - width;
            dx = -5;
        }
        if (y + height > dimension.height) {
            y = dimension.height - height;
            dy = -5;
        }

        if (y < 0) {
            y = 0;
            dy = 5;
        }

        boolean wizardCollision = collidesWithWizard();
        boolean fireCollision = collidesWithFireball();

        if (wizardCollision) {
            soundManager.playClip("goblinHit", false);
            Troll.lives--;
            heartPanel.loseHearts();
            setLocation(); // changing position
            // panel.addPoints(20);

            // soundManager.playClip("collection", false);

            // count++;

        }

        if (fireCollision) {
            soundManager.playClip("fireballHit", false);
            soundManager.playClip("goblinDeath", false);
            Troll.points++;
            setLocation();
        }

    }

    /*
     * public boolean isOnTroll (int x, int y) {
     * if (head == null)
     * return false;
     * 
     * return head.contains(x, y);
     * }
     */

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public boolean collidesWithWizard() {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double wizardRect = wizard.getBoundingRectangle();

        return myRect.intersects(wizardRect);
    }

    public boolean collidesWithFireball() {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double fireRect = fireball.getBoundingRectangle();

        return myRect.intersects(fireRect);
    }

}
