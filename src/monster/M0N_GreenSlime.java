package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Rock;

import java.util.Random;

public class M0N_GreenSlime extends Entity {
        GamePanel gp;
        public M0N_GreenSlime(GamePanel gp){
            super(gp);

            this.gp = gp;

            type = type_monster;
            name =" Green Slime";
            speed = 1;
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
            Random random = new Random();
            int dX = gp.player.worldX - worldX;
            int dY = gp.player.worldY - worldY;

            if(onPath == true){
                if(Math.abs(dX) > Math.abs(dY)){
                    if(dX > 0){
                        direction = "right";
                    }
                    else {
                        direction = "left";
                    }
                }else{
                    if(dY > 0){
                        direction ="down";
                    }
                    else{
                        direction = "up";
                    }
                }
                int i = random.nextInt(100)+1;
                if (i > 99 && projectile.alive == false && shotAvailableCounter == 80){
                    projectile.set(worldX, worldY, direction,true,this);
                    gp.projectileList.add(projectile);
                    shotAvailableCounter = 0;
                }
            }

            actionLockCounter ++;
            if(actionLockCounter == 120){

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

    @Override
    public void damageReaction(){
            actionLockCounter = 0;
            pathCounter = 0;
            onPath = true;
    }

    public void checkDrop(){
        int i = new Random().nextInt(100)+1;
        if(i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
    }

}
