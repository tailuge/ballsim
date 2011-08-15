package org.oxtail.game.numberguess.model;

import java.util.Map;

import org.oxtail.game.model.Player;
import org.oxtail.game.model.PlayingSpace;

import com.google.common.collect.Maps;

/**
 * Board for a simple number guess game
 * 
 * @author liam knox
 */
public class NumberGuessBoard extends PlayingSpace {

	private final Map<Integer, Player> guessBoard = Maps.newHashMap();

	public void guessNumber(Player player, int number) {
	}

	private void numberValid(int number) {
	}

}
