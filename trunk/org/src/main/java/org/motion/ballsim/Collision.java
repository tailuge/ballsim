package org.motion.ballsim;

public class Collision 
{

	static double collisionTime(Event e1, Event e2)
	{
		
		// distance between two balls at collision is dx^2 + dy^2 = r^2
		// dx = e1.x - e2.x
		// coefficients of quadratic of dx in time
		
		double a = e1.getAccelerationVector().getX()*0.5 - e2.getAccelerationVector().getX()*0.5;
		double b = e1.vel.getX() - e2.vel.getX();
		double c = e1.pos.getX() - e2.pos.getX();

		// dx^2 gives following quartic coefficients
		
		double t4 = a*a;
		double t3 = 2*a*b;
		double t2 = 2*a*c + b*b;
		double t1 = 2*b*c;
		double t0 = c*c;
		
		// dy^2
		
		a = e1.getAccelerationVector().getY()*0.5 - e2.getAccelerationVector().getY()*0.5;
		b = e1.vel.getY() - e2.vel.getY();
		c = e1.pos.getY() - e2.pos.getY();

		// add these to give dx^2+dy^2
		
		t4 += a*a;
		t3 += 2*a*b;
		t2 += 2*a*c + b*b;
		t1 += 2*b*c;
		t0 += c*c;

		// subtract r^2
		
		t0 -= Ball.R*Ball.R;
		
		// solve
		
		// optimise
		
		return 0;
	}
	
	
	/*
	static Pair<Event,Event> collisionEvents(Event a, Event b, double t)
	{
		
	}
	*/
}
