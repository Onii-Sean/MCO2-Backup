package View;

import Controller.VendingMachineController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateFrame extends JFrame {
    private VendingMachineController controller;
    private JButton regularButton;
    private JButton specialButton;
    private JButton backButton;
    private JPanel createPanel;

    public CreateFrame(VendingMachineController controller, JFrame mainFrame) {
        this.controller = controller;
        setContentPane(createPanel);
        setTitle("Vending Machine Factory");
        setSize(300, 300);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        regularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the vending machine into a new regular vending machine
                CreateRegularFrame createRegularFrame = new CreateRegularFrame(controller, CreateFrame.this);
            }
        });

        specialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the vending machine into a new special vending machine
                CreateSpecialFrame createSpecialFrame = new CreateSpecialFrame(controller, CreateFrame.this);
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
