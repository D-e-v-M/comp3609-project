import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.Image;
import java.util.ArrayList;

public class Wizard {

	private JPanel panel;
	private int x;
	private int y;
	private int width;
	private int height;

	private int dx;
	private int dy;

	private Fireball fireball;
	private SpikeManager spikeManager;

	private HeartPanel heartPanel;
	private SoundManager soundManager;
	private Image wizardImage;

	public Wizard(JPanel p, int xPos, int yPos, Fireball fireball, HeartPanel heartPanel, SpikeManager spikeManager) {
		panel = p;

		x = xPos;
		y = yPos;

		dx = 10; // set to zero since background moves instead
		dy = 50; // size of vertical movement

		width = 50;
		height = 50;

		this.fireball = fireball;
		this.spikeManager = spikeManager;

		this.heartPanel = heartPanel;

		soundManager = SoundManager.getInstance();
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

			if (x < -30)
				x = 380;
		} else if (direction == 2) {
			x = x + dx; // move right

			if (x > 380)
				x = -30;
		}

		fireball.setX(x);

		boolean spikeCollision;

		for (Spike spike : spikeManager.leftSpikes) {
			spikeCollision = collidesWithSpike(spike);

			if (spikeCollision) {
				Troll.lives--;
				heartPanel.loseHearts();
				soundManager.playClip("objectHit", false);
				x = x + 50;
			}
		}

		for (Spike spike : spikeManager.rightSpikes) {
			spikeCollision = collidesWithSpike(spike);

			if (spikeCollision) {
				Troll.lives--;
				heartPanel.loseHearts();
				soundManager.playClip("objectHit", false);
				x = x - 50;
			}
		}

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

	public int getX() {
		return x;
	}

	public boolean isOnWizard(int x, int y) {
		Rectangle2D.Double myRectangle = getBoundingRectangle();
		return myRectangle.contains(x, y);
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double(x, y, width, height);
	}

	public boolean collidesWithSpike(Spike spike) {
		Rectangle2D.Double myRect = getBoundingRectangle();
		Rectangle2D.Double spikeRect = spike.getBoundingRectangle();

		return myRect.intersects(spikeRect);
	}

}