package edu.usfca.cs;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * restAPI is the class to interact with a third party music database:https://www.theaudiodb.com/api_guide.php
 * Methods: JSONArtist, JSONAlbum and JSONTracks
 * Global Variables: requestURL, Arraylist, json0
 * <p>
 * @author      Gizem Hazal Senturk
 * @since       1.0
 */

public class restAPI {

    protected static String requestURL;
    protected static Object ArrayList;
    protected static JSONObject jsonO;


    /**
     * Creates a connection for the URL created woth the artist's name
     * Retrieve data and store it in response
     * Parse the response in JSON Object
     * @return JSONObject
     * @param input artist's name
     * @param type "artist"
     * @param artname artist's name
     */
    public static JSONObject JSONArtist(String input, String type, String artname) {

        requestURL = "https://www.theaudiodb.com/api/v1/json/2/search.php?s=" + input;

        StringBuilder response = new StringBuilder();
        URL u;

        try {
            u = new URL(requestURL);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
            return null;
        }
        try {
            URLConnection connection = u.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int code = httpConnection.getResponseCode();

            String message = httpConnection.getResponseMessage();
            System.out.println(code + " " + message);
            if (code != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream instream = connection.getInputStream();
            Scanner in = new Scanner(instream);
            while (in.hasNextLine()) {
                response.append(in.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading response");
            return null;
        }
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray artists = (JSONArray) jsonObject.get("artists");

            JSONObject t2 = (JSONObject) artists.get(0);
            String buf = (String) t2.get("strArtist");
//            System.out.println(input);
//            System.out.println(buf);
            if (input.equalsIgnoreCase(buf)) {
                jsonO = t2;
            }
//            System.out.println(jsonO.get("strArtist"));
//            System.out.println(jsonO);
//            String mood = (String)jsonO.get("strMood");
//            System.out.println("Mood: " + mood);
//            String bio = (String)jsonO.get("strBiographyEN");
//            System.out.println("Biography: " + bio);
//            System.out.println(beatles.get("idArtist"));
            return jsonO;
        } catch (org.json.simple.parser.ParseException e) {
            System.out.println("Error parsing JSON");
            return null;
        }

    }

    /**
     * Creates a connection for the URL created woth the artist's name
     * Retrieve data and store it in response
     * Parse the response in JSON Object
     * @return JSONObject
     * @param input album's name
     * @param type "album"
     * @param artname artist's name
     * @param aName album's name
     */
    public static JSONObject JSONAlbum(String input, String type, String artname, String aName) {
        JSONObject temp = JSONArtist(artname, type, artname);
        //System.out.println(temp.get("a"));
        String t1 = (String) temp.get("idArtist");
        System.out.println(t1);

        // System.out.println("id " + t1);

        requestURL = "https://www.theaudiodb.com/api/v1/json/2/album.php?i=" + t1;


        StringBuilder response = new StringBuilder();
        URL u;
        try {
            u = new URL(requestURL);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
            return null;
        }
        try {
            URLConnection connection = u.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int code = httpConnection.getResponseCode();

            String message = httpConnection.getResponseMessage();
            System.out.println(code + " " + message);
            if (code != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream instream = connection.getInputStream();
            Scanner in = new Scanner(instream);
            while (in.hasNextLine()) {
                response.append(in.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading response");
            return null;
        }

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray album = (JSONArray) jsonObject.get("album"); // get the list of all artists returned.


            for (int i = 0; i < album.size(); i++) {
                JSONObject t2 = (JSONObject) album.get(i);
                String buf = (String) t2.get("strAlbum");
                //System.out.println(input);
                //System.out.println(buf);
                if (aName.equals(buf.toLowerCase())) {
                    jsonO = t2;
                }
            }
            //System.out.println(jsonO.get("strAlbum"));
            //System.out.println(jsonO);
            return jsonO;
        } catch (org.json.simple.parser.ParseException e) {
            System.out.println("Error parsing JSON");
            return null;
        }
    }

    /**
     * Creates a connection for the URL created woth the artist's name
     * Retrieve data and store it in response
     * Parse the response in JSON Object
     * @return JSONObject
     * @param input song's name
     * @param type "song"
     * @param artname artist's name
     * @param albName album's name
     */
    public static JSONObject JSONTracks(String input, String type, String artname, String albName) {
        JSONObject temp = JSONAlbum(input, type, artname, albName);
        String t1 = (String) temp.get("idAlbum");

        requestURL = "https://www.theaudiodb.com/api/v1/json/2/track.php?m=" + t1;

        StringBuilder response = new StringBuilder();
        URL u;
        try {
            u = new URL(requestURL);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
            return null;
        }
        try {
            URLConnection connection = u.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int code = httpConnection.getResponseCode();

            String message = httpConnection.getResponseMessage();
            System.out.println(code + " " + message);
            if (code != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream instream = connection.getInputStream();
            Scanner in = new Scanner(instream);
            while (in.hasNextLine()) {
                response.append(in.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading response");
            return null;
        }

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray tracks = (JSONArray) jsonObject.get("track"); // get the list of all artists returned.


            for (int i = 0; i < tracks.size(); i++) {
                JSONObject t2 = (JSONObject) tracks.get(i);
                String buf = (String) t2.get("strTrack");
                if (input.equals(buf.toLowerCase())) {
                    jsonO = t2;
                }
            }
            //System.out.print(jsonO.get("strTrack") + " "+ jsonO.get("strArtist") + " "+ jsonO.get("strAlbum"));
            return jsonO;
        } catch (org.json.simple.parser.ParseException e) {
            System.out.println("Error parsing JSON");
            return null;
        }

    }


    public static JSONArray JSONTAlbum(String input, String type, String artname, String albName) {
        JSONObject temp = JSONAlbum(input, type, artname, albName);
        String t1 = (String) temp.get("idAlbum");

        requestURL = "https://www.theaudiodb.com/api/v1/json/2/track.php?m=" + t1;

        StringBuilder response = new StringBuilder();
        URL u;
        try {
            u = new URL(requestURL);
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
            return null;
        }
        try {
            URLConnection connection = u.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int code = httpConnection.getResponseCode();

            String message = httpConnection.getResponseMessage();
            System.out.println(code + " " + message);
            if (code != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream instream = connection.getInputStream();
            Scanner in = new Scanner(instream);
            while (in.hasNextLine()) {
                response.append(in.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading response");
            return null;
        }

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response.toString());
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray tracks = (JSONArray) jsonObject.get("track"); // get the list of all artists returned.

            return tracks;
        } catch (org.json.simple.parser.ParseException e) {
            System.out.println("Error parsing JSON");
            return null;
        }


    }
}
