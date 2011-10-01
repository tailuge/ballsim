package org.motion.ballsim.physics;

public final class EventPair {

	public final Event first, second;

	public EventPair(Event first, Event second) {
		this.first = first;
		this.second = second;
	}

	public String toString() {
		return "(" + first + "," + second + ")";
	}
}
