package asteroids.particles;

import java.awt.Color;
import java.util.ArrayList;

import asteroids.util.DXY;
import asteroids.util.XY;

public abstract class ParticleTask {
	protected ArrayList<Particle> particles;
	protected int duration; //Duration in frames, -1 is infinite
	protected int age; //Number of times update called
	
	//Generation Variables
	protected XY position;
	protected DXY velocity;
	protected double radius;
	protected Color color;
	
	protected XY offset; //Modifier to position
	protected double shrinkrement; //Increment that particle radius will shrink by
	
	public ParticleTask() {
		duration = -1;
		age = 0;
		
		//Generation Variables
		position = new XY(0,0);
		velocity = new DXY(0,0);
		radius = 1;
		color = Color.CYAN;
		
		offset = new XY(0, 0);
		shrinkrement = 0;
	}

	protected abstract boolean update();
	
	//Returns true if task complete
	protected boolean particleTaskUpdate() {
		age++;
		if (age == duration)
			return true;
		return false;
	}
	
	protected void setParticles(ArrayList<Particle> p) { particles = p; }
	
	public void setDuration(int d) { duration = d; }
	public void setPosition(XY p) { position = p; }
	public void setVelocity(DXY v) { velocity = v; }
	public void setRadius(double r) { radius = r; }
	public void setColor(Color c) { color = c; }
	
	public void setOffset(XY o) { offset = o; }
	public void setShrinkrement(double s) { shrinkrement = s; }
}
