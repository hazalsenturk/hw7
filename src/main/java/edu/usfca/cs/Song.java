package edu.usfca.cs;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Song is the class to create Song objects for the library
 * Extends the Entity class
 * contains six different constructors
 * Uses setters and getters to retrieve instances of the object
 * To write a file or send a request to a third party data provider it can create following formats:
 * XML, HTML, JSON
 * To write/read from the music.db it creates the right format with the methods: toSQL, fromSQL
 * <p>
 * @author      Gizem Hazal Senturk
 * @since       1.0
 */

public class Song extends Entity {
    protected Album album;
    protected Artist artist;
    // protected SongInterval duration;
    protected String genre;
    protected Boolean liked;

    /**
     * Constructor.
     * @param name Song's name
     * @param artist Artist's name, creates Artist object from Artist class
     * @param album Album's name, creates Album object from Album class
     */
    public Song(String name, String album, String artist) {
        super(name);
        this.artist = new Artist(artist);
        this.album = new Album(album, artist);
    }

    /**
     * Constructor.
     * @param name Song's name
     * @param length song interval
     * @param artist Artist's name, creates Artist object from Artist class
     * @param album Album's name, creates Album object from Album class
     * @param liked boolean, liked song
     */
    public Song(String name, int length, String album, String artist, boolean liked) {
        super(name);
        //this.duration = new SongInterval(length);
        this.artist = new Artist(artist);
        this.album = new Album(album, artist);
        this.liked = liked;
        genre = "";
    }

    /**
     * Constructor.
     * @param name Song's name
     * @param length song interval
     * @param artist Artist's name, creates Artist object from Artist class
     * @param album Album's name, creates Album object from Album class
     * @param liked boolean, liked song
     * @param genre song's genre
     */
    public Song(String name, int length, String album, String artist, boolean liked, String genre) {
        super(name);
        //this.duration = new SongInterval(length);
        this.artist = new Artist(artist);
        this.album = new Album(album, artist);
        this.liked = liked;
        this.genre = genre;
    }

    /**
     * Constructor.
     * @param name Song's name
     * @param artist Artist's name, creates Artist object from Artist class
     * @param album Album's name, creates Album object from Album class
     * @param genre song's genre
     */
    public Song(String name, String album, String artist, String genre) {
        super(name);
        this.artist = new Artist(artist);
        this.album = new Album(album, artist);
        this.genre = genre;
    }

    /**
     * Constructor.
     * @param name Song's name
     */
    public Song(String name) {
        super(name);
        //duration = new SongInterval(length);
        genre = "";
    }

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    public Song() {
    }

    /**
     * Checks if two songs are identical
     * @param otherSong name of an Song object
     */
    public boolean equals(Song otherSong) {
        if ((this.album.name.equals(otherSong.getAlbum().name) &&
                this.name.equals(getName()) &&
                this.artist.name.equals(otherSong.getArtist().name))) {
            return true;
        } else {
            return false;
        }
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    /*public void setLength(int length) {
      duration = new SongInterval(length);
   }*/

/*   public String showLength() {
        return duration.toString();
   }*/

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }


    protected Album getAlbum() {
        return album;
    }

    protected void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * Formats Song's info into string
     */
    public String toString() {
        return super.toString() + " " + this.artist + " " + this.album  /*+ " " + this.duration*/;

    }

    /**
     * Formats Song's info into XML
     */
    public String toXML() {
        return "\t<song id= \"" + this.entityID + "\">\n" + "\t\t<title>" + this.name + "</title>\n" +
                "\t\t<artist id=\"" + this.artist.entityID + "\">" + this.artist.name + "</artist>\n"
                + "\t\t<album id=\"" + this.album.entityID + "\">" + this.album.name + "</album> </song>";
    }

    /**
     * Formats Song's info into JSON
     */
    public String toJSON() {
        return "{" +
                "\"id\": \"" + this.entityID + "\"," + "\"title\": \"" + this.name + "\"," + "\"artist\": {" +
                "\"id\": \"" + this.artist.entityID + "\"," + "\"name\": \"" + this.artist.name + "\"" + "}," +
                "\"album\": {" + "\"id\": \"" + this.album.entityID + "\"," + "\"name\": \"" + this.album.name + "\"" + "}" +
                "}";
    }

    /**
     * Formats Song's info for DB
     */
    public String toSQL() {
        return "insert or ignore into songs(id, name, artist, album, genre) values(" + this.entityID + ", '" + this.name + "'," + this.artist.entityID + ", " + this.album.entityID + ", '" + this.genre +"')";

    }

    /**
     * Filters information of the Song by the data retrieved from DB
     * Retrieves Song's ID and name to be used as a Primary Key in DB
     * @param rs Bulk data retrieved from DB
     * @throws SQLException
     */
    public void fromSQL(ResultSet rs) throws SQLException {
        this.entityID = rs.getInt("id");
        this.name = rs.getString("name");

//        this.artist.entityID = rs.getInt("artist");
//        this.album.entityID = rs.getInt("album");
    }

}
