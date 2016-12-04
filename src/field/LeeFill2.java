package field;

import java.awt.Point;

import move.MoveType;

public class LeeFill2 {
	public static int WALL = -10;
	public static int EMPTY = 0;

	public int[][] matrix;
	private int height;
	private int width;
	private Point[] moves;
	private int currentIndex = 0;
	private int maxIndex = 0;
	private Field field;
	private Point end;
	private Point start;

	public LeeFill2(Field field) {
		this.height = field.getHeight();
		this.width = field.getWidth();
		this.matrix = buildIdentity(field, EMPTY);
		this.field = field;
		
		this.moves = new Point[this.width * this.height * 10];
	}
	
	public static int[][] buildIdentity(Field field, int empty) {
		int[][] matrix = new int[field.getWidth()][field.getHeight()];
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
	
	public MoveTypeScore startFill(Point start, Point end) {
		this.start = start;
		this.end = end;
		this.moves[0] = start;
		matrix[start.x][start.y] = 1;
		matrix[end.x][end.y] = 100;
		maxIndex = 1;
		while (fillNext(1)) {
			
		}
		
		
		MoveTypeScore m = MatrixHelper.findMoveType(matrix, end);
		//System.err.println(m);
		return m;
		

	}
	

	
	
	public boolean fillNext(int decay) {
		Point current = this.moves[currentIndex];
		int x = current.x;
		int y = current.y;
		Point tmp = new Point(x -1, y);
		int cv = matrix[x][y];
		if (tmp.equals(end)) return false;
		int newVal = cv + decay;
		processPoint(tmp, newVal);
		tmp = new Point(x + 1, y);
		if (tmp.equals(end)) return false;
		processPoint(tmp, newVal);
		tmp = new Point(x, y - 1);
		if (tmp.equals(end)) return false;
		processPoint(tmp, newVal);
		tmp = new Point(x, y + 1);
		if (tmp.equals(end)) return false;
		processPoint(tmp, newVal);
		currentIndex ++;

		return true;
	}
	
	
	public boolean processPoint(Point tmp,int newval) {
		if (isPointValid(tmp, newval)) {
			moves[maxIndex] = tmp;
			matrix[tmp.x][tmp.y] = newval;
			maxIndex++;

		}
		return true;
		
	}
	
    public boolean isPointValid(Point p,int ref) {
        int x = p.x;
        int y = p.y;

        return x >= 0 && x < this.width && y >= 0 && y < this.height && (matrix[x][y] == 0);
    }
    



}
