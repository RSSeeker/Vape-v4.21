package gg.vape.notification;

import gg.vape.Vape;
import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundClip {
    private static final String[] SUPPORTED_EXTENSIONS = new String[]{".wav", ".au", ".aif", ".aiff"};
    private final byte[] audioData;
    private Clip clip;

    public ByteArrayInputStream openStream() {
        return new ByteArrayInputStream(this.audioData);
    }

    private static byte[] loadAudioData(String resourceName) {
        if (resourceName.contains(".")) {
            String resourcePath = "sounds/" + resourceName;
            byte[] data = Vape.readResource(resourcePath);
            if (data != null) {
                return data;
            }
            throw new IllegalArgumentException("Missing sound resource: " + resourceName);
        }
        for (String extension : SUPPORTED_EXTENSIONS) {
            String resourcePath = "sounds/" + resourceName + extension;
            byte[] data = Vape.readResource(resourcePath);
            if (data == null) continue;
            return data;
        }
        throw new IllegalArgumentException(
                "Missing sound resource with supported extensions: " + resourceName);
    }

    public SoundClip(String resourceName) {
        this.audioData = SoundClip.loadAudioData(resourceName);
    }

    public void play(float volumePercent) {
        if (this.clip == null) {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.openStream());
                this.clip = AudioSystem.getClip();
                this.clip.open(audioStream);
            }
            catch (Exception error) {
                Vape.logThrowable(error);
            }
        }
        if (this.clip == null) {
            return;
        }
        FloatControl gainControl = (FloatControl)this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20.0f * (float)Math.log10((double)volumePercent / 100.0));
        this.clip.setFramePosition(0);
        this.clip.start();
        try {
            Thread.sleep(this.clip.getMicrosecondLength() / 1000L);
        }
        catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
    }

    private static Exception propagateException(Exception error) {
        return error;
    }
}
