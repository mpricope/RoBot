package field;

import java.awt.Point;

import move.MoveType;

public class LeeFill {

	public int[][] matrix;
	private int height;
	private int width;
	private Point[] moves;
	private int currentIndex = 0;
	private int maxIndex = 0;
	
	private Point bkPoint;
	private Point foundGoal;


	public LeeFill(Field field) {
		this.height = field.getHeight();
		this.width = field.getWidth();
		this.matrix = new int[this.width][this.height];
		for (int y = 0; y < field.getHeight(); y++) {
			for (int x = 0; x < field.getWidth(); x++) {
				String cell = field.getField()[x][y];
				if (cell.equals("x")) {
					matrix[x][y] = -1;
				} else if (cell.contains("C")){
					matrix[x][y] = 10000;
				} else {
					matrix[x][y] = 0;
				}

			}

		}
		this.moves = new Point[this.width * this.height];
	}
	
	public MoveType startFill(Point start) {
		this.moves[0] = start;
		matrix[start.x][start.y] = 1;
		maxIndex = 1;
		while (fillNext()) {
			
		}
		

		bkPoint = this.moves[currentIndex];
//		System.err.println(this);
		if (bkPoint.equals(moves[0])) {
			return getMove(moves[0], foundGoal);
		}
		while (backtrack() ) {
			
		}

//		System.err.println(bkPoint + " | " + moves[0]);
//		System.err.println(getMove(moves[0], bkPoint));
		return getMove(moves[0], bkPoint);
		
	}
	
	public MoveType getMove(Point start,Point end) {
		if (start.x == end.x + 1) return MoveType.LEFT;
		if (start.x == end.x - 1) return MoveType.RIGHT;
		if (start.y == end.y - 1) return MoveType.DOWN;
		return MoveType.UP;
	}
	
	public boolean backtrack() {
		int x = bkPoint.x;
		int y = bkPoint.y;
		//System.err.println(bkPoint);
		int v = matrix[bkPoint.x][bkPoint.y];
		Point tmp = new Point(x + 1, y);
		if (isPointValid(tmp) && (matrix[tmp.x][tmp.y] == (v -1))) {
			if (matrix[tmp.x][tmp.y] == 1) {
				return false;
			} else {
				bkPoint = tmp;
				return true;
			}
		}
		tmp = new Point(x - 1, y);
		if (isPointValid(tmp) && (matrix[tmp.x][tmp.y] == (v -1))) {
			if (matrix[tmp.x][tmp.y] == 1) {
				return false;
			} else {
				bkPoint = tmp;
				return true;
			}
		}
		tmp = new Point(x, y - 1);
		if (isPointValid(tmp) && (matrix[tmp.x][tmp.y] == (v -1))) {
			if (matrix[tmp.x][tmp.y] == 1) {
				return false;
			} else {
				bkPoint = tmp;
				return true;
			}
		}
		tmp = new Point(x, y + 1);
		if (isPointValid(tmp) && (matrix[tmp.x][tmp.y] == (v -1))) {
			if (matrix[tmp.x][tmp.y] == 1) {
				return false;
			} else {
				bkPoint = tmp;
				return true;
			}
		}
		return false;
		
	}
	
	public boolean fillNext() {
		Point current = this.moves[currentIndex];
		int x = current.x;
		int y = current.y;
		Point tmp = new Point(x -1, y);
		int cv = matrix[x][y];
		if (isPointValid(tmp)) {
			if (matrix[tmp.x][tmp.y] >= 10000) {
				foundGoal = tmp;
				return false;
			} else if (matrix[tmp.x][tmp.y] == 0) {
				moves[maxIndex] = tmp;
				matrix[tmp.x][tmp.y] = cv + 1;
				maxIndex++;
				
			}
		}
		tmp = new Point(x + 1, y);
		if (isPointValid(tmp)) {
			if (matrix[tmp.x][tmp.y] >= 10000) {
				foundGoal = tmp;
				return false;
			} else if (matrix[tmp.x][tmp.y] == 0) {
				moves[maxIndex] = tmp;
				matrix[tmp.x][tmp.y] = cv + 1;
				maxIndex++;
				
			}
		}
		tmp = new Point(x, y - 1);
		if (isPointValid(tmp)) {
			if (matrix[tmp.x][tmp.y] >= 10000) {
				foundGoal = tmp;
				return false;
			} else if (matrix[tmp.x][tmp.y] == 0) {
				moves[maxIndex] = tmp;
				matrix[tmp.x][tmp.y] = cv + 1;
				maxIndex++;
				
			}
		}
		
		tmp = new Point(x, y + 1);
		if (isPointValid(tmp)) {
			if (matrix[tmp.x][tmp.y] >= 10000) {
				foundGoal = tmp;
				return false;
			} else if (matrix[tmp.x][tmp.y] == 0) {
				moves[maxIndex] = tmp;
				matrix[tmp.x][tmp.y] = cv + 1;
				maxIndex++;
				
			}
		}
		
		currentIndex ++;
		if (currentIndex >= maxIndex) {
			return false;
		}
		
		return true;
		
	}
	
    public boolean isPointValid(Point p) {
        int x = p.x;
        int y = p.y;

        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }

	
    public String toString() {
        StringBuilder output = new StringBuilder();

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                output.append(this.matrix[x][y]).append("|");
            }
            output.append("\n");
        }

        return output.toString();
    }


}
