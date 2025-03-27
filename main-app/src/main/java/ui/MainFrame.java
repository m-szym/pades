package ui;

import javax.swing.*;
import java.awt.*;

/**
 * \class MainFrame
 * \brief Main application window for the Digital Signature Application.
 *
 * This class represents the main window of the application, providing buttons to sign and verify documents.
 */
public class MainFrame extends JFrame {
    private JButton signDocumentButton;
    private JButton verifyDocumentButton;
    private JTextArea aboutText;

    /**
     * \brief Constructor for MainFrame.
     *
     * Initializes the components and builds the frame.
     */
    public MainFrame() {
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
        setLayout(new BorderLayout());

        // Add text area at the top
        aboutText.setEditable(false);
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);
        add(aboutText, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(signDocumentButton);
        buttonPanel.add(verifyDocumentButton);

        // Add button panel at the bottom
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * \brief Initializes the components of the frame.
     *
     * Creates and configures the buttons and text area.
     */
    private void initializeComponents() {
        signDocumentButton = new JButton("Sign Document");
        signDocumentButton.addActionListener(e -> {
            SignFrame signFrame = new SignFrame();
            signFrame.setVisible(true);
            dispose();
        });
        verifyDocumentButton = new JButton("Verify Document");
        verifyDocumentButton.addActionListener(e -> {
            VerifyFrame verifyFrame = new VerifyFrame();
            verifyFrame.setVisible(true);
            dispose();
        });
        aboutText = new JTextArea("This application allows you to sign and verify documents using digital signatures.");
    }
}