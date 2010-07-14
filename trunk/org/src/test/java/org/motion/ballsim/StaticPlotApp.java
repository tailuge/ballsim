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
		Ball b1 = new Ball(Utilities.getSliding(new Vector3D(-2,2.5,0).scalarMultiply(-70) ,Vector3D.PLUS_I));

		
		t.balls.add(b1);
		Sequence s = new Sequence(t);
		s.generateSequence();
    	plot = new StaticPlot(t,50);
    	plot.draw();
    }	

}
