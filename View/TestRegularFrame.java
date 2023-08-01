package View;

import Controller.VendingMachineController;
import Model.Item;
import Model.Section;
import Model.Slot;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TestRegularFrame extends JFrame {
    private VendingMachineController controller;
    private JPanel testRegularPanel;
    private JLabel titleLabel;
    private JPanel menuPanel;
    private JPanel orderPanel;
    private JButton add;
    private JButton subtract;
    private JTextField paymentTF;
    private JButton backButton;
    private JButton doneButton;
    private JLabel totalLabel;
    private JPanel inputPanel;

    private String selectedItemName;
    private int selectedItemCount;
    private double totalPrice = 0.0;

    private Map<String, Integer> selectedItems = new HashMap<>();

    private void updateSelectedItem(Slot slot) {
        selectedItemName = slot.getAssociatedItem().getName();
        selectedItemCount = selectedItems.getOrDefault(selectedItemName, 0);
    }

    public TestRegularFrame(VendingMachineController controller, JFrame testFrame) {
        this.controller = controller;
        setContentPane(testRegularPanel);
        setTitle("Vending Machine Factory");
        setSize(320, 530);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        String vendingMachineName = controller.getVendingMachine().getName();
        titleLabel.setText("<html><body style='width: 200px; text-align: center;'>" + vendingMachineName + "</body></html>");

        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JTabbedPane tabbedPane = new JTabbedPane();
        for (Section section : controller.getVendingMachine().getSectionList()) {
            JPanel sectionPanel = createSectionPanel(section);
            tabbedPane.add(section.getName(), sectionPanel);
        }
        menuPanel.add(tabbedPane);

        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if an item is selected and its count is greater than zero
                if (selectedItemName != null && selectedItemCount >= 0) {
                    // Update the selected item count and the corresponding slot's item count
                    int count = selectedItems.getOrDefault(selectedItemName, 0);
                    selectedItems.put(selectedItemName, count + 1);
                    selectedItemCount = selectedItems.get(selectedItemName);
                    updateOrderPanel();
                }
            }
        });

        subtract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if an item is selected and its count is greater than 0
                if (selectedItemName != null && selectedItemCount > 0) {
                    // Update the selected item count and the corresponding slot's item count
                    int count = selectedItems.get(selectedItemName);
                    if (count > 0) {
                        selectedItems.put(selectedItemName, count - 1);
                        selectedItemCount = selectedItems.get(selectedItemName);
                        updateOrderPanel();
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close this frame
                testFrame.setVisible(true); // Show the test frame again
            }
        });

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double payment = Double.parseDouble(paymentTF.getText());

                    // Check if any items are ordered
                    if (selectedItems.isEmpty()) {
                        String errorMessage = "No items ordered. Please select items before proceeding.";
                        JOptionPane.showMessageDialog(null, errorMessage, "Order Error", JOptionPane.ERROR_MESSAGE);
                    } else if (payment >= totalPrice) {
                        double change = payment - totalPrice;
                        String changeMessage = "Change: Php " + change;
                        JOptionPane.showMessageDialog(null, changeMessage, "Change", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        String errorMessage = "Insufficient payment. Please input a larger amount or edit your order.";
                        JOptionPane.showMessageDialog(null, errorMessage, "Payment Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    // If the user didn't enter a valid number in the payment text field
                    String errorMessage = "Invalid payment amount. Please enter a valid number.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Payment Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        testFrame.setVisible(false);
        setVisible(true);
    }

    private JPanel createSectionPanel(Section section) {
        JPanel sectionPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        for (Slot slot : section.getSlots()) {
            JPanel slotPanel = createSlotPanel(slot);
            sectionPanel.add(slotPanel);
        }
        return sectionPanel;
    }

    private void updateOrderPanel() {
        orderPanel.removeAll();
        totalPrice = 0.0;

        if (selectedItemName != null && selectedItemCount > 0) {
            Item item = null;

            // Find the item associated with the selected item name
            for (Section section : controller.getVendingMachine().getSectionList()) {
                for (Slot slot : section.getSlots()) {
                    if (slot.getAssociatedItemName().equals(selectedItemName)) {
                        item = slot.getAssociatedItem();
                        break;
                    }
                }
                if (item != null) {
                    break;
                }
            }

            if (item != null) {
                double itemPrice = item.getPrice();
                double itemTotalPrice = itemPrice * selectedItemCount;
                totalPrice += itemTotalPrice;
                JLabel itemLabel = new JLabel(selectedItemCount + "x " + selectedItemName);
                orderPanel.add(itemLabel);
            }
        }

        totalLabel.setText("Php " + String.format("%.2f", totalPrice));
        orderPanel.revalidate();
        orderPanel.repaint();
    }

    private JPanel createSlotPanel(Slot slot) {
        JPanel slotPanel = new JPanel();
        slotPanel.setPreferredSize(new Dimension(100, 100));
        slotPanel.setLayout(new BorderLayout());

        String title = slot.getItemCount() + " / " + slot.getItemLimit();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 12));
        slotPanel.setBorder(titledBorder);

        JButton itemButton = new JButton(slot.getAssociatedItem().getName());
        itemButton.setPreferredSize(new Dimension(100, 40));
        itemButton.setFont(new Font("Arial", Font.PLAIN, 12));
        slotPanel.add(itemButton, BorderLayout.CENTER);

        itemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSelectedItem(slot);
                updateOrderPanel();
            }
        });

        JPanel detailsPanel = new JPanel(new GridLayout(2, 2, 2, 2));
        JLabel caloriesLabel = new JLabel("Cal: ");
        JLabel caloriesValueLabel = new JLabel(String.valueOf(slot.getAssociatedItem().getCalories()));
        JLabel priceLabel = new JLabel("Php: ");
        JLabel priceValueLabel = new JLabel(String.valueOf(slot.getAssociatedItem().getPrice()));

        caloriesLabel.setHorizontalAlignment(SwingConstants.LEFT);
        priceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        caloriesValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        detailsPanel.add(caloriesLabel);
        detailsPanel.add(caloriesValueLabel);
        detailsPanel.add(priceLabel);
        detailsPanel.add(priceValueLabel);

        slotPanel.add(detailsPanel, BorderLayout.SOUTH);

        return slotPanel;
    }
}
