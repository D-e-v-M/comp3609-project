import java.util.Timer;
import java.util.TimerTask;

public class LevelTimer {

    private Timer timer;
    private int delay;

    public LevelTimer(int delay) {
        this.delay = delay;
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (GamePanel.isLevel2 == false && GamePanel.levelInterval == 1) {
                    GamePanel.isLevel2 = true;
                    GamePanel.levelInterval = 2;
                } else {
                    GamePanel.isLevel3 = true;
                    GamePanel.levelInterval = 4;
                }
            }
        }, delay);
    }

}
