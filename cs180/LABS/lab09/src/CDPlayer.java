/**
 * Created by farhanshafi on 10/25/16.
 */
import java.util.ArrayList;

/**
 * Class CDPlayer
 */
public class CDPlayer extends MusicPlayer {

    private int deviceID;
    private int thisTrack;

    /**
     * Constructor for CD-Player
     */
    public CDPlayer(int id) {
        super();
        deviceID = id;
    }

    /**
     * Over-ride Method: turnOn
     */
    public void turnOn() {
        super.turnOn();
        /*status = ON;
        System.out.println("CD Player is on");*/
    }

    /**
     * Over-ride Method: turnOff
     */
    public void turnOff() {
        super.turnOff();
        /*status = OFF;
        System.out.println("CD Player is Off");*/
    }

    /**
     * Method to play next track in the playlist by
     * printing it to stdout and changing current
     */
    public void nextTrack() {
        thisTrack = thisTrack +1;
        System.out.println("Playing: " + playlist.get(thisTrack));
    }

    /**
     * Method to play previous track in the playlist by
     * printing it to stdout and changing current
     */
    public void previousTrack() {
        thisTrack = thisTrack - 1;
        System.out.println("Playing: " + playlist.get(thisTrack));
    }

    /**
     * Method to play current track
     */
    public void play() {
        System.out.println("Playing: " + playlist.get(thisTrack));
    }

}
