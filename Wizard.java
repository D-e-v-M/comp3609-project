import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.Image;

public class Wizard {

	private JPanel panel;
	private int x;
	private int y;
	private int width;
	private int height;

	private int dx;
	private int dy;

	private Image wizardImage;

	public Wizard(JPanel p, int xPos, int yPos) {
		panel = p;

		x = xPos;
		y = yPos;

		dx = 0; // set to zero since background moves instead
		dy = 50; // size of vertical movement

		width = 50;
		height = 50;

		wizardImage = ImageManager.loadImage("images/wizard.png");
	}

	public void draw(Graphics2D g2) {

		g2.drawImage(wizardImage, x, y, width, height, null);

	}

	public void move(int direction) {

		if (!panel.isVisible())
			return;

		if (direction == 1) {
			x = x - dx; // move left
		} else if (direction == 2) {
			x = x + dx; // move right
		}

		// write code here to move wizard up and down

	}

	/*
	 * public void move (int direction) {
	 * 
	 * if (!panel.isVisible ()) return;
	 * 
	 * if (direction == 1) {
	 * x = x - dx;
	 * wizardImage = wizardLeftImage;
	 * if (x < -30) // move to right of GamePanel
	 * x = 380;
	 * }
	 * else
	 * if (direction == 2) {
	 * x = x + dx;
	 * wizardImage = wizardRightImage;
	 * if (x > 380) // move to left of GamePanel
	 * x = -30;
	 * }
	 * }
	 */

	public boolean isOnWizard(int x, int y) {
		Rectangle2D.Double myRectangle = getBoundingRectangle();
		return myRectangle.contains(x, y);
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double(x, y, width, height);
	}

}