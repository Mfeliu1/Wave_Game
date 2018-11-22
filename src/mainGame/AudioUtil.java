package mainGame;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioUtil {

    private static Clip gameClip, menuClip, clip;
    private static boolean paused = false;

	public static void playClip(final String path, boolean repeat) {
		try {
			if (clip != null && clip.isRunning()) {
				return;
			}
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(AudioUtil.class.getResource(path).toURI()));
	        clip = AudioSystem.getClip();
			clip.open(inputStream);
			((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(5f);
	        if (repeat) {
	        		clip.loop(Clip.LOOP_CONTINUOUSLY);
	        } else {
	        		clip.loop(0);
	        }
		} catch (Exception ex) {
			clip.stop();
			clip.close();
		}
	}	
	
	public static void stopCurrentClip() {
		System.out.println("YOU JUST CALLED A CLIP TYPE: " + clip);
		if (clip != null) {
			clip.stop();
			clip.close();
			clip = null;
		}
	}
	
	public static void playMenuClip(boolean repeat, boolean africa) {
		try {
			closeMenuClip();

			// If africa mode is enabled, use the africa clip for the menu
			String clip = africa ? "../gameSound/africa.wav" : "../gameSound/spacejam.wav";

			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(AudioUtil.class.getResource(clip).toURI()));
	        menuClip = AudioSystem.getClip();
	        ((FloatControl) gameClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-2f);
			menuClip.open(inputStream);
	        if (repeat) {
	        		menuClip.loop(Clip.LOOP_CONTINUOUSLY);
	        } else {
	        		menuClip.loop(1);
	        }
		} catch (Exception ex) {
			System.out.println("\nException while playing menu clip: " + ex.getLocalizedMessage());
		}
	}
	
	public static void closeMenuClip() {
		if (menuClip != null) {
			menuClip.stop();
			menuClip.close();
		}
	}
	
	public static void playGameClip(boolean repeat) {
		try {
			closeGameClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(AudioUtil.class.getResource("../gameSound/battle.wav").toURI()));
	        gameClip = AudioSystem.getClip();
			gameClip.open(inputStream);
			((FloatControl) gameClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-8f);
	        if (repeat) {
	        		gameClip.loop(Clip.LOOP_CONTINUOUSLY);
	        } else {
	        		gameClip.loop(1);
	        }
		} catch (Exception ex) {
			System.out.println("\nException while playing game clip: " + ex.getLocalizedMessage());
		}
	}
	
	public static void pauseGameClip() {
		if(paused) {
			gameClip.start();
			paused = false;
		}
		else {
			gameClip.stop();
			paused = true;
		}
	}
	
	public static void closeGameClip() {
		if (gameClip != null) {
			gameClip.stop();
			gameClip.close();
		}
	}
}



