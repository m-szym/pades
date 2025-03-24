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

public class Signer {
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public void sign(File pdfFile,
                     PrivateKey privateKey,
                     PDSignature signature,
                     File outputFile)
            throws SigningException, InvalidKeyException, PdfFileOpeningException, PdfFileSavingException, FileNotFoundException {
        if (pdfFile == null || privateKey == null || signature == null) {
            throw new IllegalStateException("pdfFile, privateKey and signature must be set before signing");
        }

        try (PDDocument doc = PdfLoaderWrapper.loadPDF(pdfFile)) {
            // Create interface that will sign the document
            SignatureInterface signer = content -> {
                try {
                    java.security.Signature signature1 = java.security.Signature.getInstance(SIGNATURE_ALGORITHM);
                    signature1.initSign(privateKey);
                    signature1.update(content.readAllBytes());
                    return signature1.sign();
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
            // Add the signature to the document
            try {
                doc.addSignature(signature, signer);
                // Save the signed document
                try {
                    doc.saveIncremental(new FileOutputStream(outputFile));
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
}
