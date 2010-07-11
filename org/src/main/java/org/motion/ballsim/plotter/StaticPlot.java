package org.motion.ballsim.plotter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.motion.ballsim.Plotter;
import org.motion.ballsim.Table;

public class StaticPlot  extends JPanel 
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1224879637869008694L;

	public void plot(Table t) 
	 {
	        Plotter points = new Plotter();
	        JFrame frame = new JFrame("Test plot");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.add(points);
	        frame.setSize(400, 300);
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	 }
}
