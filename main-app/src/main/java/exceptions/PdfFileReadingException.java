package exceptions;

public class PdfFileReadingException extends RuntimeException {
    public PdfFileReadingException(String message) {
        super(message);
    }
    public PdfFileReadingException(String message, Throwable cause) {
        super(message, cause);
    }
    public PdfFileReadingException(Throwable cause) {
        super(cause);
    }
}
