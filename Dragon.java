import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * The StripAnimation class creates an animation from a strip file.
 */
public class Dragon {

	Animation animation;
	Animation animation2;
	private Random random;

	private int x; // x position of animation
	private int y; // y position of animation

	private int width;
	private int height;

	private int dx; // increment to move along x-axis
	private int dy; // increment to move along y-axis
	Image stripImageRight;
	Image stripImageLeft;
	Image stripImage;
	private GamePanel panel;
	DragonFireball[] balls;
	Fireball fireball;
	Wizard wizard;
	public static int lives;
	public static int points = 0;
	public static boolean gameComplete = false;
	private SoundManager soundManager;
	private int currentFrameIndex = 0;

	public Dragon(GamePanel p, DragonFireball balls[], Wizard wizard, Fireball fireball, HeartPanel heartPanel) {
		soundManager = SoundManager.getInstance();
		animation = new Animation(true);
		panel = p;
		// run animation once
		animation2 = new Animation(true);
		this.balls = balls;
		this.fireball = fireball;
		this.wizard = wizard;
		dx = 10; // increment to move along x-axis
		dy = 0; // increment to move along y-axis

		// load images from strip file
		random = new Random();

		stripImageRight = ImageManager.loadImage("images/dragons.png");
		stripImageLeft = ImageManager.loadImage("images/dragonsL.png");
		stripImage = stripImageRight;

		int imageWidth = (int) stripImage.getWidth(null) / 6;
		int imageHeight = stripImage.getHeight(null);

		for (int i = 0; i < 6; i++) {

			BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) frameImage.getGraphics();

			g.drawImage(stripImage,
					0, 0, imageWidth, imageHeight,
					i * imageWidth, 0, (i * imageWidth) + imageWidth, imageHeight,
					null);

			animation.addFrame(frameImage, 100);

		}

		for (int i = 0; i < 6; i++) {

			BufferedImage frameImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) frameImage.getGraphics();

			g.drawImage(stripImageLeft,
					0, 0, imageWidth, imageHeight,
					i * imageWidth, 0, (i * imageWidth) + imageWidth, imageHeight,
					null);

			animation2.addFrame(frameImage, 100);

		}
	}

	public void start() {
		x = 0;
		y = 10;
		animation.start();
	}

	public void update() {
		if (!animation.isStillActive())
			return;
		currentFrameIndex++;

		// If the current frame index exceeds the total number of frames, reset it to 0
		if (currentFrameIndex >= 6) {
			currentFrameIndex = 0;
		}

		boolean fireCollision = collidesWithFireball(currentFrameIndex);

		if (fireCollision) {
			soundManager.playClip("fireballHit", false);
			Troll.points++;
			points++;
			if (points >= 10) {
				killDezmond();
				System.out.println(points);
			}

		}
		animation.update();

		x = x + dx;
		y = y + dy;

		if (x >= 400) {

			setLocation();
			if (balls != null) {
				soundManager.playClip("fireballShoot", false);
				for (int i = 0; i < 3; i++)
					balls[i].setLocation(y);
			}
		}

	}

	public void killDezmond() {
		soundManager.stopClip("bossLevel");
		soundManager.stopClip("wingsFlap");
		soundManager.playClip("dragonDeath", false);
		animation.stop();

		gameComplete = true;
	}

	public void setLocation() {
		int panelH = 200;
		x = 0;
		y = random.nextInt(panelH - height);

	}

	public void draw(Graphics2D g2) {
		if (!animation.isStillActive())
			return;

		g2.drawImage(animation.getImage(), x, y, 180, 150, null);

	}

	public Rectangle2D.Double getBoundingRectangle(int currentFrameIndex, int frameWidth, int frameHeight) {
		// Calculate the position of the object within the current frame of the
		// animation strip
		double frameX = x + currentFrameIndex * frameWidth;
		double frameY = y; // Assuming the object's Y position remains constant within the strip

		// Adjust the bounding box based on the current frame position and size
		return new Rectangle2D.Double(frameX, frameY, frameWidth, frameHeight);
	}

	public boolean collidesWithFireball(int currentFrameIndex) {
		int imageWidth = (int) stripImage.getWidth(null) / 6;
		int imageHeight = stripImage.getHeight(null);

		Rectangle2D.Double myRect = getBoundingRectangle(currentFrameIndex, imageWidth, imageHeight);
		Rectangle2D.Double fireRect = fireball.getBoundingRectangle();

		return myRect.intersects(fireRect);
	}

}
