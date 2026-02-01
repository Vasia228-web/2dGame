package main;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.interactiveTile;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile 
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile 
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;//768 pixels
    public final int screenHeight =tileSize * maxScreenRow;//576 pixels


    //WORLD SETTINGS

    public final int maxWorldCol =50;
    public final int maxWorldRow =50;


    //FPS
    int FPS = 60;


    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public EventHandler eHandler = new EventHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public  Player player = new Player(this,keyH);
    public Entity obj[] = new Entity[20];
    public Entity npc[] =new Entity[10];
    public Entity monster[] =new Entity[20];
    public interactiveTile iTile[] = new interactiveTile[50];
    public ArrayList<Entity>particleList = new ArrayList<>();
    public ArrayList<Entity>projectileList = new ArrayList<>();
    ArrayList<Entity>entityList = new ArrayList<>();


    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractive();
        //playMusic(0);
        gameState = titleState;
    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval =1000000000/ FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            update();

            repaint();
            try{
            double remainingTime = nextDrawTime - System.nanoTime();
            remainingTime = remainingTime/1000000;
            
            if(remainingTime < 0){
                remainingTime = 0;
            }

            Thread.sleep((long)remainingTime);
                
            nextDrawTime += drawInterval;

            }catch(InterruptedException e ){
                e.printStackTrace();
            }
        }
    }


    public void update(){
        if(gameState == playState){
            //PLAYER
            player.update();
            //NPC
            for(int i =0; i < npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
            //MONSTER
            for(int i =0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if(monster[i].alive == true && monster[i].dying == false){
                        monster[i].update();
                    }
                    if(monster[i].alive == false){
                        monster[i].checkDrop();
                        monster[i] = null;
                    }

                }
            }
            //PROJECTILE
            for(int i =0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if(projectileList.get(i).alive == true){
                        projectileList.get(i).update();
                    }
                    if(projectileList.get(i).alive == false){
                        projectileList.remove(i);
                    }
                }
            }

            //PARTICLE
            for(int i =0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if(particleList.get(i).alive == true){
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive == false){
                        particleList.remove(i);
                    }
                }
            }


            for(int i = 0; i < iTile.length; i++){
                if(iTile[i] != null){
                    iTile[i].update();
                }
            }
        }
        else if(gameState == pauseState){
            //we don't use update
        }


    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //DEBUG
        long drawStart = 0;
        if(keyH.showDebugText == true){
            drawStart = System.nanoTime();
        }

        //TITLE STATE
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{
            //Tile
            tileM.draw(g2);

            for(int i = 0; i < iTile.length; i++) {
                if (iTile[i] != null) {
                    iTile[i].draw(g2);
                }
            }

            //ADD ENTITY TO THE LIST
            entityList.add(player);

            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }

            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }
            for(int i = 0; i < projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    entityList.add(projectileList.get(i));
                }
            }
            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {

                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //DRAW ENTITIES
            for(int i =0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }

            //EMPTY ENTITY LIST

            entityList.clear();

            //UI
            ui.draw(g2);
        }


        //DEBUG
        if(keyH.showDebugText == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial",Font.PLAIN,20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("worldX " + player.worldX, x , y); y += lineHeight;
            g2.drawString("worldY " + player.worldY, x , y); y += lineHeight;
            g2.drawString("Col " + (player.worldX + player.solidArea.x) / tileSize, x , y); y += lineHeight;
            g2.drawString("Row " + (player.worldY + player.solidArea.y) / tileSize, x , y); y += lineHeight;
            g2.drawString("Draw Time:" + passed, x, y);
        }

        g2.dispose();

    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i ){
        se.setFile(i);
        se.play();
    }

}
