import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collection;

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
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String roomName;
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashSet<Item> items;
    private boolean isAccessible;
    private boolean pumpWorking;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String roomName, String description, boolean isAccessible) 
    {
        this.roomName = roomName;
        this.description = description;
        exits = new HashMap<>();
        items = new HashSet<>();
        this.isAccessible = isAccessible;
    }
    
    /**
     * Returns a random exit of a room.
     */
    public Room returnRandomExit()
    {
        Collection<Room> collection = exits.values();
        ArrayList<Room> arrayList = new ArrayList<>();
        for (Room exit : collection) {
            if (!exit.getRoomName().equals("transporter room") && exit.getIsAccessible() == true) {
                arrayList.add(exit);
            }
        }
        Random rand = new Random();
        int numberOfExits = arrayList.size();
        int randomExitIndex = rand.nextInt(numberOfExits);
        return arrayList.get(randomExitIndex);
        
    }
    
    /**
     * Returns the name of the room.
     */
    public String getRoomName()
    {
        return roomName;
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
     * Changes the accessibility of the room.
     */
    public void changeAccessibility(boolean bool)
    {
        isAccessible = bool;
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
     * Returns whether the room is accessible or not.
     */
    public boolean getIsAccessible()
    {
        return isAccessible;
    }
    
    /**
     * Returns whether the pump is working or not.
     */
    public boolean getIsPumpWorking()
    {
       return pumpWorking; 
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
    
    /**
     * Adds and item to the room.
     */
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * Removes an item from the room.
     */
    public void removeItem(Item item)
    {
        items.remove(item);
    }
    
    /**
     * Returns the set of the items of the room.
     */
    public HashSet<Item> getItemSet()
    {
        return items;
    }
    
    /**
     * Returns the name of the items in the room.
     */
    public String getItemString()
    {
        String returnString = "";
        for(Item item : items) {
            if (item.getIsPerson() == false) {
                returnString += item.getItemName() + " ";
            }
        }
        return returnString;
    }
    
    /**
     * Returns the name of the people in the room.
     */
    public String getPersonString()
    {
        String returnString = "";
        for(Item item : items) {
            if (item.getIsPerson() == true) {
                returnString += item.getItemName() + " ";
            }
        }
        return returnString;
    }
    
    /**
     * Returns the inputted item, if any.
     */
    public Item getItem(String itemName)
    {
        for (Item item : items) {
            if (itemName.equals(item.getItemName())) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Returns whether the room contains any items, or not.
     */
    public boolean isEmpty()
    {
        int numberOfPeopleInTheRoom = 0;
        boolean isEmpty = false;
        for (Item item : items) {
            if (item.getIsPerson() == true) {
                numberOfPeopleInTheRoom++;
            }
        }
        
        if(numberOfPeopleInTheRoom == 0) {
            isEmpty = true;
        }
        return isEmpty;
    }
    
    /**
     * Returns whether the room contains a specified item, or not.
     */
    public boolean contains(String itemName)
    {
        boolean containsItem = false;
        for (Item item : items) {
            if (itemName.equals(item.getItemName())) {
                containsItem = true;
            }
        }
        return containsItem;
    }
}

