package org.oxtail.play.io;

import org.oxtail.play.Checker;

public class CheckerBoardLoader extends AbstractBoardLoader {

	public CheckerBoardLoader(int width, int height) {
		super(width, height);
	}

	@Override
	protected int fromChar(char charAt) {
		return Checker.fromChar(charAt).getIntValue();
	}

	
}
