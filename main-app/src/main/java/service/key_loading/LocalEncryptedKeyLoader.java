package service.key_loading;

import exceptions.InvalidKeyFileException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static java.nio.charset.Charset.defaultCharset;

public class LocalEncryptedKeyLoader implements KeyLoader{
    @Override
    public PrivateKey loadPrivateKey(File file, String pin) throws InvalidKeyFileException, InvalidKeyException {
        try {
            // Read the key bytes from the file
            byte[] keyBytes = parseKey(file);
            // Derive the secret key from the PIN
            SecretKey secret = deriveKeyFromPin(pin);
            // Decrypt the key using AES with the provided PIN
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            byte[] decrypted = cipher.doFinal(keyBytes);
            // Decode the decrypted bytes
            byte[] decoded = Base64.getDecoder().decode(decrypted);
            // Convert the decrypted bytes to a PrivateKey object
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoded));
        } catch (InvalidKeySpecException | IOException e) {
            throw new InvalidKeyFileException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            // Used algorithms are AES, RSA and SHA-256
            // all should be available in any distribution
            // if they're missing the situation's worthy of RuntimeException
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            throw new InvalidKeyException(e);
        }
    }

    @Override
    public PublicKey loadPublicKey(File file) throws InvalidKeyFileException {
        try {
            byte[] keyBytes = parseKey(file);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
        } catch (InvalidKeySpecException | IOException e) {
            throw new InvalidKeyFileException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * \brief Parses the key written as text from the specified file.
     * \param file The file from which the key text are to be parsed.
     * \param keyType The type of the key (PRIVATE or PUBLIC).
     * \return The parsed key bytes.
     * \throws IOException If an error occurs while reading the file.
     */
    private static byte[] parseKey(File file) throws IOException {
        String keyString = Files.readString(file.toPath(), defaultCharset())
                .replaceAll(System.lineSeparator(), "");
        return Base64.getDecoder().decode(keyString);
    }

    /**
     * \brief Derives a SecretKey from a given PIN.
     *
     * This method uses the SHA-256 hashing algorithm to generate a cryptographic key
     * from the provided PIN. The resulting key is used for AES encryption.
     * \param pin The PIN used to derive the encryption key.
     * \return A `SecretKey` object derived from the PIN.
     * \throws Exception If the SHA-256 hashing algorithm is not available.
     */
    public static SecretKey deriveKeyFromPin(String pin) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(pin.getBytes());
        return new SecretKeySpec(key, "AES");
    }
}
