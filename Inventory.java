import java.util.HashSet;

/**
 * Write a description of class Inventory here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Inventory
{
    // instance variables - replace the example below with your own
    private HashSet<Item> inventory;
    private int currentWeight;
    private int maxWeight;

    /**
     * Constructor for objects of class Inventory
     */
    public Inventory(int currentWeight, int maxWeight)
    {
        inventory = new HashSet<>();
        this.currentWeight = currentWeight;
        this.maxWeight = maxWeight;
    }
    
    /**
     * Adds the given item to the inventory.
     */
    public void addItem(Item item)
    {
        inventory.add(item);
        currentWeight += item.getItemWeight();
    }
    
    /**
     * Removes the given item from the inventory.
     */
    public void removeItem(Item item)
    {
        inventory.remove(item);
        currentWeight -= item.getItemWeight();
    }
    
    /**
     * Returns the items in the inventory.
     */
    public String getItemString()
    {
        String returnString = "Items in your inventory: ";
        for (Item item : inventory) {
            String itemName = item.getItemName();
            returnString += itemName + " ";
        }
        return returnString;
    }
    
    /**
     * Returns the the of the items in the inventory.
     */
    public HashSet<Item> getHashSet()
    {
        return inventory;
    }
    
    /**
     * Returns a specified item from the inventory.
     */
    public Item getItem(String itemName)
    {
        for (Item item : inventory) {
            if (itemName.equals(item.getItemName())) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Returns the available space left in the inventory.
     */
    public int getAvailableSpace()
    {
        return maxWeight - currentWeight;
    }
    
    /**
     * Returns whether the inventory contains the given item, or not.
     */
    public boolean contains(String itemName)
    {
        boolean containsItem = false;
        for (Item item : inventory) {
            if (itemName.equals(item.getItemName())) {
                containsItem = true;
            }
        }
        return containsItem;
    }
}
