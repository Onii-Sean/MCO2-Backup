import Controller.VendingMachineController;
import View.VendingMachineView;

public class Main {
    public static void main(String[] args) {
        VendingMachineController controller = new VendingMachineController();
        new VendingMachineView(controller);
    }
}
