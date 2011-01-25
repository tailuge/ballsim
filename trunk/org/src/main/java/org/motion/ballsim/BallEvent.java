package org.motion.ballsim;

import org.motion.ballsim.motion.Event;

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
