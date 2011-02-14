package org.motion.ballsim.game;

public class PlayerState {
	
	public final Activity activity;	
	public final BallSet cueBall;
	public final BallSet target;
	
	public PlayerState(Activity activity_, BallSet cueBall_, BallSet target_)
	{
		activity = activity_;
		cueBall = cueBall_;
		target = target_;
	}
}
