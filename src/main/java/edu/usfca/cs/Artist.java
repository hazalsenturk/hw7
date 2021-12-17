package edu.usfca.cs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Artist is the class to create Artist objects for the library
 * Extends the Entity class
 * Has two different constructors
 * Uses setters and getters to retrieve instances of the object
 * To write a file or send a request to a third party data provider it can create following formats:
 * XML, HTML, JSON
 * To write/read from the music.db it creates the right format with the methods: toSQL, fromSQL
 * <p>
 * @author      Gizem Hazal Senturk
 * @since       1.0
 */


public class Artist extends Entity {

    protected ArrayList<Song> songs;
    protected ArrayList<Album> albums;

    public Artist(String name) {
        super(name);
    }

    public Artist() {

    }

    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    protected ArrayList<Album> getAlbums() {
        return albums;
    }

    protected void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public void addSong(Song s) {
        songs.add(s);
    }

    /**
     * Formats Artist's info into HTML
     */
    public String toHTML() {
        return super.toHTML() + " <br> " + this.name + " </br>";
    }

    /**
     * Formats Artist's info into XML
     */
    public String toXML() {
        return "\t<artist id= \"" + this.entityID + "\">\n" +
                "\t\t<name>" + this.name + "</name>\n" +
                "\t</artist>";
    }


    /**
     * Formats Artist's info into JSON
     */

    public String toJSON() {
        return "{" +
                "\"id\": \"" + this.entityID + "\","+
                "\"name\": \""  + this.name + "\""+
                "}";
    }

    /**
     * Formats Artist's info for DB
     */
    public String toSQL() {
        return "insert or ignore into artists values(" + this.entityID+ ", '" + this.name+  "')";

    }

    /**
     * Filters information of the Artist by the data retrieved from DB
     * Retrieves Artist's ID and name
     * @param rs Bulk data retrieved from DB
     * @throws SQLException
     */
    public void fromSQL(ResultSet rs) throws SQLException {
        this.entityID = rs.getInt("id");
        this.name = rs.getString("name");
    }
}
