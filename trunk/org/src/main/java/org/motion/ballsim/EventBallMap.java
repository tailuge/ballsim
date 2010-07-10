package org.motion.ballsim;

import java.util.HashMap;

/**
 * @author luke
 *
 * When unfolding events in a sequence need to be able to
 * associate events with a ball. This class would not be needed
 * if Event contained a reference to the ball with which its associated.
 * 
 */
public class EventBallMap extends HashMap<Event, Ball> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3822858875118067922L;


}
