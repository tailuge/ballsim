package org.motion.ballsim.game;

public enum NineBallSet implements BallSet
{
	CUEBALL(1),
	ONEBALL(2),
	TWOBALL(3),
	NINEBALL(10);
	
	private int index;
	
	NineBallSet(int index_)
	{
		index = index_;
	}

	public int index()
	{
		return index;
	}
}
