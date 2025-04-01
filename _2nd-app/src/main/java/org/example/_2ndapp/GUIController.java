/**
 * \file GUIController.java
 * \brief Controller class for handling GUI interactions.
 *
 * This file contains the implementation of the `GUIController` class, which provides
 * functionality for generating RSA key pairs, encrypting private keys, and saving them to files.
 */

package org.example._2ndapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;

/**
 * \class GUIController
 * \brief A controller class for managing GUI actions.
 *
 * This class handles user interactions for generating and saving RSA key pairs.
 */
public class GUIController {
    /**
     * \brief TextField for entering the PIN.
     *
     * This field allows the user to input a PIN, which is used to encrypt the private key.
     */
    @FXML private TextField pinField;

    /**
     * \brief Label for displaying status messages.
     *
     * This label is used to show success or error messages to the user.
     */
    @FXML private Label statusLabel;

    /**
     * \brief Handles the "Generate Keys" button action.
     *
     * This method generates an RSA key pair, encrypts the private key using the provided PIN,
     * and saves both the encrypted private key and the public key to files in a user-selected directory.
     *
     * \details
     * - Prompts the user to enter a PIN.
     * - Validates that the PIN is not empty.
     * - Opens a directory chooser for the user to select a folder to save the keys.
     * - Uses the `KeyGeneratorApp` class to generate the RSA key pair and encode the keys to Base64.
     * - Encrypts the private key using the `EncryptionUtil` class.
     * - Saves the encrypted private key and public key to separate files.
     * - Updates the status label with success or error messages.
     *
     * \throws IOException If an error occurs while writing the keys to files.
     * \throws Exception If an error occurs during key generation or encryption.
     */
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

