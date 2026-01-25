package entity;
import main.KeyHandler;
import main.GamePanel;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Player extends Entity{


    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;



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

        attackArea.height = 36;
        attackArea.width = 36;


        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxLife = 6;
        life =maxLife;
        level =1;
        strength =1; //THE MORE STRENGTH HE HAS THE MORE DAMAGE HE GIVES.
        dexterity = 1;//THE MORE DEXTERITY HE HAS THE LESS DAMAGE HE RECEIVES
        exp =0;
        nexLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack =getAttack();//THE TOTAL ATTACK VALUE IS DECIDED BY STRENGTH AND WEAPON
        defense = getDefense();//THE TOTAL DEFENSE VALUE IS DECIDED BY DEXTERITY AND SHIELD
    }

    public int getAttack(){
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }


    public void getPlayerImage(){

        up1 = setup("/res/player/boy_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/res/player/boy_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/res/player/boy_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/res/player/boy_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/res/player/boy_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/res/player/boy_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/res/player/boy_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/res/player/boy_right_2",gp.tileSize,gp.tileSize);

    }

    public void getPlayerAttackImage(){
        attackUp1 = setup("/res/player/boy_attack_up_1",gp.tileSize,gp.tileSize*2);
        attackUp2 = setup("/res/player/boy_attack_up_2",gp.tileSize,gp.tileSize*2);
        attackDown1 = setup("/res/player/boy_attack_down_1",gp.tileSize,gp.tileSize*2);
        attackDown2 = setup("/res/player/boy_attack_down_2",gp.tileSize,gp.tileSize*2);
        attackLeft1 = setup("/res/player/boy_attack_left_1",gp.tileSize*2,gp.tileSize);
        attackLeft2 = setup("/res/player/boy_attack_left_2",gp.tileSize*2,gp.tileSize);
        attackRight1 = setup("/res/player/boy_attack_right_1",gp.tileSize*2,gp.tileSize);
        attackRight2 = setup("/res/player/boy_attack_right_2",gp.tileSize*2,gp.tileSize);
    }

    public void update(){

        if(attacking == true){
            attacking();
        }

        else if(keyH.upPressed ==true || keyH.downPressed ==true
            || keyH.leftPressed ==true || keyH.rightPressed ==true || keyH.enterPressed == true){

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

                //CHECK NPC COLLISION
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

                //CHECK MONSTER COLLISION
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                сontactMonster(monsterIndex);

                //CHECK EVENT
                gp.eHandler.cheackEvent();


                //if collision is false player can move
                if(collisionOn == false && keyH.enterPressed == false){
                    switch(direction) {
                        case "up" ->worldY -= speed;
                        case "down" ->worldY += speed;
                        case "left" ->worldX -= speed;
                        case "right" ->worldX += speed;
                    }
                }

                if(keyH.enterPressed == true && attackCanceled == false){
//                    gp.playSE(7);
                    attacking = true;
                    spriteCounter = 0;
                }
                attackCanceled = false;
                gp.keyH.enterPressed = false;

                spriteCounter++;
                if (spriteCounter > 12) {
                    spriteNum = (spriteNum == 1) ? 2 : 1; 
                    spriteCounter = 0;                      
                }

            }

        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void attacking(){
        spriteCounter ++;
        if(spriteCounter <= 5){
            spriteNum =1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25){
            spriteNum =2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //ADJUST PLAYER'S WORLD X/Y FOR THE ATTACKAREA
            switch (direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            //ATTACKAREA BECOMES SOLIDAREA
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //CHECK MONSTER COLLISION WITH THEE UPDATED WORLDX WORLDY AND SOLIDARE
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            //AFTER CHECKING COLLISION RESTOR THE ORIGINAL DATA
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25){
            spriteNum =1;
            spriteCounter =0;
            attacking = false;
        }
    }

    public void pickUpObject(int i ){
        if(i != 999){

        }
    }

    public void interactNPC(int i){
        if(gp.keyH.enterPressed == true){
            if(i != 999){
                    attackCanceled = true;
                    gp.gameState = gp.dialogueState;
                    gp.npc[i].speak();
            }
        }
    }

    public void сontactMonster(int i){
        if(i != 999 ){
            if(invincible == false ){

                int damage = gp.monster[i].attack - defense;
                if(damage < 0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;
                gp.playSE(6);
            }
        }
    }

    public void damageMonster(int i){
        if(i != 999){
            if(gp.monster[i].invincible == false){
               gp.playSE(5);

                int damage = attack - gp.monster[i].defense;
                if(damage < 0){
                    damage = 0;
                }

                gp.monster[i].life -= damage;
                gp.ui.addMessage("hit -"+ damage);
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if(gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("exp +" + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();

                }

            }
        }
    }

    public void checkLevelUp(){
        if(exp >= nexLevelExp){
            level++;
            nexLevelExp = nexLevelExp * 2;
            dexterity++;
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue ="You are level "+ level+ " now!";
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up" -> {
                if(!attacking){
                    image = (spriteNum == 1) ? up1 : up2;
                } else {
                    tempScreenY = screenY - gp.tileSize;
                    image = (spriteNum == 1) ? attackUp1 : attackUp2;
                }
            }
            case "down" -> {
                if(!attacking){
                    image = (spriteNum == 1) ? down1 : down2;
                }else{

                    image = (spriteNum == 1) ? attackDown1 : attackDown2;
                }
            }
            case "left" -> {
                if(!attacking) {
                    image = (spriteNum == 1) ? left1 : left2;
                }else{
                    tempScreenX = screenX - gp.tileSize;
                    image = (spriteNum == 1) ? attackLeft1 : attackLeft2;
                }
            }
            case "right" -> {
                if(!attacking) {
                    image = (spriteNum == 1) ? right1 : right2;
                }else{
                    image = (spriteNum == 1) ? attackRight1 : attackRight2;
                }
            }
        }

        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));


    }


}
