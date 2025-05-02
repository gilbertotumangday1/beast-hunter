package GameState;

import Audio.AudioPlayer;
import TileMap.Background;
import TileMap.Image;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {

    private Background bg;
    private Image help;
    private Image splashScreen;
    private Image introScreen;
    private int state;
    private boolean helpSelect;
    private AudioPlayer audioPlayer;

    private int currentChoice = 0;
    private String[] options = {//buttons
            "Level 1",
            "Level 2",
            "Level 3",
            "Level 4",
            "Help",
            "Quit"
    };

    //special stuff for the menu
    private Color titleColor;
    private Font titleFont;

    private Font font;

    public MenuState(GameStateManager gsm) {

        this.gsm = gsm;
        init();
    }

    public void init() {
        try {

            bg = new Background("/Backgrounds/grassbg1.gif", 1);//load the background

            titleColor = new Color(128, 0, 0);//set the colour (red)
            titleFont = new Font(//set the font
                    "Century Gothic",
                    Font.PLAIN, 28);

            font = new Font("Arial", Font.PLAIN, 12);// set the font of the options

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        //images
        help = new Image("/Events/help.png", 0.1);
        splashScreen = new Image("/Events/storylines/splashscreen.png", 0.1);
        introScreen = new Image("/Events/storylines/intro.png", 0.1);
        helpSelect = false;

        //states
        state = 1;

        //music
        audioPlayer = new AudioPlayer("/Music/menu.mp3");
        audioPlayer.play();
    }

    public void update() {}

    public void draw(Graphics2D g){
        if(state == 3){
            // draw bg
            bg.draw(g);

            // draw title
            g.setColor(titleColor);
            g.setFont(titleFont);
            g.drawString("Beast Hunter", 80, 70);

            // draw menu options
            g.setFont(font);
            for(int i = 0; i < options.length; i++) {
                if(i == currentChoice) {
                    g.setColor(Color.BLACK);
                }
                else {
                    g.setColor(Color.WHITE);
                }
                g.drawString(options[i], 145, 120 + i * 15);
            }
        }
        else if(state == 2){
            introScreen.draw(g);
        }
        else if(state == 1){
            splashScreen.draw(g);
        }
        else if(state == 4){
            help.draw(g);
        }
    }

    public void keyPressed(int k) {//detects keys pressed
        if(state ==3){
            if(k == KeyEvent.VK_ENTER){
                select();
            }
            if(k == KeyEvent.VK_UP) {
                currentChoice--;
                if(currentChoice == -1) {
                    currentChoice = options.length - 1;
                }
            }
            if(k == KeyEvent.VK_DOWN) {
                currentChoice++;
                if(currentChoice == options.length) {
                    currentChoice = 0;
                }
            }
            if(helpSelect && k == KeyEvent.VK_ENTER){
                helpSelect = false;
            }
        }
        else if(state == 2){
            if(k == KeyEvent.VK_ENTER){
                state++;
            }
        }
        else if(state == 1){
            if(k == KeyEvent.VK_ENTER){
                state++;
            }
        }
        else if(state == 4){
            if(k == KeyEvent.VK_ENTER){
                state = 3;
            }
        }

    }

    public void select() {//allows user to select menu options
        if(currentChoice == 0) {//if its the first thing
            gsm.setState(GameStateManager.LEVEL1STATE);//set the state
            audioPlayer.stop();
        }
        if(currentChoice == 1) {//second thing
            gsm.setState(GameStateManager.LEVEL2STATE);
            audioPlayer.stop();
        }
        if(currentChoice == 2){
            gsm.setState(GameStateManager.LEVEL3STATE);
            audioPlayer.stop();
        }
        if(currentChoice == 3) {
            gsm.setState(GameStateManager.LEVEL4STATE);
            audioPlayer.stop();
        }
        if(currentChoice == 4) {
            state = 4;
        }
        if(currentChoice == 5) {
            System.exit(0);
        }

    }
    public void keyReleased(int k) {}

}
