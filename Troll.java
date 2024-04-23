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

   public static int lives;
   public static int points;

   private Random random;

   private Wizard wizard;
   private Fireball fireball;
   private SpikeManager spikeManager;

   private SoundManager soundManager;
   private Image trollImage;

   public Troll(JPanel p, int xPos, int yPos, Wizard wizard, Fireball fireball, HeartPanel heartPanel,
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
      setLivesAndPoints();

      dx = 2;
      dy = 10; // would like the troll to drop down

      this.wizard = wizard;
      this.fireball = fireball;
      this.spikeManager = spikeManager;

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

      y = y + dy;

      int height = panel.getHeight();

      if (wizard.getX() > x)
         x = x + dx;
      if (wizard.getX() < x)
         x = x - dx;

      boolean wizardCollision = collidesWithWizard();
      boolean fireCollision = collidesWithFireball();
      boolean spikeCollision;

      if (wizardCollision) {
         lives--;
         heartPanel.loseHearts();

         if (lives > 0)
            soundManager.playClip("trollHit", false);

         setLocation();
      }

      if (fireCollision) {
         soundManager.playClip("trollDeath", false);
         soundManager.playClip("fireballHit", false);
         points++;
         setLocation();
      }

      for (Spike spike : spikeManager.leftSpikes) {
         spikeCollision = collidesWithSpike(spike);

         if (spikeCollision) {
            soundManager.playClip("trollDeath", false);
            setLocation();
         }
      }

      for (Spike spike : spikeManager.rightSpikes) {
         spikeCollision = collidesWithSpike(spike);

         if (spikeCollision) {
            soundManager.playClip("trollDeath", false);
            setLocation();
         }
      }

      if (y > height) {
         setLocation();
         // soundManager.playClip("appear", false);
         // dy = dy + 1; // speed up troll when it is re-generated at top
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

   public boolean collidesWithSpike(Spike spike) {
      Rectangle2D.Double myRect = getBoundingRectangle();
      Rectangle2D.Double spikeRect = spike.getBoundingRectangle();

      return myRect.intersects(spikeRect);
   }

   public int getPoints() {
      return points;
   }

   public int getLives() {
      return lives;
   }

   public void setLivesAndPoints() {
      points = 0;
      lives = 5;
   }

}