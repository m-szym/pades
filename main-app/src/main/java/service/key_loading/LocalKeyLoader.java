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

public class LocalKeyLoader implements KeyLoader {
    public PrivateKey loadPrivateKey(File file, String pin) throws InvalidKeyFileException {
        try {
            byte[] keyBytes = LocalKeyLoader.parseKeyBytes(file, "PRIVATE");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        } catch (InvalidKeySpecException | IOException e) {
            throw new InvalidKeyFileException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public PublicKey loadPublicKey(File file) throws InvalidKeyFileException {
        try {
            byte[] keyBytes = LocalKeyLoader.parseKeyBytes(file, "PUBLIC");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
        } catch (InvalidKeySpecException | IOException e) {
            throw new InvalidKeyFileException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] parseKeyBytes(File file, String keyType) throws IOException {
        String keyString = Files.readString(file.toPath(), defaultCharset())
                .replace("-----BEGIN " + keyType + " KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END " + keyType + " KEY-----", "");
        return Base64.getDecoder().decode(keyString);
    }
}
