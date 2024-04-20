import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

/**
    The StripAnimation class creates an animation from a strip file.
*/
public class Dragon {
	
	Animation animation;
	Animation animation2;
	private Random random;

	private int x;		// x position of animation
	private int y;		// y position of animation

	private int width;
	private int height;

	private int dx;		// increment to move along x-axis
	private int dy;		// increment to move along y-axis
	Image stripImageRight ;
		Image stripImageLeft ;
		Image stripImage ;
    private JPanel panel;
    DragonFireball[] balls;

	public Dragon(DragonFireball balls[]) {

		animation = new Animation(true);
			// run animation once
			animation2 = new Animation(true);
            this.balls=balls;

        	dx = 10;		// increment to move along x-axis
        	dy = 0;	// increment to move along y-axis
            //panel=p;

		// load images from strip file
		random = new Random();

		stripImageRight = ImageManager.loadImage("images/dragons.png");
		stripImageLeft = ImageManager.loadImage("images/dragonsL.png");
		stripImage = stripImageRight;

		int imageWidth = (int) stripImage.getWidth(null) /6;
		int imageHeight = stripImage.getHeight(null);

		for (int i=0; i<6; i++) {

			BufferedImage frameImage = new BufferedImage (imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) frameImage.getGraphics();
     
			g.drawImage(stripImage, 
					0, 0, imageWidth, imageHeight,
					i*imageWidth, 0, (i*imageWidth)+imageWidth, imageHeight,
					null);

			animation.addFrame(frameImage, 100);

		}
		
		

		for (int i=0; i<6; i++) {

			BufferedImage frameImage = new BufferedImage (imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) frameImage.getGraphics();
     
			g.drawImage(stripImageLeft, 
					0, 0, imageWidth, imageHeight,
					i*imageWidth, 0, (i*imageWidth)+imageWidth, imageHeight,
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

			animation.update();
            int drop=x+width;	
		x = x + dx;
		y = y + dy;
      /* 
        if (drop==x)
        {
            balls[0].setLocationDragon(drop,y);
        }*/
		if (x>= 400)
		{
			
			setLocation();
            if (balls != null) {
                for (int i = 0; i < 3; i++)
                    balls[i].setLocation(y);
            }
			
			
		}
			
			
			
	}

	public void setLocation() {
		int panelH =200;
		x = 0;
		y = random.nextInt (panelH-height);;
	 }
	public void draw(Graphics2D g2) {
		if (!animation.isStillActive())
			return;

		g2.drawImage(animation.getImage(), x, y, 180, 150, null);
		
	}


}
