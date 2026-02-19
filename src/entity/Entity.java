package entity;

import main.GamePanel;
import main.UtilityTool;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Entity {

    GamePanel gp;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    String dialogues[] =new String[20];

    //STATE
    public int worldX , worldY;
    public boolean collision = false;
    public boolean invincible = false;
    public int spriteNum = 1 ;
    int dialogueIndex =0;
    public String direction = "down";
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;


    //COUNTER
    public int spriteCounter =0;
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;
    public int dyingCounter = 0;
    public int shotAvailableCounter = 0;
    int hpBarCounter =0;
    public int knockBackCounter = 0;


    //CHARACTER ATRIBUTES
    public int defaultSpeed;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public String name ;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nexLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;

    //TYPE
    public int type; //PLAYER = 0, NPC = 1, MONSTER = 2 ...
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;

    //ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;


    public  Entity (GamePanel gp){
        this.gp = gp;
    }
    public int getLeftX(){
        return worldX + solidArea.x;
    }
    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY(){
        return worldY + solidArea.y;
    }
    public int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol(){
        return (worldX + solidArea.x) / gp.tileSize;
    }
    public int getRow(){
        return (worldY + solidArea.y) / gp.tileSize;
    }
    public void interact(){}
    public void setAction(){}
    public void damageReaction(){}
    public void checkDrop(){}
    public void dropItem(Entity droppeditem){
        for(int i = 1; i < gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppeditem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public void speak(){

        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction){
            case "up":
                direction ="down";
                break;
            case "down":
                direction ="up";
                break;
            case "right":
                direction ="left";
                break;
            case "left":
                direction ="right";
                break;
        }

    }
    public boolean use(Entity entity){return false;}
    public Color getParticleColor(){
        Color color = null;
        return color;
    }
    public int getParticleSize(){
        int size =0;
        return size;
    }
    public int getParticleSpeed(){
        int speed =0;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife =0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target){

        Random random = new Random();

        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();
        for(int i = 0; i < 5; i++){
            int dx = random.nextInt(-2,3);
            int dy = random.nextInt(-2,3);

            Particle p = new Particle(gp, target, color, size, speed, maxLife, dx, dy);
            gp.particleList.add(p);
        }
    }
    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this,gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer == true){
            damagePlayer(attack);
        }
    }
    public void update(){

        if(knockBack == true){
            checkCollision();
            if(collisionOn == true){
                knockBackCounter =0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(collisionOn == false){
                switch (gp.player.direction){
                    case "up" ->worldY -= speed;
                    case "down" ->worldY += speed;
                    case "left" ->worldX -= speed;
                    case "right" ->worldX += speed;
                }
            }
            knockBackCounter++;
            if(knockBackCounter == 5){
                knockBackCounter =0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else {
            setAction();
            checkCollision();
            if(collisionOn == false){
                switch(direction) {
                    case "up" ->worldY -= speed;
                    case "down" ->worldY += speed;
                    case "left" ->worldX -= speed;
                    case "right" ->worldX += speed;
                }
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 80){
            shotAvailableCounter++;
        }
    }
    public void damagePlayer(int attack){
        if(gp.player.invincible == false){
            gp.playSE(7);

            int damage = attack - gp.player.defense;
            if(damage < 0){
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

            switch (direction) {
                case "up" -> image = (spriteNum == 1) ? up1 : up2;
                case "down" -> image = (spriteNum == 1) ? down1 : down2;
                case "left" -> image = (spriteNum == 1) ? left1 : left2;
                case "right" -> image = (spriteNum == 1) ? right1 : right2;
            }

            if(type == 2 && hpBarOn == true){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1 , screenY - 16, gp.tileSize + 2 ,12);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX , screenY-15, (int)hpBarValue ,10);
                hpBarCounter++;
                if(hpBarCounter == 600){
                    hpBarOn = false;
                    hpBarCounter = 0;
                }
            }

            if(invincible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f);
            }
            if(dying == true){
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY,null);
            // RESET ALPHA
            changeAlpha(g2, 1f);
        }
    }
        //METHOD DOING BLINKING TO MONSTER
        public void dyingAnimation(Graphics2D g2){

            dyingCounter++;

            int i = 5;

            if(dyingCounter <= i){
                changeAlpha(g2, 0f);
            }
            if(dyingCounter > i && dyingCounter <= i * 2){
                changeAlpha(g2, 1f);
            }
            if(dyingCounter > i * 2 && dyingCounter <= i * 3){
                changeAlpha(g2, 0f);
            }
            if(dyingCounter > i * 3 && dyingCounter <= i * 4){
                changeAlpha(g2, 1f);
            }
            if(dyingCounter > i * 4 && dyingCounter <= i * 5){
                changeAlpha(g2, 0f);
            }
            if(dyingCounter > i * 5 && dyingCounter <= i * 6){
                changeAlpha(g2, 1f);
            }
            if(dyingCounter > i * 6 && dyingCounter <= i * 7){
                changeAlpha(g2, 0f);
            }
            if(dyingCounter > i * 7 && dyingCounter <= i * 8){
                changeAlpha(g2, 1f);
            }
            if(dyingCounter > i * 8){
                alive = false;
            }
        }
    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
    }
    public BufferedImage setup(String imageName,int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource(imageName + ".png"));
            image = uTool.scaleImage(image, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNode(startCol,startRow, goalCol, goalRow);

        if(gp.pFinder.search() == true){

            if(gp.pFinder.pathList.size() > 0) {

                // NEXT WORLDX & WORLDY
                int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
                int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

                // ENTITY SOLID AREA POSITION
                int enLeftX = worldX + solidArea.x;
                int enRightX = worldX + solidArea.x + solidArea.width;
                int enTopY = worldY + solidArea.y;
                int enBottomY = worldY + solidArea.y + solidArea.height;


                if(enTopY > nextY && enLeftX >= nextX && enRightX <= nextX + gp.tileSize ){
                    direction = "up";
                }
                else if(enTopY < nextY && enLeftX >= nextX && enRightX <= nextX + gp.tileSize){
                    direction = "down";
                }
                else if (enTopY >= nextY && enBottomY <= nextY + gp.tileSize){
                    //LEFT OR RIGHT
                    if(enLeftX > nextX){
                        direction = "left";
                    }
                    if(enLeftX < nextX){
                        direction = "right";
                    }
                }
                else if(enTopY > nextY && enLeftX > nextX){
                    //UP OR LEFT
                    direction = "up";
                    checkCollision();
                    if(collisionOn == true){
                        direction = "left";
                    }
                }
                else if(enTopY > nextY && enLeftX < nextX){
                    //UP OR RIGHT
                    direction = "up";
                    checkCollision();
                    if(collisionOn == true){
                        direction = "right";
                    }
                }
                else if(enTopY < nextY && enLeftX > nextX){
                    //DOWN OR LEFT
                    direction = "down";
                    checkCollision();
                    if(collisionOn == true){
                        direction = "left";
                    }
                }
                else if(enTopY < nextY && enLeftX < nextX){
                    //DOWN OR RIGHT
                    direction = "down";
                    checkCollision();
                    if(collisionOn == true){
                        direction = "right";
                    }
                }

                int nextCol = gp.pFinder.pathList.get(0).col;
                int nextRow = gp.pFinder.pathList.get(0).row;
                if(nextCol == goalCol && nextRow == goalRow){
                    onPath = false;
                }
            }
        }
        else {
            onPath = false;
        }
    }
    public int getDetected(Entity user, Entity target[][], String targetName){
        int index = 999;
        //CHECK THE SURROUNDING OBJECT
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction){
            case "up": nextWorldY = user.getTopY() -1;break;
            case "down": nextWorldY = user.getBottomY() +1;break;
            case "left": nextWorldX = user.getLeftX() - 1;break;
            case "right": nextWorldX = user.getRightX() + 1;break;
        }

        int col = nextWorldX /gp.tileSize;
        int row = nextWorldY /gp.tileSize;

        for(int i = 0; i < target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                if(target[gp.currentMap][i].getCol() == col && target[gp.currentMap][i].getRow() == row &&
                        target[gp.currentMap][i].name.equals(targetName)){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}
