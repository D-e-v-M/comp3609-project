import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Image;

public class Background {
	private Image bgImage;
	private int bgImageWidth; // width of the background (>= panel Width)
	private int bgImageHeight;

	private GamePanel panel;

	private int bgX; // X-coordinate of "actual" position
	private int bgY;

	private int bg1X; // X-coordinate of first background
	private int bg2X; // X-coordinate of second background
	private int bgDX; // size of the background move (in pixels)

	private int bg1Y; // X-coordinate of first background
	private int bg2Y; // X-coordinate of second background
	private int bgDY; // size of the background move (in pixels)

	public Background(GamePanel panel, String imageFile, int bgDY) {

		this.panel = panel;
		this.bgImage = ImageManager.loadImage(imageFile);
		bgImageWidth = bgImage.getWidth(null); // get width of the background
		bgImageHeight = bgImage.getHeight(null);

		System.out.println("bgImageWidth = " + bgImageWidth);
		System.out.println("bgImageHeight = " + bgImageHeight);

		if (bgImageWidth < panel.getWidth())
			System.out.println("Background width < panel width");
		if (bgImageHeight < panel.getHeight())
			System.out.println("Background height < panel height");

		this.bgDX = 200; // bgDX;
		this.bgDY = 200;

		bgX = 0;
		bg1X = 0;
		bg2X = bgImageWidth;

		bgY = 0;
		bg1Y = 0;
		bg2Y = bgImageHeight;

	}

	public void move(int direction) {

		// if (direction == 1)
		// moveRight();
		// else if (direction == 2)
		// moveLeft();
		if (direction == 3)
			moveUp();
		else if (direction == 4)
			moveDown();
	}

	public void moveLeft() {

		bgX = bgX - bgDX;

		bg1X = bg1X - bgDX;
		bg2X = bg2X - bgDX;

		String mess = "Moving background left: bgX=" + bgX + " bg1X=" + bg1X + " bg2X=" + bg2X;
		System.out.println(mess);

		if (bg1X < (bgImageWidth * -1)) {
			System.out.println("Background change: bgX = " + bgX);
			bg1X = 0;
			bg2X = bgImageWidth;
		}

	}

	public void moveRight() {

		bgX = bgX + bgDX;

		bg1X = bg1X + bgDX;
		bg2X = bg2X + bgDX;

		String mess = "Moving background right: bgX=" + bgX + " bg1X=" + bg1X + " bg2X=" + bg2X;
		System.out.println(mess);

		if (bg1X > 0) {
			System.out.println("Background change: bgX = " + bgX);
			bg1X = bgImageWidth * -1;
			bg2X = 0;
		}

	}

	public void moveUp() {
		bgY = bgY - bgDY;

		bg1Y = bg1Y - bgDY;
		bg2Y = bg2Y - bgDY;

		String mess = "Moving background up: bgY=" + bgY + " bg1Y=" + bg1Y + " bg2Y=" + bg2Y;
		System.out.println(mess);

		if (bg1Y < (bgImageHeight * -1)) {
			System.out.println("Background change: bgY = " + bgY);
			bg1Y = 0;
			bg2Y = bgImageHeight;
			GamePanel.isBackgroundChange = true;
		}
	}

	public void moveDown() {
		bgY = bgY + bgDY;

		bg1Y = bg1Y + bgDY;
		bg2Y = bg2Y + bgDY;

		String mess = "Moving background up: bgY=" + bgY + " bg1Y=" + bg1Y + " bg2Y=" + bg2Y;
		System.out.println(mess);

		if (bg1Y > 0) {
			System.out.println("Background change: bgY = " + bgY);
			bg1Y = bgImageHeight * -1;
			bg2Y = bgImageHeight;
			GamePanel.isBackgroundChange = true;
		}
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(bgImage, bg1X, bg1Y, null);
		g2.drawImage(bgImage, bg2X, bg2Y, null);
	}

}
