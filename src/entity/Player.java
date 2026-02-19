package entity;
import main.KeyHandler;
import main.GamePanel;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;

    // DEFAULT WEAPON
    OBJ_Sword_Normal startingWeapon;
    OBJ_Shield_Wood startingShield;
    OBJ_Fireball startingProjectile;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize /2);
        screenY = gp.screenHeight/2 - (gp.tileSize /2);

        type = type_player;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        //INITIALIZATION DEFAULT WEAPON
        startingWeapon = new OBJ_Sword_Normal(gp);
        startingShield = new OBJ_Shield_Wood(gp);
        startingProjectile = new OBJ_Fireball(gp);

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultPositions(){
        worldX = gp.tileSize *23;
        worldY = gp.tileSize *21;
        direction = "down";
    }
    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible =false;
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * 22;
        worldY = gp.tileSize * 23;
        defaultSpeed =4;
        speed = defaultSpeed;
        direction = "down";

        //PLAYER STATUS
        currentWeapon = startingWeapon;
        currentShield = startingShield;
        projectile = startingProjectile;
        maxLife = 6;
        life =maxLife;
        maxMana =3;
        mana = maxMana;
        level =1;
        strength =1; //THE MORE STRENGTH HE HAS THE MORE DAMAGE HE GIVES.
        dexterity = 1;//THE MORE DEXTERITY HE HAS THE LESS DAMAGE HE RECEIVES
        exp =0;
        nexLevelExp = 5;
        coin = 0;
        attack =getAttack();//THE TOTAL ATTACK VALUE IS DECIDED BY STRENGTH AND WEAPON
        defense = getDefense();//THE TOTAL DEFENSE VALUE IS DECIDED BY DEXTERITY AND SHIELD
    }
    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Lantern(gp));
        inventory.add(new OBJ_Tent(gp));
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
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
    public void getPlayerSleepingImage(BufferedImage image){
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;
    }
    public void getPlayerAttackImage(){
        if(currentWeapon.type == type_sword ) {
            attackUp1 = setup("/res/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/res/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/res/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/res/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/res/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/res/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/res/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/res/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe){
            attackUp1 = setup("/res/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/res/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/res/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/res/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/res/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/res/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/res/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/res/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }
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

                //CHECK INTERACTIVETILE COLLISION
                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);


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

            if(gp.keyH.shotKeyPressed == true && projectile.alive == false
                && shotAvailableCounter >= 80 && projectile.haveResource(this)){

                //SET DEFAULT COORDINATES, DIRECTION AND USER
                projectile.set(worldX,worldY,direction,true,this);

                //SUBTRACT THE COST (MANA, AMMO, ETC.)

                projectile.subtractResource(this);

                //ADD IT TO THE ARRAY CHEACK VACANCY
                for(int i =0; i < gp.projectile[1].length; i++){
                    if(gp.projectile[gp.currentMap][i] == null){
                        gp.projectile[gp.currentMap][i] = projectile;
                        break;
                    }
                }

                shotAvailableCounter = 0;
            }

            if(invincible == true){
                invincibleCounter++;
                if(invincibleCounter > 60){
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
            if(shotAvailableCounter < 80){
                shotAvailableCounter++;
            }
            if(life > maxLife){
                life = maxLife;
            }
            if(mana > maxMana){
                mana = maxMana;
            }
            if(life <= 0){
                gp.gameState = gp.gameOverState;
                gp.stopMusic();
                gp.playSE(11);
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
            damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);

            //CHEACK INTERACTIVETILE COLLISION TO DESTROY IT
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            //CHEACK PRROJECT TILE COLLISION
            int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
            damageProjectile(projectileIndex);

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
            //PICK UP ONLY
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly){
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            //OBSTACLE
            else if(gp.obj[gp.currentMap][i].type == type_obstacle){
                if(keyH.enterPressed == true){
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            //PICK UP OBJECT TO INVENTORY
            else{
                String text;
                if(canObtainItem(gp.obj[gp.currentMap][i]) == true){
                    text = "Got a "+ gp.obj[gp.currentMap][i].name + "!";
                    gp.playSE(1);
                }
                else{
                    text = "Your inventory is full";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }
    public void interactNPC(int i){
        if(gp.keyH.enterPressed == true){
            if(i != 999){
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }
    public void сontactMonster(int i){
        if(i != 999 ){
            if(invincible == false && gp.monster[gp.currentMap][i].dying == false){

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;
                gp.playSE(6);
            }
        }
    }
    public void damageMonster(int i, int attack,int knockBackPower){
        if(i != 999){
            if(gp.monster[gp.currentMap][i].invincible == false){
               gp.playSE(5);
               if(knockBackPower > 0){
                   knockBack(gp.monster[gp.currentMap][i],knockBackPower);
               }
                int damage = attack - gp.monster[gp.currentMap][i].defense;
                    if(damage < 0){
                        damage = 0;
                    }

                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage("hit -"+ damage);
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if(gp.monster[gp.currentMap][i].life <= 0){
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("exp +" + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void damageInteractiveTile(int i){
            if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true &&
                gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invincible == false){

            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].invincible = true;

            //GENERATE PARTICLE
            generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);

            if(gp.iTile[gp.currentMap][i].life == 0){
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyForm();
            }
        }
    }
    public void damageProjectile(int i){
        if(i != 999){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile,projectile);
        }
    }
    public void knockBack(Entity entity, int knockBackPower){
        entity.direction = direction;
        entity.speed += knockBackPower;
        entity.knockBack = true;
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
    public int searchItemInInventory(String itemName){
        int itemIndex = 999;
        for (int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item){
        boolean canObtain = false;
        //CHECK IF STACKEBLE
        if(item.stackable == true){
            int index = searchItemInInventory(item.name);
            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            //NEW ITEM SO NEED TO CHEK VACANCY
            else {
                if(inventory.size() != maxInventorySize){
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        //NOT STACKABLE ITEM
        else{
            if(inventory.size() != maxInventorySize){
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

            if(itemIndex < inventory.size()) {

                Entity selectedItem = inventory.get(itemIndex);

                if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
                    currentWeapon = selectedItem;
                    attack = getAttack();
                    getPlayerAttackImage();
                }
                if(selectedItem.type == type_shield){
                    currentShield = selectedItem;
                    attack = getDefense();
                }
                if(selectedItem.type == type_light){
                    if(currentLight == selectedItem){
                        currentLight = null;
                    }
                    else{
                        currentLight = selectedItem;
                    }
                    lightUpdated = true;
                }
                if(selectedItem.type == type_consumable){
                    if(selectedItem.use(this) == true){
                        if(selectedItem.amount > 1){
                            selectedItem.amount--;
                        }
                        else{
                            inventory.remove(itemIndex);
                        }
                    }
                }
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
