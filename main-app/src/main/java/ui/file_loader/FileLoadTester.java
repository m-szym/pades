package ui.file_loader;

import java.io.File;
import java.io.IOException;

/**
 * \interface FileLoadTester
 * \brief Interface for testing the loading of files.
 *
 * Interface used by the FileLoaderComponent to verify if a file can be loaded successfully.
 */
public interface FileLoadTester {
    /**
     * \brief Tests the loading of the specified file.
     * \param file The file to be tested.
     * \return true if the file can be loaded successfully, false otherwise.
     * \throws IOException If an error occurs while loading the file.
     */
    boolean testFileLoading(File file) throws IOException;
}

