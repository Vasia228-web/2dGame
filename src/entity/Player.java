package entity;
import main.KeyHandler;
import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;



public class Player extends Entity{


    KeyHandler keyH;
    public final int screenX;
    public final int screenY;



    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize /2);
        screenY = gp.screenHeight/2 - (gp.tileSize /2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;


        setDefaultValues();
        getPlayerImage();

    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxLife = 6;
        life =maxLife;

    }


    public void getPlayerImage(){

        up1 = setup("/res/player/boy_up_1");
        up2 = setup("/res/player/boy_up_2");
        down1 = setup("/res/player/boy_down_1");
        down2 = setup("/res/player/boy_down_2");
        left1 = setup("/res/player/boy_left_1");
        left2 = setup("/res/player/boy_left_2");
        right1 = setup("/res/player/boy_right_1");
        right2 = setup("/res/player/boy_right_2");

    }

    public void update(){

        if(keyH.upPressed ==true || keyH.downPressed ==true 
            || keyH.leftPressed ==true || keyH.rightPressed ==true){

                if(keyH.upPressed == true){
                    direction = "up";
                }
                else if(keyH.downPressed == true){
                    direction = "down";
                }
                else if(keyH.leftPressed == true){
                    direction = "left";
                }
                else if(keyH.rightPressed == true){
                    direction = "right";
                }

                //check tile collission
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // check collission object
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

                //CHECK NPC COLLISSION
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

                //CHECK EVENT
                gp.eHandler.cheackEvent();

                gp.keyH.enterPressed = false;

                //if collision is false player can move
                if(collisionOn == false){
                    switch(direction) {
                        case "up" ->worldY -= speed;
                        case "down" ->worldY += speed;
                        case "left" ->worldX -= speed;
                        case "right" ->worldX += speed;
                    }
                }

                spriteCounter++;
                if (spriteCounter > 12) {
                    spriteNum = (spriteNum == 1) ? 2 : 1; 
                    spriteCounter = 0;                      
                }

            }

    }

public void pickUpObject(int i ){
        if(i != 999){

        }
    }

    public void interactNPC(int i){
        if(i != 999){
                if(gp.keyH.enterPressed == true) {
                    gp.gameState = gp.dialogueState;
                    gp.npc[i].speak();
                }
            }
        }

public void draw(Graphics2D g2){

    BufferedImage image = null;

    switch (direction) {
        case "up" -> image = (spriteNum == 1) ? up1 : up2;
        case "down" -> image = (spriteNum == 1) ? down1 : down2;
        case "left" -> image = (spriteNum == 1) ? left1 : left2;
        case "right" -> image = (spriteNum == 1) ? right1 : right2;
    }

    g2.drawImage(image, screenX, screenY, null);
}


}
