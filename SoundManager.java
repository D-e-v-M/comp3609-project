import javax.sound.sampled.AudioInputStream; // for playing sound clips
import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap; // for storing sound clips

public class SoundManager { // a Singleton class
	HashMap<String, Clip> clips;

	private static SoundManager instance = null; // keeps track of Singleton instance

	private float volume;

	private SoundManager() {
		clips = new HashMap<String, Clip>();

		Clip clip = loadClip("sounds/background-1.wav"); // played from start of the game
		clips.put("level1", clip);

		clip = loadClip("sounds/background-2.wav"); // played when the wizard hits an troll
		clips.put("level2", clip);

		clip = loadClip("sounds/boss-background.wav"); // played when an troll is regenerated at the top of the JPanel
		clips.put("bossLevel", clip);

		clip = loadClip("sounds/dragon-death.wav");
		clips.put("dragonDeath", clip);

		clip = loadClip("sounds/dragon-roar.wav");
		clips.put("dragonRoar", clip);

		clip = loadClip("sounds/wings-flapping.wav");
		clips.put("wingsFlap", clip);

		clip = loadClip("sounds/goblin-death.wav");
		clips.put("goblinDeath", clip);

		clip = loadClip("sounds/troll-death.wav");
		clips.put("trollDeath", clip);

		clip = loadClip("sounds/goblin-hit.wav");
		clips.put("goblinHit", clip);

		clip = loadClip("sounds/troll-hit.wav");
		clips.put("trollHit", clip);

		clip = loadClip("sounds/thump-and-grunt.wav");
		clips.put("objectHit", clip);

		clip = loadClip("sounds/fireball-collision.wav");
		clips.put("fireballHit", clip);

		clip = loadClip("sounds/fireball-shoot.wav");
		clips.put("fireballShoot", clip);

		clip = loadClip("sounds/wizard-death.wav");
		clips.put("wizardDeath", clip);

		clip = loadClip("sounds/gameover.wav");
		clips.put("gameover", clip);

		clip = loadClip("sounds/victory.wav");
		clips.put("victory", clip);

		volume = 1.0f;
	}

	public static SoundManager getInstance() { // class method to retrieve instance of Singleton
		if (instance == null)
			instance = new SoundManager();

		return instance;
	}

	public Clip loadClip(String fileName) { // gets clip from the specified file
		AudioInputStream audioIn;
		Clip clip = null;

		try {
			File file = new File(fileName);
			audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (Exception e) {
			System.out.println("Error opening sound files: " + e);
		}
		return clip;
	}

	public Clip getClip(String title) {

		return clips.get(title);
	}

	public void playClip(String title, boolean looping) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.setFramePosition(0);
			if (looping)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
	}

	public void stopClip(String title) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.stop();
		}
	}

}