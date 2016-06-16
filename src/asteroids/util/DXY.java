package asteroids.util;


public class DXY{
    public double x;
    public double y;

    public DXY(double sx, double sy) {
    	x = sx;
    	y = sy;
    }

    public void add(XY other) {
    	DXY otherD = other.toDXY();
    	addD(otherD);
    }

    public void addD(DXY other) {
    	x += other.x;
    	y += other.y;
    }
    
    public static DXY add(DXY a, DXY b) {
    	return new DXY(a.x+b.x, a.y+b.y);
    }
    
    public static DXY add(XY a, XY b) {
    	return new DXY(a.x+b.x, a.y+b.y);
    }
    
    public void multiply(DXY other) {
    	x *= other.x;
    	y *= other.y;
    }

    public XY toXY() {
    	int sx = Math.round(Math.round(x));
    	int sy = Math.round(Math.round(y));
    	return new XY(sx, sy);
    }
    
    public DXY copy() {
        return new DXY(x, y);
    }
    
    @Override
    public String toString() {
        return "X"+x+", Y"+y;
    }
}