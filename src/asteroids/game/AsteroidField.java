package asteroids.game;
import asteroids.particles.CollideTask;
import asteroids.util.*;

import java.util.ArrayList;
import java.util.Random;


public class AsteroidField {

    private Level level;
    private ArrayList<Asteroid> field;

    public AsteroidField(Level l) {
    	level = l;
    	field = new ArrayList<Asteroid>();
    }

    public ArrayList<Asteroid> getField() {
    	return field;
    }

    public void clear() {
    	field = new ArrayList<Asteroid>();
    }

    public int addAsteroid() {
    	Random r = new Random();
    	int x, tries = 0;
    	double vy, vx;
    	Asteroid a;
    	do {
	    	x = r.nextInt(GameController.FRAME_DIMENSIONS.x);
	        vy = r.nextDouble()*(level.ASTEROID_Y_SPEED_MAX - level.ASTEROID_Y_SPEED_MIN) + level.ASTEROID_Y_SPEED_MIN;
	        vx = r.nextDouble()*(level.ASTEROID_X_SPEED_MAX - level.ASTEROID_X_SPEED_MIN) + level.ASTEROID_X_SPEED_MIN;
	    	a = new Asteroid(new XY(x, -level.ASTEROID_SIZE.y/2), level.ASTEROID_SIZE);
	    	a.setVelocity(new DXY(vx, vy));
	    	tries++;
    	} while(badSpawn(a) && tries < 10);
    	field.add(a);
    	return x;
    }
    
    private boolean badSpawn(Asteroid a) {
    	int xdif, ydif;
    	Asteroid b;
    	for (int y = 0; y < field.size(); y++) {
    		if (a.getY() <= 100) {
				b = field.get(y);
				xdif = a.getX()-b.getX();
	            ydif = a.getY()-b.getY();
	            if (xdif*xdif + ydif*ydif <= 60*60) {
	            	return true;
	            }
    		}
    	}
    	return false;
    }

    public void moveField() {
    	//System.out.println(field.size()); //Debug
    	
    	int xdif, ydif;
    	Asteroid a, b;
    	for (int x = 0; x < field.size()-1; x++) {
    		for (int y = x+1; y < field.size(); y++) {
    			a = field.get(x);
    			b = field.get(y);
    			xdif = a.getX()-b.getX();
                ydif = a.getY()-b.getY();
                if (xdif*xdif + ydif*ydif <= 2500) {
                	a.collide(b);
                	CollideTask e = new CollideTask();
                    e.setPosition(new XY((a.getX()+b.getX())/2, (a.getY()+b.getY())/2));
                    e.setVelocity(new DXY((a.getVelocityX()+b.getVelocityX())/2, (a.getVelocityY()+b.getVelocityY())/2));
                    level.particleSpawner.add(e);
                }
        	}
    	}
    	for (int x = 0; x < field.size(); x++) {
            if(field.get(x).move(field)) {
                field.remove(x);
                x--;
            }
    	}
    }
}