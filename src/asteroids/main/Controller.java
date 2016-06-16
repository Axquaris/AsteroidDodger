package asteroids.main;
import asteroids.game.*;
import asteroids.util.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;



public class Controller extends JFrame implements ActionListener{
    public String game_state;
    
    //Gui vars
    public static final XY FRAME_DIMENSIONS = new XY(500,800);
    Container contentPane;
    private MenuPanel menuPanel;
    private GameoverPanel gameoverPanel;
    private GameController gameController;
    
    public static int particleCap = 3000;
    public static double particleQuality = 1;

    Controller() {
    	playMusic();
    	game_state = "menu";

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_DIMENSIONS.x, FRAME_DIMENSIONS.y);
        setTitle("Asteroid Dodger");
        contentPane = getContentPane();

        contentPane.setLayout(new CardLayout());

        menuPanel = new MenuPanel(this);
        contentPane.add(menuPanel, "menu");
        menuPanel.startTimer();
        
        gameoverPanel = new GameoverPanel(this);
        contentPane.add(gameoverPanel, "gameover");

        gameController = new GameController(this);
        contentPane.add(gameController, "game");
    }
    
    /**
     * Changes the current game-state
     * @param newstate the new state
     */
    public void changeState(String newstate){
        if(game_state.equals("menu"))
                menuPanel.stopTimer();

        game_state = newstate;
        if(newstate.equals("menu")) {
                menuPanel.requestFocusInWindow();
                menuPanel.startTimer();
                setSize(FRAME_DIMENSIONS.x, FRAME_DIMENSIONS.y);
        }
        else if(newstate.equals("game"))
                setSize(FRAME_DIMENSIONS.x+40, FRAME_DIMENSIONS.y);
        else if(newstate.equals("gameover"))
                gameoverPanel.setScore(gameController.getScore());

        CardLayout cl = (CardLayout)(contentPane.getLayout());
        cl.show(contentPane, game_state);
    }
    
    /**
     * Listens for button presses and acts accordingly
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (game_state.equals("menu")){
            if(e.getActionCommand().equals("Play")){
                changeState("game");
                //Get User Mode input
                int mode = -1;
                do {
                	try {
	                	mode = Integer.parseInt(JOptionPane.showInputDialog(this, "Pick a Mode:\n"
	                				+ "1) Easy - For noobs only\n"
	                				+ "2) Normal - Fun for the whole family\n"
	                				+ "3) Hard - Prepare yourself...\n"
	                				+ "4) EXTREME - You are a true psycho if you play this\n"
	                				+ "5) God Mode - Glorious expiriments and game breaking take place here\n", 
	                			"Enter your choice #"));
                	} catch (Exception ex) {
                		mode = -1;
                	}
                } while (mode < 1 || mode > 5);
                gameController.start(mode);
            }
            else if(e.getActionCommand().equals("How to Play"))
                JOptionPane.showMessageDialog(this, "Use the WASD or arrow keys to fly your ship.\n"
                        + "Avoid the asteroids and stay alive as long as you can,\n"
                        + "the longer you survive the more points you will earn!\n"
                        + "P pauses the game\n"
                        + "ESC ends the game",
                    "How to play", JOptionPane.INFORMATION_MESSAGE);
            else if(e.getActionCommand().equals("Options")) {
            	int pc = -1;
                do {
                	try {
	                	pc = Integer.parseInt(JOptionPane.showInputDialog(this, "Type in a particle cap (3000 is the usual)", 
	                			"Enter an Integer"));
                	} catch (Exception ex) {
                		pc = -1;
                	}
                } while (pc <= 0);
                particleCap = pc;
                
                double pq = -1;
                do {
                	try {
	                	pq = Double.parseDouble(JOptionPane.showInputDialog(this, "Type in a particle quality (1.0 is the usual)", 
	                			"Enter a Decimal"));
                	} catch (Exception ex) {
                		pq = -1;
                	}
                } while (pq <= 0);
                particleQuality = pq;
            }
            else if(e.getActionCommand().equals("Quit"))
                System.exit(0);
        }

        else if (game_state.equals("gameover")){
            if(e.getActionCommand().equals("Play Again")){
                changeState("game");
                gameController.start();
            }
            else if(e.getActionCommand().equals("Back to Menu"))
                changeState("menu");
        }
    }
    
    /**
     * Initializes the game
     * @param args 
     */
    public static void main(String[] args){
    	Trig.init();
    	
        Controller g = new Controller();
        g.setVisible(true);
    }
    
    public void playMusic() {
//	  new Thread(new Runnable() {
//	    public void run() {
	      try {
	        Clip clip = AudioSystem.getClip();
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
	        		getClass().getResource("/resources/music.wav"));
	        clip.open(inputStream);
	        clip.loop(9999999);
	      } catch (Exception e) {
	        System.err.println(e.getMessage());
	      }
//	    }
//	  }).start();
	}
}
