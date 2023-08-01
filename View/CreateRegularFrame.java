package View;

import Model.Item;
import Model.Slot;
import Model.Section;
import Model.VendingMachine;
import Controller.VendingMachineController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CreateRegularFrame extends JFrame {
    private VendingMachineController controller;
    private JPanel createRegularPanel;
    private JTextField vendingMachineNameTF;
    private JTextField sectionCountTF;
    private JButton nextButton;
    private JPanel secondPanel;
    private JButton doneButton;
    private JButton backButton;
    private String vendingMachineName;
    private int sectionCount;
    private boolean isFirstPhaseDone = false;
    private List<JTextField> sectionNameFields;
    private List<List<JTextField>> slotFields;

    public CreateRegularFrame(VendingMachineController controller, JFrame createFrame) {
        this.controller = controller;
        setContentPane(createRegularPanel);
        setTitle("Regular Vending Machine Factory");
        setSize(620, 500);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        secondPanel.setVisible(false); // Hide the second panel

        sectionNameFields = new ArrayList<>();
        slotFields = new ArrayList<>();

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vendingMachineName = vendingMachineNameTF.getText();
                    sectionCount = Integer.parseInt(sectionCountTF.getText());

                    // Show the second panel and create section and slot entry components
                    updateSecondPanel();
                    isFirstPhaseDone = true;
                    secondPanel.setVisible(true);
                } catch (NumberFormatException ex) {
                    // Show an error message if NumberFormatException is caught
                    JOptionPane.showMessageDialog(CreateRegularFrame.this,
                            "Oops! Number of section/s only accepts valid numbers.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close this frame
                createFrame.setVisible(true); // Show the main frame again
            }
        });

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Check if the user is done with the first phase
                    if (!isFirstPhaseDone) {
                        JOptionPane.showMessageDialog(CreateRegularFrame.this,
                                "Oops! First phase is incomplete.",
                                "Incomplete Data", JOptionPane.WARNING_MESSAGE);
                        return; // Do not proceed if first phase isn't done
                    }

                    // Check if all the required fields are filled
                    if (!areAllFieldsFilled()) {
                        JOptionPane.showMessageDialog(CreateRegularFrame.this,
                                "Oops! You must fill every text field.",
                                "Incomplete Data", JOptionPane.WARNING_MESSAGE);
                        return; // Do not proceed if fields are not filled
                    }

                    // Create a temporary vending machine with the entered data
                    VendingMachine temporaryVendingMachine = createTemporaryVendingMachine();

                    // Pass the temporary vending machine to the controller to update the real vending machine
                    controller.createRegularVendingMachine(temporaryVendingMachine);

                    // Show a success message
                    JOptionPane.showMessageDialog(CreateRegularFrame.this,
                            "Vending Machine created successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Close the createframe and show the main frame again
                    dispose();
                    createFrame.setVisible(true);
                } catch (NumberFormatException ex) {
                    // Show an error message if NumberFormatException is caught
                    JOptionPane.showMessageDialog(CreateRegularFrame.this,
                            "Oops! You must fill every text field with valid numbers.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    // Show a generic error message for any other exceptions
                    JOptionPane.showMessageDialog(CreateRegularFrame.this,
                            "Oops! An error occurred.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        createFrame.setVisible(false);
        setVisible(true);
    }

    // Update the second panel with components for entering section and slot details
    private void updateSecondPanel() {
        secondPanel.removeAll();
        secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.Y_AXIS));

        sectionNameFields.clear();
        slotFields.clear();

        for (int i = 0; i < sectionCount; i++) {
            JPanel sectionPanel = new JPanel();
            sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));

            JLabel sectionLabel = new JLabel("Section " + (i + 1) + " Name:");
            JTextField sectionField = new JTextField(20);
            sectionNameFields.add(sectionField);

            sectionPanel.add(sectionLabel);
            sectionPanel.add(sectionField);

            List<JTextField> slots = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                JPanel slotPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                JLabel slotLabel = new JLabel("Item Name " + (j + 1) + ": ");
                JTextField itemNameField = new JTextField(15);
                JTextField caloriesField = new JTextField(5);
                JTextField priceField = new JTextField(5);
                JTextField itemLimitField = new JTextField(5);

                slotPanel.add(slotLabel);
                slotPanel.add(itemNameField);
                slotPanel.add(new JLabel("Calories:"));
                slotPanel.add(caloriesField);
                slotPanel.add(new JLabel("Price:"));
                slotPanel.add(priceField);
                slotPanel.add(new JLabel("Item Limit:"));
                slotPanel.add(itemLimitField);

                slots.add(itemNameField);
                slots.add(caloriesField);
                slots.add(priceField);
                slots.add(itemLimitField);

                sectionPanel.add(slotPanel);
            }

            slotFields.add(slots);
            secondPanel.add(sectionPanel);
        }

        secondPanel.revalidate();
        secondPanel.repaint();
    }

    // Method to check if all the required fields are filled
    private boolean areAllFieldsFilled() {
        if (vendingMachineNameTF.getText().isEmpty() || sectionCountTF.getText().isEmpty()) {
            return false;
        }

        for (JTextField sectionNameField : sectionNameFields) {
            if (sectionNameField.getText().isEmpty()) {
                return false;
            }
        }

        for (List<JTextField> slots : slotFields) {
            for (JTextField field : slots) {
                if (field.getText().isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    // Create a temporary vending machine based on the entered data
    private VendingMachine createTemporaryVendingMachine() {
        VendingMachine temporaryVendingMachine = new VendingMachine();
        temporaryVendingMachine.setName(vendingMachineName);

        for (int i = 0; i < sectionNameFields.size(); i++) {
            String sectionName = sectionNameFields.get(i).getText();
            Section section = new Section(sectionName);
            temporaryVendingMachine.getSectionList().add(section);

            List<JTextField> slots = slotFields.get(i);
            for (int j = 0; j < slots.size(); j += 4) {
                String itemName = slots.get(j).getText();
                int calories = Integer.parseInt(slots.get(j + 1).getText());
                int price = Integer.parseInt(slots.get(j + 2).getText());
                int itemLimit = Integer.parseInt(slots.get(j + 3).getText());

                Slot slot = new Slot(new Item(itemName, price, calories), itemLimit);
                section.getSlots()[j / 4] = slot;
            }
        }

        return temporaryVendingMachine;
    }
}
