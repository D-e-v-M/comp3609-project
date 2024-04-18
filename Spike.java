import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Spike {

    private int x;
    private int y;
    private int height;
    private int width;

    private Image spikeImage;

    public Spike(int x, int y, int width, int height, String spikeFile) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        spikeImage = ImageManager.loadImage(spikeFile);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(spikeImage, x, y, width, height, null);
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
