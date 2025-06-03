/**
 * \file KeyGeneratorApp.java
 * \brief Utility class for generating RSA key pairs and encoding keys to Base64.
 *
 * This class provides methods for generating RSA key pairs using the BouncyCastle library
 * and encoding keys to Base64 format.
 */

package org.example._2ndapp;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.*;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Base64;

/**
 * \class KeyGeneratorApp
 * \brief A utility class for RSA key generation and encoding.
 *
 * This class uses the BouncyCastle library to generate RSA key pairs and provides
 * functionality to encode keys in Base64 format.
 */
public class KeyGeneratorApp {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * \brief Generates an RSA key pair.
     *
     * This method generates a 4096-bit RSA key pair using the BouncyCastle provider.
     *
     * \return A `KeyPair` object containing the generated RSA public and private keys.
     *
     * \throws NoSuchAlgorithmException If the RSA algorithm is not available.
     * \throws NoSuchProviderException If the BouncyCastle provider is not available.
     * \throws InvalidAlgorithmParameterException If the RSA key generation parameters are invalid.
     */
    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyGen.initialize(new RSAKeyGenParameterSpec(4096, RSAKeyGenParameterSpec.F4));
        return keyGen.generateKeyPair();
    }

    /**
     * \brief Encodes a key to Base64 format.
     *
     * This method converts the provided cryptographic key to a Base64-encoded string.
     *
     * \param key The cryptographic key to encode.
     *
     * \return A Base64-encoded string representation of the key.
     */
    public static String encodeKeyToBase64(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}

