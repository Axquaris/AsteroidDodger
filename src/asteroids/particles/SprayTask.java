package asteroids.particles;

import asteroids.util.*;

public class SprayTask extends ParticleTask{
	//Generation Variables
	protected XY sprayMult;
	protected int sprayNum;
	
	public SprayTask() {
		super();
		
		//Generation Variables
		sprayMult = new XY(1, 1);
		sprayNum = 8;
	}
	
	@Override
	protected boolean update() {
		double m = 360.0/sprayNum;
		int angle;
		for (int i = 1; i <= sprayNum; i++) {
			angle = (int)(m*i + age*age);
			angle %= 360;
			particles.add(new ShrinkingParticle(DXY.add(position, offset), 
					DXY.add(new DXY(Trig.cos(angle)*sprayMult.x, Trig.sin(angle)*sprayMult.y), velocity), 
					radius, color, shrinkrement));
		}
		return particleTaskUpdate();
	}
	
	public void setSprayMult(XY sm) { sprayMult = sm; }
	public void setSprayNum(int sn) { sprayNum = sn; }
}
