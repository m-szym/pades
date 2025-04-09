package ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import exceptions.InvalidKeyFileException;
import exceptions.PdfFileOpeningException;
import exceptions.SignatureVerificationException;
import service.key_loading.KeyLoader;
import service.key_loading.LocalEncryptedKeyLoader;
import service.key_loading.LocalKeyLoader;
import ui.file_loader.PdfFileLoadTester;
import service.Verifier;
import ui.file_loader.FileLoaderComponent;

import java.awt.*;
import java.security.InvalidKeyException;

/**
 * \class VerifyFrame
 * \brief Frame for verifying signatures of PDF documents.
 *
 * This class represents a window where users can load a signed PDF and a public key to verify the document's signature.
 */
public class VerifyFrame extends JFrame {
    private FileLoaderComponent pdfLoader;
    private FileLoaderComponent keyFileLoader;
    private JButton verifyButton;
    private JButton backButton;
    private JTextArea aboutText;
    private final KeyLoader keyLoader;

    /**
     * \brief Constructor for VerifyFrame.
     *
     * Initializes the components and builds the frame.
     */
    public VerifyFrame() {
        keyLoader = new LocalEncryptedKeyLoader();

        // Build the frame
        initializeComponents();
        setupComponents();
        setTitle("Digital Signature Application");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * \brief Sets up the components of the frame.
     *
     * Configures the layout and adds the components to the frame.
     */
    private void setupComponents() {
        // Set frame layout
        setLayout(new BorderLayout());

        // Add components
        add(aboutText, BorderLayout.CENTER);

        JPanel fileLoadersPanel = new JPanel();
        fileLoadersPanel.setLayout(new GridLayout(2, 1));
        fileLoadersPanel.add(pdfLoader);
        fileLoadersPanel.add(keyFileLoader);
        add(fileLoadersPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(verifyButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * \brief Initializes the components of the frame.
     *
     * Creates and configures the file loaders, buttons, and text area.
     */
    private void initializeComponents() {
        pdfLoader = new FileLoaderComponent("Load signed PDF",
                new FileNameExtensionFilter("PDF files", "pdf"),
                new PdfFileLoadTester());
        keyFileLoader = new FileLoaderComponent("Load public key",
                new FileNameExtensionFilter("PEM files", "pem"),
                file -> {
                    try {
                        keyLoader.loadPublicKey(file);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });
        verifyButton = new JButton("Verify signature");
        verifyButton.addActionListener(e -> {
            if (pdfLoader.getFile() == null || keyFileLoader.getFile() == null) {
                reportError("Please select both a valid PDF file AND a valid public key file");
                return;
            }

            int result = verifyDocument();
            if (result == 1) {
                // 1 means the signature is valid
                JOptionPane.showMessageDialog(this, "The signature is valid", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else if (result == 0) {
                // 0 means the signature is invalid
                JOptionPane.showMessageDialog(this, "The signature is invalid", "Failure", JOptionPane.ERROR_MESSAGE);
            }  // -1 means an error occurred, and the error message has already been shown
        });
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            dispose();
        });
        aboutText = new JTextArea("This application allows you to verify the signature of a signed PDF document using a public key.");
        aboutText.setEditable(false);
    }

    /**
     * \brief Verifies the signature of the loaded PDF file.
     * Any errors encountered during the verification process are reported to the user.
     * \return 1 if the signature is valid, 0 if the signature is invalid, -1 if an error occurred.
     */
    private int verifyDocument() {
        try {
            if (Verifier.verify(pdfLoader.getFile(),
                                keyLoader.loadPublicKey(keyFileLoader.getFile()))) {
                return 1;
            } else {
                return 0;
            }
        } catch (InvalidKeyFileException e) {
            keyFileLoader.invalidateFile();
            reportError("Couldn't read the public key from provided file: " + keyFileLoader.getFile().getName() + "\n" + e.getMessage());
            return -1;
        } catch (PdfFileOpeningException e) {
            pdfLoader.invalidateFile();
            reportError("Couldn't open provided PDF file: " + pdfLoader.getFile().getName() + "\n" + e.getMessage());
            return -1;
        } catch (InvalidKeyException e) {
            reportError("The provided key is invalid. Maybe the format is incorrect?\n" + e.getMessage());
            return -1;
        } catch (SignatureVerificationException e) {
            reportError("Couldn't verify the provided PDF file with the provided key\n" + e.getMessage());
            return -1;
        } catch (Exception e) {
            reportError("An unexpected error occurred\n" + e.getMessage());
            return -1;
        }
    }

    /**
     * \brief Reports an error message to the user using a dialog.
     * \param message The error message to be displayed.
     */
    private void reportError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
