package exceptions;

/**
 * \class SignatureVerificationException
 * \brief Exception thrown when there is an issue verifying digital signature.
 *
 * This class extends the RuntimeException and provides constructors to handle
 * different scenarios of signature verification exceptions.
 */
public class SignatureVerificationException extends RuntimeException {

    /**
     * \brief Constructs a SignatureVerificationException with the specified detail message.
     * \param message The detail message.
     */
    public SignatureVerificationException(String message) {
        super(message);
    }

    /**
     * \brief Constructs a SignatureVerificationException with the specified detail message and cause.
     * \param message The detail message.
     * \param cause The cause of the exception.
     */
    public SignatureVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * \brief Constructs a SignatureVerificationException with the specified cause.
     * \param cause The cause of the exception.
     */
    public SignatureVerificationException(Throwable cause) {
        super(cause);
    }
}