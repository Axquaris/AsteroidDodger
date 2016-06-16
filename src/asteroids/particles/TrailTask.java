package asteroids.particles;

import asteroids.util.DXY;

public class TrailTask extends ParticleTask {
	//Generation Variables
	
	public TrailTask() {
		super();
		
		//Generation Variables
	}
	
	@Override
	protected boolean update() {
		particles.add(new ShrinkingParticle(DXY.add(position, offset), velocity, radius, color, shrinkrement));
		return super.particleTaskUpdate();
	}
}
