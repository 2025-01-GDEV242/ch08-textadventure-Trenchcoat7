import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Katie Strong
 * @version 2025.3.31
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room
    private HashMap<String, Item> items;        // stores items of this room

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Add an item to a room
     * @param item ... the item being added
     */
     public void addItem(Item item) {
        items.put(item.getName(), item);
    }
    
     /**
     * Remove item from a room
     * @param itemName name of item you're removing
     * @return the item or null if not found
     */
    public Item removeItem(String itemName) {
        return items.remove(itemName);
    }
    
    /**
     * Give info on the item in the room, if there is one.
     * @return A String decription of the item.
     */
    private String getItemInfo() {
        if (items.isEmpty()) {
            return "looks like there's nothing here.";
        }
        String returnString = "Items: ";
        for (Item item : items.values()) {
            returnString += item.getName() + " ";
        }
        return returnString;
    }
    
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

