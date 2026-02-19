package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;
    public int dayCounter = 0;
    public float filterAlpha =0f;

    //DAY STATE
    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;

    public Lighting(GamePanel gp){
        this.gp = gp;
        setLightSource();
    }
    public void setLightSource(){
        //CREATE BUFFERED IMAGE
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        if(gp.player.currentLight == null){
            g2.setColor(new Color(0,0,0,0.98f));
        }
        else{
            //GET THE CENTER X AND Y OF THE LIGHT CIRCLE
            int centerX =gp.player.screenX + (gp.tileSize) /2;
            int centerY =gp.player.screenY + (gp.tileSize) /2;

            Color color[] = new Color[12];
            float fraction[] = new float[12];

            color[0]  = new Color(0, 0, 0.1f, 0.0f);
            color[1]  = new Color(0, 0, 0.1f, 0.0f);
            color[2]  = new Color(0, 0, 0.1f, 0.02f);
            color[3]  = new Color(0, 0, 0.1f, 0.08f);
            color[4]  = new Color(0, 0, 0.1f, 0.15f);
            color[5]  = new Color(0, 0, 0.1f, 0.25f);
            color[6]  = new Color(0, 0, 0.1f, 0.40f);
            color[7]  = new Color(0, 0, 0.1f, 0.60f);
            color[8]  = new Color(0, 0, 0.1f, 0.80f);
            color[9]  = new Color(0, 0, 0.01f, 0.92f);
            color[10] = new Color(0, 0, 0.001f, 0.96f);
            color[11] = new Color(0, 0, 0, 0.98f);

            fraction[0]  = 0.0f;
            fraction[1]  = 0.1f;
            fraction[2]  = 0.2f;
            fraction[3]  = 0.3f;
            fraction[4]  = 0.4f;
            fraction[5]  = 0.5f;
            fraction[6]  = 0.6f;
            fraction[7]  = 0.7f;
            fraction[8]  = 0.8f;
            fraction[9]  = 0.9f;
            fraction[10] = 0.95f;
            fraction[11] = 1.0f;

            //CREATE A GRADATION PAINT SETTINGS FOR THE LIGHT CIRCLE
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX,centerY,gp.player.currentLight.lightRadius, fraction,color);

            //SET THE GRADIENT DATA ON G2
            g2.setPaint(gPaint);
        }

        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g2.dispose();
    }
    public void update(){
        if(gp.player.lightUpdated == true){
            setLightSource();
            gp.player.lightUpdated = false;
        }

        //CHEAKING THE STATE OF THE DAY
        if(dayState == day){
            dayCounter++;
            if(dayCounter > 600){
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if(dayState == dusk){
            filterAlpha += 0.001f;

            if(filterAlpha > 1f){
                filterAlpha = 1f;
                dayState = night;
            }
        }
        if(dayState == night){
            dayCounter++;
            if(dayCounter > 600){
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if(dayState == dawn){
            filterAlpha -= 0.001f;
            dayCounter++;
            if(filterAlpha < 0f){
                filterAlpha  = 0f;
                dayState = day;
            }
        }

    }

    public void draw(Graphics2D g2 ){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2.drawImage(darknessFilter,0,0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
