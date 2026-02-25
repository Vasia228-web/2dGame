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
    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,
    attackRight1,attackRight2,   guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public String dialogues[][] =new String[20][20];
    public Entity attacker;
    public Entity linkedEntity;

    //STATE
    public int worldX , worldY;
    public boolean collision = false;
    public boolean invincible = false;
    public int spriteNum = 1 ;
    public int dialogueSet = 0;
    public int dialogueIndex =0;
    public String direction = "down";
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;
    public boolean guard = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public boolean opened = false;
    public Entity loot;
    public boolean inRage = false;
    public boolean drawing = true;

    //COUNTER
    public int spriteCounter =0;
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;
    public int dyingCounter = 0;
    public int shotAvailableCounter = 0;
    public int hpBarCounter =0;
    public int knockBackCounter = 0;
    public int guardCounter = 0;
    int offBalanceCounter = 0;


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
    public int nextLevelExp;
    public int coin;
    public int motion1_duration;
    public int motion2_duration;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;
    public boolean boss;
    public boolean sleep = false;
    public boolean temp = false;

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
    public final int type_pickaxe = 10;

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
    public int getScreenX(){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        return screenX;
    }
    public int getScreenY(){
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        return screenY;
    }
    public int getXdistance(Entity target){
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }
    public int getYdistance(Entity target){
        int yDistance = Math.abs(getCenterY() - target.getCenterY());
        return yDistance;
    }
    public int getTiledistance(Entity target){
        int tileDistance = (getXdistance(target) + getYdistance(target)) /gp.tileSize;
        return tileDistance;
    }
    public int getGoalCol(Entity target){
        int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
        return goalCol;
    }
    public int getGoalRow(Entity target){
        int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
        return goalRow;
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
    public int getCenterX(){
        int centerX = worldX + left1.getWidth()/2;
        return centerX;
    }
    public int getCenterY(){
        int centerY = worldY + up1.getHeight()/2;
        return centerY;
    }
    public String getOppositeDirection(String direction){
        String oppositeDirection = "";

        switch (direction){
            case "up": oppositeDirection = "down";break;
            case "down": oppositeDirection = "up";break;
            case "left": oppositeDirection = "right";break;
            case "right": oppositeDirection = "left";break;
        }
        return oppositeDirection;
    }
    public void resetCounter(){
        spriteCounter =0;
        invincibleCounter = 0;
        actionLockCounter = 0;
        dyingCounter = 0;
        shotAvailableCounter = 0;
        hpBarCounter =0;
        knockBackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
    }
    public void interact(){}
    public void setAction(){}
    public void setLoot(Entity loot){}
    public void move(String direction){}
    public void damageReaction(){}
    public void checkDrop(){}
    public void checkStopChasingOrNot(Entity target, int distance, int rate){
        if(getTiledistance(target) > distance){
            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = false;
            }
        }
    }
    public void checkStartChasingOrNot(Entity target, int distance, int rate){
        if(getTiledistance(target) < distance){
            int i = new Random().nextInt(rate);
                if(i == 0){
                    onPath = true;
                }
            }
        }
    public void getRandomDirection(int interval){
        actionLockCounter ++;
        if(actionLockCounter > interval){
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
    public void moveTowardPlayer(int interval){
        actionLockCounter ++;
        if(actionLockCounter > interval) {
            if (getXdistance(gp.player) > getYdistance(gp.player)) {
                if (gp.player.getCenterX() < getCenterX()) {
                    direction = "left";
                } else {
                    direction = "right";
                }
            }
             else if (getXdistance(gp.player) < getYdistance(gp.player)) {
                    if (gp.player.getCenterY() < getCenterY()) {
                        direction = "up";
                    } else {
                        direction = "down";
                    }

            }
             actionLockCounter = 0;
        }
    }
    public void checkShootOrNot(int rate, int shotInterval){
        int i = new Random().nextInt(rate);
        if (i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval){
            projectile.set(worldX, worldY, direction,true,this);

            for(int ii =0; ii < gp.projectile[1].length; ii++){
                if(gp.projectile[gp.currentMap][ii] == null){
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }
    public void checkAttackOrNot(int rate, int straight, int horizontal){
        boolean targetInRange = false;
        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);

        switch (direction){
            case "up":
                if(gp.player.getCenterY() <  getCenterY() && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
            case "down":
                if(gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
            case "left":
                if(gp.player.getCenterX() <  getCenterX()&& xDis < straight && yDis < horizontal){
                    targetInRange = true;
                }
            case "right":
                if(gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal){
                    targetInRange = true;
                }
            if(targetInRange == true){
                //CHECK IF IT INITIATES an attack
                int i = new Random().nextInt(rate);
                if(i == 0){
                    attacking = true;
                    spriteNum = 1;
                    spriteCounter = 0;
                    shotAvailableCounter = 0;
                }
            }
        }
    }
    public void setKnockBack(Entity target,Entity attacker, int knockBackPower){
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }
    public boolean inCamera(){
        boolean inCamera = false;
        if(worldX + gp.tileSize * 5 > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize * 5 > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            inCamera = true;
        }
        return inCamera;
    }
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
    public void speak(){}
    public void turnToPLayer(){
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
    public void startDialogue(Entity entity, int setNum){
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
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
        if(sleep == false){
            if(knockBack == true){
                checkCollision();
                if(collisionOn == true){
                    knockBackCounter =0;
                    knockBack = false;
                    speed = defaultSpeed;
                }
                else if(collisionOn == false){
                    switch (knockBackDirection){
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
            else if (attacking == true) {
                attacking();
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
                spriteCounter++;
                if (spriteCounter > 12) {
                    spriteNum = (spriteNum == 1) ? 2 : 1;
                    spriteCounter = 0;
                }
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
            if(offBalance == true){
                offBalanceCounter++;
                if(offBalanceCounter > 60){
                    offBalance = false;
                    offBalanceCounter =0;
                }
            }
        }
    }
    public void damagePlayer(int attack){
        if(gp.player.invincible == false){
            gp.playSE(7);

            int damage = attack - gp.player.defense;
            String canGuardDirection = getOppositeDirection(direction);
            if(gp.player.guard == true && gp.player.direction.equals(canGuardDirection)){
                //PARRY
                if(gp.player.guardCounter < 10){
                    damage =0;
                    gp.playSE(15);
                    setKnockBack(this,gp.player, knockBackPower);
                    offBalance = true;
                    spriteCounter =- 60;
                }
                //NORMAL GUARD
                else{
                    damage /= 2;
                    gp.playSE(14);
                }
            }
            else{
                gp.playSE(5);
                if(damage < 1){
                    damage = 1;
                }
            }
            if(damage != 0){
                gp.player.transparent = true;
                setKnockBack(gp.player, this, knockBackPower);
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public void attacking(){
        spriteCounter ++;
        if(spriteCounter <= motion1_duration){
            spriteNum =1;
        }
        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration){
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

            if(type == type_monster){
                if(gp.cChecker.checkPlayer(this) == true){
                    damagePlayer(attack);
                }
            }
            else{
                //CHECK MONSTER COLLISION WITH THEE UPDATED WORLDX WORLDY AND SOLIDARE
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex,this, attack, currentWeapon.knockBackPower);

                //CHEACK INTERACTIVETILE COLLISION TO DESTROY IT
                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);

                //CHEACK PRROJECT TILE COLLISION
                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }

            //AFTER CHECKING COLLISION RESTOR THE ORIGINAL DATA
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > motion2_duration){
            spriteNum =1;
            spriteCounter =0;
            attacking = false;
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = getScreenX();
        int screenY = getScreenY();

        if(inCamera() == true){

            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch (direction) {
                case "up" -> {
                    if(!attacking){
                        image = (spriteNum == 1) ? up1 : up2;
                    } else {
                        tempScreenY = getScreenY() - up1.getHeight();
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
                        tempScreenX = getScreenX() - left1.getWidth();
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
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f);
            }
            if(dying == true){
                dyingAnimation(g2);
            }
            g2.drawImage(image, tempScreenX, tempScreenY,null);
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
            case "up": nextWorldY = user.getTopY() - gp.player.speed;break;
            case "down": nextWorldY = user.getBottomY() + gp.player.speed;break;
            case "left": nextWorldX = user.getLeftX() - gp.player.speed;break;
            case "right": nextWorldX = user.getRightX() + gp.player.speed;break;
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
