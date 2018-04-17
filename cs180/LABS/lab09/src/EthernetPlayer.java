/**
 * Created by farhanshafi on 10/25/16.
 */
import java.util.ArrayList;

/**
 * Class EthernetPlayer
 */

public class EthernetPlayer extends MusicPlayer {

    private int deviceID;
    private int connStatus;

    static final int CONNECTED = 1;
    static final int NOT_CONNECTED = 0;

    private ArrayList<String> downloadList = new ArrayList<String>();

    public EthernetPlayer(int id) {
        super(); //Super calls the constructor of the class which is being extended
        deviceID = id;
        connStatus = NOT_CONNECTED;

        downloadList.add("Dark Horse");
        downloadList.add("Royals");
        downloadList.add("Counting Stars");
        downloadList.add("Let Her Go");
        downloadList.add("The Fox");
    }

    /**
     *  @override turnOn and connect
     *
     */
    public void turnOn() {
        //status = ON;
        super.turnOn();
        connStatus = CONNECTED;
        //System.out.println("Ethernet Player is on!");
    }

    /**
     *  @override turnOff and disconnect
     *
     */
    public void turnOff() {
       // status = OFF;
        super.turnOff();
        connStatus = NOT_CONNECTED;
        //System.out.println("Ethernet player is OFF");
    }


    /**
     *  addTrackToPlaylist: Adds mentioned track to the end of playlist array
     *  print appropriate messages to stdout
     *
     */
    public void addToPlaylist(String trackName) {
        downloadList.add(trackName);
        System.out.println("Added " + trackName + " to playlist");
        playlist.add(trackName);
    }

    /**
     *  deleteFromPlaylist: deletes track of give name from the playlist
     *  print appropriate messages to stdout
     *
     */
    public void deleteFromPlaylist(String trackName) {
        if (playlist.contains(trackName)==true) {
            int toRemove = playlist.indexOf(trackName);
            playlist.remove(toRemove);
            System.out.println("Removed " + trackName + " from playlist");
        }else System.out.println("Removed " + trackName + "From playlist");
    }

    /**
     *  download: Downloads mentioned song from the given Download list and adds to playlist
     *  print appropriate messages to stdout
     *
     */
    public void download(String trackName) {
        //implement this section of code to lookup tracName in the downloadlist and
        if (downloadList.contains(trackName) == true) {
            playlist.add(trackName);
            System.out.println("Downloaded: " + trackName);
        }else System.out.println("Track " + trackName + " not available for download");
    }

}
