import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.Image;

public class Fireball extends Thread {

    private JPanel panel;
    private int x;
    private int y;
    private int width;
    private int height;

    private int dx;
    private int dy;

    private Rectangle2D.Double fireball;

    private Color backgroundColour;
    private Dimension dimension;

    private Image fireImage;

    public Fireball(JPanel p, int xPos, int yPos) {
        panel = p;
        dimension = panel.getSize();
        backgroundColour = panel.getBackground();
        x = xPos;
        y = yPos;

        dx = 10;
        dy = 10;

        width = 25;
        height = 25;

        fireImage = ImageManager.loadImage("images/fireball.png");
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(fireImage, x, y, width, height, null);
    }

    // public void erase() {
    // Graphics g = panel.getGraphics();
    // Graphics2D g2 = (Graphics2D) g;

    // // erase fireball by drawing a rectangle on top of it with the background
    // colour

    // g2.setColor(backgroundColour);
    // g2.fill(new Rectangle2D.Double(x, y, width, height));

    // g.dispose();
    // }

    /*
     * public void move (int direction) {
     * 
     * if (!panel.isVisible ()) return;
     * 
     * if (direction == 1) {
     * x = x - dx; // move left
     * batImage = batLeftImage;
     * }
     * else
     * if (direction == 2) {
     * x = x + dx; // move right
     * batImage = batRightImage;
     * }
     * }
     */

    public boolean move() {
        if (!panel.isVisible())
            return false;

        y = y - dy;

        if (y < 0) {
            y = y - 20;
            x = x + 18;

            return false;
        }

        return true;
    }

    public void shoot(Graphics2D g2) {

        boolean isRunning = true;

        // Fireball not rendering on screen. Need fix
        try {
            while (isRunning) {
                isRunning = move();
                draw(g2);
                sleep(10); // increase value of sleep time to slow down ball
            }
        } catch (InterruptedException e) {
        }
    }

    /*
     * public void drawBullet() {
     * Graphics g = panel.getGraphics();
     * Graphics2D g2 = (Graphics2D) g;
     * 
     * bulletX = x + 18; // getting current position of cannon
     * 
     * // Draw the bullet
     * 
     * bullet = new Line2D.Double(bulletX, bulletY, bulletX, bulletY - 10);
     * g2.setColor(Color.RED);
     * g2.draw(bullet);
     * 
     * g.dispose();
     * }
     */

    // public void erase() {
    // Graphics g = panel.getGraphics();
    // Graphics2D g2 = (Graphics2D) g;

    // // erase bullet by drawing a line over it
    // g2.setColor(Color.BLACK);
    // g2.draw(new Line2D.Double(bulletX, bulletY, bulletX, bulletY - 10));

    // g.dispose();
    // }

    /*
     * public boolean isOnShip(int x, int y) {
     * if (ship == null)
     * return false;
     * 
     * return ship.contains(x, y);
     * }
     */

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    /*
     * public Line2D.Double getBoundingBullet() {
     * return new Line2D.Double(bulletX - 10, bulletY, bulletX + 10, bulletY - 10);
     * }
     */
    // public Graphics2D getShipContext() {
    // return shipContext;
    // }

}