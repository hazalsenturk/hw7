package edu.usfca.cs;

import java.util.Date;

/**
 * Entity is the
 * An entity class is the object wrapper for Song, Artist and Album objects
 * contains two constructors
 * Uses setters and getters to retrieve instances of the object
 * To write a file or send a request to a third party data provider it can create following formats:
 * XML, HTML, JSON, string
 * To write from the music.db it creates the right format with the methods: toSQL
 * <p>
 * @author      Gizem Hazal Senturk
 * @since       1.0
 */


public class Entity {
    protected String name;
    protected static int counter = 0;
    protected int entityID;
    protected Date dateCreated;
    protected static String mood;

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    public Entity() {
        this.name = "";
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
        this.mood = "";
    }

    /**
     * Checks if the Entity ID' of the two objects are the same
     * @param otherEntity name of an Song object
     */
    public boolean equals(Entity otherEntity) {
        return entityID == otherEntity.entityID;
    }


    /**
     * Constructor.
     * @param name Entity's name
     */
    public Entity(String name) {
        this.name = name;
        counter++;
        this.entityID = counter+21;
        dateCreated = new Date();
        this.mood = "";
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Formats Entity's info into string
     */
    public String toString() {
        return "Name: " + this.name + " Entity ID: " + this.entityID;
    }

    /**
     * Formats Entity's info into HTML
     */
    public String toHTML() {
        return "<b>" + this.name + "</b><i> " + this.entityID + "</i>";
    }

    /**
     * Formats Entity's info into XML
     */
    public String toXML() {
        return "<entity><name>" + this.name + "</name><ID> " + this.entityID + "</ID></entity>";
    }

    /**
     * Formats Entity's info for DB
     */
    public String toSQL(){
        return " ";
    }
}
