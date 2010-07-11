package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;
import org.motion.ballsim.plotter.StaticPlot;

public class StaticPlotApp 
{
	static StaticPlot plot;
	
    public static void main(String[] args) 
    {
		Table t = new Table();
		Ball b1 = new Ball(Utilities.getSliding(UtilVector3D.rnd().scalarMultiply(1000),Vector3D.MINUS_I ));
		t.balls.add(b1);
		Sequence s = new Sequence(t);
		s.generateSequence();
    	plot = new StaticPlot(t);
    	plot.draw();
    }	

}
