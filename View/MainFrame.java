package View;

import Controller.VendingMachineController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private VendingMachineController controller;
    private JPanel mainPanel;
    private JButton createButton;
    private JButton testButton;

    public MainFrame(VendingMachineController controller) {
        this.controller = controller;
        setContentPane(mainPanel);
        setTitle("Vending Machine Factory");
        setSize(300, 300);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // when CREATE is pressed, pass the controller to the CreateFrame
                CreateFrame createFrame = new CreateFrame(controller, MainFrame.this);
            }
        });

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // when the TEST is pressed, pass the controller to the TestFrame
                TestFrame testFrame = new TestFrame(controller, MainFrame.this);
            }
        });

        setVisible(true);
    }
}
