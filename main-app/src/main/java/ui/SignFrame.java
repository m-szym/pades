package ui;

import exceptions.InvalidKeyFileException;
import exceptions.PdfFileOpeningException;
import exceptions.PdfFileSavingException;
import exceptions.SigningException;
import service.key_loading.KeyLoader;
import service.key_loading.LocalKeyLoader;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import ui.file_loader.PdfFileLoadTester;
import service.Signer;
import ui.file_loader.FileLoaderComponent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;

/**
 * \class SignFrame
 * \brief Frame for signing PDF documents.
 *
 * This class represents a window where users can load a PDF and a private key to sign the document.
 */
public class SignFrame extends JFrame {
    private FileLoaderComponent inputPdfFileLoader;
    private FileLoaderComponent keyFileLoader;
    private JButton signButton;
    private JButton backButton;
    private JTextArea aboutText;
    private final KeyLoader keyLoader;
    private String privateKeyPIN;

    /**
     * \brief Constructor for SignFrame.
     *
     * Initializes the components and builds the frame.
     */
    public SignFrame() {
        keyLoader = new LocalKeyLoader();
        privateKeyPIN = "";

        initializeComponents();
        buildFrame();
        setTitle("Digital Signature Application");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * \brief Builds the frame layout.
     *
     * Sets the layout of the frame and adds the components.
     */
    private void buildFrame() {
        // Set frame layout
        setLayout(new BorderLayout());

        // Add components
        add(aboutText, BorderLayout.CENTER);

        JPanel fileLoadersPanel = new JPanel();
        fileLoadersPanel.setLayout(new GridLayout(2, 1));
        fileLoadersPanel.add(inputPdfFileLoader);
        fileLoadersPanel.add(keyFileLoader);
        add(fileLoadersPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(signButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * \brief Initializes the components of the frame.
     *
     * Creates and configures the file loaders, buttons, and text area.
     */
    private void initializeComponents() {
        inputPdfFileLoader = new FileLoaderComponent("Load PDF to sign",
                new FileNameExtensionFilter("PDF files", "pdf"),
                new PdfFileLoadTester());
        keyFileLoader = new FileLoaderComponent("Load private key",
                new FileNameExtensionFilter("PEM files", "pem"),
                file -> {
                    try {
                        keyLoader.loadPrivateKey(file, privateKeyPIN);   // TODO: get PIN with dialog
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });
        signButton = new JButton("Sign PDF");
        signButton.addActionListener(e -> {
            if (inputPdfFileLoader.getFile() == null || keyFileLoader.getFile() == null) {
                reportError("Please select both a PDF file AND a private key file");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("PDF files", "pdf"));
            fileChooser.setDialogTitle("Save signed PDF");
            if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                reportError("You need to choose a file to save the signed PDF");
            } else {
                if (sign(fileChooser.getSelectedFile())) {
                    JOptionPane.showMessageDialog(this, "PDF signed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                // else an error occurred and message was already shown
            }
        });
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            dispose();
        });
        aboutText = new JTextArea("This application allows you to sign a PDF document using a private key.");
        aboutText.setEditable(false);
    }

    /**
     * \brief Signs the loaded PDF file and saves it to the specified output file.
     * Any errors during the signing process are reported to the user using the reportError method.
     * \param outputFile The file to save the signed PDF.
     * \return true if the signing is successful, false otherwise.
     */
    private boolean sign(File outputFile) {
        try {
            // create and configure the signature
            PDSignature signature = new PDSignature();
            signature.setName("SIG TEST");
            signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
            signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);

            // sign the file
            Signer.sign(inputPdfFileLoader.getFile(),
                    keyLoader.loadPrivateKey(keyFileLoader.getFile(), privateKeyPIN),
                    signature,
                    outputFile);

            return true;
        } catch (InvalidKeyFileException e) {
            keyFileLoader.invalidateFile();
            reportError("Couldn't read the public key from provided file: " + keyFileLoader.getFile().getName() + "\n" + e.getMessage());
        } catch (PdfFileOpeningException e) {
            inputPdfFileLoader.invalidateFile();
            reportError("Couldn't open provided PDF file: " + inputPdfFileLoader.getFile().getName() + "\n" + e.getMessage());
        } catch (InvalidKeyException e) {
            reportError("The provided key is invalid. Maybe the format is incorrect?\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            reportError("The output file could not be created\n" + e.getMessage());
        } catch (SigningException e) {
            reportError("An error occurred while signing the document\n" + e.getMessage());
        } catch (PdfFileSavingException e) {
            reportError("Couldn't save the signed document\n" + e.getMessage());
        } catch (Exception e) {
            reportError("An unexpected error occurred\n" + e.getMessage());
        }
        return false;
    }

    /**
     * \brief Reports an error message to the user using a dialog.
     * \param message The error message to be displayed.
     */
    private void reportError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
