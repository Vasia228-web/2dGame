package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;
    int healingValue = 2;

    public OBJ_Potion_Red(GamePanel gp){
        super(gp);
        this.gp = gp;
        type=type_consumable;
        name ="Red Potion";
        down1 = setup("/res/objects/potion_red",gp.tileSize,gp.tileSize);
        description = "["+name+"]\n"+ "Heals your live by " + healingValue;
    }

    public void use(Entity entity){
            gp.playSE(2);
            entity.life += healingValue;
            if(gp.player.life > gp.player.maxLife){
                gp.player.life = gp.player.maxLife;
            }
    }
}
