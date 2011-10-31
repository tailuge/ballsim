package a.b.c.client;

import gwt.g2d.client.util.FpsTimer;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.util.Rack;

import a.b.c.client.view3d.BilliardsViewImpl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Test3D implements EntryPoint {

	protected final Button launch3D = new Button("3D View");
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		RootPanel.get().add(launch3D);
		
		launch3D.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				launch3D.setEnabled(false);
				launch();
			}
		});
	}
	
	private void launch()
	{
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
