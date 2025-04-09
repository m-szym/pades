package service.key_loading;

import exceptions.InvalidKeyFileException;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * \interface KeyLoader
 * \brief Interface for loading cryptographic keys from files.
 *
 * This interface provides methods to load private and public keys from specified files.
 */
public interface KeyLoader {

    /**
     * \brief Loads a private key from the specified file using the provided PIN.
     * \param file The file from which the private key is to be loaded.
     * \param pin The PIN used to access the private key.
     * \return The loaded PrivateKey.
     * \throws InvalidKeyFileException If an error occurs while loading the private key.
     */
    PrivateKey loadPrivateKey(File file, String pin) throws InvalidKeyFileException, InvalidKeyException;

    /**
     * \brief Loads a public key from the specified file.
     * \param file The file from which the public key is to be loaded.
     * \return The loaded PublicKey.
     * \throws InvalidKeyFileException If an error occurs while loading the public key.
     */
    PublicKey loadPublicKey(File file) throws InvalidKeyFileException;
}