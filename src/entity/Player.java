package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public final class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey2 = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 10;
        solidArea.y = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 28;
        solidArea.height = 22;

        setDefaultValues(); // Set default values for the player
        getPlayerImage(); // Load player images

    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 24; // Set the initial world X position of the player
        worldY = gp.tileSize * 24; // Set the initial world Y position of the player
        speed = 4;
        direction = "down"; // Set the initial direction of the player

    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Char up.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Char up.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/Character 1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/Character 1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Char left.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Char left.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Char right.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Char right.png"));

        } catch (IOException e) {
            System.err.println("Error loading player images: " + e.getMessage());
        }
    }

    public void update() {

        // Check for an NPC in front of the player - works while standing still
        if (gp.gameState == gp.playState && keyH.enterPressed) {
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
        }

        boolean moving = keyH.upPressed || keyH.downPressed
                || keyH.leftPressed || keyH.rightPressed;

        if (moving) {
            // diagonal speed
            boolean movingV = keyH.upPressed || keyH.downPressed;
            boolean movingH = keyH.leftPressed || keyH.rightPressed;

            int moveSpeed = (movingV && movingH)
                    ? (int) Math.round(speed / Math.sqrt(2))
                    : speed;

            if (keyH.upPressed || keyH.downPressed) {
                direction = keyH.upPressed ? "up" : "down";

                collisionOn = false;
                gp.cChecker.checkTile(this);

                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

                gp.cChecker.checkEntity(this, gp.npc);

                if (!collisionOn) {
                    worldY += keyH.upPressed ? -moveSpeed : moveSpeed;
                }

            }

            if (keyH.leftPressed || keyH.rightPressed) {
                direction = keyH.leftPressed ? "left" : "right";

                collisionOn = false;
                gp.cChecker.checkTile(this);

                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

                gp.cChecker.checkEntity(this, gp.npc);

                if (!collisionOn) {
                    worldX += keyH.leftPressed ? -moveSpeed : moveSpeed;
                }
            }

            // Clamp to the map so the player can't walk off the world
            int maxX = (gp.maxWorldCol - 1) * gp.tileSize;
            int maxY = (gp.maxWorldRow - 1) * gp.tileSize;
            if (worldX < 0)
                worldX = 0;
            if (worldY < 0)
                worldY = 0;
            if (worldX > maxX)
                worldX = maxX;
            if (worldY > maxY)
                worldY = maxY;

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;

                case "Key2":
                    hasKey2++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You Found the vault key!");
                    break;

                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                    }
                    System.out.println("Key:" + hasKey);
                    break;
                case "Chest":
                    if (hasKey2 > 0) {
                        gp.obj[i] = null;
                        hasKey2--;
                        //gp.ui.showMessage("You found the treasure");
                        gp.ui.gameFinished = true;
                    } else {
                        gp.ui.showMessage("The vault is locked. Find the fault key.");
                    }
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                } else {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                } else {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                } else {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                } else {
                    image = right2;
                }
            }
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // Draw the player image at the player's
                                                                               // position with the specified tile size
    }

    public void interactNPC(int i) {
        
        
        if (i != 999) {
            if (keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
                keyH.enterPressed = false;
            }
        }

    }

}
