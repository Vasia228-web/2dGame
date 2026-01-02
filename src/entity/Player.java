package entity;
import main.KeyHandler;
import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int hasKey = 0;


    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
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

    }


    public void getPlayerImage(){
        try{
        up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_1.png"));
        up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_up_2.png"));
        down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_1.png"));
        down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_down_2.png"));
        left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_1.png"));
        left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_left_2.png"));
        right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_1.png"));
        right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/boy_right_2.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
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
            String ObjectName = gp.obj[i].name;
            switch(ObjectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    System.out.println("Key:"+hasKey);
                    break;

                case "Door":
                    gp.playSE(3);
                    if(hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        System.out.println("Key:" +hasKey);
                    }
                case "Boots":
                    gp.playSE(2);
                    speed +=2;
                    gp.obj[i] = null;
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

    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
}


}
