package org.oxtail.game.numberguess.model;

import java.util.Map;

import org.oxtail.game.model.Player;
import org.oxtail.game.model.PlayingSpace;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

/**
 * Board for a simple number guess game i.e. numbers from 0 to 9 
 * 
 * @author liam knox
 */
public class NumberGuessBoard extends PlayingSpace {

	private final Map<Integer, Player> guessBoard = Maps.newHashMap();

	private final int startNumber;
	private final int endNumber;

	public NumberGuessBoard(int startNumber, int endNumber) {
		Assert.isTrue(startNumber <= endNumber, "invalid number range, "
				+ startNumber + " to " + endNumber);
		this.startNumber = startNumber;
		this.endNumber = endNumber;
	}

	public void guessNumber(Player player, int number) {
		assertNumberValid(number);
		guessBoard.put(number, player);
	}

	private void assertNumberValid(int number) {
		Assert.isTrue(number >= startNumber);
		Assert.isTrue(number <= endNumber);
		Assert.isTrue(!guessBoard.containsKey(number), "number already guessed");
	}
}
