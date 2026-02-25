package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {
    GamePanel gp;
    public static final String objName ="Lantern";
    public OBJ_Lantern(GamePanel gp){
        super(gp);

        type = type_light;
        name =objName;
        down1 = setup("/res/objects/lantern",gp.tileSize,gp.tileSize);
        description = "[Lantern]\nYou can see what around you at night";
        price = 10;
        lightRadius = 250;
    }

}
