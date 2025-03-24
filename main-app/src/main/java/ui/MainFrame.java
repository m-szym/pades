package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JButton signDocumentButton;
    private JButton verifyDocumentButton;
    private JTextArea aboutText;

    public MainFrame() {
        initializeComponents();
        buildFrame();
        setTitle("Digital Signature Application");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

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