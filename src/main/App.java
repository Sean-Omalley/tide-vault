package main;

import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame("TideVault");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false); // Prevent the window from being resized

        GamePanel gamePanel = new GamePanel(); // Create an instance of GamePanel
        window.add(gamePanel); // Add the game panel to the window
        window.pack(); // sizes the window to fit the panel exactly

        window.setLocationRelativeTo(null); // Center the window on the screen
        window.setVisible(true); // Make the window visible

        gamePanel.setupGame(); // Set up the game (initialize objects, etc.)
        gamePanel.startGameThread(); // Start the game loop in a separate thread

    }
}
