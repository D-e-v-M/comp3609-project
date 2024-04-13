import java.awt.Image;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
    The FaceAnimation class creates a face animation containing six frames 
    based on three images.
*/
public class FaceAnimation {
	
	Animation animation;

	private int x;		// x position of animation
	private int y;		// y position of animation

	private int width;
	private int height;

	private int dx;		// increment to move along x-axis
	private int dy;		// increment to move along y-axis

	public FaceAnimation() {

		animation = new Animation(true);		// loop continuously

		x = 0;		// set x position
        	y = 10;		// set y position
        	dx = 5;		// increment to move along x-axis
        	dy = 0;		// increment to move along y-axis

		// load images for blinking face animation

		Image animImage1 = ImageManager.loadImage("images/dragon1_right.png");
		Image animImage2 = ImageManager.loadImage("images/dragon2_right.png");
		Image animImage3 = ImageManager.loadImage("images/dradon3_right.png");
		Image animImage4 = ImageManager.loadImage("images/dragon4_right.png");
		Image animImage5 = ImageManager.loadImage("images/dragon5_right.png");
		Image animImage6 = ImageManager.loadImage("images/dradon6_right.png");
		Image animImage1L = ImageManager.loadImage("images/dragon1_left.png");
		Image animImage2L = ImageManager.loadImage("images/dragon2_left.png");
		Image animImage3L = ImageManager.loadImage("images/dradon3_left.png");
		Image animImage4L = ImageManager.loadImage("images/dragon4_left.png");
		Image animImage5L = ImageManager.loadImage("images/dragon5_left.png");
		Image animImage6L = ImageManager.loadImage("images/dradon6_left.png");
	
	
	
		// create animation object and insert frames

		animation.addFrame(animImage1, 150);
		animation.addFrame(animImage2, 150);
		animation.addFrame(animImage3, 150);
		animation.addFrame(animImage4, 150);
		animation.addFrame(animImage5, 150);
		animation.addFrame(animImage4, 150);
		animation.addFrame(animImage3, 150);
		animation.addFrame(animImage4, 150);
		animation.addFrame(animImage5, 150);
		animation.addFrame(animImage4, 150);
		animation.addFrame(animImage3, 150);
		animation.addFrame(animImage4, 150);
		animation.addFrame(animImage5, 150);
		animation.addFrame(animImage4, 150);
		animation.addFrame(animImage3, 150);
		animation.addFrame(animImage4, 150);
		animation.addFrame(animImage5, 150);
		animation.addFrame(animImage4, 150);
		animation.addFrame(animImage3, 150);
		animation.addFrame(animImage4, 150);
		animation.addFrame(animImage5, 150);
		animation.addFrame(animImage4, 150);
		animation.addFrame(animImage5, 150);
		animation.addFrame(animImage6, 150);
		animation.addFrame(animImage1L, 150);

		animation.addFrame(animImage1L, 150);
		animation.addFrame(animImage2L, 150);
		animation.addFrame(animImage3L, 150);
		animation.addFrame(animImage4L, 150);
		animation.addFrame(animImage5L, 150);
		animation.addFrame(animImage4L, 150);
		animation.addFrame(animImage3L, 150);
		animation.addFrame(animImage4L, 150);
		animation.addFrame(animImage5L, 150);
		animation.addFrame(animImage4L, 150);
		animation.addFrame(animImage3L, 150);
		animation.addFrame(animImage4L, 150);
		animation.addFrame(animImage5L, 150);
		animation.addFrame(animImage4L, 150);
		animation.addFrame(animImage3L, 150);
		animation.addFrame(animImage4L, 150);
		animation.addFrame(animImage5L, 150);
		animation.addFrame(animImage4L, 150);
		animation.addFrame(animImage3L, 150);
		animation.addFrame(animImage4L, 150);
		animation.addFrame(animImage5L, 150);
		animation.addFrame(animImage4L, 150);
		animation.addFrame(animImage5L, 150);
		animation.addFrame(animImage6L, 150);
		animation.addFrame(animImage1, 150);
	}


	public void start() {
		x = 0;
        	y = 10;
		animation.start();
	}

	
	public void update() {
		if (!animation.isStillActive())
			return;

		animation.update();
		if(x+180>400)
			dx=-4;
        if(x<=0)
		    dx=4;
		x = x + dx;
		y = y + dy;
	}


	public void draw(Graphics2D g2) {
		if (!animation.isStillActive())
			return;

		g2.drawImage(animation.getImage(), x, y, 180, 135, null);
	}

}
