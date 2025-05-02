package Entity;

import TileMap.TileMap;

public class Enemy extends MapObject{//global class for all enemie classes

    //health stuff
    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;

    //if you're close to the enemy
    protected boolean flinching;
    protected long flinchTimer;

    public Enemy(TileMap tm){
        super(tm);
    }//creates enemy method

    //basic methods
    public boolean isDead(){return dead;}
    public int getDamage() {return damage;}

    public void hit(int damage){//when enemy gets hit
        if(dead || flinching) return;//if its dead and its close enough its over
        health -= damage;//if it gets hit, the health loses the amount of damage done by the attack
        if(health < 0) health = 0;
        if(health == 0) dead = true;
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    public void update(){}

}
