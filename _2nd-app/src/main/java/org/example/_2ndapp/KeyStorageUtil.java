package org.example._2ndapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class KeyStorageUtil {
    public static void saveToFile(String filePath, String data) throws IOException {
        Files.write(Path.of(filePath), data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
