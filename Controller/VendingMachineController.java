package Controller;

import Model.*;

import java.util.Map;

/**
 * The `VendingMachineController` class serves as the controller in the MVC architecture for the vending machine.
 * It acts as an intermediary between the view and the model (VendingMachine).
 * The controller is responsible for handling user interactions, processing input, and updating the model accordingly.
 * It implements the business logic for vending and maintenance actions specified in the requirements.
 *
 * Note: This is a basic outline for the `VendingMachineController` class, and you can add more methods as needed
 * to handle additional functionalities or specific use cases.
 *
 * - Sean Andrei Olores
 * - Amiel Elijah Tenerife
 */
public class VendingMachineController {
    private VendingMachine vendingMachine;
    public VendingMachineController() {
        vendingMachine = new Special();
        vendingMachine.setName("Food and Stuff");

        // Create and add sections to the vending machine
        Section section1 = new Section("Drinks");
        Section section2 = new Section("Snacks");

        vendingMachine.getSectionList().add(section1);
        vendingMachine.getSectionList().add(section2);

        // Add slots with items to each section
        Slot slot0 = new Slot(new Item("Cookies", 10, 180), 10);
        Slot slot1 = new Slot(new Item("Coke", 30, 150), 10);
        Slot slot2 = new Slot(new Item("Pepsi", 20, 140), 10);
        Slot slot3 = new Slot(new Item("Chips", 65, 200), 10);
        Slot slot4 = new Slot(new Item("Cookies", 10, 180), 10);
        Slot slot5 = new Slot(new Item("Coke", 20, 150), 10);
        Slot slot6 = new Slot(new Item("Pepsi", 70, 140), 10);
        Slot slot7 = new Slot(new Item("Chips", 15, 201), 10);
        Slot slot8 = new Slot(new Item("Cookies", 11, 180), 10);

        section1.getSlots()[0] = slot0; section2.getSlots()[1] = slot0;
        section1.getSlots()[1] = slot1; section2.getSlots()[6] = slot1;
        section1.getSlots()[2] = slot2; section2.getSlots()[3] = slot2;
        section1.getSlots()[3] = slot3; section2.getSlots()[2] = slot3;
        section1.getSlots()[4] = slot4; section2.getSlots()[4] = slot4;
        section1.getSlots()[5] = slot5; section2.getSlots()[7] = slot5;
        section1.getSlots()[6] = slot6; section2.getSlots()[5] = slot6;
        section1.getSlots()[7] = slot7; section2.getSlots()[8] = slot7;
        section1.getSlots()[8] = slot8; section2.getSlots()[0] = slot8;
    }

    public void createRegularVendingMachine(VendingMachine tempVendingMachine) {
        // Step 1: Create a new Regular vending machine
        vendingMachine = new Regular();

        // Step 2: Transfer the data from the temporary vending machine to the new Regular vending machine
        transferData(tempVendingMachine, vendingMachine);
    }

    public void createSpecialVendingMachine(VendingMachine tempVendingMachine) {
        // Step 1: Create a new Regular vending machine
        vendingMachine = new Special();

        // Step 2: Transfer the data from the temporary vending machine to the new Regular vending machine
        transferData(tempVendingMachine, vendingMachine);
    }

    // Method to transfer data from the source vending machine to the destination vending machine
    private void transferData(VendingMachine source, VendingMachine destination) {
        // Transfer vending machine name
        destination.setName(source.getName());

        // Transfer sections
        for (Section sourceSection : source.getSectionList()) {
            Section destinationSection = new Section(sourceSection.getName());

            // Transfer slots
            Slot[] sourceSlots = sourceSection.getSlots();
            Slot[] destinationSlots = destinationSection.getSlots();

            for (int i = 0; i < sourceSlots.length; i++) {
                Slot sourceSlot = sourceSlots[i];
                if (sourceSlot != null) {
                    Item sourceItem = sourceSlot.getAssociatedItem();
                    int itemLimit = sourceSlot.getItemLimit();
                    boolean standAlone = sourceItem.isStandAlone();

                    Item destinationItem = new Item(sourceItem.getName(), sourceItem.getPrice(), sourceItem.getCalories());
                    destinationItem.setStandAlone(standAlone);
                    Slot destinationSlot = new Slot(destinationItem, itemLimit);
                    destinationSlots[i] = destinationSlot;
                }
            }

            // Add the destination section to the destination vending machine
            destination.getSectionList().add(destinationSection);
        }
    }

