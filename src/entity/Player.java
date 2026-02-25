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
        setDialogue();
    }

    public void setDefaultPositions(){
        gp.currentMap = 0;
        worldX = gp.tileSize *23;
        worldY = gp.tileSize *21;
        direction = "down";
    }
    public void restoreStatus(){
        life = maxLife;
        mana = maxMana;
        invincible =false;
        transparent = false;
        attacking = false;
        guard = false;
        knockBack =false;
        lightUpdated = true;
        speed = defaultSpeed;
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
        nextLevelExp = 5;
        coin = 50;
        currentLight = null;
        attack =getAttack();//THE TOTAL ATTACK VALUE IS DECIDED BY STRENGTH AND WEAPON
        defense = getDefense();//THE TOTAL DEFENSE VALUE IS DECIDED BY DEXTERITY AND SHIELD
        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
    }
    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Lantern(gp));
        inventory.add(new OBJ_Tent(gp));
    }
    public void setDialogue(){
        dialogues[0][0] ="You are level "+ level+ " now!\n Now you are more stronger";
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }
    public int getCurrentWeaponSlot(){
        int currentWeaponSlot =0;
        for(int i = 0; i< inventory.size(); i++){
            if(inventory.get(i) == currentWeapon){
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot(){
        int currentShieldSlot =0;
        for(int i = 0; i< inventory.size(); i++){
            if(inventory.get(i) == currentShield){
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }
    public void getImage(){
        up1 = setup("/res/player/boy_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/res/player/boy_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/res/player/boy_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/res/player/boy_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/res/player/boy_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/res/player/boy_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/res/player/boy_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/res/player/boy_right_2",gp.tileSize,gp.tileSize);
    }
    public void getGuardImage(){
        guardUp = setup("/res/player/boy_guard_up",gp.tileSize,gp.tileSize);
        guardDown = setup("/res/player/boy_guard_down",gp.tileSize,gp.tileSize);
        guardLeft = setup("/res/player/boy_guard_left",gp.tileSize,gp.tileSize);
        guardRight = setup("/res/player/boy_guard_right",gp.tileSize,gp.tileSize);
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
    public void getAttackImage(){
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
        if(currentWeapon.type == type_pickaxe){
            attackUp1 = setup("/res/player/boy_pick_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/res/player/boy_pick_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/res/player/boy_pick_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/res/player/boy_pick_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/res/player/boy_pick_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/res/player/boy_pick_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/res/player/boy_pick_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/res/player/boy_pick_right_2", gp.tileSize * 2, gp.tileSize);
        }
    }
    public void update(){

        if(knockBack == true){

            //check tile collission
            collisionOn = false;
            gp.cChecker.checkTile(this);
            // check collission object
            gp.cChecker.checkObject(this, true);
            //CHECK NPC COLLISION
            gp.cChecker.checkEntity(this, gp.npc);
            //CHECK MONSTER COLLISION
            gp.cChecker.checkEntity(this, gp.monster);
            //CHECK INTERACTIVETILE COLLISION
            gp.cChecker.checkEntity(this, gp.iTile);

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
        else if(attacking == true){
            attacking();
        }
        else if (keyH.spacePressed == true) {
            guard = true;
            guardCounter++;
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

                //DEBUG GOD MODE!!!
                if (keyH.godMode == true) {
                    collisionOn = false;
                }

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
                guard = false;
                guardCounter =0;

                spriteCounter++;
                if (spriteCounter > 12) {
                    spriteNum = (spriteNum == 1) ? 2 : 1; 
                    spriteCounter = 0;
                }
                guard = false;
                guardCounter = 0;
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
                    transparent = false;
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
            //DEBUD GOD MOD!!!
            if(keyH.godMode == false){
                speed = defaultSpeed;

                if(life <= 0){
                    gp.gameState = gp.gameOverState;
                    gp.stopMusic();
                    gp.playSE(11);
                }
            }
            else{
                godMode();
            }

    }
    //DEBUG GOD MODE
    public void godMode(){
        collisionOn = false;
        speed = defaultSpeed + 5;
        life = maxLife;
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
        if(i != 999){
        if(gp.keyH.enterPressed == true){
                attackCanceled = true;
                gp.npc[gp.currentMap][i].speak();
            }
            gp.npc[gp.currentMap][i].move(direction);
        }
    }
    public void сontactMonster(int i){
        if(i != 999 ){
            if(invincible == false && gp.monster[gp.currentMap][i].dying == false){

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 1){
                    damage = 1;
                }
                life -= damage;
                invincible = true;
                transparent = true;
                gp.playSE(6);
            }
        }
    }
    public void damageMonster(int i,Entity attacker, int attack,int knockBackPower){
        if(i != 999){
            if(gp.monster[gp.currentMap][i].invincible == false){
               gp.playSE(5);
               if(knockBackPower > 0){
                   setKnockBack(gp.monster[gp.currentMap][i],attacker,knockBackPower);
               }
               if(gp.monster[gp.currentMap][i].offBalance == true){
                   attack *=2;
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
    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp * 2;
            dexterity++;
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            setDialogue();
            startDialogue(this,0);
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
        Entity newItem = gp.eGenerator.getObject(item.name);
        //CHECK IF STACKEBLE
        if(newItem.stackable == true){
            int index = searchItemInInventory(newItem.name);
            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            //NEW ITEM SO NEED TO CHEK VACANCY
            else {
                if(inventory.size() != maxInventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        //NOT STACKABLE ITEM
        else{
            if(inventory.size() != maxInventorySize){
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

            if(itemIndex < inventory.size()) {

                Entity selectedItem = inventory.get(itemIndex);

                if (selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe) {
                    currentWeapon = selectedItem;
                    attack = getAttack();
                    getAttackImage();
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
                if(guard == true){
                    image = guardUp;
                }
            }
            case "down" -> {
                if(!attacking){
                    image = (spriteNum == 1) ? down1 : down2;
                }else{
                    image = (spriteNum == 1) ? attackDown1 : attackDown2;
                }
                if(guard == true){
                    image = guardDown;
                }
            }
            case "left" -> {
                if(!attacking) {
                    image = (spriteNum == 1) ? left1 : left2;
                }else{
                    tempScreenX = screenX - gp.tileSize;
                    image = (spriteNum == 1) ? attackLeft1 : attackLeft2;
                }
                if(guard == true){
                    image = guardLeft;
                }
            }
            case "right" -> {
                if(!attacking) {
                    image = (spriteNum == 1) ? right1 : right2;
                }else{
                    image = (spriteNum == 1) ? attackRight1 : attackRight2;
                }
                if(guard == true){
                    image = guardRight;
                }
            }
        }

        if(transparent == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
        }
        if(drawing == true){
            g2.drawImage(image, tempScreenX, tempScreenY, null);
        }
        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));


    }
}
