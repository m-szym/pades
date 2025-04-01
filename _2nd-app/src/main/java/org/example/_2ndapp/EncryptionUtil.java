package org.example._2ndapp;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class EncryptionUtil {
    public static SecretKey deriveKeyFromPin(String pin) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(pin.getBytes());
        return new SecretKeySpec(key, "AES");
    }

    public static String encryptPrivateKey(String privateKey, String pin) throws Exception {
        SecretKey secretKey = deriveKeyFromPin(pin);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(privateKey.getBytes()));
    }
}
