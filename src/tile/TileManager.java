package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public final class TileManager {
    GamePanel gp; // Reference to the GamePanel object
    public Tile[] tile; // Array to hold Tile objects
    public int mapTileNum[][]; // 2D array to hold the tile numbers for the map

    public TileManager(GamePanel gp) { // Constructor that takes a GamePanel object as a parameter
        this.gp = gp; // Assign the GamePanel object to the instance variable

        tile = new Tile[10]; // Create an array to hold 10 Tile objects
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // Initialize the mapTileNum array based on the world size

        getTileImage(); // Call the method to load tile images
        loadMap("/maps/map01.txt"); // Call the method to load the map from a file

    }

    public void getTileImage() {
        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass1.png")); // Load grass tile image

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png")); // Load stone tile image
            tile[1].collision = true; // Set collision to true for the stone tile

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water1.png")); // Load water tile image
            tile[2].collision = true; // Set collision to true for the water tile

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png")); // Load dirt tile image

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png")); // Load tree tile image
            tile[4].collision = true; // Set collision to true for the tree tile

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png")); // Load sand tile image

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/woodpath.png")); // Load wood path tile
                                                                                                 // image

        } catch (IOException e) {
            // Handle exception
        }
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public void loadMap(String filePath) { // Method to load the map from a text file
        try {
            InputStream is = getClass().getResourceAsStream(filePath); // Get the input stream for the map file
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // Create a BufferedReader to read the
                                                                               // map file

            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) { // Loop through the columns and rows of the map

                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" "); // Split the line into an array of strings based on spaces

                    int num = Integer.parseInt(numbers[col]); // Convert the string to an integer

                    mapTileNum[col][row] = num;
                    col++;

                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (IOException e) {

        }

    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow]; // Get the tile number for the current position

            int worldX = worldCol * gp.tileSize; // Calculate the world coordinates for the current tile
            int worldY = worldRow * gp.tileSize; // Calculate the world coordinates for the current tile
            int screenX = worldX - gp.player.worldX + gp.player.screenX; // Calculate the screen coordinates for the
                                                                         // current tile based on the player's position
            int screenY = worldY - gp.player.worldY + gp.player.screenY; //
            // Creating a boundry for the player so that the player is always in the center
            // of the screen and the world moves around the player
            if (worldX + gp.tileSize + 1 > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize + 1 < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize + 1 > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize + 1 < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null); // Draw the tile at
                                                                                                     // the specified
                                                                                                     // position

            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;

            }
        }

    }
}
