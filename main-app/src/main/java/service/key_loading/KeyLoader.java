package service.key_loading;

import exceptions.InvalidKeyFileException;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyLoader {
    PrivateKey loadPrivateKey(File file, String pin) throws InvalidKeyFileException;
    PublicKey loadPublicKey(File file) throws InvalidKeyFileException;
}
