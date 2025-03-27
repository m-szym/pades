import ui.MainFrame;

import javax.swing.*;

/**
 * \class App
 * \brief Main application class to launch the Digital Signature Application.
 *
 * This class contains the main method which serves as the entry point for the application.
 */
public class App {
    /**
     * \brief Main method to launch the application.
     * \param args Command line arguments.
     *
     * This method initializes the main frame of the application and makes it visible.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}