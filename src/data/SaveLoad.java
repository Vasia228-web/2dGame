package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveLoad {
    GamePanel gp;
    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }
    public void save(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            DataStorage ds = new DataStorage();

            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;
            //PLAYER INVENTORY
            for(int i = 0; i < gp.player.inventory.size(); i++){
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmount.add(gp.player.inventory.get(i).amount);
            }
            //PLAYER EQUIPMENT
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();

            //OBJECTS ON MAP
            ds.mapObjectsNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectsWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectsWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectsLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];

            for(int mapNum = 0; mapNum< gp.maxMap; mapNum++){
                for(int i = 0; i< gp.obj[1].length; i++){
                    if(gp.obj[mapNum][i] == null){
                        ds.mapObjectsNames[mapNum][i] ="NA";
                    }
                    else{
                        ds.mapObjectsNames[mapNum][i] =gp.obj[mapNum][i].name;
                        ds.mapObjectsWorldX[mapNum][i] =gp.obj[mapNum][i].worldX;
                        ds.mapObjectsWorldY[mapNum][i] =gp.obj[mapNum][i].worldY;
                        if(gp.obj[mapNum][i].loot != null){
                            ds.mapObjectsLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }
            }
            oos.writeObject(ds);
            oos.close();

        }
        catch (Exception e){
            System.out.println("Save Exception!");
            e.printStackTrace();
        }
    }
    public void load(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            //READ THE DATASTORAGE OBJECT
            DataStorage ds = (DataStorage)ois.readObject();

            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;

            //PLAYER INVENTORY
            gp.player.inventory.clear();
            for(int i = 0; i< ds.itemNames.size();i++){
                gp.player.inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemAmount.get(i);
            }
            //PLAYER EQUIPMENT
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getAttackImage();

            //OBJECTS ON MAP
            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++){
                for(int i = 0; i< gp.obj[1].length; i++){
                    if(ds.mapObjectsNames[mapNum][i].equals("NA")){
                        gp.obj[mapNum][i] = null;
                    }
                    else{
                        gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectsNames[mapNum][i]);
                        if(gp.obj[mapNum][i] != null){
                            gp.obj[mapNum][i].worldX = ds.mapObjectsWorldX[mapNum][i];
                            gp.obj[mapNum][i].worldY = ds.mapObjectsWorldY[mapNum][i];

                            if(ds.mapObjectsLootNames[mapNum][i] != null){
                                gp.obj[mapNum][i].setLoot(gp.eGenerator.getObject(ds.mapObjectsLootNames[mapNum][i]));
                            }
                            gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
                            if(gp.obj[mapNum][i].opened == true){
                                gp.obj[mapNum][i].down1 =gp.obj[mapNum][i].image2;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println("Load Exception!");
            e.printStackTrace();
        }
    }
}
