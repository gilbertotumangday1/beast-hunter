package Main;

import javax.swing.JFrame;

public class Game {

    public static void main(String[] args) {

        JFrame window = new JFrame("BEAST HUNTER");//name of game
        window.setContentPane(new GamePanel());//the panel that is
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//window can be closed VIA x button
        window.setResizable(false);//you cant stretch the window
        window.pack();//resizing
        window.setVisible(true);//you can see the window

    }

}
