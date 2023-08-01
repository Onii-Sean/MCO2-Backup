package View;

import Controller.VendingMachineController;
import Model.Section;
import Model.Slot;
import Model.Item;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TestSpecialFrame extends JFrame {
    private VendingMachineController controller;
    private JPanel testSpecialPanel;
    private JLabel titleLabel;
    private JPanel menuPanel;
    private JPanel orderPanel;
    private JPanel totalPanel;
    private JLabel totalLabel;
    private JTextField paymentTF;
    private JButton backButton;
    private JButton doneButton;

    private String selectedItemName;
    private int selectedItemCount;
    private double totalPrice = 0.0;

    private Map<String, Integer> selectedItems = new HashMap<>();

    private void updateSelectedItem(Slot slot) {
        selectedItemName = slot.getAssociatedItem().getName();
        selectedItemCount = selectedItems.getOrDefault(selectedItemName, 0);
    }

    public TestSpecialFrame(VendingMachineController controller, JFrame testFrame) {
        this.controller = controller;
        setContentPane(testSpecialPanel);
        setTitle("Special Vending Machine");
        setSize(450, 520); // Increase the size to accommodate additional components
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        String vendingMachineName = controller.getVendingMachine().getName();
        titleLabel.setText("<html><body style='width: 200px; text-align: center;'>" + vendingMachineName + "</body></html>");

        // Assuming you have methods to create the panels for the menu, order, and total
        // You can use the existing createSectionPanel, updateOrderPanel, and createSlotPanel methods from the previous code.
        // Create the menuPanel with sections and slots, similar to TestRegularFrame.
        menuPanel.setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        for (Section section : controller.getVendingMachine().getSectionList()) {
            JPanel sectionPanel = createSectionPanel(section);
            tabbedPane.add(section.getName(), sectionPanel);
        }
        menuPanel.add(tabbedPane, BorderLayout.CENTER);

        // Initialize the orderPanel and totalPanel
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));

        // Total Label
        totalLabel = new JLabel("Total: Php 0.00");
        totalPanel.add(totalLabel);

        // Payment Text Field
        paymentTF = new JTextField();
        paymentTF.setPreferredSize(new Dimension(100, 30));
        paymentTF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doneButton.doClick(); // Simulate clicking the done button when the user presses Enter in the payment text field
            }
        });

        // Back Button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close this frame
                testFrame.setVisible(true); // Show the main frame again
            }
        });

        // Done Button
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

        // Layout for the main frame
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(menuPanel, BorderLayout.CENTER);
        mainPanel.add(totalPanel, BorderLayout.EAST); // Assuming you want totalPanel to be on the right side
        mainPanel.add(orderPanel, BorderLayout.WEST); // Assuming you want orderPanel to be on the left side
        add(mainPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(new JLabel("Payment:"));
        bottomPanel.add(paymentTF);
        bottomPanel.add(doneButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

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
        slotPanel.setPreferredSize(new Dimension(50, 50));
        slotPanel.setLayout(new BorderLayout());

        String title = slot.getItemCount() + " / " + slot.getItemLimit();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 12));
        slotPanel.setBorder(titledBorder);

        JLabel itemNameLabel = new JLabel(slot.getAssociatedItem().getName());
        itemNameLabel.setPreferredSize(new Dimension(40, 40));
        itemNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        itemNameLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the item name

        slotPanel.add(itemNameLabel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridLayout(2, 2));
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

        slotPanel.add(detailsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton addBtn = new JButton("+");
        JButton subBtn = new JButton("-");
        buttonPanel.add(addBtn);
        buttonPanel.add(subBtn);

        slotPanel.add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // subtract 1 from the item count
                // add 1 on the orderCount, or the number of that item in the user's order
            }
        });

        subBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // add 1 from the item count
                // subtract 1 on the orderCount, or the number of that item in the user's order
            }
        });

        return slotPanel;
    }
}
