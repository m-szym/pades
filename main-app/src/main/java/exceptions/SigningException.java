package exceptions;

public class SigningException extends RuntimeException {
    public SigningException(String message) {
        super(message);
    }
    public SigningException(Throwable cause) {super(cause);}
    public SigningException(String message, Throwable cause) {
        super(message, cause);
    }
}
