package framework.resources;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
  public Clip clip;
  File soundFiles[] = new File[30];

  public Sound() {
    //        soundFiles[0] = new File("sound/music01.wav");
    //        soundFiles[1] = new File("sound/success.wav");
  }

  public void setFile(int i) {
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(soundFiles[i]);
      clip = AudioSystem.getClip();
      clip.open(ais);
    } catch (Exception e) {

    }
  }

  public void play() {
    clip.start();
  }

  public void loop() {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void stop() {
    clip.stop();
  }
}
