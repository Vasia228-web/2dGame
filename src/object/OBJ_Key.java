package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends Entity {

    GamePanel gp;
    public static final String objName ="Key";

    public OBJ_Key(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = objName;
        stackable = true;
        down1 = setup("/res/objects/key",gp.tileSize,gp.tileSize);
        description = "[ " +name + " ]\n YOU CAN OPEN ANY DOOR .";
        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] = "Door Unlocked";
        dialogues[1][0] = "Here is nou door close to you!";
    }
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        int objIndex = getDetected(entity,gp.obj,"Door");

        if(objIndex != 999){
            startDialogue(this,0);
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else{
            startDialogue(this,1);
            return false;
        }
    }

}
