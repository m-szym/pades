/**
 * \file InvalidKeyFileException.java
 * \brief Defines the InvalidKeyFileException class thrown when a file containing RSA key is invalid for some reason.
 */

package exceptions;

import java.io.IOException;

/**
 * \class InvalidKeyFileException
 * \brief Exception thrown when an invalid file that should contain RSA key is encountered.
 *
 * Extends the IOException
 */
public class InvalidKeyFileException extends IOException {

    /**
     * \brief Constructs an InvalidKeyFileException with the specified detail message.
     * \param message The detail message.
     */
    public InvalidKeyFileException(String message) {
        super(message);
    }

    /**
     * \brief Constructs an InvalidKeyFileException with the specified detail message and cause.
     * \param message The detail message.
     * \param cause The cause of the exception.
     */
    public InvalidKeyFileException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * \brief Constructs an InvalidKeyFileException with the specified cause.
     * \param cause The cause of the exception.
     */
    public InvalidKeyFileException(Throwable cause) {
        super(cause);
    }
}