package View;

import Controller.VendingMachineController;
import Model.Regular;
import Model.Special;
import Model.VendingMachine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestFrame extends JFrame {
    private VendingMachineController controller;
    private JButton vendingFeaturesButton;
    private JButton maintenanceFeaturesButton;
    private JButton backButton;
    private JPanel testPanel;

    public TestFrame(VendingMachineController controller, JFrame mainFrame) {
        this.controller = controller;
        setContentPane(testPanel);
        setTitle("Vending Machine Factory");
        setSize(300, 300);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        vendingFeaturesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VendingMachine vendingMachine = controller.getVendingMachine(); // Retrieve the VendingMachine instance

                if (vendingMachine == null) {
                    // check if vending machine is null (no data)
                    JOptionPane.showMessageDialog(null,
                            "Vending Machine is empty.",
                            "Empty Vending Machine", JOptionPane.INFORMATION_MESSAGE);
                    // if null, don't let the user proceed
                } else if (vendingMachine instanceof Regular) {
                    // if the vending machine is regular, crate a regular vending machine GUI
                    TestRegularFrame testRegularFrame = new TestRegularFrame(controller, TestFrame.this);
                } else if (vendingMachine instanceof Special) {
                    // if the vending machine is special, crate a special vending machine GUI
                    TestSpecialFrame testSpecialFrame = new TestSpecialFrame(controller, TestFrame.this);
                }
            }
        });

        maintenanceFeaturesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestMaintenanceFrame testMaintenanceFrame = new TestMaintenanceFrame(controller, TestFrame.this);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close this frame
                mainFrame.setVisible(true); // Show the main frame again
            }
        });

        mainFrame.setVisible(false);
        setVisible(true);
    }
}
