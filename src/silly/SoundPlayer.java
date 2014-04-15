package silly;

import java.io.BufferedInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer 
{
	//SOUND
	public static synchronized void PlaySound(final String url) 
	{
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        java.io.InputStream audioSrc = this.getClass().getResourceAsStream(url);
		        BufferedInputStream bufferedInput = new BufferedInputStream(audioSrc);
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedInput);
		        
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		    	  
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
	}
}
