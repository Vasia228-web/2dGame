package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventReact[][];
    int previousEventX , previousEventY;
    boolean canTouchEvent = true;


    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventReact = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventReact[col][row] = new EventRect();
            eventReact[col][row].x = 23;
            eventReact[col][row].y = 23;
            eventReact[col][row].width =2;
            eventReact[col][row].height =2;
            eventReact[col][row].eventRectDefaultX = eventReact[col][row].x;
            eventReact[col][row].eventRectDefaultY = eventReact[col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col =0;
                row++;
            }
        }
    }

    public void cheackEvent(){

        //CHEK IF PLAYER GET AWAY FROM EVENT
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent =true;
        }

        if(canTouchEvent == true){
            if(hit(27, 16, "right") == true){
                damagePit(27, 16,gp.dialogueState);
            }
            if(hit(23,12,"up") == true){
                healinPool(23, 12,gp.dialogueState);
            }
        }
    }

    public boolean hit(int col, int row, String reqDirection){

        boolean hit = false;
        //FIND HIT BOKS OF PLAYER
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        //FIND HIT BOKS OF EVENT
        eventReact[col][row].x = col*gp.tileSize + eventReact[col][row].x;
        eventReact[col][row].y = row*gp.tileSize + eventReact[col][row].y;

        if(gp.player.solidArea.intersects(eventReact[col][row]) && eventReact[col][row].eventDone == false){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit =true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;

            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventReact[col][row].x = eventReact[col][row].eventRectDefaultX;
        eventReact[col][row].y = eventReact[col][row].eventRectDefaultY;

        return hit;
    }

    public  void damagePit(int col,int row, int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "fi atent!";
        gp.player.life -= 1;
//        eventReact[col][row].eventDone = true;
        canTouchEvent = false;

    }

    public void healinPool(int col,int row, int gameState){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.ui.currentDialogue = "ai tras un 50 de vodka";
            gp.player.life = gp.player.maxLife;
            gp.aSetter.setMonster();
        }
    }
}
