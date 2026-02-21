package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Rock;

import java.awt.*;
import java.util.Random;

public class M0N_GreenSlime extends Entity {
        GamePanel gp;
        public M0N_GreenSlime(GamePanel gp){
            super(gp);

            this.gp = gp;

            type = type_monster;
            name =" Green Slime";
            defaultSpeed = 1;
            speed = defaultSpeed;
            maxLife =4;
            life = maxLife;
            attack = 5;
            defense = 0;
            exp = 1;
            projectile = new OBJ_Rock(gp);

            solidArea.x =3;
            solidArea.y =18;
            solidArea.width =42;
            solidArea.height =30;
            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y;
            getImage();
        }
        public void getImage(){
            up1 = setup("/res/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
            up2 = setup("/res/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
            down1 = setup("/res/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
            down2 = setup("/res/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
            left1 = setup("/res/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
            left2 = setup("/res/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
            right1 = setup("/res/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
            right2 = setup("/res/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
        }
    public void setAction(){

        if(onPath == true){
            //CHECK IF IT STOP CHASING
            checkStopChasingOrNot(gp.player, 10,100);
            //SEARCH DIRECTION TO GO
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            //CHECK IF IT SHOOT A PROJECT TILE
            checkShootOrNot(100,80);
        }
        else{
            checkStartChasingOrNot(gp.player, 5, 100);
            getRandomDirection();
        }
    }
    @Override
    public void damageReaction(){
            actionLockCounter = 0;
            onPath = true;
    }

    public void checkDrop(){
        int i = new Random().nextInt(100)+1;
        if(i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
    }
}
