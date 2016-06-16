package asteroids.game;

import java.util.ArrayList;

import asteroids.particles.Particle;
import asteroids.particles.ParticleSpawner;
import asteroids.util.XY;

public class Level {
	
	//===============//
    //Game Properties// These change based
    //===============// on difficulty.
    //Asteroid
    protected XY ASTEROID_SIZE = new XY(50,50);
    //Asteroid Spawning
    protected int STARTING_RATE = 20;
    protected double RATE_MULTIPLIER = .99;
    protected int MAX_RATE = 9;
    protected double ASTEROID_Y_SPEED_MIN = 3;
    protected double ASTEROID_Y_SPEED_MAX = 6;
    protected double ASTEROID_X_SPEED_MIN = -2;
    protected double ASTEROID_X_SPEED_MAX = 2;
    //Asteroid Spawning
    protected double spawnRate;
    protected int spawnCounter;
    //Ship
    protected XY SHIP_POSITION = new XY(250,400); //Starting position
    protected XY SHIP_DIMENSIONS = new XY(50,50);
    protected double THRUST_POWER = .3;
    protected int MAX_HP = 100;
    protected double HP_REGEN = .02;
    protected double SHIP_MASS_MULTIPLIER = 4.0/5;
    
    
    //Objects
    protected ParticleSpawner particleSpawner;
    protected AsteroidField asteroidField;
    protected Ship ship;
    protected int score;
    
    public Level() {
    	initObjects();
    }
    
    public void initObjects() {
    	//Setup Asteroid Spawning
        spawnRate = STARTING_RATE;
        spawnCounter = 0;
        
        //Objects
        particleSpawner = new ParticleSpawner();
        asteroidField = new AsteroidField(this);
        ship = new Ship(this);
        score = 0;
    }
    
    public Level(int a) {
    	//Setup Asteroid Spawning
    	MAX_RATE = a;
        spawnCounter = 0;
        
        //Objects
        particleSpawner = new ParticleSpawner();
        asteroidField = new AsteroidField(this);
    }
    
    public boolean update() {
    	particleSpawner.updateTasks();
    	particleSpawner.updateParticles();
    	
    	asteroidField.moveField();
    	
        if (spawnCounter >= spawnRate) {
        	asteroidField.addAsteroid();
            spawnCounter = 0;
            if (spawnRate > MAX_RATE)
                spawnRate *= RATE_MULTIPLIER;
            
            score += 5;
        }
        spawnCounter++;
        
        if (ship.update()<=0) {
            return true; // gameover
        }
        
        score++;
        return false;
    }
    
    public void menuUpdate() {
    	particleSpawner.updateTasks();
    	particleSpawner.updateParticles();
    	
    	asteroidField.moveField();
    	
        if (spawnCounter >= MAX_RATE) {
        	asteroidField.addAsteroid();
            spawnCounter = 0;
        }
        spawnCounter++;
    }
    
    public ArrayList<Asteroid> getField() {
    	return asteroidField.getField();
    }
    
    public ArrayList<Particle> getParticles() {
    	return particleSpawner.getParticles();
    }
}
