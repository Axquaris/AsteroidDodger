package asteroids.game;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;


public class ScorePanel extends JPanel{
	
	private Level currentLevel;
	
    public ScorePanel(Level l) {
    	currentLevel = l;
    	
    	setLayout(new FlowLayout(1, 20, 30));
		Dimension mainDimenson = new Dimension(40, 800);
		setPreferredSize(mainDimenson);
		setMaximumSize(mainDimenson);
		setBackground(Color.red);
		
		JLabel hpLabel = new JLabel("HP");
                hpLabel.setFont(new Font("", Font.BOLD, 16));
		hpLabel.setForeground(Color.black);
		add(hpLabel);
    }
    
    public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.CYAN);
		g.fillRect(0,Math.round(Math.round(800-currentLevel.ship.getHpPercent()*800)),50,800);
	}
    
    public void changeLevel(Level l) {
    	currentLevel = l;
    }
}