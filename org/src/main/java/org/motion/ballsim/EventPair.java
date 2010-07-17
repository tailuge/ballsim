package org.motion.ballsim;

public class EventPair 
{

	public final Event first,second;
	
	public EventPair(Event first_,Event second_)
	{
		first=first_;
		second=second_;
	}

	public String toString()
	{
		return "("+first+","+second+")";
	}
}
