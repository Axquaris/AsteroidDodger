package asteroids.game;
import asteroids.util.*;


public class Entity {
    public XY position;
    public DXY velocity;
    public XY size;
    public int mass;

    public Entity(XY p, XY s) {
        position = p;
        velocity = new DXY(0,0);
        size = s;
        mass = s.x * s.y / 100;
    }

    public XY getPosition() {
        return position;
    }
    
    public int getX() {
        return position.x;
    }
    
    public int getY() {
        return position.y;
    }

    public DXY getVelocity() {
        return velocity;
    }
    
    public double getVelocityX() {
        return velocity.x;
    }
    
    public double getVelocityY() {
        return velocity.y;
    }

    public XY getSize() {
        return size;
    }
    
    public int getWidth() {
        return size.x;
    }
    
    public int getHeight() {
        return size.y;
    }

    public void setVelocity(DXY v) {
        velocity = v;
    }
    
    public int getMass() {
    	return mass;
    }
    
    public boolean move() {
        position.addD(velocity);
        return true;
    }
	
    public double collide(Entity other) {
    	//http://ericleong.me/research/circle-circle/#dynamic-circle-circle-collision
    	int x1 = getX(), y1 = getY(), x2 = other.getX(), y2 = other.getY();
        double vx1 = getVelocityX(), vy1 = getVelocityY(), vx2 = other.getVelocityX(), vy2 = other.getVelocityY();
        int m1 = getMass(), m2 = other.getMass();
    	
		double d = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)); 
		double nx = (x2 - x1) / d; 
		double ny = (y2 - y1) / d; 
		double p = 2 * (vx1 * nx + vy1 * ny - vx2 * nx - vy2 * ny) / (m1 + m2); 
		double new_vx1 = vx1 - p * m1 * nx; 
		double new_vy1 = vy1 - p * m1 * ny; 
		double new_vx2 = vx2 + p * m2 * nx; 
		double new_vy2 = vy2 + p * m2 * ny;
		
		double difx = vx1-new_vx1, dify = vy1 - new_vy1;
		double velocityChange = Math.sqrt(difx*difx + dify*dify);
		
		velocity.x = new_vx1;
        velocity.y = new_vy1;
        other.velocity.x = new_vx2;
        other.velocity.y = new_vy2;
        
        move();
        other.move();
        
        return velocityChange;
    }
}