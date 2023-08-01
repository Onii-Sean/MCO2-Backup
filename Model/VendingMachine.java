package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Model.VendingMachine class represents a vending machine that manages slots for items and denominations for payments.
 * It acts as the model in the MVC architecture and holds the data related to the vending machine.
 * The controller will use the methods provided by this class to interact with the vending machine and perform vending and maintenance actions.
 * The view can use the getter methods to display the vending machine's state and information to the user.
 * Any business logic related to vending and maintenance should be implemented in the controller.
 * The Model.VendingMachine class doesn't directly interact with the user.
 * Any input/output should be handled by the view and controller classes.
 *
 * By providing getter methods, it encapsulates the internal details of the vending machine,
 * allowing the controller to handle the business logic based on the vending machine's state.
 * The Model.VendingMachine class doesn't handle user input or manage denominations directly.
 * These responsibilities are delegated to the controller.
 *
 * @author Sean Andrei Olores
 * @author Amiel Elijah Tenerife
 */
public class VendingMachine {
    private String name;
    private List<Section> sectionList;
    private Denomination denominations;

    /**
     * Constructs a Model.VendingMachine object with default settings.
     * Initializes the slots and denominations.
     */
    public VendingMachine() {
        sectionList = new ArrayList<>();
        denominations = new Denomination();
    }

    /**
     * Get the list of sections in the vending machine.
     *
     * @return The list of sections.
     */
    public List<Section> getSectionList() {
        return sectionList;
    }

    /**
     * Get the denomination model for the vending machine.
     *
     * @return The denomination model.
     */
    public Denomination getDenominations() {
        return denominations;
    }

    public String getName() {
        return name;
    }

    public void setName(String vendingMachineName) {
        name = vendingMachineName;
    }
}
