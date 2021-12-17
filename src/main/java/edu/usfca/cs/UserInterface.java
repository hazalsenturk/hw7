package edu.usfca.cs;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * UserInterface is the interface class for users to access and manage their music library stored in music.db
 * it uses SQLite as a DBMS to access the data in music.db
 * if user choose to display the library, the data back from songs, albums,artists tables of the database
 * if user choose to new, asks for the type of the new item; song, album or artist
 * for the related item the methods used in the interface searches for the item in API and adds it to the database:
 * The URL for the AudioDb API guide: https://www.theaudiodb.com/api_guide.php
 * <p>
 *
 * @author      Gizem Hazal Senturk
 * @since       1.0
 */

public class UserInterface
{
    static toSQLite sq;
    static restAPI ra;
    public static void main( String[] args )
    {
        Boolean isRun = true;
        sq = new toSQLite();

        while(isRun){
            Scanner sc = new Scanner(System.in);

            System.out.println("\n\nMENU ");
            System.out.println("Type display to go into your library ");
            System.out.println("Type new if you want to add new item to your lists");
            System.out.println("Type XML to create an XML file");
            System.out.println("Type exit to quit the app\n");

            String input = sc.nextLine();

            if (input.toLowerCase().equals("display")) {
                sq.fromSQL();
            } else if (input.toLowerCase().equals("new")) {
                System.out.println("Type song, album or artist to create new item in related table");
                String inp = sc.nextLine();
                String artName = "";
                String label, albName;

                if (inp.equals("song")) {
                    System.out.println("Type the artist's name");
                    artName = sc.nextLine().toLowerCase();
                    System.out.println("Type name of the " + inp + " you would like to add");
                    label = sc.nextLine().toLowerCase();
                    System.out.println("Type name of the Album you would like to add");
                    albName = sc.nextLine().toLowerCase();
                    JSONObject obj = ra.JSONTracks(label,inp, artName, albName );
                    sq.toSQLite(inp, obj);
                } else if (inp.equals("album")) {
                    System.out.println("Type the artist's name");
                    artName = sc.nextLine().toLowerCase();
                    System.out.println("Type name of the " + inp + " you would like to add");
                    albName = sc.nextLine().toLowerCase();
                    //JSONObject obj = ra.JSONAlbum(albName, inp, artName, albName);
                    JSONArray array = restAPI.JSONTAlbum(albName, inp, artName, albName);
                    sq.toSQLite(inp, (JSONObject) array.get(0));
                    for (Object obj:array){
                        JSONObject obj1 = (JSONObject) obj;
                        System.out.println(obj1.get("strTrack"));
                         sq.toSQLite("song", obj1);
                    }
                } else if (inp.equals("artist")){
                    System.out.println("Type name of the " + inp + " you would like to add");
                    label = sc.nextLine().toLowerCase();
                    artName = label;
                    JSONObject obj = ra.JSONArtist(label,inp,artName);
                    sq.toSQLite(inp, obj);
                }

            } else if (input.toLowerCase().equals("xml")) {
                Library.writeXML();
            } else {
                isRun = false;
            }
        }
    }
}
