package exceptions;

/**
 * \class SigningException
 * \brief Exception thrown when there is an issue during the signing process.
 *
 * This class extends the RuntimeException and provides constructors to handle
 * different scenarios of signing exceptions.
 */
public class SigningException extends RuntimeException {

    /**
     * \brief Constructs a SigningException with the specified detail message.
     * \param message The detail message.
     */
    public SigningException(String message) {
        super(message);
    }

    /**
     * \brief Constructs a SigningException with the specified cause.
     * \param cause The cause of the exception.
     */
    public SigningException(Throwable cause) {
        super(cause);
    }

    /**
     * \brief Constructs a SigningException with the specified detail message and cause.
     * \param message The detail message.
     * \param cause The cause of the exception.
     */
    public SigningException(String message, Throwable cause) {
        super(message, cause);
    }
}