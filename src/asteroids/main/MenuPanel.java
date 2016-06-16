package asteroids.main;
import asteroids.game.*;
import asteroids.particles.Particle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;


public class MenuPanel extends JPanel implements ActionListener{
    private JLabel title;
    private JButton b1;
    private JButton b2;
    private JButton o;
    private JButton b3;
    

    //background
    private BufferedImage background;
    private BufferedImage asteroidImg;
    private Level level;
    private Timer timer;
    private final int TICK_RATE = 1000/40;

    public MenuPanel(Controller g) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 200, 40));
        setBackground(Color.black);

        try {
            background = ImageIO.read(GamePanel.class.getResource("/resources/background.jpg"));
        } catch (IOException e) {}
        level = new Level(8);
        
        timer = new Timer(TICK_RATE, this);

        title = new JLabel();
        ImageIcon t = new ImageIcon(Ship.class.getResource("/resources/title.png"));
        title.setIcon(t);
        add(title);
        
        JLabel d = new JLabel("CREATED BY: DOMAS B.");
        d.setForeground(Color.LIGHT_GRAY);
        d.setFont(new Font("", Font.BOLD, 16));
        add(d);
        
        b1  = new JButton("Play");
        b1.setToolTipText("Start a new singleplayer run");
        b1.addActionListener(g);
        add(b1);

        b2  = new JButton("How to Play");
        b2.setToolTipText("Show instructions");
        b2.addActionListener(g);
        add(b2);
        
        o  = new JButton("Options");
        o.setToolTipText("Advanced particle effect options");
        o.addActionListener(g);
        add(o);

        b3  = new JButton("Quit");
        b3.setToolTipText("Exit the game");
        b3.addActionListener(g);
        add(b3);
        
        try {
            asteroidImg = ImageIO.read(Asteroid.class.getResource("/resources/smallAsteroid.png"));
        } catch (IOException e) {}
    }
    
    public void startTimer(){
            timer.start();
    }
    
    public void stopTimer(){
            timer.stop();
    }
    
    /**
     * Updates the background
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        level.menuUpdate();
        repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        
        for (Particle p: level.getParticles()) {
        	g.setColor(p.getColor());
        	g.fillOval(p.getX()-p.getRadius(), p.getY()-p.getRadius(), p.getDiameter(), p.getDiameter());
        }
        
        for (Asteroid a: level.getField())
            g.drawImage(asteroidImg, a.getX()-a.getWidth()/2, a.getY()-a.getHeight()/2, null);
    }
}
