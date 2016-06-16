package asteroids.particles;

import java.awt.Color;

import asteroids.util.DXY;

public class ShrinkingParticle extends Particle{
	private double shrinkrement;
	

	public ShrinkingParticle(DXY p, DXY v, double r, Color c, double s) {
		super(p, v, r, c);
		shrinkrement = s;
	}
	
	public boolean update() {
		radius -= shrinkrement;
		return super.update() || radius <= 0;
	}
}
