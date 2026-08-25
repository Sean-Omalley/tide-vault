package data;

import java.io.Serializable;

public class DataStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    int hasKey;
    int hasKey2;
    int playerWorldX, playerWorldY;

    String[] objNames;
    int[] objWorldX, objWorldY;

}
