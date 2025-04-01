/**
 * \file Main.java
 * \brief Entry point for the Key Generator application.
 *
 * This class initializes and launches the JavaFX application.
 */

package org.example._2ndapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * \class Main
 * \brief The main class for the Key Generator application.
 *
 * This class extends the `Application` class and serves as the entry point for the JavaFX application.
 */
public class Main extends Application {

    /**
     * \brief Starts the JavaFX application.
     *
     * This method sets up the primary stage, loads the FXML layout, and displays the main application window.
     *
     * \param primaryStage The primary stage for the JavaFX application.
     *
     * \throws IOException If an error occurs while loading the FXML layout.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Key Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * \brief The main method for launching the application.
     *
     * This method calls the `launch` method to start the JavaFX application.
     *
     * \param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch();
    }
}