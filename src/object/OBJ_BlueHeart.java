package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity {
    GamePanel gp;
    public static final String objName = "Blue Heart";

    public OBJ_BlueHeart(GamePanel gp){
        super(gp);

        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        down1 = setup("/res/objects/blueheart",gp.tileSize,gp.tileSize);

        setDalogoue();
    }

    public void setDalogoue(){
        dialogues[0][0] = "You found a blue heart, now you are free.";
    }
    public boolean use(Entity entity){
        gp.gameState = gp.cutsceneState;
        gp.csManager.sceneNum = gp.csManager.ending;
        return true;
    }

}
