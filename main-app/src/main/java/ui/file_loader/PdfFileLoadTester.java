package ui.file_loader;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class PdfFileLoadTester implements FileLoadTester {
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
