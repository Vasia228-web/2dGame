package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    boolean opened = false;
    Entity loot;

    public OBJ_Chest(GamePanel gp ,Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;
        name = "Chest";
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
    public void interact(){
        gp.gameState = gp.dialogueState;
        if(opened == false){
            gp.playSE(3);

            StringBuilder sb = new StringBuilder();
            sb.append("Chest open" + loot.name+"!");
            if(gp.player.canObtainItem(loot) == false){
                sb.append("\n you cannot carry any more!");
            }
            else{
                sb.append("\n you pick up the " + loot.name + "!");
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        }
        else{
            gp.ui.currentDialogue = "it's empty";
        }
    }
}