package ui.file_loader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileLoaderComponent extends JPanel {
    private final FileLoadTester verifier;
    private File file;
    private JButton browseButton;
    private JLabel label;
    private final FileNameExtensionFilter filter;

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

    private void initializeComponents(String buttonText) {
        browseButton = new JButton(buttonText);
        label = new JLabel("No file selected");
        file = null;

        browseButton.addActionListener(e -> {chooseFile();});
    }

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

    public File getFile() {return file;}
    public void clear() {
        file = null;
        setNeutralLabel("No file selected");
    }
    private void setNeutralLabel(String message) {
        label.setForeground(Color.GRAY);
        label.setText(message);
    }
    private void setNegativeLabel(String message) {
        label.setForeground(Color.RED);
        label.setText(message);
    }
    private void setPositiveLabel(String message) {
        label.setForeground(Color.GREEN);
        label.setText(message);
    }
    public void invalidateFile() {
        file = null;
        setNegativeLabel("Invalid file");
    }
}
