package edu.usfca.cs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Album is the class to create Album objects for the library
 * Extends the Entity class
 * Has three different constructors
 * Uses setters and getters to retrieve instances of the object
 * To write a file or send a request to a third party data provider it can create following formats:
 * XML, HTML, JSON
 * To write/read from the music.db it creates the right format with the methods: toSQL, fromSQL
 * <p>
 * @author      Gizem Hazal Senturk
 * @since       1.0
 */

public class Album extends Entity {
    protected ArrayList<Song> songs;
    protected Artist artist;

    /**
     * Constructor.
     * @param name album name
     */
    public Album(String name) {
        super(name);
        songs = new ArrayList<Song>();
    }

    /**
     * Constructor.
     * @param name album name
     * @param name artist name, creates Artist object from Artist class
     */
    public Album(String name, String artist) {
        super(name);
        songs = new ArrayList<Song>();
        this.artist = new Artist(artist);
    }

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    public Album() {

    }

    public String getName() {
        return name;
    }

    /**
     * Checks if two albums are identical
     * @param otherAlbum name of an Album object
     */
    public boolean equals(Album otherAlbum) {
        if ((this.artist.equals(otherAlbum.getArtist())) &&
                (this.name.equals(otherAlbum.getName()))) {
            return true;
        } else {
            return false;
        }
    }

    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

//    public String toString() {
//        return super.toString() + " " + this.artist;
//    }

    /**
     * Formats Album's info into HTML
     */
    public String toHTML() {
        return super.toHTML() + " <br> " + this.artist + " </br>";
    }

    /**
     * Formats Album's info into XML
     */
    public String toXML() {
        return "\t<album id= \"" + this.entityID + "\">\n" +
                "\t\t<title>" + this.name + "</title>\n" +
                "\t</album>";
    }

    /**
     * Formats Album's info into JSON
     */
    public String toJSON() {
        return "{" + "\"id\": \"" + this.entityID + "\","+
                "\"name\": \""  + this.name + "\""+
                "}";
    }

    /**
     * Formats Album's info for DB
     */
    public String toSQL() {
        return "insert or ignore into albums(id, name, artist) values(" + this.entityID+ ", '" + this.name+ "'," +  this.artist.entityID + ")";

}

    /**
     * Filters information of the Artist by the data retrieved from DB
     * Retrieves Album's ID, name and Artist's ID to be used as a Foreign Key in DB
     * @param rs Bulk data retrieved from DB
     * @throws SQLException
     */
    public void fromSQL(ResultSet rs) throws SQLException {
        this.entityID = rs.getInt("id");
        this.name = rs.getString("name");
        this.artist.entityID = rs.getInt("artist");

    }
}
