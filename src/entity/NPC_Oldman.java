package entity;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class NPC_Oldman extends Entity {

    public NPC_Oldman(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;

        solidArea = new Rectangle();
        solidArea.x = 10;
        solidArea.y = 24;
        solidArea.width = 28;
        solidArea.height = 22;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setDialogue();
    }

    public void getImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/npc/Character 1-1.png"));
            down2 = down1;
            up1 = down1;
            up2 = down1;
            left1 = down1;
            left2 = down1;
            right1 = down1;
            right2 = down1;
            System.out.println("NPC iamge laoded: " + (down1 != null));
        } catch (IOException e) {
            System.err.println("NPC image failed to load: " + e);
        }
    }

    public void setDialogue() {
        dialogues[0] = "Hello, traveler. You've come a long way.";
        dialogues[1] = "The vault to the east holds great treasure...";
        dialogues[2] = "But you'll need the right key to open it.";
        dialogues[3] = "Search well, and fortune may find you.";
    }

}
