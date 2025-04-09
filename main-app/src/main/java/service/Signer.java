package service;

import exceptions.*;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;

import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;

/**
 * \class Signer
 * \brief Service class for signing PDF documents with RSA private keys.
 *
 * This class provides functionality to sign PDF documents using provided RSA private key and configured signature object.
 * It handles the signing process, including loading the PDF document, applying the signature, and saving the signed document.
 */
public class Signer {
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";  /**< Algorithm used for signing the PDF. */

    /**
     * \brief Signs a PDF file with the given private key and signature.
     *
     * \param inputDoc The PDF file to be signed.
     * \param key The file containing the private key to be used for signing.
     * \param signature The configured signature to be applied to the PDF.
     * \param outputDoc The output file where the signed PDF will be saved.
     *
     * \throws SigningException If an error occurs during the signing process.
     * \throws InvalidKeyException If the provided private key is invalid.
     * \throws PdfFileOpeningException If the PDF file cannot be opened.
     * \throws PdfFileSavingException If the PDF file cannot be saved.
     * \throws FileNotFoundException If the output file cannot be found.
     * \throws IllegalStateException If any of the required input parameters are null.
     * \throws RunTimeException If the signing algorithm is not available (should never happen).
     */
    public static void sign(File inputDoc,
                     PrivateKey key,
                     PDSignature signature,
                     File outputDoc)
            throws SigningException, InvalidKeyException, PdfFileOpeningException, PdfFileSavingException, FileNotFoundException {
        if (inputDoc == null || key == null || signature == null || outputDoc == null) {
            throw new IllegalStateException("inputDoc, key, signature and outputDoc must not be NULL");
        }

        try (PDDocument doc = PdfLoaderWrapper.loadPDF(inputDoc)) {
            // Create interface that will sign the document
            SignatureInterface signer = getSignatureInterface(key);
            // Add the signature to the document
            try {
                doc.addSignature(signature, signer);
                // Save the signed document
                try {
                    doc.saveIncremental(new FileOutputStream(outputDoc));
                } catch (FileNotFoundException e) {
                    // rethrown to get around the IOException from the saveIncremental method and bring the FileNotFoundException to the caller
                    throw e;
                } catch (IOException | IllegalStateException e) {
                    // file could not be saved for some reason
                    throw new PdfFileSavingException(e);
                }
            } catch (IOException e) {
                // for the addSignature method IOException
                throw new SigningException("Couldn't create signature\n" + e.getMessage());
            } catch (IllegalStateException e) {
                throw new SigningException("A signature is already present in this document\nDocuments can only have ONE signature");
            }
        } catch (PdfFileOpeningException e) {
            // rethrown to inform the caller that the PDF file could not be opened
            // doc will be closed automatically after the try block
            throw e;
        } catch (InvalidKeyFileException e) {
            // rethrown to inform the caller that the private key is invalid
            throw new InvalidKeyException(e);
        } catch (IOException e) {
            // catch additional IOExceptions that might be thrown while loading the PDF file
            // and rethrow them as PdfFileOpeningException to inform the caller of their origin
            throw new PdfFileOpeningException(e);
        }
    }

    private static SignatureInterface getSignatureInterface(PrivateKey key) {
        SignatureInterface signer = content -> {
            try {
                java.security.Signature algorithm = java.security.Signature.getInstance(SIGNATURE_ALGORITHM);
                algorithm.initSign(key);
                algorithm.update(content.readAllBytes());
                return algorithm.sign();
            } catch (NoSuchAlgorithmException e) {
                // This should never happen as the algorithm is hardcoded
                // and should be available in all JVMs
                // If it happens, it's a programming error
                throw new RuntimeException(e);
            } catch (SignatureException e) {
                // Error occurred while signing the document
                throw new SigningException(e);
            } catch (InvalidKeyException e) {
                // The private key is invalid
                // Recast the exception because the interface doesn't allow for the InvalidKeyException
                // if thrown the InvalidKeyFileException will be caught by the surrounding catch block of Signer::sign
                // and rethrown as an InvalidKeyException to the caller
                throw new InvalidKeyFileException(e);
            }
        };
        return signer;
    }
}
