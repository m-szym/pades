package exceptions;

import java.io.IOException;

public class PdfFileOpeningException extends IOException {
    public PdfFileOpeningException(String message) {
        super(message);
    }
    public PdfFileOpeningException(String message, Throwable cause) {
        super(message, cause);
    }
    public PdfFileOpeningException(Throwable cause) {
        super(cause);
    }
}
