package Entity;

import java.awt.image.BufferedImage;//imports

public class Animation {
	
	private BufferedImage[] frames;//image data where images are stored
	private int currentFrame;//variable for the frame at the time
	
	private long startTime;//start of animation
	private long delay;//delay
	
	private boolean playedOnce;//if the animation has gone through once, for example slashing animation
	
	public Animation() {
		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long d) { delay = d; }//set delay
	public void setFrame(int i) { currentFrame = i; }//set current frame
	
	public void update() {//updating animation
		
		if(delay == -1) return;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}
		
	}
	
	public int getFrame() { return currentFrame; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	public boolean hasPlayedOnce() { return playedOnce; }
	
}
















