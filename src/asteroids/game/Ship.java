package asteroids.game;
import asteroids.particles.ExplosionTask;
import asteroids.util.*;


public class Ship extends Entity {
	private Level currentLevel;
	
    private double thrustPower;
    public XY accel;
    
    private double hpCapacity;
    private double regen;
    private double hp;
    
    
    public Ship(Level l) {
        super(l.SHIP_POSITION, l.SHIP_DIMENSIONS);
        currentLevel = l;
        
        thrustPower = currentLevel.THRUST_POWER;
        accel = new XY(0,0);
        
        hpCapacity = currentLevel.MAX_HP;
        regen = currentLevel.HP_REGEN;
        hp = hpCapacity;
        
        mass *= currentLevel.SHIP_MASS_MULTIPLIER;
    }

    public double update() {
    	accelerate();
        moveShip();
        if (hp < hpCapacity)
        	hp += regen;
    	return hp;
    }
    
    private void accelerate() {
        velocity.x += accel.x*thrustPower;
        velocity.y += accel.y*thrustPower;
    }

    private void moveShip() {
        //Bottom and top Limits
        if (position.y + velocity.y - size.y/2 <= 0 && velocity.y < 0) {
            position.y = size.y/2;
            velocity.y *= -.5;
        }
        else if (position.y + velocity.y + size.y - 7 >= GameController.FRAME_DIMENSIONS.y && velocity.y > 0) {
            position.y = GameController.FRAME_DIMENSIONS.y - size.y + 7;
            velocity.y *= -.5;
        }
        
        super.move();
        
        int xdif, ydif;
        for(Asteroid x: currentLevel.getField()) {
            xdif = getX()-x.getX();
            ydif = getY()-x.getY();
            if (xdif*xdif + ydif*ydif <= 45*45) {
                hp -= collide(x);
                ExplosionTask e = new ExplosionTask();
                e.setPosition(new XY((getX()+x.getX())/2, (getY()+x.getY())/2));
                e.setVelocity(new DXY((getVelocityX()+x.getVelocityX())/2, (getVelocityY()+x.getVelocityY())/2));
                currentLevel.particleSpawner.add(e);
            }
        }
        
        //Wrap Around
        if (position.x < 0)
            position.x += GameController.FRAME_DIMENSIONS.x;
        else if (position.x > GameController.FRAME_DIMENSIONS.x)
            position.x -= GameController.FRAME_DIMENSIONS.x;
    }
    
    public void reset() {
        position = new XY(250,400);
        velocity = new DXY(0,0);
        accel = new XY(0,0);
        hp = 100;
    }
    
    public void changeLevel(Level l) {
    	currentLevel = l;
    }
    
    public double getHp() {
        return hp;
    }
    
    public double getHpPercent() {
        return hp/currentLevel.MAX_HP;
    }

    public void setAccelX(int x) {
        accel.x = x;
    }

    public void setAccelY(int y) {
        accel.y = y;
    }
}