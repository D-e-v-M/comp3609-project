import java.util.ArrayList;
import java.util.Random;

public class SpikeManager {

    public ArrayList<Spike> leftSpikes;
    public ArrayList<Spike> rightSpikes;
    private Random random;

    public SpikeManager() {
        leftSpikes = new ArrayList<>();
        rightSpikes = new ArrayList<>();
        random = new Random();
    }

    public void spawnSpikes() {
        int minY = 50;
        int maxY = 350;

        for (int i = 0; i < 3; i++) {
            int spawnY = random.nextInt(maxY - minY) + minY;
            Spike spike = new Spike(0, spawnY, 75, 60, "images/spike_left.png");
            leftSpikes.add(spike);
        }

        for (int i = 0; i < 3; i++) {
            int spawnY = random.nextInt(maxY - minY) + minY;
            Spike spike = new Spike(325, spawnY, 75, 60, "images/spike_right.png");
            rightSpikes.add(spike);
        }
    }

}
