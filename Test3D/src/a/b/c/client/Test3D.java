package a.b.c.client;

import gwt.g2d.client.util.FpsTimer;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.util.Rack;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Test3D implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Table table = new Table(true);
		Rack.rack(table, "2", "");
		table.generateSequence();
		
		final BilliardsViewImpl demo = new BilliardsViewImpl();
		
		

		FpsTimer timer = new FpsTimer(30) {
			double startTime = System.currentTimeMillis();
			@Override
			public void update() {
				demo.plotAtTime(table, (System.currentTimeMillis() - startTime)/1000.0);
				
				if (((System.currentTimeMillis() - startTime)/1500.0)>10)
				{
					startTime = System.currentTimeMillis();
				}
			}
		};
		timer.start();
	}



}
