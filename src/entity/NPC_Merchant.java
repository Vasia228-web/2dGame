package entity;

import main.GamePanel;
import object.*;

import java.awt.*;

public class NPC_Merchant extends Entity{

    public NPC_Merchant(GamePanel gp){
        super(gp);

        type = type_npc;
        direction = "down";
        solidArea = new Rectangle(0, 0, 48, 48*2);
        getImage();
        setDialogue();
        setItems();
    }

    public void getImage(){
        up1 = setup("/res/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        up2 = setup("/res/npc/merchant_down_2",gp.tileSize,gp.tileSize);
        down1 = setup("/res/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/res/npc/merchant_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/res/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        left2 = setup("/res/npc/merchant_down_2",gp.tileSize,gp.tileSize);
        right1 = setup("/res/npc/merchant_down_1",gp.tileSize,gp.tileSize);
        right2 = setup("/res/npc/merchant_down_2",gp.tileSize,gp.tileSize);
    }

    public void setDialogue(){
        dialogues[0][0] ="Okey you find me now your \ngame will be more dificult";
        dialogues[1][0] ="See you later";
        dialogues[2][0] ="You don't have enough coins";
        dialogues[3][0] ="Your inventory is full!";
        dialogues[4][0] ="You can't sell an equipped item!";

    }
    public void setItems(){
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
        inventory.add(new OBJ_Fireball(gp));
        inventory.add(new OBJ_Boots(gp));
    }
    public void speak(){
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
