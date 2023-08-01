package View;

import Controller.VendingMachineController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestMaintenanceFrame extends JFrame{
    private VendingMachineController controller;
    private JPanel testMaintenancePanel;
    private JPanel mainPanel;
    private JPanel transactionPanel;
    private JPanel totalPanel;
    private JButton collectRevenueButton;
    private JButton backButton;
    private JButton replenishAllButton;

    public TestMaintenanceFrame(VendingMachineController controller, JFrame testFrame) {
        this.controller = controller;
        setContentPane(testMaintenancePanel);
        setTitle("Vending Machine Maintenance");
        setSize(620, 500);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        collectRevenueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close this frame
                testFrame.setVisible(true); // Show the main frame again
            }
        });

        testFrame.setVisible(false);
        setVisible(true);
    }
}
