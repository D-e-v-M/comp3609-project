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

    private Image fireImage;
    Random random;

    public DragonFireball(GamePanel p, int xPos, int yPos) {
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

        // this.wizard = wizard;
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
        if (!panel.isVisible ()) return false;

      x = x + dx;
      y = y + dy;

      int height = panel.getHeight();
      return true;
    }

        
    
    public void setLocation(int yval) {
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        x = random.nextInt (panelWidth - width);
        //y= random.nextInt(panelHeight-height);
        y=yval;
     }



    // public int getX() {
    // return x;
    // }

    // public void shoot(Graphics2D g2) {

    // boolean isRunning = true;

    // // Fireball not rendering on screen. Need fix
    // try {
    // while (isRunning) {
    // isRunning = move();
    // draw(g2);
    // sleep(10); // increase value of sleep time to slow down ball
    // }
    // } catch (InterruptedException e) {
    // }
    // }

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