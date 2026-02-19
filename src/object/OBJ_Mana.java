package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana extends Entity {
    GamePanel gp;

    public OBJ_Mana(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        value = 1;
        name = "Mana Crystal";
        down1 = setup("/res/objects/manacrystal_full",gp.tileSize, gp.tileSize);
        image = setup("/res/objects/manacrystal_blank",gp.tileSize, gp.tileSize);
        image2 = setup("/res/objects/manacrystal_full",gp.tileSize, gp.tileSize);

    }

    public boolean use(Entity entity){
        gp.playSE(1);
        gp.ui.addMessage("Mana "+ value);
        entity.mana += value;
        return true;

    }

}
