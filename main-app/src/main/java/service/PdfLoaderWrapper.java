package service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

import exceptions.PdfFileOpeningException;

public class PdfLoaderWrapper {
    public static PDDocument loadPDF(File file) throws PdfFileOpeningException {
        try {
            return Loader.loadPDF(file);
        } catch (IOException e) {
            throw new PdfFileOpeningException("Couldn't open: " + file.getName());
        }
    }
}
