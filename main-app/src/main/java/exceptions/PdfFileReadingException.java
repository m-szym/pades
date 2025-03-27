package exceptions;

/**
 * \class PdfFileReadingException
 * \brief Exception thrown when Apache PDFBox encounters an issue while reading a PDF file.
 *
 * This class extends the RuntimeException and provides constructors to handle
 * different scenarios of PDF file reading exceptions.
 */
public class PdfFileReadingException extends RuntimeException {

    /**
     * \brief Constructs a PdfFileReadingException with the specified detail message.
     * \param message The detail message.
     */
    public PdfFileReadingException(String message) {
        super(message);
    }

    /**
     * \brief Constructs a PdfFileReadingException with the specified detail message and cause.
     * \param message The detail message.
     * \param cause The cause of the exception.
     */
    public PdfFileReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * \brief Constructs a PdfFileReadingException with the specified cause.
     * \param cause The cause of the exception.
     */
    public PdfFileReadingException(Throwable cause) {
        super(cause);
    }
}