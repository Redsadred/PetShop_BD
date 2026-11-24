package src.petShop;

import javax.swing.SwingUtilities;
import src.petShop.view.TelaPrincipal;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal();
        });
    }
}
