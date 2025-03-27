package ui.file_loader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * \class FileLoaderComponent
 * \brief Component for loading files with a specific extension filter and verification.
 *
 * This class provides a UI component for selecting files, verifying them, and displaying the status.
 * Uses the provided FileLoadTester to validate the selected file.
 * If the file is valid, it updates the label to show the file name and a success message and provides the file with getFile().
 * If the file is invalid, it shows an error message and resets the file selection.
 */
public class FileLoaderComponent extends JPanel {
    private final FileLoadTester verifier;
    private File file;
    private JButton browseButton;
    private JLabel label;
    private final FileNameExtensionFilter filter;

    /**
     * \brief Constructor for FileLoaderComponent.
     * \param buttonText The text to display on the browse button.
     * \param extensionFilter The file extension filter to apply in the file chooser.
     * \param verifier The verifier to test the selected file.
     */
    public FileLoaderComponent(String buttonText,
                               FileNameExtensionFilter extensionFilter,
                               FileLoadTester verifier) {
        this.verifier = verifier;
        this.filter = extensionFilter;

        initializeComponents(buttonText);
        setLayout(new FlowLayout());
        add(browseButton);
        add(label);
    }

    /**
     * \brief Initializes the components of the file loader.
     * \param buttonText The text to display on the browse button.
     */
    private void initializeComponents(String buttonText) {
        browseButton = new JButton(buttonText);
        label = new JLabel("No file selected");
        file = null;

        browseButton.addActionListener(e -> {chooseFile();});
    }

    /**
     * \brief Opens a file chooser dialog to select a file and verifies the selected file.
     */
    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                if (verifier.testFileLoading(selectedFile)) {
                    file = selectedFile;
                    setPositiveLabel("File " + file.getName() + " selected and ready to use");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid file\nPlease select another", "Error", JOptionPane.ERROR_MESSAGE);
                    setNegativeLabel(file != null ? "Invalid file: " + file.getName() : "No file selected");
                    file = null;
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * \brief Gets the selected file.
     * \return The selected file.
     */
    public File getFile() {return file;}

    /**
     * \brief Clears the selected file and resets the label.
     */
    public void clear() {
        file = null;
        setNeutralLabel("No file selected");
    }

    /**
     * \brief Sets the label to a neutral message.
     * \param message The message to display.
     */
    private void setNeutralLabel(String message) {
        label.setForeground(Color.GRAY);
        label.setText(message);
    }

    /**
     * \brief Sets the label to a negative message.
     * \param message The message to display.
     */
    private void setNegativeLabel(String message) {
        label.setForeground(Color.RED);
        label.setText(message);
    }

    /**
     * \brief Sets the label to a positive message.
     * \param message The message to display.
     */
    private void setPositiveLabel(String message) {
        label.setForeground(Color.GREEN);
        label.setText(message);
    }

    /**
     * \brief Invalidates the selected file and sets the label to a negative message.
     */
    public void invalidateFile() {
        file = null;
        setNegativeLabel("Invalid file");
    }
}
