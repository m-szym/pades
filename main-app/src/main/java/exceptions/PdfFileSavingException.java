package exceptions;

public class PdfFileSavingException extends RuntimeException {
    public PdfFileSavingException(String message) {
        super(message);
    }
    public PdfFileSavingException(String message, Throwable cause) {
        super(message, cause);
    }
    public PdfFileSavingException(Throwable cause) {
        super(cause);
    }
}

