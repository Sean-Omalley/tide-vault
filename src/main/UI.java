package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class UI {
    GamePanel gp;
    Font arial_40, arial_80;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public BufferedImage titleScreen;
    public int commandNum = 0;
    public String currentDialogue = "";
    int winCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80 = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        // Title State
        if (gp.gameState == gp.titleState) {
            drawTitleScreen(g2);
            return;
        }

        // Dialogue State
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen(g2);
            return;
        }

        if (gameFinished == true) {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            if (gp.gameState == gp.playState) {
                // do play stuff

                String text;
                int textLength;

                int x;
                int y;

                text = "YOU FOUND THE TREASURE!";
                textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

                x = gp.screenWidth / 2 - textLength / 2;
                y = gp.screenHeight / 2 - (gp.tileSize * 3);
                g2.drawString(text, x, y);

                g2.setFont(arial_80);
                g2.setColor(Color.yellow);
                text = "Congratulations!";
                textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

                x = gp.screenWidth / 2 - textLength / 2;
                y = gp.screenHeight / 2 + (gp.tileSize * 2);
                g2.drawString(text, x, y);


                //pauses, then finishes game
                winCounter++;

                if (winCounter > 180) {
                winCounter = 0;
                gameFinished = false;
                gp.gameState = gp.titleState;
                }
                

            } else {
                g2.setFont(arial_40);
                g2.setColor(Color.white);
                g2.drawString("Key: " + gp.player.hasKey, 50, 50); // Display the number of keys the player has at
                                                                   // position (50, 50)
            }



            

        }

                        if (messageOn == true) {

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));
                    g2.setColor(Color.black);
                    g2.drawString(message, gp.tileSize + 2, gp.tileSize * 2 + 2);
                    g2.setColor(Color.white);
                    g2.drawString(message, gp.tileSize, gp.tileSize * 2);

                    messageCounter++;

                    if (messageCounter > 150) {
                        messageCounter = 0;
                        messageOn = false;
                    }
                }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen(g2);
        }

    }

    public void drawTitleScreen(Graphics2D g2) {
        // Title BACKGROUND
        try {
            BufferedImage bg = ImageIO.read(getClass().getResourceAsStream("/tiles/TVcover.jpg"));
            g2.drawImage(bg, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Menu Customization

        g2.setColor(new Color(6, 20, 48, 110));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        int centerX = gp.screenWidth / 2;

        // Title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Tide Vault";
        int x = (int) (gp.screenWidth / 2 - g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
        int y = gp.tileSize * 3;

        // More menu customization
        g2.setColor(new Color(8, 24, 52)); // deep navy shadow
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(new Color(151, 222, 122)); // seafoam green from your art
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        String[] options = { "NEW GAME", "LOAD GAME", "QUIT" }; //
        int panelW = 340; // Width of each option panel
        int panelH = 62; // Height of each option panel
        int panelX = centerX - panelW / 2; // Center the panel horizontally

        for (int i = 0; i < options.length; i++) {

            int panelY = gp.tileSize * 6 + (i * 80) - 45;
            boolean selected = (commandNum == i);

            g2.setColor(selected ? new Color(30, 74, 122, 210) : new Color(10, 32, 64, 160));
            g2.fillRoundRect(panelX, panelY, panelW, panelH, 14, 14);

            g2.setColor(selected ? new Color(151, 222, 122) : new Color(70, 110, 150, 180));
            g2.setStroke(new BasicStroke(selected ? 3f : 2f));
            g2.drawRoundRect(panelX, panelY, panelW, panelH, 14, 14);

            text = options[i];
            int textW = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int textY = panelY + 44;

            g2.setColor(selected ? Color.white : new Color(200, 216, 230));
            g2.drawString(text, centerX - textW / 2, textY);

            if (selected) {
                g2.setColor(new Color(151, 222, 122));
                g2.drawString(">", panelX - 34, textY);
            }
        }
    }

    public void drawPauseScreen(Graphics2D g2) {
        String text = "Paused";
        g2.setFont(new Font("Arial", Font.BOLD, 48));
        g2.setColor(Color.white);

        int x = (int) (gp.screenWidth / 2 - g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);

        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);

        String savetext = "To Save Game Press O";
        g2.drawString(savetext, x - 150, y + 100);

    }

    public void drawDialogueScreen(Graphics2D g2) {

        int x = gp.tileSize;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 2);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.white);

        x += gp.tileSize / 2;
        y += gp.tileSize;

        if (currentDialogue != null) {
            for (String line : currentDialogue.split("\n")) {
                g2.drawString(line, x, y);
                y += 32;
            }
        }
    }

    public void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {

        g2.setColor(new Color(10, 32, 64, 210));
        g2.fillRoundRect(x, y, width, height, 25, 25);

        g2.setColor(new Color(151, 222, 122));
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x + 4, y + 4, width - 8, height - 8, 20, 20);
    }

}
