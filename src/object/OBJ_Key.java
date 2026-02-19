package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends Entity {

    GamePanel gp;
    public OBJ_Key(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = "Key";
        stackable = true;
        down1 = setup("/res/objects/key",gp.tileSize,gp.tileSize);
        description = "[ " +name + " ]\n YOU CAN OPEN ANY DOOR .";
    }

    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        int objIndex = getDetected(entity,gp.obj,"Door");

        if(objIndex != 999){
            gp.ui.currentDialogue = "Door Unlocked";
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else{
            gp.ui.currentDialogue = "Ther is nou door colose to you!";
            return false;
        }
    }

}
