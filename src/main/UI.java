package main;



import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Mana;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    Graphics2D g2;
    GamePanel gp;
    BufferedImage heart_full, heart_half, heart_blank, mana_full, mana_blank,coin;
    Font purisaB;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0 ; // state 0 the first screen : 1 the second screen
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public int playerSlotCol =0;
    public int playerSlotRow =0;
    public int npcSlotCol =0;
    public int npcSlotRow =0;
    int subState = 0;
    int counter = 0;
    public Entity npc;

    public UI(GamePanel gp){
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/res/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch (FontFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        //CREATE HUB OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_blank = heart.image;
        heart_full = heart.image2;
        heart_half = heart.image3;
        Entity manaCrystal = new OBJ_Mana(gp);
        mana_blank = manaCrystal.image;
        mana_full = manaCrystal.image2;
        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;

    }
    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(purisaB);
        g2.setColor(Color.white);

        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        //  PLAY STATE
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMessage();
        }

        //  PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }
        // CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawPlayerLife();
            drawCharacterScreen();
            drawInvetory(gp.player, true);
        }
        // OPTION STATE
        if(gp.gameState == gp.optionState){
            drawPlayerLife();
            drawOptionsScreen();
        }
        // GAMEOVER STATE
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        // TRANSITION STATE
        if(gp.gameState == gp.transitionState){
            drawTransition();
        }
        // TRADE STATE
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }
    }
    //HERE WE DRAW PLAYER HEART AND PLAYER MANACRYSTAL.
    public void drawPlayerLife(){

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // DRAW MAX LIFE
        while(i < gp.player.maxLife / 2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // RESET
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        int life = gp.player.life;

        // DRAW CURRENT LIFE
        while(i < gp.player.maxLife / 2){

            if(life >= 2){
                g2.drawImage(heart_full, x, y, null);
                life -= 2;
            }
            else if(life == 1){
                g2.drawImage(heart_half, x, y, null);
                life -= 1;
            }

            i++;
            x += gp.tileSize;
        }

        //DRAW MAX MANACRYSTAL
        x = (gp.tileSize /2)+5;
        y = (int)(gp.tileSize * 1.5);
        i =0;
        while(i < gp.player.maxMana){
            g2.drawImage(mana_blank,x,y,null);
            i++;
            x += 40;
        }
        //DRAW MANA
        x = (gp.tileSize /2)+5;
        y = (int)(gp.tileSize * 1.5);
        i =0;
        while(i < gp.player.mana){
            g2.drawImage(mana_full,x,y,null);
            i++;
            x += 40;
        }
    }
    public void drawTradeScreen(){
            switch (subState){
                case 0: trade_select();break;
                case 1: trade_buy();break;
                case 2: trade_sell();break;
            }
            gp.keyH.enterPressed = false;
    }
    public void trade_select(){
        drawDialogueScreen();

        //DRAW WINDOW
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 5;
        int width = gp.tileSize * 4;
        int height = (int)(gp.tileSize *3.5);
        drawSubWindow(x,y,width,height);

        //DRAW TEXT
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy",x ,y);
        if(commandNum == 0){
            g2.drawString(">",x-24,y);
            if(gp.keyH.enterPressed == true){
                subState = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell",x ,y);
        if(commandNum == 1){
            g2.drawString(">",x-24,y);
            if(gp.keyH.enterPressed == true){
                subState = 2;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave",x ,y);
        if(commandNum == 2){
            g2.drawString(">",x-24,y);
            if(gp.keyH.enterPressed == true){
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "See you later";
            }
        }


    }
    public void trade_buy(){
        //DRAW PLAYER INVENTORY
        drawInvetory(gp.player, true);

        //DRAW NPC INVENTORY
        drawInvetory(npc, true);

        //DRAW HINT WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize *2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x+24,y+60);

        //DRAW HINT WINDOW
        x = gp.tileSize * 9;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize *2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Your Coins: " + gp.player.coin, x+24,y+60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y,width,height);
            g2.drawImage(coin,x + 10, y +1  ,44,44,null);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,24F));
            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlginToRightText(text, gp.tileSize*8-24);
            g2.drawString(text,x,y+32);
        }
        //BUY AN ITEM
        if(gp.keyH.enterPressed == true){
            if(npc.inventory.get(itemIndex).price > gp.player.coin){
                subState = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "You don't have enough coins";
                drawDialogueScreen();
            }
            else if(gp.player.inventory.size() == gp.player.maxInventorySize){
                subState = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Your inventory is full!";
            }
            else {
                gp.player.coin -= npc.inventory.get(itemIndex).price;
                gp.player.inventory.add(npc.inventory.get(itemIndex));
            }
        }
    }
    public void trade_sell(){

        drawInvetory(gp.player, true);

        //DRAW HINT WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize *2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x+24,y+60);

        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 9;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize *2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Your Coins: " + gp.player.coin, x+24,y+60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()){
            x = (int)(gp.tileSize*15.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y,width,height);
            g2.drawImage(coin,x + 10, y +1  ,44,44,null);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,24F));
            int price = gp.player.inventory.get(itemIndex).price / 2;
            String text = "" + price;
            x = getXforAlginToRightText(text, gp.tileSize*18-24);
            g2.drawString(text,x,y+32);


            //SELL AN ITEM
            if(gp.keyH.enterPressed == true){
                if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                        gp.player.inventory.get(itemIndex) == gp.player.currentShield){
                    subState = 0;
                    commandNum = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue ="You can't sell an equipped item!";
                }
                else{
                    gp.player.inventory.remove(itemIndex);
                    gp.player.coin += price;
                }
            }
        }
    }
    public void drawMessage(){
        int messageX = gp.tileSize * 6;
        int messageY = gp.tileSize * 8;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for(int i = 0 ; i< message.size(); i++){
            g2.setColor(Color.red);
            g2.drawString(message.get(i),messageX, messageY);

            int counter = messageCounter.get(i) + 1;
            messageCounter.set(i, counter);
            messageY += 50;

            if (messageCounter.get(i) > 60){
                message.remove(i);
                messageCounter.remove(i);
            }
        }
    }
    public void drawTitleScreen(){
        if(titleScreenState == 0){
            //BACKGROUND
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

            //TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,68F));
            String text = "Game for freaks";
            int x = getXforCenteredText(text);
            int y = gp.tileSize *3;

            //SWADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x+5, y+5);

            //MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,42F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize *4;
            g2.drawString(text,x , y);
            if(commandNum == 0){
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x , y);
            if(commandNum == 1){
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x , y);
            if(commandNum == 2){
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
//        else if (titleScreenState == 1){
//            //CLASS SELECTION SCREEN
//            g2.setColor(Color.white);
//            g2.setFont(g2.getFont().deriveFont(42F));
//
//            String text = "Select your class";
//            int x = getXforCenteredText(text);
//            int y = gp.tileSize *3;
//            g2.drawString(text,x , y);
//
//            text = "Fighter";
//            x = getXforCenteredText(text);
//            y += gp.tileSize*3;
//            g2.drawString(text,x , y);
//            if(commandNum == 0){
//                g2.drawString(">", x- gp.tileSize, y);
//            }
//
//            text = "Shooter";
//            x = getXforCenteredText(text);
//            y += gp.tileSize;
//            g2.drawString(text,x , y);
//            if(commandNum == 1){
//                g2.drawString(">", x- gp.tileSize, y);
//            }
//
//            text = "Peaceful";
//            x = getXforCenteredText(text);
//            y += gp.tileSize;
//            g2.drawString(text,x , y);
//            if(commandNum == 2){
//                g2.drawString(">", x- gp.tileSize, y);
//            }
//
//            text = "Back";
//            x = getXforCenteredText(text);
//            y += gp.tileSize*2;
//            g2.drawString(text,x , y);
//            if(commandNum == 3){
//                g2.drawString(">", x- gp.tileSize, y);
//            }
//
//        }

    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight /2;

        g2.drawString(text, x , y);
    }
    public void drawDialogueScreen(){

        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth -(gp.tileSize*4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line,x, y);
            y += 40;
        }

    }
    public void drawCharacterScreen(){
        //CREATE A FRAME
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(22F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 34;

        //NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Shield", textX, textY);

        // VALUE
        int tailX = (frameX + frameWidth) -30;
        //RESET TEXTY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlginToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlginToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlginToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlginToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlginToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlginToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlginToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlginToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nexLevelExp);
        textX = getXforAlginToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlginToRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY -15, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 15, null);


    }
    public void drawInvetory(Entity entity, boolean cursor){
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player){
            frameX = gp.tileSize *9 ;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }


        //WINDOW OF INVENTORY
        drawSubWindow(frameX, frameY, frameWidth,frameHeight);

        //SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;


        //CURSOR
        int cursorX = slotXstart + (gp.tileSize * slotCol);
        int cursorY = slotYstart + (gp.tileSize * slotRow);;
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //DRAW PLAYER ITEMS INVENTORY
        for(int i = 0; i < entity.inventory.size(); i++){

            //DRAW PLAYER EQUIPMENT
            if(entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1,slotX, slotY, null );

            slotX +=gp.tileSize;
            if(i == 4 || i == 9 || i== 14){
                slotX = slotXstart;
                slotY += gp.tileSize;
            }
        }

        //DRAW CURSOR
        if(cursor == true){
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);


            //DRAW DESCRIPTION
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;


            //DRAW DESCRIPTION TEXT
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(14F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if(itemIndex < entity.inventory.size()){
                drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);
                for(String line: entity.inventory.get(itemIndex).description.split("\n")){
                    g2.drawString(line , textX, textY);
                    textY += 32;
                }
            }
        }
    }
    public int getItemIndexOnSlot(int slotCol, int slotRow){
        int itemIndex = slotCol+ (slotRow * 5);
        return itemIndex;
    }
    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0, 0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,100F));

        text= "Game Over";
        //SHADOW
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text,x,y);
        //MAIN
        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);

        //RETRY
        g2.setFont(g2.getFont().deriveFont(50F));
        text ="Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*3;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.drawString(">",x -24,y);
        }

        //BACK TO TITLE SCREEN
        g2.setFont(g2.getFont().deriveFont(50F));
        text ="Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">",x -24,y);
        }
    }
    public void drawOptionsScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(28F));

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize *8;
        int frameHeight = gp.tileSize * 10;

        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch (subState){
            case 0 : option_top(frameX,frameY);break;
            case 1 : options_fullScreenNotification(frameX, frameY);break;
            case 2 : option_control(frameX,frameY);break;
            case 3 : option_endGameConfirmation(frameX,frameY);break;
        }
        gp.keyH.enterPressed = false;
    }
    public void option_top(int frameX, int frameY){
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        //FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen",textX,textY);
        if(commandNum == 0){
            g2.drawString(">",textX -25, textY);
            if(gp.keyH.enterPressed == true){
                if(gp.fullScreen0n == false){
                    gp.fullScreen0n = true;
                }
                else if(gp.fullScreen0n == true){
                    gp.fullScreen0n = false;
                }
                subState = 1;
            }
        }



        //MUSIC
        textY += gp.tileSize;
        g2.drawString("Music",textX,textY);
        if(commandNum == 1){
            g2.drawString(">",textX -25, textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("SE",textX,textY);
        if(commandNum == 2){
            g2.drawString(">",textX -25, textY);
        }

        //CONTROL
        textY += gp.tileSize;
        g2.drawString("Control",textX,textY);
        if(commandNum == 3){
            g2.drawString(">",textX -25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 2;
                commandNum =0;
            }
        }

        //END GAME
        textY += gp.tileSize;
        g2.drawString("End Game",textX,textY);
        if(commandNum == 4){
            g2.drawString(">",textX -25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 3;
                commandNum =0;
            }
        }

        //BACK
        textY += gp.tileSize * 2;
        g2.drawString("Back",textX,textY);
        if(commandNum == 5){
            g2.drawString(">",textX -25, textY);
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.playState;
            }
        }


        //FULL SCREEN CHECK BOX
        textX = frameX + (gp.tileSize* 6);
        textY = frameY + gp.tileSize * 2 + 28;
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(textX, textY, 24,24);
        if(gp.fullScreen0n == true){
            g2.fillRect(textX,textY,24,24);
        }

        //  MUSIC CHECK BOX
        textY += gp.tileSize;
        textX = frameX + (gp.tileSize* 4);
        g2.drawRect(textX, textY, 120,24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        // SE CHECK BOX
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120,24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        gp.config.saveConfig();

    }
    public void options_fullScreenNotification(int frameX, int frameY){
        int textX = frameX + gp.tileSize - 20;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "The change will \ntake effect after \nrestarting the game";
        for(String line:currentDialogue.split("\n")){
            g2.drawString(line, textX,textY);
            textY += 40;
        }

        //BACK
        textY = frameY + gp.tileSize* 9;
        g2.drawString("Back",textX,textY);
        if(commandNum == 0){
            g2.drawString(">",textX -24, textY);
            if(gp.keyH.enterPressed == true){
                subState =0;
            }
        }
    }
    public void option_control(int frameX, int frameY){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22f));
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        textX = frameX + 24;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confrim/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY);

        textX = frameX + (gp.tileSize-4) * 6;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX , textY); textY+= gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY+= gp.tileSize;
        g2.drawString("F", textX, textY); textY+= gp.tileSize;
        g2.drawString("C", textX, textY); textY+= gp.tileSize;
        g2.drawString("P", textX, textY); textY+= gp.tileSize;
        g2.drawString("ESCAPE", textX, textY);

        //BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize* 9;
        g2.drawString("Back",textX,textY);
        if(commandNum == 0){
            g2.drawString(">",textX -24, textY);
            if(gp.keyH.enterPressed == true){
                subState =0;
            }
        }
    }
    public void option_endGameConfirmation(int frameX,int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Quit the game and \n return to the \ntitle screen?";
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX,textY);
            textY += 40;
        }
        //YES
        String text ="Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize+ 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">",textX -24, textY);
            if(gp.keyH.enterPressed == true){
                subState =0;
                gp.resetGame();
            }
        }
        //NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize+ 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">",textX -24, textY);
            if(gp.keyH.enterPressed == true){
                subState =0;
            }
        }
    }
    public void drawTransition(){
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        if(counter == 50){
            counter =0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }
    public void drawSubWindow(int x , int y, int width, int height){
        Color c = new Color(0,0,0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height -10,25,25);
    }
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;

    }
    public int getXforAlginToRightText(String text ,int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;

    }
}

