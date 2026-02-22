package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    //PLAYER STATUS
    int level;
    int maxLife;
    int life;
    int maxMana;
    int strength;
    int dexterity;
    int mana;
    int exp;
    int nextLevelExp;
    int coin;

    // player inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer>itemAmount = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;

    //OBJECTS ON MAP
    String mapObjectsNames[][];
    int mapObjectsWorldX[][];
    int mapObjectsWorldY[][];
    String mapObjectsLootNames[][];
    boolean mapObjectOpened[][];
}
