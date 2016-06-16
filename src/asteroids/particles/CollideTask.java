package asteroids.particles;

import java.awt.Color;

import asteroids.main.Controller;
import asteroids.util.DXY;
import asteroids.util.Trig;
import asteroids.util.XY;

public class CollideTask extends ParticleTask {
	//Generation Variables
	protected double spiralSpeed;

	public CollideTask() {
		super();

		//Generation Variables
		spiralSpeed = 1;
		duration = 8;
	}

	protected boolean update() {
		spawn((int)(30*Controller.particleQuality), new XY(4, 4), Color.DARK_GRAY, 6/Controller.particleQuality, 0.1/Controller.particleQuality);
		spawn((int)(10*Controller.particleQuality), new XY(4, 4), Color.LIGHT_GRAY, 6/Controller.particleQuality, 0.1/Controller.particleQuality);
		position.addD(velocity);
		return particleTaskUpdate();
	}
	
	protected void spawn(int sprayNum, XY sprayMult, Color color, double radius, double shrinkrement) {
		double m = 360.0/sprayNum;
		int angle;
		for (int i = 1; i <= sprayNum; i++) {
			angle = (int)(m*i + Math.random()*360);
			angle %= 360;
			if (particles.size() < Controller.particleCap)
				particles.add(new ShrinkingParticle(DXY.add(position, offset), 
					DXY.add(new DXY(Trig.cos(angle)*sprayMult.x, Trig.sin(angle)*sprayMult.y), velocity), 
					radius, color, shrinkrement));
		}
	}
}