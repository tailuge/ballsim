package org.java.game.demo;

import org.java.game.BestGameListener;
import org.java.game.Board;
import org.java.game.Game;
import org.java.game.GameEvaluationResult;
import org.java.game.IBoard;
import org.java.game.Player;
import org.java.minmax.MinMaxEvaluator;

public class DemoTree implements BestGameListener {

	
	@Override
	public void notifyBestGame(GameEvaluationResult result) {
	}

	private static void testOne() {
		MinMaxEvaluator evaluator = new MinMaxEvaluator(new DemoTreePositionEvaluator(), new DemoTreePositionGenerator(), new DemoTree());
		IBoard board = Board.newSquareBoard(1);
		Game game = Game.newGame(new Player(), new Player(), board);
		System.out.println(evaluator.minimax(game, 2));
	}

	private static void testTwo() {
		MinMaxEvaluator evaluator = new MinMaxEvaluator(new DemoTreePositionEvaluator(), new DemoTreePositionGenerator(), new DemoTree());
		IBoard board = Board.newSquareBoard(1);
		Game game = Game.newGame(new Player(), new Player(), board);
		System.out.println(evaluator.minimax(game, 2));
	}

	public static void main(String[] args) {
		testOne();
		System.out.println("...");
		testTwo();
	}
	
}
