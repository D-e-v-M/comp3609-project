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
	private boolean fireballShoot;
	private boolean trollDropped;
	private boolean goblinsDropped;
	private boolean isRunning;
	private boolean isPaused;
	public static boolean isLevel1;
	public static boolean isLevel2;
	public static boolean isLevel3;
	public static int levelInterval; // Signals for the space between levels
	private int lives;
	private int points;
	private LevelTimer timer1;
	private LevelTimer timer2;

	private Thread gameThread;

	private BufferedImage image;
	// private Image backgroundImage;

	private Background background;

	private Graphics2D imageContext;

	private HeartPanel heartPanel;

	private ImageFX imageFX;
	private ImageFX imageFX2;

	private FaceAnimation animation;
	private CatAnimation animation2;
	private StripAnimation animation3;

	public GamePanel(HeartPanel heartPanel) {
		wizard = null;
		trolls = null;
		trollDropped = false;
		isRunning = false;
		isPaused = false;
		soundManager = SoundManager.getInstance();

		this.heartPanel = heartPanel;

		// backgroundImage = ImageManager.loadImage ("images/Background.jpg");

		image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
	}

	public void createGameEntities() {

		background = new Background(this, "images/cave-background.png", 96);

		fireball = new Fireball(this, 175, 350);
		wizard = new Wizard(this, 175, 350, fireball);
		fireballShoot = false;

		trolls = new Troll[3];
		trolls[0] = new Troll(this, 275, 10, wizard, fireball, heartPanel);
		trolls[1] = new Troll(this, 150, 10, wizard, fireball, heartPanel);
		trolls[2] = new Troll(this, 330, 10, wizard, fireball, heartPanel);

		goblins = new Goblin[3];
		goblins[0] = new Goblin(this, 275, 10, wizard, fireball, heartPanel);
		goblins[1] = new Goblin(this, 150, 10, wizard, fireball, heartPanel);
		goblins[2] = new Goblin(this, 330, 10, wizard, fireball, heartPanel);

		imageFX = new TintFX(this);
		imageFX2 = new GrayScaleFX2(this);

		points = 0;
		levelInterval = 0; // At the start of the game

		// animation = new FaceAnimation();
		// animation2 = new CatAnimation();
		// animation3 = new StripAnimation();
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

	public void gameUpdate() throws InterruptedException {

		// If requirements to beat level 1 are met
		if (levelInterval == 1) {
			timer1 = new LevelTimer();
			// levelInterval++;
			// isLevel2 = true;
		}

		// If requirements to beat level 2 are met
		if (levelInterval == 3) {
			timer2 = new LevelTimer();
		}

		// The two above if statementsa are at the top to allow the enemies to be
		// unrendered

		if (fireballShoot) {
			fireball.move();
		}

		if (isLevel1 || isLevel2) {
			for (int i = 0; i < NUM_TROLLS; i++) {
				trolls[i].move();
				lives = getLives();
				points = getPoints();
			}
		}

		if (isLevel2) {
			for (int i = 0; i < NUM_GOBLINS; i++) {
				goblins[i].move();
				lives = getLives();
				points = getPoints();
			}
		}

		// Probably need to increase this limit
		// Signals the completion of level 1
		if (points > 10 && levelInterval == 0) {
			isLevel1 = false;
			levelInterval++;
			// isLevel2 = true;
			Troll.lives++;
			heartPanel.addHeart();
		}

		// Signals the completion of level 2
		if (points > 150 && levelInterval == 2) {
			isLevel2 = false;
			levelInterval++;
			// isLevel2 = true;
			Troll.lives++;
			heartPanel.addHeart();
		}

		// System.out.println("Points: " + points);
		// System.out.println("Lives: " + lives);

		/*
		 * imageFX.update();
		 * imageFX2.update();
		 * 
		 * animation.update();
		 * animation2.update();
		 * animation3.update();
		 */
		// animation.update();
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

	// public void shootFireball() {
	// if (fireball != null && wizard != null) {
	// fireball.shoot(imageContext);
	// soundManager.playClip("fireballShoot", false);
	// }
	// }

	public void shootFireball() {
		if (fireballShoot)
			fireballShoot = false;
		else {
			fireballShoot = true;
			soundManager.playClip("fireballShoot", false);
		}
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

		if (fireballShoot) {
			fireball.draw(imageContext);
		}

		if (isLevel1 || isLevel2) {
			if (trolls != null) {
				for (int i = 0; i < NUM_TROLLS; i++)
					trolls[i].draw(imageContext);
			}
		}

		if (isLevel2) {
			if (goblins != null) {
				for (int i = 0; i < NUM_GOBLINS; i++)
					goblins[i].draw(imageContext);
			}
		}

		/*
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

		if (animation != null) {
			animation.draw(imageContext);
		}

		Graphics2D g2 = (Graphics2D) getGraphics(); // get the graphics context for the panel
		g2.drawImage(image, 0, 0, 400, 400, null);

		imageContext.dispose();
		g2.dispose();
	}

	public void startGame() { // initialise and start the game thread

		if (gameThread == null) {
			// soundManager.playClip("level1", true);
			createGameEntities();
			gameThread = new Thread(this);
			gameThread.start();

			isLevel1 = true;

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
			heartPanel.setHearts();
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

	public int getPoints() {
		points = trolls[0].getPoints();

		return points;
	}

	public int getLives() {
		lives = trolls[0].getLives();

		return lives;
	}

}