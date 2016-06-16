package asteroids.game;

import asteroids.main.Controller;
import asteroids.particles.SpiralTask;
import asteroids.particles.SprayTask;
import asteroids.particles.TrailTask;
import asteroids.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.Color;

public class GameController extends JPanel implements ActionListener
{
    //===============//
    //Core Properties// These will not change 
    //===============// from game to game.
    protected static final XY FRAME_DIMENSIONS = new XY(500,800);
    protected static final int TIME_INTERVAL = 1000/60;
    protected static final int TITLE_BAR_HEIGHT = 25;
    protected static final int MENU_HEIGHT = 52;
    
    //==============//
    //Game Variables// Declares variables and
    //==============// related constants.
    //Objects
    protected Controller controller;
    protected Level currentLevel;
    protected ScorePanel scorePanel;
    protected GamePanel gamePanel;
    protected JLabel scoreDisplay;
    //Timers
    private Timer timer;
    private Timer slowTimer;
    //Key Binding
    private boolean[] pressed; //W, A, S, D
    private static final int W = 0, A = 1, S = 2, D = 3;
    //Difficulty
    private int mode; //0=easy, 1=medium, 2=hard
    private double scoreMultiplier;

    public GameController(Controller ctrl) {
        //Setup Objects
        controller = ctrl;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setFocusable(true);
        setBackground(Color.black);
        
        currentLevel = new Level();
        
        scorePanel = new ScorePanel(currentLevel);
        add(scorePanel);
        
        gamePanel = new GamePanel(currentLevel);
        add(gamePanel);
        
        scoreDisplay = new JLabel();
        scoreDisplay.setFont(new Font("", Font.BOLD, 20));
        scoreDisplay.setForeground(Color.cyan);
        gamePanel.add(scoreDisplay);
        
        //Setup Timers
        timer = new Timer(TIME_INTERVAL, this);
        timer.stop();
        slowTimer = new Timer((int)( TIME_INTERVAL*2 ), this);
        slowTimer.stop();
        
        //Setup Key Binding
        pressed = new boolean[4];
        InputMap im = getInputMap(gamePanel.WHEN_FOCUSED);
        ActionMap am = getActionMap();
        bindKeys(im, am);
        
        //Difficulty
        mode = -1;
        scoreMultiplier = 1;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer || e.getSource() == slowTimer) {
            if (pressed[W] == pressed[S])
            	currentLevel.ship.setAccelY(0);
            else if (pressed[W])
            	currentLevel.ship.setAccelY(-1);
            else //if (pressed[S])
            	currentLevel.ship.setAccelY(1);

            if (pressed[A] == pressed[D])
                    currentLevel.ship.setAccelX(0);
            else if (pressed[A])
            	currentLevel.ship.setAccelX(-1);
            else //if (pressed[D])
            	currentLevel.ship.setAccelX(1);
            
            if (currentLevel.update()) {
            	end();
            }
            
            scorePanel.repaint();
            scoreDisplay.setText(getScore()+" Points");
            
            gamePanel.repaint();
        }
    }

    public XY getFrameDimensions() {
        return FRAME_DIMENSIONS;
    }

    public int getScore() {
        return (int)(currentLevel.score*scoreMultiplier);
    }
    
    public void start() {
    	start(mode);
    }
    
    public void start(int m) {
    	mode = m;
        currentLevel = new Level();
        
        if (mode == 1) {//Easy
	        //AsteriodSpawning
	        currentLevel.STARTING_RATE = 22;
	        currentLevel.MAX_RATE = 14;
	        currentLevel.ASTEROID_Y_SPEED_MIN = 2;
	        currentLevel.ASTEROID_Y_SPEED_MAX = 4;
	        currentLevel.ASTEROID_X_SPEED_MIN = -1;
	        currentLevel.ASTEROID_X_SPEED_MAX = 1;
	        //Ship
	        currentLevel.THRUST_POWER = .2;
	        currentLevel.HP_REGEN = .04;
	        //Score
	        scoreMultiplier = .25;
        }
        else if (mode == 2) {//Medium
	        currentLevel = new Level();
	        
	        
	        //Score
	        scoreMultiplier = 1;
        }
        else if (mode == 3) {//Hard
	        //AsteriodSpawning
	        currentLevel.STARTING_RATE = 18;
	        currentLevel.RATE_MULTIPLIER = .985;
	        currentLevel.MAX_RATE = 8;
	        currentLevel.ASTEROID_Y_SPEED_MIN = 6;
	        currentLevel.ASTEROID_Y_SPEED_MAX = 8;
	        currentLevel.ASTEROID_X_SPEED_MIN = -3;
	        currentLevel.ASTEROID_X_SPEED_MAX = 3;
	        //Ship
	        currentLevel.MAX_HP = 50;
	        //Score
	        scoreMultiplier = 2;
        }
        else if (mode == 4) {//EXTREME
        	//AsteriodSpawning
	        currentLevel.STARTING_RATE = 14;
	        currentLevel.RATE_MULTIPLIER = .98;
	        currentLevel.MAX_RATE = 6;
	        currentLevel.ASTEROID_Y_SPEED_MIN = 8;
	        currentLevel.ASTEROID_Y_SPEED_MAX = 10;
	        currentLevel.ASTEROID_X_SPEED_MIN = -4;
	        currentLevel.ASTEROID_X_SPEED_MAX = 4;
	        //Ship
	        currentLevel.THRUST_POWER = .4;
	        currentLevel.MAX_HP = 50;
	        currentLevel.SHIP_MASS_MULTIPLIER = 1;
	        //Score
	        scoreMultiplier = 4;
        }
        else if (mode == 5) {//God mode
        	//AsteriodSpawning
	        currentLevel.STARTING_RATE = 14;
	        currentLevel.RATE_MULTIPLIER = .98;
	        currentLevel.MAX_RATE = 4;
	        //Ship
	        currentLevel.THRUST_POWER = .4;
	        currentLevel.MAX_HP = 500;
	        currentLevel.HP_REGEN = 2;
	        currentLevel.SHIP_MASS_MULTIPLIER = .4;
	        //Score
	        scoreMultiplier = 0;
        }
        currentLevel.initObjects();
        
        //Ship trail
        if (mode < 4 ) {
        	//Add ship trail
            TrailTask shipTrail = new TrailTask();
            shipTrail.setColor(Color.CYAN);
            shipTrail.setPosition(currentLevel.ship.getPosition());
            shipTrail.setVelocity(new DXY(0, 4));
            shipTrail.setOffset(new XY(0, currentLevel.ship.getHeight()/3));
            shipTrail.setRadius(8);
            shipTrail.setShrinkrement(.05);
            currentLevel.particleSpawner.add(shipTrail);
        }
        //Spray effects
        if (mode == 4) {
        	SprayTask s1 = new SprayTask();
        	s1.setDuration(-1);
        	s1.setRadius(8);
        	s1.setShrinkrement(.4);
        	s1.setSprayMult(new XY(5, 4));
        	s1.setSprayNum(8);
        	s1.setPosition(currentLevel.ship.getPosition());
        	s1.setColor(Color.RED);
        	currentLevel.particleSpawner.add(s1);
        	
        	SprayTask s2 = new SprayTask();
        	s2.setDuration(-1);
        	s2.setRadius(10);
        	s2.setShrinkrement(.4);
        	s2.setSprayMult(new XY(5, 4));
        	s2.setSprayNum(10);
        	s2.setPosition(currentLevel.ship.getPosition());
        	s2.setColor(Color.BLACK);
        	currentLevel.particleSpawner.add(s2);
        }
        else if (mode == 5) {
        	SpiralTask s1 = new SpiralTask();
        	s1.setDuration(-1);
        	s1.setSpiralSpeed(1);
        	s1.setRadius(6);
        	s1.setShrinkrement(.1);
        	s1.setSprayMult(new XY(5, 5));
        	s1.setSprayNum(10);
        	s1.setPosition(currentLevel.ship.getPosition());
        	s1.setColor(Color.YELLOW);
        	currentLevel.particleSpawner.add(s1);
        	
        	SpiralTask s2 = new SpiralTask();
        	s2.setDuration(-1);
        	s2.setSpiralSpeed(2);
        	s2.setRadius(4);
        	s2.setShrinkrement(.1);
        	s2.setSprayMult(new XY(10, 10));
        	s2.setSprayNum(7);
        	s2.setPosition(currentLevel.ship.getPosition());
        	s2.setColor(Color.CYAN);
        	currentLevel.particleSpawner.add(s2);
        }
        
        pressed = new boolean[4];
    	gamePanel.changeLevel(currentLevel);
        scorePanel.changeLevel(currentLevel);
        
        requestFocusInWindow();
        timer.start();
    }

    protected void end() {
        timer.stop();
        slowTimer.stop();
        controller.changeState("gameover");
    }

    public void pause() {
        if(timer.isRunning())
            timer.stop();
        else {
            requestFocus();
            timer.start();
        }
    }
    
    protected void bindKeys(InputMap im, ActionMap am) {
        //key W
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "WP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "WP");
        am.put("WP", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
                pressed[W] = true;
        }
        });
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "WU");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "WU");
        am.put("WU", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
                pressed[W] = false;
        }
        });

        //key A
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "AP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "AP");
        am.put("AP", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
                pressed[A] = true;
        }
        });
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "AU");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "AU");
        am.put("AU", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
                pressed[A] = false;
        }
        });

        //key S
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "SP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "SP");
        am.put("SP", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
                pressed[S] = true;
        }
        });
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "SU");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "SU");
        am.put("SU", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
                pressed[S] = false;
        }
        });

        //key D
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "DP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "DP");
        am.put("DP", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
                pressed[D] = true;
        }
        });
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "DU");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "DU");
        am.put("DU", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
                pressed[D] = false;
        }
        });
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, false), "P");
        am.put("P", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (timer.isRunning())    
            	timer.stop();
            else
            	timer.start();
        }
        });
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "ESC");
        am.put("ESC", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            end();
        }
        });
    }
}
