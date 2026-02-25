package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_DestructibleWall extends interactiveTile {

    GamePanel gp;

    public IT_DestructibleWall(GamePanel gp, int col, int row){
        super(gp, col, row);
        life = 3;
        this.gp = gp;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/res/tiles_interactive/destructiblewall",gp.tileSize, gp.tileSize);
        destructible = true;
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrecItem = false;
        if(entity.currentWeapon.type == type_pickaxe){
            isCorrecItem = true;
        }
        return isCorrecItem;
    }
    public void playSE(){
        gp.playSE(10);
    }
    public interactiveTile getDestroyForm(){
        interactiveTile tile = null;
        return tile;
    }
    public Color getParticleColor(){
        Color color = new Color(65,65,65);
        return color;
    }
    public int getParticleSize(){
        int size =6;
        return size;
    }
    public int getParticleSpeed(){
        int speed =1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife =20;
        return maxLife;
    }

}
