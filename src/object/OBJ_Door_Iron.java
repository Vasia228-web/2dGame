package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door_Iron extends Entity {

    GamePanel gp;
    public static final String objName ="Door Iron";
    public  OBJ_Door_Iron(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = objName;
        down1 = setup("/res/objects/door_iron",gp.tileSize,gp.tileSize);
        collision = true;
        type = type_obstacle;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width =48;
        solidArea.height =32;
        solidAreaDefaultX =solidArea.x;
        solidAreaDefaultY =solidArea.y;
        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] ="You need to press all plate!";
    }
    public void interact(){
        startDialogue(this,0);
    }
}
