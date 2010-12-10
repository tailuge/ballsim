package org.java.game.demo;

import java.util.ArrayList;
import java.util.List;

import org.java.game.Game;
import org.java.game.GamePositionGenerator;
import org.java.game.Piece;
import org.java.game.PositionBean;

public class DemoTreePositionGenerator implements GamePositionGenerator {

	@Override
	public Iterable<Game> nextValidPositions(Game game) {
		List<Game> l = new ArrayList<Game>();
		l.add(game.move(Piece.NONE, PositionBean.newPosition(0, 0))); 
		l.add(game.move(Piece.NONE, PositionBean.newPosition(0, 0))); 
		return l;
	}
	
}
