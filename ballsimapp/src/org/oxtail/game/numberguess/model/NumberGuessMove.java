package org.oxtail.game.numberguess.model;

import org.oxtail.game.model.Move;
import org.oxtail.game.model.Player;

/**
 * A single player guess
 */
public class NumberGuessMove extends Move {

private final int numberGuessed;

	public NumberGuessMove(Player player, int numberGuessed) {
		super(player);
		this.numberGuessed = numberGuessed;
	}

	public int getNumberGuessed() {
		return numberGuessed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numberGuessed;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NumberGuessMove other = (NumberGuessMove) obj;
		if (numberGuessed != other.numberGuessed)
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}

}
