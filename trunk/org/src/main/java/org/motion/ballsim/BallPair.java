package org.motion.ballsim;

public class BallPair 
{

	private final Ball a,b;
	
	public BallPair(Ball a_,Ball b_)
	{
		a=a_;
		b=b_;
	}

	public Ball getFirst() 
	{
		return a;
	}

	public Ball getSecond() 
	{
		return b;
	}
}
