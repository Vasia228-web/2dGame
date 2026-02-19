package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;

    public OBJ_Potion_Red(GamePanel gp){
        super(gp);
        this.gp = gp;
        type=type_consumable;
        value = 2;
        name ="Red Potion";
        stackable = true;
        price =2;
        down1 = setup("/res/objects/potion_red",gp.tileSize,gp.tileSize);
        description = "["+name+"]\n"+ "Heals your live by " + value;
    }

    public boolean use(Entity entity){
            gp.playSE(2);
            entity.life += value;
            return true;
    }
}
