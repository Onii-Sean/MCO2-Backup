package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * The `Section` class represents a section in a vending machine with a name and an array of slots.
 * Each section can have multiple slots to store items.
 *
 * This class acts as a model in the MVC architecture.
 * It holds the data related to the section and provides methods to interact with the slots.
 * The controller can use these methods to manage the slots in the section.
 * The view can use the getter methods to display the section's information to the user.
 * The `Section` class doesn't contain any business logic specific to the vending machine itself.
 * It mainly focuses on managing the slots.
 * Any business logic related to the vending machine should be implemented in the controller.
 * The `Section` class doesn't directly interact with the user or other components.
 * Any input/output and interactions with the user should be handled by the view and controller classes.
 *
 * Note: This class assumes that there will always be exactly 9 slots in a section.
 * If the number of slots per section is variable, consider using a List instead of an array.
 *
 * @author Sean Andrei Olores
 * @author Amiel Elijah Tenerife
 */
public class Section {

    private String name;
    private Slot[] slots;

    /**
     * Constructs a new `Section` with the given name.
     *
     * @param name The name of the section.
     */
    public Section(String name) {
        this.name = name;
        this.slots = new Slot[9];
    }

    /**
     * Get the name of the section.
     *
     * @return The name of the section.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the section.
     *
     * @param name The name to set for the section.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the list of all slots in the section.
     *
     * @return The list of slots in the section.
     */
    public Slot[] getSlots() {
        return slots;
    }
}
