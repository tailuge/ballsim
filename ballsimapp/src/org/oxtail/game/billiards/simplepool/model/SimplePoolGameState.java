package org.oxtail.game.billiards.simplepool.model;

import org.oxtail.game.billiards.simplepool.state.InPlay;

public enum SimplePoolGameState {

	FoulGameOver() {
		@Override
		public void doMove(InPlay inplay) {
			inplay.turnOver();
			inplay.notifyGameOver();
		}

		@Override
		public SimplePoolGameState forFoul() {
			return FoulGameOver;
		}

	},
	GameOver() {
		@Override
		public void doMove(InPlay inplay) {
			inplay.notifyGameOver();
		}

		@Override
		public SimplePoolGameState forFoul() {
			return FoulGameOver;
		}

	},
	Foul() {
		@Override
		public void doMove(InPlay inplay) {
			inplay.turnOver();
			inplay.notifyFoul();
		}

		@Override
		public SimplePoolGameState forFoul() {
			return Foul;
		}

	},
	TurnOver() {
		@Override
		public void doMove(InPlay inplay) {
			inplay.turnOver();
			inplay.notifyMove();
		}

		@Override
		public SimplePoolGameState forFoul() {
			return Foul;
		}

	},
	TurnContinued() {
		@Override
		public void doMove(InPlay inplay) {
			inplay.notifyMove();
		}

		@Override
		public SimplePoolGameState forFoul() {
			return Foul;
		}

	};

	public abstract void doMove(InPlay inplay);

	/** Switch this state to is foul equivalent */
	public abstract SimplePoolGameState forFoul();

}
