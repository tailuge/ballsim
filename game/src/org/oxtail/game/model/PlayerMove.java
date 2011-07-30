package org.oxtail.game.model;

public class PlayerMove<T extends PlayingSpace> {

	private Player inPlay;
	private T beforeMoveState;
	private T afterMoveState;
}
