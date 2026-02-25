package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {

    public static final String objName ="Pickaxe";

    public OBJ_Pickaxe(GamePanel gp){
        super(gp);

        knockBackPower = 5;
        type = type_pickaxe;
        name =objName;
        down1 = setup("/res/objects/pickaxe",gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.height = 30;
        attackArea.width = 30;
        description = "["+name+"]\n" + " You can destroy some walls";
        motion1_duration =20;
        motion2_duration = 35;
    }
}
