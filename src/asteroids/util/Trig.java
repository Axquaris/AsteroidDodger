package asteroids.util;

public class Trig {
	private static double[] sin;
	private static double[] cos;
	private static double[] tan;
	
	//Populate cache
	public static void init() {
		double pi = 3.1415926;
		sin = new double[361];
		cos = new double[361];
		tan = new double[361];
		
		for (int i = 0; i <= 360; i++) {
			sin[i] = Math.sin(i*pi/180);
			cos[i] = Math.cos(i*pi/180);
			tan[i] = Math.tan(i*pi/180);
		}
	}
	
	//In Degrees
	public static double sin(int a) {
		return sin[a];
	}
	
	//In Degrees
	public static double cos(int a) {
		return cos[a];
	}
		
	//In Degrees
	public static double tan(int a) {
		return tan[a];
	}
}
