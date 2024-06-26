import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The {@code Sound} class manages playing various sound effects related to chess game events.
 * It uses {@code javax.sound.sampled} API to play WAV audio files.
 *
 * @author SirPatschiii
 * @version 2024-06-26
 */
public class Sound {
    private final File move;
    private final File capture;
    private final File check;
    private final File castle;

    /**
     * Constructs a {@code Sound} object and initializes sound files for different game events.
     * Sound files are expected to be located in the "sounds" directory with specific filenames.
     */
    public Sound() {
        move = new File("sounds/move.wav");
        capture = new File("sounds/capture.wav");
        check = new File("sounds/check.wav");
        castle = new File("sounds/castle.wav");
    }

    /**
     * Plays the sound effect for a move event.
     * This method opens, plays, and closes the audio clip for the move sound.
     * Throws a {@code RuntimeException} if there is an issue with audio playback.
     */
    public void playMoveSound() {
        try (AudioInputStream audioInputStreamMove = AudioSystem.getAudioInputStream(move)) {
            Clip clipMove = AudioSystem.getClip();
            clipMove.open(audioInputStreamMove);
            clipMove.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Plays the sound effect for a capture event.
     * This method opens, plays, and closes the audio clip for the capture sound.
     * Throws a {@code RuntimeException} if there is an issue with audio playback.
     */
    public void playCaptureSound() {
        try (AudioInputStream audioInputStreamCapture = AudioSystem.getAudioInputStream(capture)) {
            Clip clipCapture = AudioSystem.getClip();
            clipCapture.open(audioInputStreamCapture);
            clipCapture.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Plays the sound effect for a check event.
     * This method opens, plays, and closes the audio clip for the check sound.
     * Throws a {@code RuntimeException} if there is an issue with audio playback.
     */
    public void playCheckSound() {
        try (AudioInputStream audioInputStreamCheck = AudioSystem.getAudioInputStream(check)) {
            Clip clipCheck = AudioSystem.getClip();
            clipCheck.open(audioInputStreamCheck);
            clipCheck.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Plays the sound effect for a castling event.
     * This method opens, plays, and closes the audio clip for the castling sound.
     * Throws a {@code RuntimeException} if there is an issue with audio playback.
     */
    public void playCastleSound() {
        try (AudioInputStream audioInputStreamCastle = AudioSystem.getAudioInputStream(castle)) {
            Clip clipCastle = AudioSystem.getClip();
            clipCastle.open(audioInputStreamCastle);
            clipCastle.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
