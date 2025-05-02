package Entity.Enemies;

import Entity.Animation;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import Entity.*;

public class Totem extends Enemy{

    private BufferedImage[] sprites;

    public Totem(TileMap tm){
        super(tm);

        moveSpeed = 0.5;
        maxSpeed = 0.5;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 30;
        height = 30;
        cwidth = 30;
        cheight = 30;

        health = maxHealth = 2;
        damage = 3;

        //load sprites
        try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/dragonsheet.png"));
            sprites = new BufferedImage[2];
            for(int i = 0 ; i < sprites.length ; i++){
                sprites[i] = spritesheet.getSubimage(i* width, 0, width, height);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
        facingRight = true;

    }

    private void getNextPosition(){}

    public void update(){
        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //check flinching
        if(flinching){
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400){
                flinching = false;
            }
        }

        //update animation
        animation.update();
    }

    public void draw(Graphics2D g){
        //if(notOnScreen()) return;

        setMapPosition();

        super.draw(g);
    }

}
