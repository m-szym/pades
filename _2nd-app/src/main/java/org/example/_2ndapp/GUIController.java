package org.example._2ndapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;

public class GUIController {
    @FXML private TextField pinField;
    @FXML private Label statusLabel;

    @FXML
    private void handleGenerateKeys() {
        try {
            String pin = pinField.getText();
            if (pin.isEmpty()) {
                statusLabel.setText("PIN cannot be empty!");
                return;
            }

            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Folder to Save Keys");

            File selectedDirectory = directoryChooser.showDialog(null);

            if (selectedDirectory != null) {
                KeyPair keyPair = KeyGeneratorApp.generateRSAKeyPair();

                String encryptedPrivateKey = EncryptionUtil.encryptPrivateKey(
                        KeyGeneratorApp.encodeKeyToBase64(keyPair.getPrivate()), pin);
                String publicKey = KeyGeneratorApp.encodeKeyToBase64(
                        keyPair.getPublic());

                File outputFilePrivateKey = new File(selectedDirectory, "private_key.txt");
                File outputFilePublicKey = new File(selectedDirectory, "public_key.txt");

                try (FileWriter fileWriter = new FileWriter(outputFilePrivateKey)) {
                    fileWriter.write(encryptedPrivateKey);
                }
                try (FileWriter fileWriter = new FileWriter(outputFilePublicKey)) {
                    fileWriter.write(publicKey);
                }

                statusLabel.setText("Keys generated and saved to " + outputFilePrivateKey.getAbsolutePath());
            } else {
                statusLabel.setText("No folder selected.");
            }
        } catch (IOException e) {
            statusLabel.setText("Error saving file: " + e.getMessage());
        } catch (Exception e) {
            statusLabel.setText("Error generating keys: " + e.getMessage());
        }
    }
}

