/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package bot;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import field.Field;
import field.LeeFill2;
import field.MoveTypeScore;
import move.Move;
import move.MoveType;

/**
 * bot.BotStarter
 * 
 * Magic happens here. You should edit this file, or more specifically the
 * doMove() method to make your bot do more than random moves.
 * 
 * @author Jim van Eeden - jim@riddles.io
 */

public class BotStarter {

	private Random rand;

	private BotStarter() {
		this.rand = new Random();
	}

	/**
	 * Does a move action. Edit this to make your bot smarter.
	 * 
	 * @param state
	 *            The current state of the game
	 * @return A Move object
	 */
	public Move doMove(BotState state) {
		ArrayList<MoveType> validMoveTypes = state.getField().getValidMoveTypes();
		Field field = state.getField();
		int maxDist = 2 * (field.getWidth() + field.getHeight());
		Map<MoveType, Integer> moveScores = new HashMap<>();
		for (MoveType mt : validMoveTypes) {
			moveScores.put(mt, maxDist);
		}

		Point start = field.getMyPosition();
		Point oponent = field.getOpponentPosition();
		for (Point p : field.getSnippetPositions()) {

			LeeFill2 lf = new LeeFill2(field);
			MoveTypeScore ms = lf.startFill(p, start);
			int currentScore = moveScores.get(ms.moveType);
			lf = new LeeFill2(field);
			MoveTypeScore opSc = lf.startFill(p, oponent);
			int score = ms.score;
			if (ms.score > opSc.score) {
				score += 5;
			}

			if (score < currentScore) {
				moveScores.put(ms.moveType, score);

			}

		}
		for (Point p : field.getWeaponPositions()) {

			LeeFill2 lf = new LeeFill2(field);
			MoveTypeScore ms = lf.startFill(p, start);
			int currentScore = moveScores.get(ms.moveType);
			if (ms.score < currentScore) {
				moveScores.put(ms.moveType, ms.score);

			}

		}
		List<MoveTypeScore> enemies = new ArrayList<>();
		int safeDist = 4;

		if (!state.getMe().hasWeapon()) {

			for (Point p : field.getEnemyPositions()) {

				LeeFill2 lf = new LeeFill2(field);
				MoveTypeScore ms = lf.startFill(p, start);
				enemies.add(ms);
				// int currentScore = moveScores.get(ms.moveType);
				// if (ms.score < safeDist) {
				// moveScores.put(ms.moveType, -1);
				// safeDist += 2;
				// }

				// moveScores.put(ms.moveType, currentScore + maxDist -
				// ms.score);

			}
		} else {
			LeeFill2 lf = new LeeFill2(field);
			MoveTypeScore ms = lf.startFill(field.getOpponentPosition(), start);
			// int currentScore = moveScores.get(ms.moveType);
			if (ms.score < 3) {
				moveScores.put(ms.moveType, 5);
			}

		}

		if (state.getOpponent().hasWeapon()) {
			LeeFill2 lf = new LeeFill2(field);
			MoveTypeScore ms = lf.startFill(field.getOpponentPosition(), start);
			enemies.add(ms);

			// int currentScore = moveScores.get(ms.moveType);
			// if (ms.score < 4) {
			// moveScores.put(ms.moveType, -1);
			// }

		}
		enemies.sort(new Comparator<MoveTypeScore>() {

			@Override
			public int compare(MoveTypeScore o1, MoveTypeScore o2) {
				// TODO Auto-generated method stub
				return o1.score - o2.score;
			}
		});

		for (MoveTypeScore ms : enemies) {
			if ((ms.score < safeDist) && (hasMoreMoves(moveScores))) {
				moveScores.put(ms.moveType, -1);
				safeDist += 1;
			} else {
				break;
			}
			

		}
		// state.getPlayers().
		System.err.println(moveScores);

		MoveType candidateMove = MoveType.PASS;
		int minScore = maxDist;
		for (MoveType mt : moveScores.keySet()) {
			int c = moveScores.get(mt);
			if ((c <= minScore) && (c > 0)) {
				candidateMove = mt;
				minScore = moveScores.get(mt);
			}

		}

		if (validMoveTypes.size() <= 0)
			return new Move(); // No valid moves, pass

		return new Move(candidateMove); // Return random but valid move
	}
	
	private boolean hasMoreMoves(Map<MoveType, Integer> moveScores) {
		int moves = 0;
		for (Integer i: moveScores.values()) {
			if (i >= 0) {
				moves++;
			}
			if (moves > 1) return true;
		}
		return false;
	}

	public static void main(String[] args) {
		BotParser parser = new BotParser(new BotStarter());
		parser.run();
	}
}
