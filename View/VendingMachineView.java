package View;

import Controller.VendingMachineController;
import Model.VendingMachine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VendingMachineView {
    private VendingMachineController controller;

    public VendingMachineView(VendingMachineController controller) {
        this.controller = controller;
        MainFrame mainFrame = new MainFrame(controller);
    }
}



