package org.motion.ballsim;

public class BallEventPair 
{

	public final BallEvent first,second;
	
	public BallEventPair(BallEvent first_,BallEvent second_)
	{
		first=first_;
		second=second_;
	}
	
	public BallEventPair(Ball ball1, Event event1, Ball ball2, Event event2)
	{
		first = new BallEvent(ball1,event1);
		second = new BallEvent(ball2,event2);
	}

}
