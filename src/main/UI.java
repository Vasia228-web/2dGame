package main;



import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    Graphics2D g2;
    GamePanel gp;
    BufferedImage heart_full, heart_half, heart_blank;
    Font purisaB;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0 ; // state 0 the first screen : 1 the second screen



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
        }

        //  PAUSE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
            drawPlayerLife();
        }

        // DIALOGUE
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
            drawPlayerLife();
        }
    }

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
        else if (titleScreenState == 1){
            //CLASS SELECTION SCREEN
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class";
            int x = getXforCenteredText(text);
            int y = gp.tileSize *3;
            g2.drawString(text,x , y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text,x , y);
            if(commandNum == 0){
                g2.drawString(">", x- gp.tileSize, y);
            }

            text = "Shooter";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x , y);
            if(commandNum == 1){
                g2.drawString(">", x- gp.tileSize, y);
            }

            text = "Peaceful";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x , y);
            if(commandNum == 2){
                g2.drawString(">", x- gp.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text,x , y);
            if(commandNum == 3){
                g2.drawString(">", x- gp.tileSize, y);
            }

        }

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
}

