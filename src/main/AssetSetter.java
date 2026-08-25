package main;

import entity.NPC_Oldman;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Key_2;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Key_2();
        gp.obj[0].worldX = 12 * gp.tileSize;
        gp.obj[0].worldY = 14 * gp.tileSize;

        gp.obj[1] = new OBJ_Door();
        gp.obj[1].worldX = 13 * gp.tileSize;
        gp.obj[1].worldY = 16 * gp.tileSize;

        gp.obj[2] = new OBJ_Chest();
        gp.obj[2].worldX = 17 * gp.tileSize;
        gp.obj[2].worldY = 48 * gp.tileSize;

        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 24 * gp.tileSize;
        gp.obj[3].worldY = 27 * gp.tileSize;

        gp.obj[4] = new OBJ_Door();
        gp.obj[4].worldX = 24 * gp.tileSize;
        gp.obj[4].worldY = 21 * gp.tileSize;

        gp.obj[5] = new OBJ_Key();
        gp.obj[5].worldX = 24 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;

        gp.obj[6] = new OBJ_Key();
        gp.obj[6].worldX = 21 * gp.tileSize;
        gp.obj[6].worldY = 26 * gp.tileSize;

        gp.obj[7] = new OBJ_Key();
        gp.obj[7].worldX = 9 * gp.tileSize;
        gp.obj[7].worldY = 9 * gp.tileSize;

    }

    public void setNPC() {
        gp.npc[0] = new NPC_Oldman(gp);
        gp.npc[0].worldX = gp.tileSize * 22;
        gp.npc[0].worldY = gp.tileSize * 22;
        System.out.println("NPC created at " + gp.npc[0].worldX + "," + gp.npc[0].worldY);
    }

}
