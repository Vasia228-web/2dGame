package main;

import entity.NPC_OldMan;
import monster.M0N_GreenSlime;
import object.*;
import tile_interactive.IT_DryTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }


    public void setObject(){
        int i =0;
        gp.obj[i] = new OBJ_Coin_Bronze(gp);
        gp.obj[i].worldX = gp.tileSize*22;
        gp.obj[i].worldY = gp.tileSize*25;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize*22;
        gp.obj[i].worldY = gp.tileSize*26;
        i++;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize*22;
        gp.obj[i].worldY = gp.tileSize*27;
        i++;
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize*33;
        gp.obj[i].worldY = gp.tileSize*21;
        i++;
        gp.obj[i] = new OBJ_Shield_Blue(gp);
        gp.obj[i].worldX = gp.tileSize*34;
        gp.obj[i].worldY = gp.tileSize*22;
        i++;
        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = gp.tileSize*37;
        gp.obj[i].worldY = gp.tileSize*22;

    }

    public void setNPC(){

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

    }

    public void setMonster(){

        int i =0;

        gp.monster[i] = new M0N_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*22;
        gp.monster[i].worldY = gp.tileSize*36;
        i++;
        gp.monster[i] = new M0N_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*23;
        gp.monster[i].worldY = gp.tileSize*37;
        i++;
        gp.monster[i] = new M0N_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*24;
        gp.monster[1].worldY = gp.tileSize*38;
        i++;
        gp.monster[i] = new M0N_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*25;
        gp.monster[i].worldY = gp.tileSize*39;
        i++;
        gp.monster[i] = new M0N_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*26;
        gp.monster[i].worldY = gp.tileSize*40;
        i++;
        gp.monster[i] = new M0N_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*27;
        gp.monster[i].worldY = gp.tileSize*41;

    }

    public void setInteractive(){
        int i =0;
        gp.iTile[i] = new IT_DryTree(gp, 27, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 28, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 29, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 30, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 31, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 32, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 33, 12);

    }

}
