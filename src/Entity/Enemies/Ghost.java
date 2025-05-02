package Entity.Enemies;

import Entity.*;
import TileMap.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Ghost extends Enemy {

    private BufferedImage[] sprites;
    private int startingpoint;

    public Ghost(TileMap tm) {
        super(tm);

        moveSpeed = 1;
        maxSpeed = 1;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;
        startingpoint = 0;

        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        health = maxHealth = 2;
        damage = 3;

        //load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Ghost.png"));
            sprites = new BufferedImage[1];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(1);
        startingpoint = 0;

        right = true;
        facingRight = true;

    }

    private void getNextPosition(){

        // movement
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
    }

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

        if(right && dx == 0){
            right = false;
            left = true;
            facingRight = false;
            startingpoint = 0;
        }
        else if(!right && dx ==0){
            right = true;
            left = false;
            facingRight = true;
            startingpoint = 0;
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