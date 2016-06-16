package asteroids.game;
import java.util.ArrayList;

import asteroids.util.*;

public class Asteroid extends Entity{

    public Asteroid(XY p, XY s) {
        super(p,s);
        position = p;
        size = s;
        velocity = new DXY(0,0);
    }
    
    /**
     * Moves the asteroid
     * @return returns whether the asteroid is still on screen
     */
    public boolean move(ArrayList<Asteroid> field) {
        if (position.y - size.y/2 > GameController.FRAME_DIMENSIONS.y
        		|| position.x - size.x/2 > GameController.FRAME_DIMENSIONS.x
        		|| position.x + size.x/2 < 0
        		|| (position.y + size.y/2 < 0 && velocity.y < 0)) {
            
            return true;
        }
        position.addD(velocity);
        return false;
    }
}