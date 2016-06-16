package asteroids.particles;

import java.util.ArrayList;

public class ParticleSpawner {
	private ArrayList<Particle> particles;
	private ArrayList<ParticleTask> tasks;
	
	public ParticleSpawner() {
		particles = new ArrayList<Particle>();
		tasks = new ArrayList<ParticleTask>();
	}
	
	public void updateTasks() {
		for (int i = tasks.size()-1; i >= 0; i--) {
			if (tasks.get(i).update())
				tasks.remove(i);
			
		}
	}
	
	public void updateParticles() {
		for (int i = particles.size()-1; i >= 0 ; i--) {
        	if (particles.get(i).update()) {
        		particles.remove(i);
        	}
        }
	}
	
	public void add(ParticleTask pt) {
//		if (tasks.size() < 10) {
			pt.setParticles(particles);
			tasks.add(pt);
//		}
	}
	
	public ArrayList<Particle> getParticles() {
		return particles;
	}
}
