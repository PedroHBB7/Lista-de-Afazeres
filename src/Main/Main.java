package Main;

import javax.swing.SwingUtilities;
import login.login;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new login().setVisible(true); // abre a tela de login
        });
    }
}

