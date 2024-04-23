import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
	private Dragon dragon;
	private DragonFireball[] dragonFireballs;

	private boolean fireballShoot;
	private boolean trollDropped;
	private boolean goblinsDropped;
	private boolean isRunning;
	private boolean isPaused;
	public static boolean isLevel1;
	public static boolean isLevel2;
	public static boolean isLevel3;
	public static int levelInterval; // Signals for the space between levels
	public static boolean isBackgroundChange;
	private int lives;
	private int points;
	private LevelTimer timer;
	private boolean gameover;

	private Thread gameThread;

	private BufferedImage image;

	private Background background;

	private Graphics2D imageContext;

	private HeartPanel heartPanel;

	private SpikeManager spikeManager;

	public GamePanel(HeartPanel heartPanel) {
		wizard = null;
		trolls = null;
		trollDropped = false;
		isRunning = false;
		isPaused = false;
		isBackgroundChange = true;
		fireballShoot = false;
		gameover = false;
		soundManager = SoundManager.getInstance();

		this.heartPanel = heartPanel;

		image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
	}

	public void createGameEntities() {

		background = new Background(this, "images/cave-background.png", 96);

		spikeManager = new SpikeManager();

		fireball = new Fireball(this, 175, 350);
		wizard = new Wizard(this, 175, 350, fireball, heartPanel, spikeManager);

		trolls = new Troll[3];
		trolls[0] = new Troll(this, 275, 10, wizard, fireball, heartPanel, spikeManager);
		trolls[1] = new Troll(this, 150, 10, wizard, fireball, heartPanel, spikeManager);
		trolls[2] = new Troll(this, 330, 10, wizard, fireball, heartPanel, spikeManager);

		goblins = new Goblin[3];
		goblins[0] = new Goblin(this, 275, 10, wizard, fireball, heartPanel, spikeManager);
		goblins[1] = new Goblin(this, 150, 10, wizard, fireball, heartPanel, spikeManager);
		goblins[2] = new Goblin(this, 330, 10, wizard, fireball, heartPanel, spikeManager);

		dragonFireballs = new DragonFireball[3];
		dragonFireballs[0] = new DragonFireball(this, 60, 0, wizard, heartPanel);
		dragonFireballs[1] = new DragonFireball(this, 175, 0, wizard, heartPanel);
		dragonFireballs[2] = new DragonFireball(this, 180, 0, wizard, heartPanel);
		dragon = new Dragon(this, dragonFireballs, wizard, fireball, heartPanel);

		spikeManager.spawnSpikes();

		points = 0;
		levelInterval = 0; // At the start of the game

		soundManager.playClip("level1", true);
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
			timer = new LevelTimer(5000);
		}

		// If requirements to beat level 2 are met
		if (levelInterval == 3) {
			timer = new LevelTimer(5000);
		}

		if (Dragon.gameComplete) {
			timer = new LevelTimer(5000);
		}

		// The if statementsa are at the top to allow the enemies to be
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

		if (isLevel3) {
			dragon.update();
			dragonFireballs[0].move();
			dragonFireballs[1].move();
			dragonFireballs[2].move();
		}

		if (Troll.lives <= 0) {
			gameover = true;
			gameRender();
			gameover();
		}

		if (isBackgroundChange) {
			spikeManager.leftSpikes.clear();
			spikeManager.rightSpikes.clear();
			spikeManager.spawnSpikes();
			isBackgroundChange = false;
		}

		// Probably need to increase this limit
		// Signals the completion of level 1
		if (points >= 50 && levelInterval == 0) {
			isLevel1 = false;
			levelInterval = 1;

			Troll.lives++;
			heartPanel.addHeart();

			soundManager.stopClip("level1");
		}

		// Signals the completion of level 2
		if (points >= 150 && levelInterval == 2 && isLevel2) {
			isLevel2 = false;
			levelInterval = 3;

			Troll.lives++;
			heartPanel.addHeart();

			soundManager.stopClip("level2");
			soundManager.playClip("dragonRoar", false);
		}
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

		if (wizard != null) {
			wizard.draw(imageContext);
		}

		if (fireballShoot) {
			fireball.draw(imageContext);
		}

		// Scoreboard
		String pointsString = Integer.toString(Troll.points);
		Font font = new Font("MS Gothic", Font.PLAIN, 16);
		imageContext.setFont(font);
		FontMetrics fm = imageContext.getFontMetrics();
		int pointsWidth = fm.stringWidth(pointsString);
		imageContext.setColor(Color.WHITE);
		imageContext.drawString(pointsString, getWidth() - pointsWidth - 5, fm.getAscent() + 5);

		for (Spike spike : spikeManager.leftSpikes) {
			spike.draw(imageContext);
		}

		for (Spike spike : spikeManager.rightSpikes) {
			spike.draw(imageContext);
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

		if (isLevel3) {
			if (dragon != null) {
				dragon.draw(imageContext);
			}

			if (dragonFireballs != null) {
				for (int i = 0; i < 3; i++)
					dragonFireballs[i].draw(imageContext);
			}
		}

		if (gameover) {
			imageContext.setColor(new Color(255, 0, 0, 100));
			imageContext.fillRect(0, 0, getWidth(), getHeight());
			font = new Font("MS Gothic", Font.PLAIN, 28);
			imageContext.setFont(font);
			fm = imageContext.getFontMetrics();
			imageContext.setColor(Color.WHITE);
			imageContext.drawString("GAME OVER", (getWidth() / 2) - 60, fm.getAscent());
		}

		// Checks to see if game is completed
		if (levelInterval == 5) {
			imageContext.setColor(new Color(255, 223, 0, 100));
			imageContext.fillRect(0, 0, getWidth(), getHeight());
			font = new Font("MS Gothic", Font.PLAIN, 28);
			imageContext.setFont(font);
			fm = imageContext.getFontMetrics();
			imageContext.setColor(Color.WHITE);
			imageContext.drawString("YOU WIN!", (getWidth() / 2) - 60, fm.getAscent());

			soundManager.playClip("victory", false);

			endGame();
		}

		Graphics2D g2 = (Graphics2D) getGraphics(); // get the graphics context for the panel
		g2.drawImage(image, 0, 0, 400, 400, null);

		imageContext.dispose();
		g2.dispose();
	}

	public void startGame() { // initialise and start the game thread

		if (gameThread == null) {
			createGameEntities();
			gameThread = new Thread(this);
			gameThread.start();

			isLevel1 = true;

			if (dragon != null) {
				dragon.start();
			}
		}

	}

	public void startNewGame() { // initialise and start a new game thread

		isPaused = false;

		soundManager.stopClip("wingsFlap");

		if (gameThread == null || !isRunning) {
			createGameEntities();
			heartPanel.setHearts();
			gameover = false;
			gameThread = new Thread(this);
			gameThread.start();

			isLevel2 = false;
			isLevel3 = false;
			isLevel1 = true;

			if (dragon != null) {
				dragon.start();
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
		soundManager.stopClip("level1");
		soundManager.stopClip("level2");
		soundManager.stopClip("bossLevel");
	}

	public void gameover() {
		isPaused = true;
		isRunning = false;
		soundManager.stopClip("level1");
		soundManager.stopClip("level2");
		soundManager.stopClip("bossLevel");
		soundManager.playClip("wizardDeath", false);
		soundManager.playClip("gameover", false);
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