package Entity;

import TileMap.TileMap;

//imports
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FireBall extends MapObject {

	//attack stuff
	private boolean hit;
	private boolean remove;

	//sprite stuff
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;


	public FireBall(TileMap tm, boolean right) {
		
		super(tm);
		
		facingRight = right;

		//directio nand speed stuff
		moveSpeed = 3.8;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;

		//characteristics
		width = 30;
		height = 30;
		cwidth = 14;
		cheight = 14;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Player/fireball.gif"
				)
			);
			
			sprites = new BufferedImage[4];//when its rolling
			for(int i = 0; i < sprites.length; i++) {//for loop
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
			
			hitSprites = new BufferedImage[3];//when it collides with stuff
			for(int i = 0; i < hitSprites.length; i++) {//using a for loop
				hitSprites[i] = spritesheet.getSubimage(
					i * width,
					height,
					width,
					height
				);
			}

			//creating an instance of the animation class
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setHit() {//detecting a hit
		if(hit) return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx = 0;
	}
	
	public boolean shouldRemove() { return remove; }
	
	public void update() {//updating fireballs
		
		checkTileMapCollision();//checking for collision
		setPosition(xtemp, ytemp);//set its new position
		
		if(dx == 0 && !hit) {//check for hitting something
			setHit();
		}
		
		animation.update();
		if(hit && animation.hasPlayedOnce()) {
			remove = true;
		}
		
	}
	
	public void draw(Graphics2D g) {//drawing it to screen
		
		setMapPosition();
		
		super.draw(g);//referencing the draw method in the super class
		
	}
	
}


















