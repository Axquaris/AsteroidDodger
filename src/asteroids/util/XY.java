package asteroids.util;

public class XY{

    public int x;
    public int y;

    public XY(int sx, int sy) {
    	x = sx;
    	y = sy;
    }

    public void add(XY other) {
    	x += other.x;
    	y += other.y;
    }
    
    public static XY add(XY a, XY b) {
    	return new XY(a.x+b.x, a.y+b.y);
    }

    public void addD(DXY otherD) {
    	XY other = otherD.toXY();
    	add(other);
    }

    public void multiply(XY other) {
    	x *= other.x;
    	y *= other.y;
    }

    public DXY toDXY() {
    	return new DXY(x, y);
    }
    
    public XY copy() {
        return new XY(x, y);
    }
    
    @Override
    public String toString() {
        return "X"+x+", Y"+y;
    }
}