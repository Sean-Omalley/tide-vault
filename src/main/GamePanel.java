package main;

import data.SaveLoad;
import entity.Entity;
import entity.Player;
import java.awt.*;
import javax.swing.JPanel;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    public final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12; // This sets the max tile size to 16x12 tiles
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World Settings

    public final int maxWorldCol = 50; // This sets the max world size to 50x50 tiles
    public final int maxWorldRow = 50; // This sets the max world size to 50x50 tiles
    public final int worldWidth = tileSize * maxWorldCol; // 2400 pixels
    public final int worldHeight = tileSize * maxWorldRow; // 2400 pixels
    public SaveLoad saveload = new SaveLoad(this);

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this); // Create an instance of TileManager
    KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    Sound se = new Sound();

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this); // Create an instance of AssetSetter
    public UI ui = new UI(this);
    Thread gameThread;

    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10]; // Create an array of SuperObject to hold game objects
    public Entity npc[] = new Entity[10];

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH); // Add the KeyHandler as a KeyListener
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject(); // Set up the game objects

        playMusic(0); // Play background music
        gameState = titleState; // Set the initial game state to TitleState
        aSetter.setObject();
        aSetter.setNPC();

    }

    public void startGameThread() {
        gameThread = new Thread(this); // Create a new thread for the game loop
        gameThread.start(); // Start the game loop
    }

    @Override
    public void run() {
        // Game loop
        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update(); // Update game state
                repaint(); // Call the paintComponent method to redraw the screen
                delta--; // Decrease delta by 1 after updating and repainting
                drawCount++; // Increment the draw count
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0; // Reset draw count
                timer = 0; // Reset timer
            }

        }

    }

    public void update() {
        if (gameState == playState) {
            player.update();
        }

        if (gameState == pauseState) {
            // Do nothing
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // Cast Graphics to Graphics2D
        // debug
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        // Title Screen
        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            // draw game elements

            // Tile
            tileM.draw(g2); // Draw the tiles

            // Object
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this); // Draw the object if it exists
                }
            }

            // npc

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(g2);
                }
            }

            // Player
            player.draw(g2);

            // UI
            ui.draw(g2);

            // debug
            if (keyH.checkDrawTime == true) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.white);
                g2.drawString("Draw Time: " + passed, 10, 400);
                System.out.println("Draw Time: " + passed);
            }
            g2.dispose();
        }
    }

    public class cChecker {

        public cChecker() {
        }
    }

    // MUSIC AND SOUND EFFECTS
    public void playMusic(int i) {
        sound.setFile(i);
        sound.setVolume(-30f); // Set volume to -20 decibels
        sound.loop();

    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSoundEffect(int i) {
        se.setFile(i);
        se.play();
    }

    //resets game when you finish it
    public void retry(){
    player.setDefaultValues();
    player.hasKey = 0;
    player.hasKey2 = 0;
    ui.gameFinished = false;

    for (int i = 0; i < obj.length; i++) {
        obj[i] = null;
    }
    aSetter.setObject();

    for (int i = 0; i < npc.length; i++) {
        npc[i] = null;
    }
    aSetter.setNPC();
    }

}
