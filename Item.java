
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private String name; //the item's name
    private String weight; //the item's weight, shown as a category
    private String description; //the item's short description
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String weight, String description)
    {
        this.name = name;
        this.weight = weight;
        this.description = description;
    }

    /**
     * Returns the item's name
     */
    
    public String getName()
    {
    
        return name;
    
    }
    
    /**
     * Returns the item's weight
     */
    public String getWeight()
    {
    
        return weight;
    
    }
    
    
    /**
     * Returns the item's description
     */
    public String getDesc()
    {
    
        return description;
    
    }
}
