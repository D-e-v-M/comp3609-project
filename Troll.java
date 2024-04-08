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

public class Troll {

   private JPanel panel;

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
   private SoundManager soundManager;
   private Image trollImage;

   public Troll(JPanel p, int xPos, int yPos, Wizard wizard, Fireball fireball) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground();

      width = 50;
      height = 45;

      random = new Random();

      x = xPos;
      y = yPos;

      setLocation();

      dx = 0; // no movement along x-axis
      dy = 5; // would like the troll to drop down

      this.wizard = wizard;
      this.fireball = fireball;

      trollImage = ImageManager.loadImage("images/troll.png");
      soundManager = SoundManager.getInstance();
   }

   public void setLocation() {
      int panelWidth = panel.getWidth();
      x = random.nextInt(panelWidth - width);
      y = 10;
   }

   public void draw(Graphics2D g2) {

      g2.drawImage(trollImage, x, y, width, height, null);

   }

   public void move() {

      if (!panel.isVisible())
         return;

      x = x + dx;
      y = y + dy;

      int height = panel.getHeight();
      boolean collision = collidesWithWizard();

      if (collision)
         soundManager.playClip("hit", false);

      if (collision) {
         setLocation();
      }

      if (y > height) {
         setLocation();
         soundManager.playClip("appear", false);
         dy = dy + 1; // speed up troll when it is re-generated at top
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