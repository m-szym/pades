package service.key_loading;

import exceptions.InvalidKeyFileException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static java.nio.charset.Charset.defaultCharset;

/**
 * \class LocalKeyLoader
 * \brief Implementation of KeyLoader interface for loading keys from PEM files.
 *
 * This class is meant to be used for development purposes only and should not be used in production.
 * It assumes that the keys are stored in PEM format and are not password-protected,
 * as such the PIN for the private key file is never used.
 * The class provides methods to load private and public keys from specified local PEM files.
 */
public class LocalKeyLoader implements KeyLoader {

    /**
     * \brief Loads a private key from the specified file using the provided PIN.
     * \param file The file from which the private key is to be loaded.
     * \param pin The PIN used to access the private key.
     * \return The loaded PrivateKey.
     * \throws InvalidKeyFileException If an error occurs while loading the private key.
     */
    public PrivateKey loadPrivateKey(File file, String pin) throws InvalidKeyFileException {
        try {
            byte[] keyBytes = LocalKeyLoader.parseKey(file, "PRIVATE");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        } catch (InvalidKeySpecException | IOException e) {
            throw new InvalidKeyFileException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * \brief Loads a public key from the specified file.
     * \param file The file from which the public key is to be loaded.
     * \return The loaded PublicKey.
     * \throws InvalidKeyFileException If an error occurs while loading the public key.
     */
    public PublicKey loadPublicKey(File file) throws InvalidKeyFileException {
        try {
            byte[] keyBytes = LocalKeyLoader.parseKey(file, "PUBLIC");
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
    private static byte[] parseKey(File file, String keyType) throws IOException {
        String keyString = Files.readString(file.toPath(), defaultCharset())
                .replace("-----BEGIN " + keyType + " KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END " + keyType + " KEY-----", "");
        return Base64.getDecoder().decode(keyString);
    }
}
