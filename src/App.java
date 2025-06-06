import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // Lancement de l'application dans le thread de l'interface Swing
        SwingUtilities.invokeLater(() -> {
            CustomerViewer viewer = new CustomerViewer(); // Fenêtre principale des clients
            viewer.setVisible(true);
        });
    }
}