package org.motion.ballsim.game;

public class PlayerState {
	
	final PlayerActivity activity;	
	final BallSet cueBall;
	final BallSet target;
	
	public PlayerState(PlayerActivity activity_, BallSet cueBall_, BallSet target_)
	{
		activity = activity_;
		cueBall = cueBall_;
		target = target_;
	}
}
