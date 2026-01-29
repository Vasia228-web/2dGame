package main;



import entity.Entity;
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
    BufferedImage heart_full, heart_half, heart_blank, mana_full, mana_blank;
    Font purisaB;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0 ; // state 0 the first screen : 1 the second screen
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public int slotCol =0;
    public int slotRow =0;





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

    }
    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(purisaB);
        g2.setColor(Color.white);

        //TITLE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        //  PLAY
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMessage();
        }

        //  PAUSE
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }

        // DIALOGUE
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }
        //CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawPlayerLife();
            drawCharacterScreen();
            drawInvetory();
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

    public void drawInvetory(){
        //WINDOW OF INVENTORY
        int frameX = gp.tileSize *9 ;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
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
        for(int i = 0; i < gp.player.inventory.size(); i++){

            //DRAW PLAYER EQUIPMENT
            if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
                gp.player.inventory.get(i) == gp.player.currentShield){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize, 10, 10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1,slotX, slotY, null );

            slotX +=gp.tileSize;
            if(i == 4 || i == 9 || i== 14){
                slotX = slotXstart;
                slotY += gp.tileSize;
            }
        }

        //DRAW CURSOR
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

        int itemIndex = getItemIndexOnSlot();
            if(itemIndex < gp.player.inventory.size()){
                drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);
                for(String line: gp.player.inventory.get(itemIndex).description.split("\n")){
                    g2.drawString(line , textX, textY);
                    textY += 32;
                }
            }
    }

    public int getItemIndexOnSlot(){
        int itemIndex = slotCol+ (slotRow * 5);
        return itemIndex;
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

