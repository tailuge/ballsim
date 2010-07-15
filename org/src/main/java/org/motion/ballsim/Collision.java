package org.motion.ballsim;

import java.util.Arrays;

import org.apache.commons.math.geometry.Vector3D;

import com.google.common.base.Function;

public class Collision 
{

	public static double[] quarticCoefficients(Event e1, Event e2)
	{
		// distance between two balls at collision is dx^2 + dy^2 = r^2
		// dx = e1.x - e2.x
		// coefficients of quadratic of dx in time
		
		double a = e1.acceleration().getX()*0.5 - e2.acceleration().getX()*0.5;
		double b = e1.vel.getX() - e2.vel.getX();
		double c = e1.pos.getX() - e2.pos.getX();

		// dx^2 gives following quartic coefficients
		
		double t4 = a*a;
		double t3 = 2*a*b;
		double t2 = 2*a*c + b*b;
		double t1 = 2*b*c;
		double t0 = c*c;
		
		// dy^2
		
		a = e1.acceleration().getY()*0.5 - e2.acceleration().getY()*0.5;
		b = e1.vel.getY() - e2.vel.getY();
		c = e1.pos.getY() - e2.pos.getY();

		// add these to give dx^2+dy^2
		
		t4 += a*a;
		t3 += 2*a*b;
		t2 += 2*a*c + b*b;
		t1 += 2*b*c;
		t0 += c*c;

		// subtract r^2
		
		t0 -= 4*Ball.R*Ball.R;
		
		// solve for roots giving zero. pick least +ve
				
		double[] coeffs = { t0, t1, t2, t3, t4 };
		return coeffs;
	}
	
	static double collisionTime(Event e1, Event e2, double maxt)
	{

		double coeffs[] = quarticCoefficients(e1, e2);
	    double root = Quartic.smallestRoot( coeffs , maxt); 
	       
		// optimise
				
		System.out.println("quartic coeffs      :"+Arrays.toString(coeffs));
		Quartic.print(coeffs);
		System.out.println("1st root            :"+root);
		System.out.println("quartic eval at root:"+Quartic.evalAt(coeffs,root));
		System.out.println("seperation at root  :"+startingSeperation(e1.advanceDelta(root),e2.advanceDelta(root)));
		
		return latestInstantBeforeCollision(e1,e2,root);
	}
	
	
	
	static EventPair collisionEvents(Event a, Event b, double t)
	{
		EventPair result = new EventPair(a.advanceDelta(t),b.advanceDelta(t));

		Event ca = result.getFirst();
		Event cb = result.getSecond();
		
		Vector3D collisionAxis = ca.pos.subtract(cb.pos).normalize();
		
		Vector3D av = ca.vel;
		Vector3D bv = cb.vel;
	
		Vector3D relativeVel = av.subtract(bv);
		
		Vector3D dv = collisionAxis.scalarMultiply(Vector3D.dotProduct(collisionAxis, relativeVel));
		
		ca.vel = ca.vel.subtract(dv);
		cb.vel = cb.vel.add(dv);
		
		ca.state = State.deriveStateOf(ca);
		cb.state = State.deriveStateOf(cb);
		
		ca.type = EventType.Collision;
		cb.type = EventType.Collision;
		
		return result;
	}
	
	
	static double startingSeperation(Event e1, Event e2)
	{
		return Vector3D.distance(e1.pos, e2.pos) - 2*Ball.R;
	}

	static double seperationAt(Event e1, Event e2,double t)
	{
		return Vector3D.distance(e1.advanceDelta(t).pos, e2.advanceDelta(t).pos) - 2*Ball.R;
	}

	private static double latestInstantBeforeCollision(final Event a,final Event b,double tCollision)
	{
		Function<Double,Double> func = new Function<Double, Double>() 
		{	
			@Override
			public Double apply(Double arg) {
				return startingSeperation(a.advanceDelta(arg),b.advanceDelta(arg));
			}
		};

		double last = Quadratic.optimise(func, tCollision);
		
		if (last>0)
			return last;
		
		return 0;
	}

	public static EventPair get(Event e1, Event e2, double maxt) 
	{
		double tCol = collisionTime(e1, e2, maxt);
		
		if (tCol > 0)
			return collisionEvents(e1,e2,tCol);
		
		return null;
	}
}