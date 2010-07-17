package org.motion.ballsim;

public class BallEvent 
{
	public final Ball ball;
	public final Event event;
	
	public BallEvent(Ball ball_, Event event_)
	{
		ball = ball_;
		event = event_;
	}
}
