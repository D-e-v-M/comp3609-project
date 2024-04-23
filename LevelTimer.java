import java.util.Timer;
import java.util.TimerTask;

public class LevelTimer {

    private Timer timer;
    private int delay;
    private SoundManager soundManager;
    public static boolean victoryScreen;

    public LevelTimer(int delay) {
        this.delay = delay;
        timer = new Timer();

        soundManager = SoundManager.getInstance();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (GamePanel.isLevel2 == false && GamePanel.levelInterval == 1) {
                    GamePanel.isLevel2 = true;
                    GamePanel.levelInterval = 2;

                    soundManager.playClip("level2", true);
                } else if (GamePanel.levelInterval == 3) {
                    GamePanel.isLevel3 = true;
                    GamePanel.levelInterval = 4;

                    soundManager.playClip("bossLevel", true);
                    soundManager.playClip("wingsFlap", true);
                } else if (Dragon.gameComplete) {
                    GamePanel.levelInterval = 5;
                }
            }
        }, delay);
    }

}
