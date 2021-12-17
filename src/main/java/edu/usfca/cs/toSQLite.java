package edu.usfca.cs;

import org.json.simple.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * toSQLite is the class to manage the DB
 * Retrieves data: fromSQL
 * Edits data: toSQL
 * <p>
 *
 * @author Gizem Hazal Senturk
 * @since 1.0
 */


public class toSQLite {

    public static Song s;
    public static Album alb;
    public static Artist art;
    public static ResultSet rs;


    /**
     * Returns data from the DB tables: songs, albums, artists
     */
    public static ResultSet fromSQL() {


        Connection connection = null;
        try {
            // create a database connection

            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            Scanner sc = new Scanner(System.in);
            System.out.println("Type the table name you like to retrieve the data from: songs, albums or artists");
            String input = sc.nextLine().toLowerCase();

            ResultSet rs = statement.executeQuery("select * from " + input);

            if (input.equals("songs")) {
                {
                    int count = 0;
                    while (rs.next()) {
                        count++;
                    }

                    ArrayList<Song> list = new ArrayList<>();


                    for (int i = 0; i < count; i++) {
                        list.add(i, new Song());

                    }

                    //System.out.println(count);

                    System.out.println(list.size());
                    ResultSet rv = statement.executeQuery("select * from " + input);

                    for (Song s : list) {

                        if (rv.next()) {
                            s.fromSQL(rs);
                        }
                    }
                    System.out.println(list.size());


                    for (Song s : list
                    ) {
                        System.out.println("id =" + s.entityID + "\nname " + s.name);
                    }
                }
            } else if (input.equals("artists")) {
                {
                    int count = 0;
                    while (rs.next()) {
                        count++;
                    }


                    ArrayList<Artist> list = new ArrayList<>();

                    for (int i = 0; i < count; i++) {
                        list.add(i, new Artist());
                    }

                    //System.out.println(count);

                    System.out.println(list.size());
                    ResultSet rv = statement.executeQuery("select * from " + input);

                    for (Artist s : list) {

                        if (rv.next()) {
                            s.fromSQL(rs);
                        }
                    }
                    System.out.println(list.size());

                    for (Artist s : list
                    ) {
                        System.out.println("id =" + s.entityID + "\nname " + s.name);
                    }
                }
            } else if (input.equals("albums")) {
                {
                    int count = 0;
                    while (rs.next()) {
                        count++;
                    }


                    ArrayList<Album> list = new ArrayList<>();

                    for (int i = 0; i < count; i++) {
                        list.add(i, new Album("", ""));
                    }

                    //System.out.println(count);

//                    System.out.println(list.size());
                    ResultSet rv = statement.executeQuery("select * from " + input);

                    for (Album s : list) {

                        if (rv.next()) {
                            s.fromSQL(rs);
                        }
                    }
//                    System.out.println(list.size());

                    for (Album s : list
                    ) {
                        System.out.println("id =" + s.entityID + "\nname " + s.name + "\nartist id  " + s.artist.entityID);
                    }
                }
            }

        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return rs;

    }

    /**
     * Edits data in the DB tables: songs, albums or artists
     *
     * @param input user prompt for the table they want to edit
     * @param obj   JSONObject retrieved from third party data provider to process
     */

    public void toSQLite(String input, JSONObject obj) {
        Connection connection = null;
        try {
            // create a database connection

            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            if (input.equals("song")) {
                int t1 = Integer.parseInt((String) obj.get("idAlbum"));
                String t2 = (String) obj.get("strAlbum");
                alb = new Album(t2, "");
                alb.entityID = t1;


                int t3 = Integer.parseInt((String) obj.get("idArtist"));
                String t4 = (String) obj.get("strArtist");
                art = new Artist(t4);
                art.entityID = t3;
                alb.artist.entityID = art.entityID;

                int t5 = Integer.parseInt((String) obj.get("idTrack"));
                String t6 = (String) obj.get("strTrack");
                String gen = (String) obj.get("strGenre");
                String a1 = (String) obj.get("strArtist");
                s = new Song(t6, "", a1, gen);
                s.entityID = t5;
                s.artist.entityID = art.entityID;
                s.album.entityID = alb.entityID;
                int counter = 0;
                ResultSet rs = statement.executeQuery("select * from artists");
                while (rs.next()) {
                    // read the result set
                    if (art.entityID == rs.getInt("id")) {
                        counter++;
                        break;
                    }
//                    System.out.println("id = " + rs.getInt("id"));
//                    System.out.println("name = " + rs.getString("name"));
//                    System.out.println("artist = " + rs.getInt("artist"));
//                    System.out.println("album = " + rs.getInt("album"));
                }

                if (counter == 0) {
                    statement.executeUpdate(art.toSQL());
                }
                counter = 0;

                ResultSet rv = statement.executeQuery("select * from albums");
                while (rv.next()) {
                    // read the result set
                    if (alb.entityID == rv.getInt("id")) {
                        counter++;
                        break;
                    }
//                    System.out.println("id = " + rs.getInt("id"));
//                    System.out.println("name = " + rs.getString("name"));
//                    System.out.println("artist = " + rs.getInt("artist"));
//                    System.out.println("album = " + rs.getInt("album"));
                }
                if (counter == 0) {
                    statement.executeUpdate(alb.toSQL());
                }
                statement.executeUpdate(s.toSQL());


//                while (rs.next()) {
//                    // read the result set
//                    System.out.println("id = " + rs.getInt("id"));
//                    System.out.println("name = " + rs.getString("name"));
//                    System.out.println("artist = " + rs.getInt("artist"));
//                    System.out.println("album = " + rs.getInt("album"));
//                }
            } else if (input.equals("album")) {

                //alb.artist.entityID = art.entityID;


                int a2 = Integer.parseInt((String) obj.get("idArtist"));
                String b2 = (String) obj.get("strArtist");
                art = new Artist(b2);
                art.entityID = a2;

                int t1 = Integer.parseInt((String) obj.get("idAlbum"));
                String t2 = (String) obj.get("strAlbum");
                alb = new Album(t2, "");
                alb.entityID = t1;
                alb.artist.entityID = art.entityID;
                int counter = 0;
                ResultSet rs = statement.executeQuery("select * from artists");
                while (rs.next()) {
                    // read the result set
                    if (art.entityID == rs.getInt("id")) {
                        counter++;
                        break;
                    }
//                    System.out.println("id = " + rs.getInt("id"));
//                    System.out.println("name = " + rs.getString("name"));
//                    System.out.println("artist = " + rs.getInt("artist"));
//                    System.out.println("album = " + rs.getInt("album"));
                }

                if (counter == 0) {
                    statement.executeUpdate(art.toSQL());
                }

                statement.executeUpdate(alb.toSQL());


                //ResultSet rs = statement.executeQuery("select * from albums");
//                while (rs.next()) {
//                    // read the result set
//                    System.out.println("id = " + rs.getInt("id"));
//                    System.out.println("name = " + rs.getString("name"));
//                    System.out.println("artist = " + rs.getInt("artist"));
//                    System.out.println("Number of songs = " + rs.getInt("nSongs"));
//                }
            } else if (input.equals("artist")) {
                //System.out.println(obj.get("idArtist"));
                int t1 = Integer.parseInt((String) obj.get("idArtist"));
                String t2 = (String) obj.get("strArtist");
                art = new Artist(t2);
                art.entityID = t1;
                //System.out.println(art.name + art.entityID);
                ResultSet r = statement.executeQuery("select * from artists");
//                while (r.next()) {
//                    // read the result set
////                    System.out.println("name = " + rs.getString(println("id = " + r.getInt("id"));
////                    System.out."name"));
//                    if(art.entityID)
//                }

                statement.executeUpdate(art.toSQL());

                ResultSet rs = statement.executeQuery("select * from artists");
//                while (rs.next()) {
//                    // read the result set
//                    System.out.println("id = " + rs.getInt("id"));
//                    System.out.println("name = " + rs.getString("name"));
//                }
            }

        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

}
