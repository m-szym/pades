package exceptions;

import java.io.IOException;

/**
 * \class PdfFileOpeningException
 * \brief Exception thrown when Apache PDFBox fails to open a PDF file and create a PDDocument.
 *
 * This class extends the IOException and provides constructors to handle
 * different scenarios of PDF file opening exceptions.
 */
public class PdfFileOpeningException extends IOException {

    /**
     * \brief Constructs a PdfFileOpeningException with the specified detail message.
     * \param message The detail message.
     */
    public PdfFileOpeningException(String message) {
        super(message);
    }

    /**
     * \brief Constructs a PdfFileOpeningException with the specified detail message and cause.
     * \param message The detail message.
     * \param cause The cause of the exception.
     */
    public PdfFileOpeningException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * \brief Constructs a PdfFileOpeningException with the specified cause.
     * \param cause The cause of the exception.
     */
    public PdfFileOpeningException(Throwable cause) {
        super(cause);
    }
}