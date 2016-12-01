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
import java.util.Random;

import field.Field;
import field.LeeFill;
import field.LeeFillGeneric;
import field.MatrixHelper;
import move.Move;
import move.MoveType;

/**
 * bot.BotStarter
 * 
 * Magic happens here. You should edit this file, or more specifically
 * the doMove() method to make your bot do more than random moves.
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
	 * @param state The current state of the game
	 * @return A Move object
	 */
	public Move doMove(BotState state) {
		ArrayList<MoveType> validMoveTypes = state.getField().getValidMoveTypes();

		Field field = state.getField();
		
		double fieldSize = field.getWidth() * field.getHeight();
		double[][] m = LeeFillGeneric.buildIdentity(field, 1);
		for (Point p : field.getSnippetPositions()) {
			 
			LeeFillGeneric lfg = new LeeFillGeneric(state.getField());
			double[][] t = lfg.startFill(state.getField().getSnippetPositions().get(0), 1, -1/fieldSize);
			m = MatrixHelper.multiply(m, t, LeeFillGeneric.WALL);
		}
		for (Point p : field.getWeaponPositions()) {
			 
			LeeFillGeneric lfg = new LeeFillGeneric(state.getField());
			double[][] t = lfg.startFill(state.getField().getWeaponPositions().get(0), 1, -3/fieldSize);
			m = MatrixHelper.multiply(m, t, LeeFillGeneric.WALL);
		}

		for (Point p: field.getEnemyPositions()) {
			LeeFillGeneric lfg = new LeeFillGeneric(state.getField());
			double[][] t = lfg.startFill(state.getField().getEnemyPositions().get(0), 1, -3/fieldSize);
			t = MatrixHelper.multiply(t, -1, LeeFillGeneric.WALL);
			m = MatrixHelper.add(m, t, LeeFillGeneric.WALL);
		}
		
		MoveType tentativeMove = MatrixHelper.findMoveType(m, state.getField().getMyPosition());
		
		//LeeFill lf = new LeeFill(state.getField());
		
		if (validMoveTypes.size() <= 0) return new Move(); // No valid moves, pass
//		MoveType leeMoveType = MoveType.PASS;
//		if (state.getField().getSnippetPositions().size() != 0) {
//			leeMoveType = lf.startFill(state.getField().getMyPosition());
//		}

//		MoveType randomMoveType = validMoveTypes.get(rand.nextInt(validMoveTypes.size()));

		
		return new Move(tentativeMove); // Return random but valid move
	}

 	public static void main(String[] args) {
 		BotParser parser = new BotParser(new BotStarter());
 		parser.run();
 	}
 }
