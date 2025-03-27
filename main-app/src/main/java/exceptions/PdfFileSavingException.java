package exceptions;

/**
 * \class PdfFileSavingException
 * \brief Exception thrown when there is an issue saving a PDF file with Apache PDFBox.
 *
 * This class extends the RuntimeException and provides constructors to handle
 * different scenarios of PDF file saving exceptions.
 */
public class PdfFileSavingException extends RuntimeException {

    /**
     * \brief Constructs a PdfFileSavingException with the specified detail message.
     * \param message The detail message.
     */
    public PdfFileSavingException(String message) {
        super(message);
    }

    /**
     * \brief Constructs a PdfFileSavingException with the specified detail message and cause.
     * \param message The detail message.
     * \param cause The cause of the exception.
     */
    public PdfFileSavingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * \brief Constructs a PdfFileSavingException with the specified cause.
     * \param cause The cause of the exception.
     */
    public PdfFileSavingException(Throwable cause) {
        super(cause);
    }
}