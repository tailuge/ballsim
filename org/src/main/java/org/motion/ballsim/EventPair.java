package org.motion.ballsim;

public class EventPair 
{

	private final Event a,b;
	
	public EventPair(Event a_,Event b_)
	{
		a=a_;
		b=b_;
	}

	public Event getFirst() 
	{
		return a;
	}

	public Event getSecond() 
	{
		return b;
	}
	
	public String toString()
	{
		return "("+a+","+b+")";
	}
}
