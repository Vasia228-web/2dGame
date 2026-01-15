package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    Rectangle eventReact;
    int eventReactDefaultX , eventReactDefaultY;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventReact = new Rectangle();
        eventReact.x = 23;
        eventReact.y = 23;
        eventReact.width =2;
        eventReact.height =2;
        eventReactDefaultX = eventReact.x;
        eventReactDefaultY = eventReact.y;
    }

    public void cheackEvent(){

        if(hit(27, 16, "right") == true){
            damagePit(gp.dialogueState);
        }

        if(hit(23,12,"up") == true){
            healinPool(gp.dialogueState);
        }

    }

    public boolean hit(int eventCol, int eventRow, String reqDirection){

        boolean hit = false;
        //FIND HIT BOKS OF PLAYER
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        //FIND HIT BOKS OF EVENT
        eventReact.x = eventCol*gp.tileSize + eventReact.x;
        eventReact.y = eventRow*gp.tileSize + eventReact.y;

        if(gp.player.solidArea.intersects(eventReact)){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit =true;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventReact.x = eventReactDefaultX;
        eventReact.y = eventReactDefaultY;

        return hit;
    }

    public  void damagePit(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "fi atent!";
        gp.player.life -= 1;

    }

    public void healinPool(int gameState){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "ai tras un 50 de vodka";
            gp.player.life = gp.player.maxLife;
        }
    }
}
