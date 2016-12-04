package field;

import move.MoveType;

public class MoveTypeScore {

	public MoveType moveType;
	public int score;
	
	public MoveTypeScore(MoveType moveType, int score) {
		this.moveType = moveType;
		this.score = score;
	}
	
	public String toString() {
		return moveType + "|" + score;
	}
}
