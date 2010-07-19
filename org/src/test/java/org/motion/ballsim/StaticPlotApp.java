package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;
import org.motion.ballsim.plotter.StaticPlot;

public class StaticPlotApp 
{
	static StaticPlot plot;
	
    public static void main(String[] args) 
    {
		Table t = new Table();
//		Ball b1 = new Ball(Utilities.getRolling(new Vector3D(2,1,0).scalarMultiply(-100) ));
	//	Ball b1 = new Ball(Utilities.getSliding(new Vector3D(-2,2.5,0).scalarMultiply(-10) ,Vector3D.PLUS_I));
//		Ball b2 = new Ball(Utilities.getStationary(new Vector3D(-4*Ball.R,4.5*Ball.R,0)));
		Ball b2 = new Ball(Utilities.getRolling(Vector3D.MINUS_J.scalarMultiply(300) , new Vector3D(Ball.R*7.6,Ball.R*4,0)));
		Ball b1 = new Ball(Utilities.getStationary(new Vector3D(Ball.R*6,-Ball.R*6,0)));
		Ball b3 = new Ball(Utilities.getStationary(new Vector3D(Ball.R*5,-Ball.R*3,0)));
		
		//Ball b1 = new Ball(Utilities.getRolling(Vector3D.MINUS_I.scalarMultiply(30) ));
		//Ball b2 = new Ball(Utilities.getRolling(Vector3D.MINUS_I.scalarMultiply(30) , Vector3D.PLUS_J.scalarMultiply(Ball.R*4)));

		Vector3D dir = new Vector3D(Math.PI/2.0+2*Math.PI*(double)47/(double)360,0);
		b2.lastEvent().vel = dir.scalarMultiply(280);
		b2.lastEvent().angularVel = dir.scalarMultiply(1);
		b2.lastEvent().state = State.Sliding;
		
		t.balls.add(b1);
		t.balls.add(b2);
		t.balls.add(b3);
		
		ShotFinder finder = new ShotFinder(new OneCushionRuleSet(),t);
		
		Table tResult = finder.FindBest(b2, 360);
		
		b2.resetToFirst();
		System.out.println(b2.lastEvent().format());
		
		//t.generateSequence();
		//t.generateNext();

		plot = new StaticPlot(tResult,50);
    	plot.draw();
    }	

}
