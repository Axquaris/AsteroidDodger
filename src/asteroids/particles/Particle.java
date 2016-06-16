package asteroids.particles;
import java.awt.Color;

import asteroids.main.Controller;
import asteroids.util.*;

public class Particle {
	protected DXY position;
    protected DXY velocity;
    protected double radius;
    protected Color color;

    public Particle(DXY p, DXY v, double r, Color c) {
	    position = p;
	    velocity = v;
	    radius = r;
    	color = c;
    }
    
    //Returns true if particle should be deleted
    public boolean update() {
        position.addD(velocity);
        
        return outOfBounds();
    }
    
    private boolean outOfBounds() {
    	XY dimensions = Controller.FRAME_DIMENSIONS;
    	return position.x <= 0 || position.y <= 0
    		|| position.x >= dimensions.x || position.x >= dimensions.y;
    }
    
    public DXY getPosition() { return position; }
    public int getX() { return (int) position.x; }
    public int getY() { return (int) position.y; }
    public int getRadius() { return (int)radius; }
    public int getDiameter() { return (int)(radius*2); }
    public Color getColor() { return color; }
}