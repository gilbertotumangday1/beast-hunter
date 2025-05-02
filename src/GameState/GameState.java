package GameState;

public abstract class GameState {//super class with characteristics for every different state

    protected GameStateManager gsm;

    //methods every mode of the game has
    public abstract void init();
    public abstract void update();
    public abstract void draw(java.awt.Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);

}
