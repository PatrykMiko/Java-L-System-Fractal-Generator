package org.example;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Main entry point for the L-System Fractal Generator application.
 * It reads the L-System configuration from a JSON file and initializes the Swing GUI.
 */
public class Main {
    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(() -> {

            String jsonContent = null;
            try {
                // Read the L-System definition from an external JSON file
                jsonContent = Files.readString(Path.of("choinka.json"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Deserialize the JSON string into an LSystem object
            LSystem lSystem = LSystem.fromJson(jsonContent);

            // Set up the main application window
            JFrame frame = new JFrame(lSystem.getName());
            frame.setContentPane(new DrawPanel(lSystem));

            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null); // Center the window on the screen
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(true);
            frame.setVisible(true);
        });
    }
}