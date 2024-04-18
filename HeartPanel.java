import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeartPanel extends JPanel {
    private int hearts = 5;

    private Image heartImage;

    public HeartPanel() {
        heartImage = ImageManager.loadImage("images/heart.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < hearts; i++) {
            drawHeart(g, i * 50 + 20, 10);
        }
    }

    private void drawHeart(Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.drawImage(heartImage, x, y, 50, 50, null);

        // g2.setColor(Color.RED);
        // int[] xPoints = { x, x + 20, x + 40 };
        // int[] yPoints = { y + 30, y, y + 30 };
        // g2.fillPolygon(xPoints, yPoints, 3);
        // g2.fillArc(x, y, 20, 20, 0, 180);
        // g2.fillArc(x + 20, y, 20, 20, 0, 180);

        g2.dispose();
    }

    public void loseHearts() {
        if (hearts > 0) {
            hearts--;
            repaint();
        } else {
            hearts = 0;
            repaint();
        }
    }

    public void addHeart() {
        hearts++;
        repaint();
    }

    public void setHearts() {
        hearts = 5;
        repaint();
    }

}