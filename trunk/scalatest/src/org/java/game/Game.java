package org.java.game;

public class Game {

	private Player player1;

	private Player player2;

	private Player inPlay;

	private Board board;

	private Game(Player player1, Player player2, Board board) {
		this.player1 = player1;
		this.player2 = player2;
		this.inPlay = player1;
		this.board = board;
	}

	private Game(Game game) {
		this.player1 = game.player1;
		this.player2 = game.player2;
		this.inPlay = game.inPlay;
		this.board = game.board.copy();
	}

	public static Game newGame(Player player1, Player player2, Board board) {
		player1.setPlayerOne();
		return new Game(player1, player2, board);
	}

	public boolean isPlayerOneInPlayer() {
		return inPlay.isPlayerOne();
	}

	public Board getBoard() {
		return board;
	}

	public void nextTurn() {
		if (inPlay == player1)
			inPlay = player2;
		else
			inPlay = player1;
	}

	public Game copy() {
		return new Game(this);
	}

	public String toString() {
		return "Inplay : " + inPlay + "\n" + board;
	}

}
