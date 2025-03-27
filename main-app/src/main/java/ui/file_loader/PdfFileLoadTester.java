package ui.file_loader;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

/**
 * \class PdfFileLoadTester
 * \brief Class for testing the loading of PDF files.
 *
 * This class implements the FileLoadTester interface to provide a method for testing if a PDF file can be loaded successfully.
 */
public class PdfFileLoadTester implements FileLoadTester {
    /**
     * \brief Tests the loading of the specified PDF file.
     * \param file The file to be tested.
     * \return true if the file can be loaded successfully, false otherwise.
     * \throws IOException If an error occurs while loading the file.
     */
    @Override
    public boolean testFileLoading(File file) throws IOException {
        if (!file.getName().endsWith(".pdf")) {
            return false;
        }

        try (PDDocument doc = Loader.loadPDF(file)) {
            doc.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}