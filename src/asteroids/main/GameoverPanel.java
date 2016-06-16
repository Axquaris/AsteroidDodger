package asteroids.main;
import asteroids.game.Ship;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameoverPanel extends JPanel{
    private JLabel gOver;
    private JLabel d;
    private JButton pA;
    private JButton bTM;

    private int score;
    private JLabel s;

    public GameoverPanel(Controller g) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 200, 40));
        setBackground(Color.black);

        gOver = new JLabel();
        ImageIcon go = new ImageIcon(Ship.class.getResource("/resources/gameover.png"));
        gOver.setIcon(go);
        add(gOver);

        d = new JLabel("You Died!");
        d.setForeground(Color.LIGHT_GRAY);
        d.setFont(new Font("", Font.BOLD, 20));
        add(d);

        score = 0;
        s = new JLabel("You scored "+score+" points");
        s.setForeground(Color.LIGHT_GRAY);
        s.setFont(new Font("", Font.BOLD, 20));
        add(s);

        pA  = new JButton("Play Again");
        pA.setToolTipText("Start a new run of the same difficulty");
        pA.addActionListener(g);
        add(pA);

        bTM  = new JButton("Back to Menu");
        bTM.setToolTipText("Return to main menu");
        bTM.addActionListener(g);
        add(bTM);
    }

    public void setScore(int iscore){
        score = iscore;
        s.setText("You scored "+score+" points");
    }
}
