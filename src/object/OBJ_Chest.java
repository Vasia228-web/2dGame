package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    public static final String objName ="Chest";

    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        type = type_obstacle;
        image= setup("/res/objects/chest",gp.tileSize,gp.tileSize);
        image2 = setup("/res/objects/chest_opened",gp.tileSize,gp.tileSize);
        down1 = image;
        collision =true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }
    public void setLoot(Entity loot){
        this.loot = loot;
        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] = "You open the chest and get \n" + loot.name+" !";
        dialogues[1][0] = "You cannot carry any more!";
        dialogues[2][0] = "It's empty";
    }
    public void interact(){
        gp.gameState = gp.dialogueState;
        if(opened == false){
            gp.playSE(3);
            startDialogue(this,0);
            if(gp.player.canObtainItem(loot) == false){
                startDialogue(this,1);
            }
            else{
                down1 = image2;
                opened = true;
            }
        }
        else{
            startDialogue(this,2);
        }
    }
}