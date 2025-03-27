package service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

import exceptions.PdfFileOpeningException;

/**
 * \class PdfLoaderWrapper
 * \brief Utility class for loading PDF documents.
 *
 * This class provides a method to load PDF documents and handle exceptions that may occur during the process.
 */
public class PdfLoaderWrapper {

    /**
     * \brief Loads a PDF document from the specified file.
     * \param file The file from which the PDF document is to be loaded.
     * \return The loaded PDDocument.
     * \throws PdfFileOpeningException If an error occurs while opening the PDF file.
     */
    public static PDDocument loadPDF(File file) throws PdfFileOpeningException {
        try {
            return Loader.loadPDF(file);
        } catch (IOException e) {
            throw new PdfFileOpeningException("Couldn't open: " + file.getName());
        }
    }
}