package Entity;

import TileMap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Portal extends MapObject {

    private BufferedImage[] sprites;

    public Portal(TileMap tm) {
        super(tm);

        moveSpeed = 0.5;
        maxSpeed = 0.5;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 20;
        height = 40;
        cwidth = 10;
        cheight = 40;

        //load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Portal/portalsheet.png"));
            sprites = new BufferedImage[4];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(100);

    }

    private void getNextPosition() {

        // movement
    }

    public void update() {
        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //falling
        if (falling) {
            dy += fallSpeed;
        }

        //if it gets to a specific offset, switch directions

        //update animation
        animation.update();
    }

    public void draw(Graphics2D g) {
        //if(notOnScreen()) return;

        setMapPosition();

        super.draw(g);
    }
}
