package org.oxtail.play.tictactoe;

import org.oxtail.play.io.AbstractBoardLoader;

public class TicTacToeBoardLoader extends AbstractBoardLoader {

	public TicTacToeBoardLoader() {
		super(3, 3);
	}

	@Override
	protected int fromChar(char charAt) {
		return TicTacToeSquare.fromChar(charAt).getIntValue();
	}

}
