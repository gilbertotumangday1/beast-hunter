package Main;
//imports
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;

import GameState.GameStateManager;


public class GamePanel extends JPanel
        implements Runnable, KeyListener{//functions needed

    //splashscreen
    private BufferedImage splashscreen;

    // dimensions
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    // game thread
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    // image
    private BufferedImage image;//this is what the user will see
    private Graphics2D g;

    // game state manager
    private GameStateManager gsm;//this is the object that switches through the game states

    public GamePanel() {
        super();
        setPreferredSize(
                new Dimension(WIDTH * SCALE, HEIGHT * SCALE));//dimensions of panel
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init() {

        image = new BufferedImage(
                WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_RGB
        );
        g = (Graphics2D) image.getGraphics();

        running = true;

        gsm = new GameStateManager();//this is what will control the states of the game, switching from menu to level, etc.

    }

    public void run() {

        init();

        long start;
        long elapsed;
        long wait;

        // game loop
        while(running) {

            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000;
            if(wait < 0) wait = 5;

            try {
                Thread.sleep(wait);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void update() {
        gsm.update();
    }
    private void draw() {
        gsm.draw(g);
    }
    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0,
                WIDTH * SCALE, HEIGHT * SCALE,
                null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {}//get keys pressed
    public void keyPressed(KeyEvent key) {
        gsm.keyPressed(key.getKeyCode());
    }
    public void keyReleased(KeyEvent key) {
        gsm.keyReleased(key.getKeyCode());
    }

}
