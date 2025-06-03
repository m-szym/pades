/**
 * \file KeyStorageUtil.java
 * \brief Utility class for saving data to files.
 *
 * This class provides a method for saving string data to a specified file.
 */

package org.example._2ndapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * \class KeyStorageUtil
 * \brief A utility class for file storage operations.
 *
 * This class provides functionality to save data to files.
 */
public class KeyStorageUtil {
    /**
     * \brief Saves data to a file.
     *
     * This method writes the provided string data to the specified file path. If the file
     * already exists, it will be overwritten.
     *
     * \param filePath The path to the file where the data will be saved.
     * \param data The string data to save to the file.
     *
     * \throws IOException If an error occurs while writing to the file.
     */
    public static void saveToFile(String filePath, String data) throws IOException {
        Files.write(Path.of(filePath), data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
