package service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import exceptions.PdfFileOpeningException;
import exceptions.PdfFileReadingException;
import exceptions.SignatureVerificationException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;

/**
 * \class Verifier
 * \brief Service class for verifying signatures in PDF documents.
 *
 * This class provides functionality to verify signatures in PDF documents using a specified public key.
 */
public class Verifier {
    public static final int KEY_SIZE = 1024;
    public static final String ALGORITHM = "SHA256withRSA";

    /**
     * \brief Verifies the signature of a PDF file with the given public key.
     *
     * \param inputDoc The PDF file to be verified.
     * \param key The file containing the public key to be used for verification.
     *
     * \return true if the signature is valid, false otherwise.
     *
     * \throws InvalidKeyException If the provided public key is invalid.
     * \throws SignatureVerificationException If an error occurs during the signature verification process.
     * \throws PdfFileOpeningException If the PDF file cannot be opened.
     * \throws PdfFileReadingException If the PDF file cannot be read.
     * \throws RuntimeException If the signature algorithm is not available (should never happen).
     * \throws IllegalStateException If any of the required input parameters are null.
     */
    public static boolean verify(File inputDoc, PublicKey key)
            throws InvalidKeyException, SignatureVerificationException, PdfFileOpeningException, PdfFileReadingException {
        if (inputDoc == null || key == null) {
            throw new IllegalStateException("Both inputDoc and key must be supplied before verifying");
        }

        try (PDDocument doc = PdfLoaderWrapper.loadPDF(inputDoc)) {
            PDSignature signature = doc.getSignatureDictionaries().getFirst();
            byte[] signatureBytes = Arrays.copyOfRange(signature.getContents(), 0, KEY_SIZE / 8);
            // Contents field is always 96 bytes long, but we only need the first KEY_SIZE bytes

            java.security.Signature signatureVerifier = java.security.Signature.getInstance(ALGORITHM);
            signatureVerifier.initVerify(key);
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(Files.readAllBytes(inputDoc.toPath()));
                signatureVerifier.update(signature.getSignedContent(bis));
                return signatureVerifier.verify(signatureBytes);
            } catch (IOException e) {
                throw new PdfFileReadingException("Couldn't read: " + inputDoc.getName(), e);
            }
        } catch (PdfFileOpeningException e) {
            // rethrown to inform the caller that the PDF file could not be opened
            // doc will be closed automatically after the try block
            throw e;
        } catch (NoSuchAlgorithmException e) {
            // SHA256withRSA is a valid algorithm, so this exception should never be thrown
            // if it is thrown nonetheless, nothing can be done about it
            throw new RuntimeException(e);
        } catch (IOException e) {
            // catch additional IOExceptions that might be thrown while loading the PDF file
            // and rethrow them as PdfFileOpeningException to inform the caller of their origin
            throw new PdfFileOpeningException(e);
        } catch (SignatureException e) {
            // error occurred while verifying the signature
            throw new SignatureVerificationException(e);
        } catch (NoSuchElementException e) {
            // no signature found in the PDF file
            throw new SignatureVerificationException("No signature found in the provided PDF file");
        }
    }
}
