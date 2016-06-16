package asteroids.game;

import asteroids.particles.Particle;
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel
{
    private Level currentLevel;
    
    private BufferedImage backgroundImg;
    private BufferedImage shipImg;
    private BufferedImage asteroidImg;

    /**
     *	Create the panel to display the ship in.
     *	@param s the ship to display in the window
     *	@param t the timer for the panel
     */
    public GamePanel(Level level)
    {
        setBackground(Color.black);
        currentLevel = level;
        try {
            backgroundImg = ImageIO.read(GamePanel.class.getResource("/resources/background.jpg"));
            shipImg = ImageIO.read(Ship.class.getResource("/resources/smallShip.png"));
            asteroidImg = ImageIO.read(Asteroid.class.getResource("/resources/smallAsteroid.png"));
        } catch (IOException e) {}
    }

    /**
     *	Paints the ship in the window at the correct location
     *	then updates the ship to the next location to be displayed
     *	and sets the direction it is going
     *	@param g the Graphics context in which to paint
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(backgroundImg, 0, 0, null);
        
        for (Particle p: currentLevel.getParticles()) {
        	g.setColor(p.getColor());
        	g.fillOval(p.getX()-p.getRadius(), p.getY()-p.getRadius(), p.getDiameter(), p.getDiameter());
        }
        g.drawImage(shipImg, currentLevel.ship.getX()-currentLevel.ship.getWidth()/2, currentLevel.ship.getY()-currentLevel.ship.getHeight()/2, null);
        for (Asteroid a: currentLevel.getField())
            g.drawImage(asteroidImg, a.getX()-a.getWidth()/2, a.getY()-a.getHeight()/2, null);
    }
    
    public void changeLevel(Level l) {
    	currentLevel = l;
    }
}
