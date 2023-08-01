package Model;

/**
 * The `Item` class represents an item in a vending machine.
 * Each item has a name, price, and calorie information.
 * It also provides a maintenance feature to update the item's price.
 *
 * This class acts as a model in the MVC architecture.
 * It holds the data related to the item and provides methods to interact with its attributes.
 * The controller can use these methods to update the item's price.
 * The view can use the getter methods to display the item's information to the user.
 * The `Item` class doesn't contain any business logic specific to the vending machine itself.
 * It mainly focuses on managing the item's attributes.
 * Any business logic related to the vending machine should be implemented in the controller.
 * The `Item` class doesn't directly interact with the user.
 * Any input/output should be handled by the view and controller classes.
 *
 * @author Sean Andrei Olores
 * @author Amiel Elijah Tenerife
 */
public class Item {
    private final String name;
    private final int calories;
    private int price;
    private boolean standAlone = true;

    /**
     * Constructs a new `Item` object with the specified name, price, and calories.
     *
     * @param name     The name of the item.
     * @param price    The price of the item.
     * @param calories The calories of the item.
     */
    public Item(String name, int price, int calories) {
        this.name = name;
        this.price = price;
        this.calories = calories;
    }

    /**
     * Returns the name of the item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the item.
     *
     * @return The price of the item.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns the calories of the item.
     *
     * @return The calories of the item.
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Sets a new price for the item. This is a maintenance feature.
     *
     * @param price The new price to set for the item.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Checks if the item can be sold as a stand-alone food item.
     *
     * @return `true` if the item can be sold as a stand-alone food item, `false` otherwise.
     */
    public boolean isStandAlone() {
        return this.standAlone;
    }

    public void setStandAlone(boolean standAlone) { this.standAlone = standAlone; }

}
