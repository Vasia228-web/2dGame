package main;

import data.Progress;
import entity.Entity;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventReact[][][];
    int previousEventX , previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;
    Entity eventMaster;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventMaster = new Entity(gp);
        eventReact = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventReact[map][col][row] = new EventRect();
            eventReact[map][col][row].x = 23;
            eventReact[map][col][row].y = 23;
            eventReact[map][col][row].width =2;
            eventReact[map][col][row].height =2;
            eventReact[map][col][row].eventRectDefaultX = eventReact[map][col][row].x;
            eventReact[map][col][row].eventRectDefaultY = eventReact[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col =0;
                row++;
            }
            if(row == gp.maxWorldRow){
                row = 0;
                map++;
            }
        }
        setDialogue();
    }
    public void setDialogue(){
        eventMaster.dialogues[0][0] = "You step into a trap";
        eventMaster.dialogues[1][0] = "You feel better now and \nCheck point";
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
            if(hit(0,27, 16, "right") == true){
                damagePit(gp.dialogueState);
            }
           else if(hit(0,23,12,"up") == true){
                healinPool(gp.dialogueState);
            }
           else if(hit(0,10,39,"any") == true){//TO THE MERCHANT HUT
                teleport(1,12,13,gp.indoor);
            }
           else if(hit(1,12,13,"any") == true){//TO OUTSIDE
                teleport(0,10,39,gp.outside);
            }
            else if(hit(0,12,9,"any") == true){//TO THE DUNGEON
                teleport(2,9,41,gp.dungeon);
            }
            else if(hit(2,9,41,"any") == true){//TO OUTSIDE
                teleport(0,12,9,gp.outside);
            }
            else if(hit(2,8,7,"any") == true){//TO FLOOR 2
                teleport(3,21,41, gp.dungeon);
            }
            else if(hit(3,26,41,"any") == true){// TO FLOOR 1
                teleport(2,8,7,gp.dungeon);
            }
            else if(hit(3,25,27,"any") == true){// TO FLOOR 2
                skeletonLord();
            }
        }
    }
    public boolean hit(int map, int col, int row, String reqDirection){

        boolean hit = false;

        if(map == gp.currentMap){
            //FIND HIT BOKS OF PLAYER
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            //FIND HIT BOKS OF EVENT
            eventReact[map][col][row].x = col*gp.tileSize + eventReact[map][col][row].x;
            eventReact[map][col][row].y = row*gp.tileSize + eventReact[map][col][row].y;

            if(gp.player.solidArea.intersects(eventReact[map][col][row]) && eventReact[map][col][row].eventDone == false){
                if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit =true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;

                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventReact[map][col][row].x = eventReact[map][col][row].eventRectDefaultX;
            eventReact[map][col][row].y = eventReact[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
    public void damagePit(int gameState){
        gp.gameState = gameState;
        eventMaster.startDialogue(eventMaster,0);
        gp.player.life -= 1;
        canTouchEvent = false;
    }
    public void healinPool(int gameState){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            eventMaster.startDialogue(eventMaster,1);
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
            gp.saveLoad.save();
        }
    }
    public void teleport(int map,int col,int row, int area){
        gp.gameState = gp.transitionState;
        gp.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSE(12);
    }
    public void skeletonLord(){
        if(gp.boosBattleOn == false && Progress.skeletonLordDefeated == false){
            gp.gameState = gp.cutsceneState;
            gp.csManager.sceneNum = gp.csManager.skeletonLord;
        }
    }
}
