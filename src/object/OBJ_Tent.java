package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {
    GamePanel gp;
    public static final String objName ="Tent";
    public OBJ_Tent(GamePanel gp){
        super(gp);
        this.gp = gp;

        name =objName;
        type = type_consumable;
        description = "[Tent]\n You can skip the night";
        down1 = setup("/res/objects/tent",gp.tileSize,gp.tileSize);
        price = 50;
        stackable = true;
    }

    public boolean use(Entity entity){
        boolean useIt = false;
         if(gp.eManager.lighting.dayState == gp.eManager.lighting.night){
             gp.gameState = gp.sleepState;
             gp.playSE(13);
             gp.player.getPlayerSleepingImage(down1);
             useIt = true;
            }
         else{
             useIt = false;
         }
        return useIt;
    }
}
