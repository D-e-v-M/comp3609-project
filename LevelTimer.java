import java.util.Timer;
import java.util.TimerTask;

public class LevelTimer {

    private Timer timer;

    public LevelTimer() {
        timer = new Timer();

        timer.schedule(new TimerTask() {
            int seconds = 5;

            @Override
            public void run() {
                if (seconds >= 0) {
                    System.out.println(seconds--);
                } else {
                    timer.cancel();

                    if (!GamePanel.isLevel2) {
                        GamePanel.isLevel2 = true;
                    } else {
                        GamePanel.isLevel3 = true;
                    }

                    GamePanel.levelInterval++;
                }
            }
        }, 0, 1000);
    }

}