    public VendingMachine getVendingMachine() {
        return vendingMachine;
    }

    /************************************************* Vending Feature *************************************************/

    /**
     * Processes the payment by updating the denominations based on the payment amount.
     *
     * @param payment the payment amount
     */
    public void receivePayment(int payment) {
        Map<Integer, Integer> result = vendingMachine.getDenominations().calculateDenomination(payment);

        for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
            int denomination = entry.getKey();
            int count = entry.getValue();
            vendingMachine.getDenominations().updateDenominations(denomination, count);
        }
    }

    /**
     * Dispenses an item from the specified slot.
     *
     * @param slot the slot from which to dispense the item
     */
    public void dispenseItem(Slot slot) {
        slot.removeItem();
    }

    /**
     * Calculates and prints the change to be given based on the payment and price of an item.
     *
     * @param payment the payment amount
     * @param totalPrice   the price of the item
     */
    public void produceChange(int payment, int totalPrice) {
        Map<Integer, Integer> result = vendingMachine.getDenominations().calculateDenomination(payment - totalPrice);

        for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
            int denomination = entry.getKey();
            int count = entry.getValue();
            System.out.println(denomination + ": " + count);
        }
    }

    /**
     * Checks if the given payment is enough to cover the total cost of items in the vending machine.
     *
     * @param payment the payment amount
     * @return true if the payment is enough, false otherwise
     */
    private boolean isChangeEnough(int payment) {
        int totalMoney = vendingMachine.getDenominations().getTotalMoneyInDenomination();
        return payment <= totalMoney;
    }

    /*********************************************** Maintenance Feature ***********************************************/

    /**
     * Find the slot containing an item with the given name in the specified section.
     *
     * @param section  the section to search in
     * @param itemName the name of the item to find
     * @return the Slot containing the item, or null if not found
     */
    private Slot findSlotInSection(Section section, String itemName) {
        for (Slot slot : section.getSlots()) {
            if (slot.getAssociatedItem().getName().equalsIgnoreCase(itemName)) {
                return slot;
            }
        }
        return null;
    }

    /**
     * Restocks the items in the vending machine for the given item in the specified section.
     *
     * @param section       the section to restock items in
     * @param itemName      the name of the item to restock
     * @param restockAmount the amount to be added to the current stock of the item
     * @return true if the item was restocked successfully, false otherwise
     */
    public boolean restockItems(Section section, String itemName, int restockAmount) {
        Slot slotToRestock = findSlotInSection(section, itemName);

        if (slotToRestock == null) {
            System.out.println("Item not found in the section. Restocking failed.");
            return false;
        }

        if ((restockAmount + slotToRestock.getItemCount()) > slotToRestock.getItemLimit()) {
            System.out.println("Amount entered exceeds item limit. Restocking failed.");
            return false;
        }

        for (int i = 0; i < restockAmount; i++) {
            slotToRestock.addItem();
        }

        return true;
    }

    /**
     * Sets a new price for an item in the vending machine.
     *
     * @param section   the section containing the item
     * @param itemName  the name of the item to set the new price for
     * @param newPrice  the new price to be set for the item
     * @return true if the item's price was updated successfully, false otherwise
     */
    public boolean setItemPrice(Section section, String itemName, int newPrice) {
        Slot slotToSetPrice = findSlotInSection(section, itemName);

        if (slotToSetPrice == null) {
            System.out.println("Item not found in the section. Setting new price failed.");
            return false;
        }

        for (Item item : slotToSetPrice.getItemList()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                item.setPrice(newPrice);
                return true;
            }
        }

        System.out.println("Item not found in the slot. Setting new price failed.");
        return false;
    }

    /**
     * Collects payments and replenishes denominations in the machine.
     */
    public void collectPayments() {
        int totalRevenue = 0;

        for (Map.Entry<Integer, Integer> entry : vendingMachine.getDenominations().getDenominationList().entrySet()) {
            totalRevenue += (entry.getKey() * entry.getValue());
        }

        System.out.println("Total revenue is " + totalRevenue);

        for (Map.Entry<Integer, Integer> entry : vendingMachine.getDenominations().getDenominationList().entrySet()) {
            vendingMachine.getDenominations().updateDenominations(entry.getKey(), 5);
        }

        System.out.println("Denominations replenished.");
    }
}
