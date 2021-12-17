package edu.usfca.cs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Playlist is the class to create Artist objects for the library
 * Extends the Entity class
 * contains array list of Songs, Artists and Albums as objects
 * writeXML method: writes the playlist content to an XML file
 * has a main method to test writeXML()
 * <p>
 * @author      Gizem Hazal Senturk
 * @since       1.0
 */

public class Playlist extends Entity{

    public static ArrayList<Song> songs;
    public static ArrayList<Album> albums;
    public static ArrayList<Artist> artists;

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    public Playlist() {

    }

    /**
     * Constructor.
     * @param name playlist name
     * Array list of Songs, Albums and Artists objects are the type variables
     */
    public Playlist(String name) {
        super(name);
        songs = new ArrayList<Song>();
        artists = new ArrayList<Artist>();
        albums = new ArrayList<Album>();
    }

    /**
     * Adds songs of an album to a Playlist
     * @param s name of a Song to add
     */
    public static void addAlbums(Song s) {
        if(!albums.stream().anyMatch( x -> s.getAlbum().equals(x))){
            albums.add(s.getAlbum());
        }
    }

    /**
     * Adds a unique artist to a Playlist
     * @param s name of a Song to get the Artist
     */
    public static void addArtist(Song s) {
        if(!artists.stream().anyMatch( x -> s.getArtist().equals(x))){
            artists.add(s.getArtist());
        }
    }

    /**
     * Adds a song, artist of the song and album of the song to a Playlist
     * @param s name of a Song
     * @return boolean
     */
    public boolean addSong(Song s) {
        addAlbums(s);
        addArtist(s);
        return songs.add(s);
    }

    /**
     * Deletes a song from the Playlist
     * @param s name of a Song to get the Artist
     * @return boolean
     */
    public boolean deleteSong(Song s) {
        return songs.remove(s);
    }

    /**
     * Finds a song in the Playlist
     * @param s name of an Song to get the Artist
     * @return boolean
     */
    public boolean findSong(Song s){
        return songs.contains(s);
    }

    /**
     * Deletes a song from the Playlist
     * Sorts the list
     * Uses Comparable Class
     * @return list of songs
     */
    public List<Song> sortList() {
        List<Song> sl = songs.stream().sorted(Comparator.comparing(Song::getLiked)).collect(Collectors.toList());
        return sl;
    }

    /**
     * @return Arraylist of Song objects from the Playlist
     */
    public ArrayList<Song> getSongs(){
        ArrayList<Song> list = new ArrayList<Song>();
        list.addAll(songs);
        return list;
    }

    /**
     * @return  Arraylist of Album objects from the Playlist
     */
    public ArrayList<Album> getAlbums(){
        ArrayList<Album> list = new ArrayList<>();
        list.addAll(albums);
        return list;
    }

    /**
     * @return  Arraylist of Artist objects from the Playlist
     */
    public ArrayList<Artist> getArtist(){
        ArrayList<Artist> list = new ArrayList<>();
        list.addAll(artists);
        return list;
    }

    /**
     * Merges two list of Songs
     * @return  List of Songs
     */
    public List<Song> mergeLists(List<Song> otherList){

        List<Song> list2 = new ArrayList<Song>(songs);
        list2.removeAll(otherList);
        songs.addAll(list2);
        System.out.println(songs);
        return songs;

    }

    /**
     * Shuffles the list of Songs
     * @return  List of Songs
     */
    public List<Song> shuffleList() {
        Collections.shuffle(songs);
        return songs;
    }

    /**
     * Creates a shuffled list of Songs
     * @return  List of Songs based on genre
     */
    public List<Song> randomList(String g){
        List<Song> list = songs.stream().filter(s -> s.genre.equals(g)).collect(Collectors.toList());
        return list;
    }

    /**
     * Writes the list of Songs, Albums and Artists to an XML file
     * creates a new XML file
     */
    public static void writeXML(){

        try {
            FileWriter fw = new FileWriter("sample.xml");
            fw.write("<Playlist>\n" );
            fw.write("<songs>\n" );
            for (Song s: songs) {
                fw.write(s.toXML()+ "\n");
            }
            fw.write("</songs>\n" );

            fw.write("<artists>\n" );
            for (Artist art: artists) {
                fw.write(art.toXML()+ "\n");
            }
            fw.write("</artists>\n" );

            fw.write("<albums>\n" );
            for (Album a: albums) {
                fw.write(a.toXML()+ "\n");
            }
            fw.write("</albums>\n" );

            fw.write("</Playlist>");

            fw.close();
            System.out.println("Successfully written to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}