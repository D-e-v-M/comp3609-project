import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A component that displays all the game entities
 */

public class GamePanel extends JPanel
		implements Runnable {

	private static int NUM_TROLLS = 3;
	private static int NUM_GOBLINS = 3;

	private SoundManager soundManager;
	private Wizard wizard;
	private Troll[] trolls;
	private Goblin[] goblins;
	private Fireball fireball;
	private boolean trollDropped;
	private boolean goblinsDropped;
	private boolean isRunning;
	private boolean isPaused;

	private Thread gameThread;

	private BufferedImage image;
	// private Image backgroundImage;

	private Background background;

	private Graphics2D imageContext;

	private ImageFX imageFX;
	private ImageFX imageFX2;

	private FaceAnimation animation;
	private CatAnimation animation2;
	private StripAnimation animation3;

	public GamePanel() {
		wizard = null;
		trolls = null;
		trollDropped = false;
		isRunning = false;
		isPaused = false;
		soundManager = SoundManager.getInstance();

		// backgroundImage = ImageManager.loadImage ("images/Background.jpg");

		image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
	}

	public void createGameEntities() {

		background = new Background(this, "images/Scroll-Background.png", 96);

		wizard = new Wizard(this, 175, 350);
		fireball = new Fireball(this, 175, 350);

		trolls = new Troll[3];
		trolls[0] = new Troll(this, 275, 10, wizard, fireball);
		trolls[1] = new Troll(this, 150, 10, wizard, fireball);
		trolls[2] = new Troll(this, 330, 10, wizard, fireball);

		goblins = new Goblin[3];
		goblins[0] = new Goblin(this, 275, 10, wizard, fireball);
		goblins[1] = new Goblin(this, 150, 10, wizard, fireball);
		goblins[2] = new Goblin(this, 330, 10, wizard, fireball);

		imageFX = new TintFX(this);
		imageFX2 = new GrayScaleFX2(this);

		animation = new FaceAnimation();
		animation2 = new CatAnimation();
		animation3 = new StripAnimation();
	}

	public void run() {
		try {
			isRunning = true;
			while (isRunning) {
				if (!isPaused)
					gameUpdate();
				gameRender();
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
		}
	}

	public void gameUpdate() {
		/*
		 * for (int i=0; i<NUM_TROLLS; i++) {
		 * trolls[i].move();
		 * }\
		 * for (int i=0; i<NUM_GOBLINS; i++) {
		 * goblins[i].move();
		 * }
		 * 
		 * 
		 * imageFX.update();
		 * imageFX2.update();
		 * 
		 * animation.update();
		 * animation2.update();
		 * animation3.update();
		 */
	}

	public void updateWizard(int direction) {

		if (isPaused)
			return;

		if (background != null) {
			background.move(direction);
		}

		if (wizard != null) {
			wizard.move(direction);
		}

	}

	public void shootFireball() {
		if (fireball != null && wizard != null)
			fireball.shoot(imageContext);
	}

	public void gameRender() {

		// draw the game objects on the image

		imageContext = (Graphics2D) image.getGraphics();

		background.draw(imageContext);

		// imageContext.drawImage(backgroundImage, 0, 0, null); // draw the background
		// image

		if (wizard != null) {
			wizard.draw(imageContext);
		}

		/*
		 * if (trolls != null) {
		 * for (int i=0; i<NUM_TROLLS; i++)
		 * trolls[i].draw(imageContext);
		 * }
		 * 
		 * if (goblins != null) {
		 * for (int i=0; i<NUM_GOBLINS; i++)
		 * goblins[i].draw(imageContext);
		 * }
		 * 
		 * if (imageFX != null) {
		 * imageFX.draw (imageContext);
		 * }
		 * 
		 * if (imageFX2 != null) {
		 * imageFX2.draw (imageContext);
		 * }
		 * 
		 * if (animation != null) {
		 * animation.draw (imageContext);
		 * }
		 * 
		 * if (animation2 != null) {
		 * animation2.draw (imageContext);
		 * }
		 * 
		 * if (animation3 != null) {
		 * animation3.draw (imageContext);
		 * }
		 */
		Graphics2D g2 = (Graphics2D) getGraphics(); // get the graphics context for the panel
		g2.drawImage(image, 0, 0, 400, 400, null);

		imageContext.dispose();
		g2.dispose();
	}

	public void startGame() { // initialise and start the game thread

		if (gameThread == null) {
			// soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread(this);
			gameThread.start();

			if (animation != null) {
				animation.start();
			}

			if (animation2 != null) {
				animation2.start();
			}

			if (animation3 != null) {
				animation3.start();
			}
		}

	}

	public void startNewGame() { // initialise and start a new game thread

		isPaused = false;

		if (gameThread == null || !isRunning) {
			// soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread(this);
			gameThread.start();

			if (animation != null) {
				animation.start();
			}

			if (animation2 != null) {
				animation2.start();
			}

			if (animation3 != null) {
				animation3.start();
			}
		}
	}

	public void pauseGame() { // pause the game (don't update game entities)
		if (isRunning) {
			if (isPaused)
				isPaused = false;
			else
				isPaused = true;
		}
	}

	public void endGame() { // end the game thread
		isRunning = false;
		// soundManager.stopClip ("background");
	}

	public void shootCat() {
		animation3.start();
	}

	public boolean isOnWizard(int x, int y) {
		return wizard.isOnWizard(x, y);
	}
}