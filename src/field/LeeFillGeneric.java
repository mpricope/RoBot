package field;

import java.awt.Point;

public class LeeFillGeneric {

	public static double WALL = -10;
	public static double EMPTY = 0;

	public double[][] matrix;
	private int height;
	private int width;
	private Point[] moves;
	private int currentIndex = 0;
	private int maxIndex = 0;
	private Field field;

	public LeeFillGeneric(Field field) {
		this.height = field.getHeight();
		this.width = field.getWidth();
		this.matrix = buildIdentity(field, EMPTY);
		this.field = field;
		
		this.moves = new Point[this.width * this.height * 10];
	}
	
	
	public static double[][] buildIdentity(Field field, double empty) {
		double[][] matrix = new double[field.getWidth()][field.getHeight()];
		for (int y = 0; y < field.getHeight(); y++) {
			for (int x = 0; x < field.getWidth(); x++) {
				String cell = field.getField()[x][y];
				if (cell.equals("x")) {
					matrix[x][y] = WALL;
				} else {
					matrix[x][y] = empty;
				}

			}

		}
		return matrix;
		
	}
	
	public double[][] startFill(Point start,double startScore, double decay) {

		this.moves[0] = start;
		matrix[start.x][start.y] = startScore;
		maxIndex = 1;
		while (currentIndex < maxIndex) {
			fillNext(decay);
		}
		
		return matrix;

	}
	
	public void fillNext(double decay) {
		Point current = this.moves[currentIndex];
		int x = current.x;
		int y = current.y;
		Point tmp = new Point(x -1, y);
		double cv = matrix[x][y];
		double newVal = cv + decay;
		processPoint(tmp, newVal);
		tmp = new Point(x + 1, y);
		processPoint(tmp, newVal);
		tmp = new Point(x, y - 1);
		processPoint(tmp, newVal);
		tmp = new Point(x, y + 1);
		processPoint(tmp, newVal);
		currentIndex ++;

	}
	
	public void processPoint(Point tmp,double newval) {
		if (isPointValid(tmp, newval)) {
			moves[maxIndex] = tmp;
			matrix[tmp.x][tmp.y] = newval;
			maxIndex++;

		}
		
	}
	
    public boolean isPointValid(Point p,double ref) {
        int x = p.x;
        int y = p.y;

        return x >= 0 && x < this.width && y >= 0 && y < this.height &&
               (matrix[x][y] < ref) && (matrix[x][y] > -1);
    }
    
    
    public String toString() {
        StringBuilder output = new StringBuilder();

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                output.append(String.format("%9.3f",this.matrix[x][y])).append("|");
            }
            output.append("\n");
        }

        return output.toString();
    }


}
