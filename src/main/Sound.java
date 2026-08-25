package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/Inter_Tide.wav"); // Import my music
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            System.err.println("Sound load failed for index " + i + ": " + e);
            e.printStackTrace();
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

    public void setVolume(float decibels) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(decibels);
    }

}
