package GameState;

import Main.GamePanel;
import TileMap.*;
import TileMap.Image;
import Entity.*;
import Entity.Enemies.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level4State extends GameState {

    //music and sound effects
    private AudioPlayer bgMusic;

    //tilemap
    private TileMap tileMap;
    private Background bg;

    //player
    private Player player;

    //enemies
    private ArrayList<Enemy> enemies;
    private ArrayList<Portal> portals;
    private ArrayList<Explosion> explosions;

    //health and fireball bar
    private HUD hud;

    //events
    private boolean eventFinish;
    private boolean eventPause;
    private boolean eventDead;

    //images
    private Image pausedImage;
    private Image deadImage;
    private Image wonImage;

    public Level4State(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    public void init() {

        //tilemap
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/level4tiles.png");
        tileMap.loadMap("/Maps/level4-cave");
        tileMap.setPosition(0, 0);
        tileMap.setTween(1);

        //background
        bg = new Background("/Backgrounds/level4bg.png", 0.1);

        //player
        player = new Player(tileMap);
        player.setPosition(100, 100);

        //enemies
        populateEnemies();

        //explosions
        explosions = new ArrayList<Explosion>();

        //health and fireball bar
        hud = new HUD(player);

        //music and sound effects
        bgMusic = new AudioPlayer("/Music/Level3.mp3");
        bgMusic.play();

        //start event
        eventPause = false;

        //images
        pausedImage = new Image("/Events/pause.png", 0.1);
        deadImage = new Image("/Events/dead.png", 0.1);
        wonImage = new Image("/Events/storylines/end4.png", 0.1);

    }

    private void populateEnemies() {

        enemies = new ArrayList<Enemy>();

        Slugger s;
        Point[] points = new Point[] {};
        for(int i = 0; i < points.length; i++) {
            s = new Slugger(tileMap);
            s.setPosition(points[i].x, points[i].y);
            enemies.add(s);
        }

        Totem t;
        Point[] points1 = new Point[] {
                new Point(1670, 80),
                new Point(2361, 80),
                new Point(2775, 140),
                new Point(3005, 80),
                new Point(312, 50),
                new Point(2890 , 160),
                new Point(2925 , 70),
                new Point(1812 , 160),
                new Point(1550,130)
        };
        for(int i = 0 ; i < points1.length ; i ++){
            t = new Totem(tileMap);
            t.setPosition(points1[i].x, points1[i].y);
            enemies.add(t);
        }

        portals = new ArrayList<Portal>();

        Portal g;
        Point[] pts = new Point[] {
                new Point(2720, 190)
        };
        for(int i = 0; i < pts.length; i++) {
            g = new Portal(tileMap);
            g.setPosition(pts[i].x, pts[i].y);
            portals.add(g);
        }
    }

    public void update() {
        if(!eventPause && !eventDead && !eventFinish){

            // check if player dead event should start
            if(player.getHealth() == 0|| player.gety() > tileMap.getHeight() - 20){
                eventDead = true;
            }

            // update player
            player.update();

            //update tilemap
            tileMap.setPosition(
                    GamePanel.WIDTH / 2 - player.getx(),
                    GamePanel.HEIGHT / 2 - player.gety()
            );

            // set background
            bg.setPosition(tileMap.getx(), tileMap.gety());


            // attack enemies
            player.checkAttack(enemies);

            // update all enemies
            for(int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                e.update();
                if(e.isDead()) {
                    enemies.remove(i);
                    i--;
                    explosions.add(
                            new Explosion(e.getx(), e.gety()));
                }
            }

            //update portals
            for(int i = 0 ; i < portals.size(); i++){
                Portal p = portals.get(i);
                p.update();
                if(p.intersects(player)){
                    eventFinish = true;
                }
            }

            // update explosions
            for(int i = 0; i < explosions.size(); i++) {
                explosions.get(i).update();
                if(explosions.get(i).shouldRemove()) {
                    explosions.remove(i);
                    i--;
                }
            }
        }
    }

    public void draw(Graphics2D g) {
        if(!eventPause && !eventDead && !eventFinish){
            // draw bg
            bg.draw(g);

            // draw tilemap
            tileMap.draw(g);

            // draw player
            player.draw(g);

            // draw enemies
            for(int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(g);
            }

            //draw portals
            for(int i = 0 ; i < portals.size() ; i++){
                portals.get(i).draw(g);
            }

            // draw explosions
            for(int i = 0; i < explosions.size(); i++) {
                explosions.get(i).setMapPosition(
                        (int)tileMap.getx(), (int)tileMap.gety());
                explosions.get(i).draw(g);
            }

            // draw hud
            hud.draw(g);
        }
        else{
            //if paused
            if(eventPause){
                pausedImage.draw(g);
            }

            //if dead
            if(eventDead){
                deadImage.draw(g);
                bgMusic.stop();

            }

            //if finished
            if(eventFinish){
                wonImage.draw(g);
                bgMusic.stop();
            }
        }
    }

    public void keyPressed(int k) {

        //pause events
        if(!eventPause){
            if(k == KeyEvent.VK_ESCAPE && !eventDead && !eventFinish) {
                eventPause = true;
            }
        }
        //unpause events
        else if(eventPause){
            if(k == KeyEvent.VK_ESCAPE && !eventDead && !eventFinish){
                eventPause = false;
            }
            if(k == KeyEvent.VK_M ){
                gsm.setState(GameStateManager.MENUSTATE);
                bgMusic.stop();
            }
        }

        //dead events
        if(eventDead){
            if(k == KeyEvent.VK_R){
                reset();
            }
            if(k == KeyEvent.VK_M){
                gsm.setState(GameStateManager.MENUSTATE);
                bgMusic.stop();
            }
        }

        //finished events
        if(eventFinish){
            if(k == KeyEvent.VK_ENTER){
                gsm.setState(GameStateManager.MENUSTATE);
            }
            if(k == KeyEvent.VK_M){
                gsm.setState(GameStateManager.MENUSTATE);
            }
        }

        //regular commands
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_W) player.setJumping(true);
        if(k == KeyEvent.VK_E) player.setGliding(true);
        if(k == KeyEvent.VK_R) player.setScratching();
        if(k == KeyEvent.VK_F) player.setFiring();
    }

    public void keyReleased(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_W) player.setJumping(false);
        if(k == KeyEvent.VK_E) player.setGliding(false);
    }

    private void reset(){
        player.setDead();
        player.reset();
        player.setPosition(100, 100);
        populateEnemies();
        bgMusic.play();
        eventDead = false;
    }
}