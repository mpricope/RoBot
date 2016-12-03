package field;

import java.awt.Point;

import move.MoveType;

public class MatrixHelper {

	public static double EPSILON = 1e-10;

	public static boolean equalDouble(double a, double b) {
		if (Math.abs(a - b) < EPSILON)
			return true;
		return false;
	}

	public static double[][] multiply(double[][] m, double s, double ignore) {

		for (int x = 0; x < m.length; x++) {
			for (int y = 0; y < m[0].length; y++) {
				if (!equalDouble(m[x][y], ignore) ) {
				
					m[x][y] *= s;
				}
			}
		}
		return m;
	}
	
	public static double[][] multiply(double[][] m1, double[][] m2, double ignore) {
		for (int x = 0; x < m1.length; x++) {
			for (int y = 0; y < m1[0].length; y++) {
				if (!equalDouble(m1[x][y], ignore) ) {
					m1[x][y] *= m2[x][y];
				}
			}
		}
		
		return m1;
		
	}
	public static double[][] add(double[][] m1, double[][] m2, double ignore) {
		for (int x = 0; x < m1.length; x++) {
			for (int y = 0; y < m1[0].length; y++) {
				if (!equalDouble(m1[x][y], ignore) ) {
					m1[x][y] += m2[x][y];
				}
			}
		}
		
		return m1;
		
	}
	
    public static String asString(double[][] m) {
        StringBuilder output = new StringBuilder();

        output.append(String.format("%9f",0.0)).append("|");
		for (int x = 0; x < m.length; x++) {
			output.append(String.format("%9f",0.0 + x)).append("|");
		}
		output.append("\n");
		for (int y = 0; y < m[0].length; y++) {
            output.append(String.format("%9f",0.0 + y)).append("|");
			for (int x = 0; x < m.length; x++) {
                output.append(String.format("%9.3f",m[x][y])).append("|");
            }
            output.append("\n");
        }

        return output.toString();
    }
    
    public static MoveType findMoveType(double[][] m, Point current) {
    	Point ret = (Point)current.clone();
		int x = current.x;
		int y = current.y;
		double v = m[x][y];
		Point tmp = new Point(x -1, y);

    	if (isPointValid(tmp, m) && m[tmp.x][tmp.y] >= v) {
    		v = m[tmp.x][tmp.y];
    		ret = tmp;
    	}
		tmp = new Point(x + 1, y);
    	if (isPointValid(tmp, m) && m[tmp.x][tmp.y] >= v) {
    		v = m[tmp.x][tmp.y];
    		ret = tmp;
    	}
		tmp = new Point(x, y - 1);
    	if (isPointValid(tmp, m) && m[tmp.x][tmp.y] >= v) {
    		v = m[tmp.x][tmp.y];
    		ret = tmp;
    	}
		tmp = new Point(x, y + 1);
    	if (isPointValid(tmp, m) && m[tmp.x][tmp.y] >= v) {
    		v = m[tmp.x][tmp.y];
    		ret = tmp;
    	}
    	
    	return getMove(current, ret);

    }
    
    public static boolean isPointValid(Point p,double[][] m) {
        int x = p.x;
        int y = p.y;
        int height = m[0].length;
        int width = m.length;

        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    
	public static MoveType getMove(Point start,Point end) {
		if (start.equals(end)) return MoveType.PASS;
		if (start.x == end.x + 1) return MoveType.LEFT;
		if (start.x == end.x - 1) return MoveType.RIGHT;
		if (start.y == end.y - 1) return MoveType.DOWN;
		return MoveType.UP;
	}


}
