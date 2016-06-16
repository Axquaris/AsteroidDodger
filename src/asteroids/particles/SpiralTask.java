package asteroids.particles;

import asteroids.util.DXY;
import asteroids.util.Trig;

public class SpiralTask extends SprayTask{
	//Generation Variables
	protected double spiralSpeed;

	public SpiralTask() {
		super();

		//Generation Variables
		spiralSpeed = 1;
	}

	@Override
	protected boolean update() {
		double m = 360.0/sprayNum;
		int angle;
		for (int i = 1; i <= sprayNum; i++) {
			angle = (int)(m*i + age*spiralSpeed);
			angle %= 360;
			particles.add(new ShrinkingParticle(DXY.add(position, offset), 
					DXY.add(new DXY(Trig.cos(angle)*sprayMult.x, Trig.sin(angle)*sprayMult.y), velocity), 
					radius, color, shrinkrement));
		}
		return particleTaskUpdate();
	}

	public void setSpiralSpeed(double ss) { spiralSpeed = ss; }
}
