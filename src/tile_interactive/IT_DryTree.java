package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_DryTree extends interactiveTile{
    GamePanel gp;

    public IT_DryTree(GamePanel gp, int col, int row){
        super(gp, col, row);
        life = 3;
        this.gp = gp;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/res/tiles_interactive/drytree",gp.tileSize, gp.tileSize);
        destructible = true;
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrecItem = false;
        if(entity.currentWeapon.type == type_axe){
            isCorrecItem = true;
        }
        return isCorrecItem;
    }

    public void playSE(){
        gp.playSE(10);
    }

    public interactiveTile getDestroyForm(){
        interactiveTile tile = new IT_Trunk(gp, worldX/ gp.tileSize,worldY/ gp.tileSize);
        return tile;
    }

    public Color getParticleColor(){
        Color color = new Color(65,50,30);
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
