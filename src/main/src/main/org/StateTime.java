package main.org;

public class StateTime 
{
	public State state;
	public double t;
	
	public StateTime()
	{
		state = State.Unknown;
		t = 0;
	}

	public StateTime(State state, double t) 
	{
		this.state = state;
		this.t = t;
	}
	
}
