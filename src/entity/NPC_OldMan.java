package entity;

import main.GamePanel;
import java.util.Random;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp){
        super(gp);

        type = type_npc;
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
        dialogueSet = -1;
    }
    public void getImage(){

        up1 = setup("/res/npc/oldman_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/res/npc/oldman_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/res/npc/oldman_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/res/npc/oldman_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/res/npc/oldman_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/res/npc/oldman_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/res/npc/oldman_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/res/npc/oldman_right_2",gp.tileSize,gp.tileSize);

    }
    public void setDialogue(){
        dialogues[0][0] ="Well, who knows what will lead \nyou to this island?";
        dialogues[0][1] = "Be careful, there are many \nmonsters hiding here.";
        dialogues[0][2] = "I heard there's a treasure\n dungeon somewhere here.";

        dialogues[1][0] = "You can go to the lake \nto drink water.";
        dialogues[1][1] = "I always drink it, it heals me.";

        dialogues[2][0] = "Come on, friend, be careful, \nthere are a lot of strange things here.";
    }
    public void setAction(){

        if(onPath == true){
            int goalCol = 12;
            int goalRow = 9;
            searchPath(goalCol, goalRow);
        }
        else{
            actionLockCounter ++;
            if(actionLockCounter == 120){
                Random random = new Random();
                int i = random.nextInt(100)+1;

                if(i <= 25){
                    direction = "up";
                }
                if(i >= 25 && i <= 50){
                    direction = "down";
                }
                if(i >= 50 && i <= 75){
                    direction = "left";
                }
                if(i >= 75 && i <= 100){
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
    public void speak(){
        turnToPLayer();
        startDialogue(this,dialogueSet);
        dialogueSet++;
        if(dialogues[dialogueSet][0] == null){
            dialogueSet--;
        }
//        onPath = true;
    }
}



