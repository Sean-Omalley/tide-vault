package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Entity {
    public int worldX, worldY;
    public int speed;
    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // For animation purposes
    public String direction; // To keep track of the direction the entity is facing

    public int spriteCounter = 0; // To keep track of the animation frame
    public int spriteNum = 1; // To keep track of which sprite to display
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // To define the area of the entity that can collide with
                                                              // other objects
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false; // To check if the entity is colliding with something
    public int hasKey;
    public String dialogues[] = new String[20];
    public int dialogueIndex = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draw the object if it's within the visible screen area
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(down1, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public void speak() {

        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
            gp.gameState = gp.playState; // out of lines, close the box
            return;
        }

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        // face the player
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;

            case "down":
                direction = "up";
                break;

            case "left":
                direction = "right";
                break;

            case "right":
                direction = "left";
                break;

        }
    }
}
