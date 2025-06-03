/**
 * \file EncryptionUtil.java
 * \brief Utility class for encryption-related operations.
 *
 * This file contains the implementation of the `EncryptionUtil` class, which provides
 * methods for deriving encryption keys from a PIN and encrypting private keys.
 */

package org.example._2ndapp;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * \class EncryptionUtil
 * \brief A utility class for encryption operations.
 *
 * This class provides methods to:
 * - Derive a cryptographic key from a PIN using the SHA-256 hashing algorithm.
 * - Encrypt private keys using AES encryption and encode the result in Base64.
 */
public class EncryptionUtil {
        /**
     * \brief Derives a SecretKey from a given PIN.
     *
     * This method uses the SHA-256 hashing algorithm to generate a cryptographic key
     * from the provided PIN. The resulting key is used for AES encryption.
     *
     * \param pin The PIN used to derive the encryption key.
     *
     * \return A `SecretKey` object derived from the PIN.
     *
     * \throws Exception If the SHA-256 hashing algorithm is not available.
     */
    public static SecretKey deriveKeyFromPin(String pin) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(pin.getBytes());
        return new SecretKeySpec(key, "AES");
    }

    /**
     * \brief Encrypts a private key using a PIN.
     *
     * This method encrypts the provided private key using a `SecretKey` derived from the given PIN.
     * The encryption algorithm used is AES, and the encrypted result is encoded in Base64 format.
     *
     * \param privateKey The private key to be encrypted, provided as a plain string.
     * \param pin The PIN used to derive the encryption key.
     *
     * \return A Base64-encoded string representing the encrypted private key.
     *
     * \throws Exception If an error occurs during encryption or key derivation.
     */
    public static String encryptPrivateKey(String privateKey, String pin) throws Exception {
        SecretKey secretKey = deriveKeyFromPin(pin);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(privateKey.getBytes()));
    }
}
