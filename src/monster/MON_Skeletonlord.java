package monster;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;

import java.util.Random;

public class MON_Skeletonlord extends Entity {

    GamePanel gp;
    public static final String monName = "Skeleton Lord";

    public MON_Skeletonlord(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife =50;
        life = maxLife;
        attack = 10;
        defense = 2;
        exp = 50;
        motion1_duration =15;
        motion2_duration = 30;
        knockBackPower = 5;
        sleep = true;

        int size = gp.tileSize * 5;
        solidArea.x =48;
        solidArea.y =48;
        solidArea.width =size - 48*2;
        solidArea.height =size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.height =170;
        attackArea.width = 170;
        getImage();
        getAttackImage();
        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] = "Nou one can steel my treasure!";
        dialogues[0][1] = "If you try you will die here";
    }
    public void getImage() {

        int i = 5;
        if(inRage == false){
            up1 = setup("/res/monster/skeletonlord_up_1", gp.tileSize*i, gp.tileSize*i);
            up2 = setup("/res/monster/skeletonlord_up_2", gp.tileSize*i, gp.tileSize*i);
            down1 = setup("/res/monster/skeletonlord_down_1", gp.tileSize*i, gp.tileSize*i);
            down2 = setup("/res/monster/skeletonlord_down_2", gp.tileSize*i, gp.tileSize*i);
            left1 = setup("/res/monster/skeletonlord_left_1", gp.tileSize*i, gp.tileSize*i);
            left2 = setup("/res/monster/skeletonlord_left_2", gp.tileSize*i, gp.tileSize*i);
            right1 = setup("/res/monster/skeletonlord_right_1", gp.tileSize*i, gp.tileSize*i);
            right2 = setup("/res/monster/skeletonlord_right_2", gp.tileSize*i, gp.tileSize*i);
        }
        if(inRage == true){
            up1 = setup("/res/monster/skeletonlord_phase2_up_1", gp.tileSize*i, gp.tileSize*i);
            up2 = setup("/res/monster/skeletonlord_phase2_up_2", gp.tileSize*i, gp.tileSize*i);
            down1 = setup("/res/monster/skeletonlord_phase2_down_1", gp.tileSize*i, gp.tileSize*i);
            down2 = setup("/res/monster/skeletonlord_phase2_down_2", gp.tileSize*i, gp.tileSize*i);
            left1 = setup("/res/monster/skeletonlord_phase2_left_1", gp.tileSize*i, gp.tileSize*i);
            left2 = setup("/res/monster/skeletonlord_phase2_left_2", gp.tileSize*i, gp.tileSize*i);
            right1 = setup("/res/monster/skeletonlord_phase2_right_1", gp.tileSize*i, gp.tileSize*i);
            right2 = setup("/res/monster/skeletonlord_phase2_right_2", gp.tileSize*i, gp.tileSize*i);
        }
    }
    public void getAttackImage() {

        int i = 5;
        if(inRage == false){
            attackUp1 = setup("/res/monster/skeletonlord_attack_up_1", gp.tileSize*i, gp.tileSize*i*2);
            attackUp2 = setup("/res/monster/skeletonlord_attack_up_2", gp.tileSize*i, gp.tileSize*i*2);
            attackDown1 = setup("/res/monster/skeletonlord_attack_down_1", gp.tileSize*i, gp.tileSize*i*2);
            attackDown2 = setup("/res/monster/skeletonlord_attack_down_2", gp.tileSize*i, gp.tileSize*i*2);
            attackLeft1 = setup("/res/monster/skeletonlord_attack_left_1", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft2 = setup("/res/monster/skeletonlord_attack_left_2", gp.tileSize*i*2, gp.tileSize*i);
            attackRight1 = setup("/res/monster/skeletonlord_attack_right_1", gp.tileSize*i*2, gp.tileSize*i);
            attackRight2 = setup("/res/monster/skeletonlord_attack_right_2", gp.tileSize*i*2, gp.tileSize*i);
        }
        if(inRage == true){
            attackUp1 = setup("/res/monster/skeletonlord_phase2_attack_up_1", gp.tileSize*i, gp.tileSize*i*2);
            attackUp2 = setup("/res/monster/skeletonlord_phase2_attack_up_2", gp.tileSize*i, gp.tileSize*i*2);
            attackDown1 = setup("/res/monster/skeletonlord_phase2_attack_down_1", gp.tileSize*i, gp.tileSize*i*2);
            attackDown2 = setup("/res/monster/skeletonlord_phase2_attack_down_2", gp.tileSize*i, gp.tileSize*i*2);
            attackLeft1 = setup("/res/monster/skeletonlord_phase2_attack_left_1", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft2 = setup("/res/monster/skeletonlord_phase2_attack_left_2", gp.tileSize*i*2, gp.tileSize*i);
            attackRight1 = setup("/res/monster/skeletonlord_phase2_attack_right_1", gp.tileSize*i*2, gp.tileSize*i);
            attackRight2 = setup("/res/monster/skeletonlord_phase2_attack_right_2", gp.tileSize*i*2, gp.tileSize*i);
        }
    }
    public void setAction(){

        if(inRage == false && life  < maxLife /2){
            inRage =true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack += 5;
        }

        if(getTiledistance(gp.player) < 10){
            moveTowardPlayer(60);
        }
        else{
            getRandomDirection(120);
        }
        if(attacking == false){
            checkAttackOrNot(60,gp.tileSize*10, gp.tileSize*5);
        }
    }
    @Override
    public void damageReaction(){
        actionLockCounter = 0;
        onPath = true;
    }
    public void checkDrop(){

        gp.boosBattleOn = false;
        Progress.skeletonLordDefeated = true;

        gp.stopMusic();
        gp.playMusic(16);

        for(int i = 0; i < gp. obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)){
                gp.obj[gp.currentMap][i] = null;
                gp.playSE(19);
            }
        }

        int i = new Random().nextInt(100)+1;
        if(i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
    }

}
