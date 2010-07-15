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
		Ball b1 = new Ball(Utilities.getStationary());
		Ball b2 = new Ball(Utilities.getRolling(Vector3D.MINUS_J.scalarMultiply(20) , Vector3D.PLUS_I.scalarMultiply(Ball.R*4)));

		
	t.balls.add(b1);
		t.balls.add(b2);
//		t.generateSequence();
		t.generateNext();
		    	plot = new StaticPlot(t);
    	plot.draw();
    }	

}
