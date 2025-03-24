package ui.file_loader;

import java.io.File;
import java.io.IOException;

public interface FileLoadTester {
    boolean testFileLoading(File file) throws IOException;
}

