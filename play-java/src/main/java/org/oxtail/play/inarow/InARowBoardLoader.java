package org.oxtail.play.inarow;

import org.oxtail.play.Checker;
import org.oxtail.play.io.AbstractBoardLoader;

public class InARowBoardLoader extends AbstractBoardLoader {

	public InARowBoardLoader(int x, int y) {
		super(x, y);
	}

	@Override
	protected int fromChar(char charAt) {
		return Checker.fromChar(charAt).getIntValue();
	}

}
