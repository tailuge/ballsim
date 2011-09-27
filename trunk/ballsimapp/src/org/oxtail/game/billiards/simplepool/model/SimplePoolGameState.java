package org.oxtail.game.billiards.simplepool.model;

import org.oxtail.game.billiards.simplepool.state.InPlay;

public enum SimplePoolGameState {

	GameOver() {
		@Override
		public void doMove(InPlay inplay) {
			inplay.notifyGameOver();
		}
	},
	Foul() {
		@Override
		public void doMove(InPlay inplay) {
			inplay.turnOver();
			inplay.notifyFoul();
		}

	},
	TurnOver() {
		@Override
		public void doMove(InPlay inplay) {
			inplay.turnOver();
			inplay.notifyMove();
		}

	},
	TurnContinued() {
		@Override
		public void doMove(InPlay inplay) {
			inplay.notifyMove();
		}
	};

	public abstract void doMove(InPlay inplay);
}
