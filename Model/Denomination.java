package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * The `Denomination` class represents the denominations and their quantities in a vending machine.
 * This class acts as a model in the MVC architecture.
 * It holds the data related to the denominations and provides methods to interact with them.
 * The controller can use these methods to manage the denominations.
 * The view can use the getter methods to display the denominations to the user.
 * The `Denomination` class doesn't contain any business logic specific to the vending machine itself.
 * It mainly focuses on managing the denominations.
 * Any business logic related to the vending machine should be implemented in the controller.
 * The `Denomination` class doesn't directly interact with the user or other components.
 * Any input/output and interactions with the user should be handled by the view and controller classes.
 *
 * By using a map to store the denominations and quantities, it provides a convenient way to access and update the denominations.
 * The `Denomination` class encapsulates the internal details of managing denominations,
 * allowing the controller to handle the business logic based on the available denominations.
 *
 * @author Sean Andrei Olores
 * @author Amiel Elijah Tenerife
 */
public class Denomination {
    // The map that stores the denominations and their quantities.
    private Map<Integer, Integer> denominationList;

    /**
     * Constructs a new `Denomination` object and initializes the default denominations and quantities.
     * The default denominations are 1000, 500, 100, 50, 10, 5, and 1, each with an initial quantity of 5.
     */
    public Denomination() {
        denominationList = new HashMap<>();
        denominationList.put(1000, 5);
        denominationList.put(500, 5);
        denominationList.put(100, 5);
        denominationList.put(50, 5);
        denominationList.put(10, 5);
        denominationList.put(5, 5);
        denominationList.put(1, 5);
    }

    /**
     * Updates the quantity of a specific denomination.
     *
     * @param bill The denomination value to update.
     * @param pcs  The new quantity of the denomination.
     */
    public void updateDenominations(int bill, int pcs) {
        denominationList.put(bill, pcs);
    }

    /**
     * Returns the current list of denominations and their quantities.
     *
     * @return The map containing the denominations and quantities.
     */
    public Map<Integer, Integer> getDenominationList() {
        return denominationList;
    }

    /**
     * Calculates the denominations for a given payment amount based on the available denominations.
     *
     * @param payment The payment amount to calculate denominations for.
     * @return A map containing the calculated denominations and their quantities.
     */
    public Map<Integer, Integer> calculateDenomination(int payment) {
        Map<Integer, Integer> result = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : denominationList.entrySet()) {
            int denomination = entry.getKey();
            int count = entry.getValue();

            if (payment >= denomination) {
                int numNotes = payment / denomination;
                if (numNotes > count) {
                    numNotes = count;
                }
                result.put(denomination, numNotes);
                payment -= numNotes * denomination;
            }
        }

        return result;
    }

    /**
     * Get the total amount of money in the denomination.
     *
     * @return The total amount of money in the denomination.
     */
    public int getTotalMoneyInDenomination() {
        int totalMoneyInside = 0;

        for (Map.Entry<Integer, Integer> entry : denominationList.entrySet()) {
            int denomination = entry.getKey();
            int count = entry.getValue();
            totalMoneyInside += denomination * count;
        }

        return totalMoneyInside;
    }
}
