package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import main.GamePanel;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Key_2;

public class SaveLoad {

    public GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")))) {

            DataStorage ds = new DataStorage();

            ds.hasKey = gp.player.hasKey;
            ds.hasKey2 = gp.player.hasKey2;
            ds.playerWorldX = gp.player.worldX;
            ds.playerWorldY = gp.player.worldY;

            ds.objNames = new String[gp.obj.length];
            ds.objWorldX = new int[gp.obj.length];
            ds.objWorldY = new int[gp.obj.length];

            for (int i = 0; i < gp.obj.length; i++) {
                if (gp.obj[i] == null) {
                    ds.objNames[i] = null;
                } else {
                    ds.objNames[i] = gp.obj[i].name;
                    ds.objWorldX[i] = gp.obj[i].worldX;
                    ds.objWorldY[i] = gp.obj[i].worldY;
                }
            }

            oos.writeObject(ds);
            System.out.println("Game saved.");

        } catch (IOException e) {
            System.out.println("Save failed: " + e);
        }
    }

    public void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")))) {

            DataStorage ds = (DataStorage) ois.readObject();

            gp.player.hasKey = ds.hasKey;
            gp.player.hasKey2 = ds.hasKey2;
            gp.player.worldX = ds.playerWorldX;
            gp.player.worldY = ds.playerWorldY;

            for (int i = 0; i < gp.obj.length; i++) {
                if (ds.objNames[i] == null) {
                    gp.obj[i] = null; // picked up / opened - stays gone
                } else {
                    switch (ds.objNames[i]) {
                        case "Key":
                            gp.obj[i] = new OBJ_Key();
                            break;

                        case "Key2":
                            gp.obj[i] = new OBJ_Key_2();
                            break;

                        case "Door":
                            gp.obj[i] = new OBJ_Door();
                            break;

                        case "Chest":
                            gp.obj[i] = new OBJ_Chest();
                            break;

                    }
                    gp.obj[i].worldX = ds.objWorldX[i];
                    gp.obj[i].worldY = ds.objWorldY[i];
                }
            }
            System.out.println("Game loaded.");

        } catch (Exception e) {
            System.out.println("Load failed: " + e);
        }
    }
}
