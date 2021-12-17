package edu.usfca.cs;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

/**
 * Library is the class to create Library object
 * Extends the Entity class
 * contains array list of Songs, Artists and Albums as objects
 * writeXML method: writes the playlist content to an XML file
 * writeJSON method: writes the playlist content to a JSON file
 * <p>
 * @author      Gizem Hazal Senturk
 * @since       1.0
 */

public class Library extends Entity {

    public static ArrayList<Song> songs;
    public static ArrayList<Album> albums;
    public static ArrayList<Artist> artist;
    public ArrayList<Integer> listID = new ArrayList<>();

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    public Library() {
        songs = new ArrayList<Song>();
        albums = new ArrayList<Album>();
        artist = new ArrayList<Artist>();
    }

    /**
     * Adds songs of an album to a Library
     * @param a name of an Album to add
     */
    public static void addAlbums(Album a) {
        if(!albums.stream().anyMatch( x -> a.equals(x))){
            albums.add(a);
        }
    }

    /**
     * Adds a unique artist to a Library
     * @param a name of an Artist to get the Artist
     */
    public static void addArtist(Artist a) {
        if(!artist.stream().anyMatch( x -> a.equals(x))){
            artist.add(a);
        }
    }

    /**
     * Adds a song, artist of the song and album of the song to a Library
     * @param s name of a Song
     */
    public static void addSongs(Song s) {
        if (!songs.contains(s)) {
            songs.add(s);
            addAlbums(s.getAlbum());
            addArtist(s.getArtist());
        }
        addAlbums(s.getAlbum());
        addArtist(s.getArtist());
    }

    /**
     * Adds a song to the list that is already in XML format
     * @param s name of a Song
     */

    public static void addSongsXML(Song s) {
        if (!songs.contains(s)) {
            songs.add(s);
        }
    }

    /**
     * Checks if a song is in the library
     * @return boolean
     */
    public boolean checkDup(Song sng){
        if (listID.contains(sng.entityID)) {
            return false;
        } else {
            for (Song s : songs) {
                if (sng.entityID != s.entityID) {
                    if (
                            sng.getName().equals(s.getName()) &&
                                    (sng.getAlbum().getName().equals(s.getAlbum().getName()) ||
                                            sng.getArtist().getName().equals(s.getArtist().getName()))) {
                        listID.add(s.entityID);
                        return true;
                    } else if (
                            sng.getAlbum().getName().equals(s.getAlbum().getName()) &&
                                    sng.getArtist().getName().equals(s.getArtist().getName()) &&
                                    sng.getName().toLowerCase().replaceAll("\\p{Punct}", "")
                                            .equals(s.getName().toLowerCase().replaceAll("\\p{Punct}", "")))
                    {
                        listID.add(s.entityID);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Checks if a song is duplicated in the Library
     */

    public void duplicates() {
        if (songs.stream()
                .anyMatch(sng -> checkDup(sng) == true)) {
            Scanner sc = new Scanner(System.in);
            System.out.println("There are duplicates in the library. Do you wish to remove them? (y/n)");
            String input = sc.nextLine().toLowerCase();
            if (input.equals("y")) {
                songs.removeIf(sng -> listID.contains(sng.entityID));
            } else {
                System.out.println(" Invalid input.");
            }
        } else {
            System.out.println("There are no duplicate songs in the library.");
        }
    }
    /**
     * Checks if a song is in the library
     * @return boolean
     */
    public boolean findSongs(Song s) {
        return songs.contains(s);
    }

    /**
     * Checks if a song liked
     * @return boolean
     */
    public boolean getLiked(boolean liked) {

        if (liked == true) {
            return true;
        }
        return false;

    }
    public ArrayList<Artist> getArtists() {
        return artist;
    }
    public ArrayList<Album> getAlbums() {
        return albums;
    }
    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * Writes the list of Songs, Albums and Artists to an XML file
     * creates a new XML file
     */
    public static void writeXML(){

        try {
            FileWriter fw = new FileWriter("sample.xml");
            fw.write("<Library>\n" );
            fw.write("<songs>\n" );
            for (Song s: songs) {
                fw.write(s.toXML()+ "\n");
            }
            fw.write("</songs>\n" );

            fw.write("<artists>\n" );
            for (Artist art: artist) {
                fw.write(art.toXML()+ "\n");
            }
            fw.write("</artists>\n" );

            fw.write("<albums>\n" );
            for (Album a: albums) {
                fw.write(a.toXML()+ "\n");
            }
            fw.write("</albums>\n" );

            fw.write("</Library>");

            fw.close();
            System.out.println("Successfully written to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


    /**
     * Writes the list of Songs, Albums and Artists to a JSON file
     * creates a new JSON file
     */
    public static void writeJSON(){

        try {
            FileWriter fw = new FileWriter("sample.json");
            fw.write("{\n" );
            fw.write("\"songs\" : [ \n" );
            int size = 0;
            for (Song s: songs) {

                fw.write(s.toJSON()+ "\n");
                size++;

                if(size <= songs.size() - 1){
                    System.out.println(size);
                    fw.write("," );
                }
            }
            fw.write("],\n" );
            size = 0;
            fw.write("\"artists\" : [ \n" );

            for (Artist art: artist) {

                fw.write(art.toJSON()+ "\n");
                size++;

                if(size <= artist.size() - 1){
                    System.out.println(size);
                    fw.write("," );
                }
            }
            fw.write("],\n" );
            size = 0;
            fw.write("\"albums\" : [ \n");
            for (Album a: albums) {
                fw.write(a.toJSON()+ "\n");
                size++;

                if(size <= albums.size() - 1){
                    System.out.println(size);
                    fw.write("," );
                }
            }

            fw.write("]\n}" );
            fw.close();

            System.out.println("Successfully written to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}


