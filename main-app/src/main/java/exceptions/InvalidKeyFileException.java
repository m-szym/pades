package exceptions;

import java.io.IOException;

public class InvalidKeyFileException extends IOException {
    public InvalidKeyFileException(String message) {super(message);}
    public InvalidKeyFileException(String message, Throwable cause) {super(message, cause);}
    public InvalidKeyFileException(Throwable cause) {super(cause);}
}
