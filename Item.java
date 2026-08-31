
/**
 * Write a description of class Items here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private String itemName;
    private int itemWeight;
    private boolean collectable;
    private boolean isPerson;
    private Item possession;
    /**
     * Constructor for objects of class Items
     */
    public Item(String itemName, int itemWeight, boolean collectable, boolean isPerson, Item possession)
    {
        this.itemWeight = itemWeight;
        this.itemName = itemName;
        this.collectable = collectable;
        this.isPerson = isPerson;
        this.possession = possession;
    }
    
    /**
     * Returns the name of the item.
     */
    public String getItemName()
    {
        return itemName;
    }
    
    /**
     * Returns the weight of the item.
     */
    public int getItemWeight()
    {
        return itemWeight;
    }
    
    /**
     * Returns if the item is collectable or not.
     */
    public boolean getCollectable()
    {
        return collectable;
    }
    
    /**
     * Changes the items collectibililty.
     */
    public void changeCollectable(boolean bool)
    {
        collectable = bool;
    }
    
    /**
     * Returns if the item is a person or not.
     */
    public boolean getIsPerson()
    {
        return isPerson;
    }
    
    /**
     * Returns if the item has some other item in their possession.
     */
    public Item getPossession()
    {
        return possession;
    }
    
    /**
     * Removes the possession of the item.
     */
    public void removePossession()
    {
        possession = null;
    }
    
    /**
     * Returns whether the item has a possession or not.
     */
    public boolean hasItem()
    {
        if (possession != null) {
            return true;
        }
        return false;
    }
}
