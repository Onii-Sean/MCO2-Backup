package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * The `Slot` class represents a slot in a vending machine that can hold a certain number of items.
 * It is associated with an item and keeps track of the items stored in the slot.
 *
 * This class acts as a model in the MVC architecture.
 * It holds the data related to the slot and provides methods to interact with the slot and its associated item.
 * The controller can use these methods to manage items in the slot, such as adding and removing items.
 * The view can use the getter methods to display information about the slot's associated item to the user.
 * The `Slot` class doesn't contain any business logic specific to the vending machine itself.
 * It mainly focuses on managing the state of the slot and its items.
 * Any business logic related to the vending machine should be implemented in the controller.
 * The `Slot` class doesn't directly interact with the user or other components.
 * Any input/output and interactions with the user should be handled by the view and controller classes.
 *
 * @author Sean Andrei Olores
 * @author Amiel Elijah Tenerife
 */
public class Slot {
    private final Item associatedItem;
    private final int itemLimit;
    private List<Item> itemList;

    /**
     * Constructs a new `Slot` object with the specified item and item limit.
     *
     * @param item      The associated item.
     * @param itemLimit The maximum number of items that can be stored in the slot.
     */
    public Slot(Item item, int itemLimit) {
        this.associatedItem = item;
        this.itemLimit = itemLimit;
        this.itemList = new ArrayList<>();
    }

    public Item getAssociatedItem() {
        return associatedItem;
    }

    /**
     * Returns the name of the associated item.
     *
     * @return The name of the associated item.
     */
    public String getAssociatedItemName() {
        return associatedItem.getName();
    }

    /**
     * Returns the item limit of the slot.
     *
     * @return The item limit of the slot.
     */
    public int getItemLimit() {
        return itemLimit;
    }

    /**
     * Returns the current number of items stored in the slot.
     *
     * @return The current number of items stored in the slot.
     */
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * Returns the list of items stored in the slot.
     *
     * @return The list of items stored in the slot.
     */
    public List<Item> getItemList() {
        return itemList;
    }

    /**
     * Adds an item to the slot if the item limit has not been reached.
     *
     * @return true if the item was added successfully, false if the item limit has been reached.
     */
    public boolean addItem() {
        if (itemList.size() < itemLimit) {
            itemList.add(associatedItem);
            return true; // Item added successfully
        }
        return false; // Failed to add item
    }

    /**
     * Removes an item from the slot.
     */
    public void removeItem() {
        itemList.remove(associatedItem);
    }
}
