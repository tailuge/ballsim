package org.motion.ballsimapp.client;




import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.Interpolator;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.util.UtilEvent;
import org.motion.ballsimapp.canvas.PlotScale;
import org.motion.ballsimapp.canvas.PowerInputCanvas;
import org.motion.ballsimapp.canvas.SpinInputCanvas;
import org.motion.ballsimapp.canvas.TableCanvas;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ballsimapp implements EntryPoint {


	// inputs
	
	private final SpinInputCanvas spin = new SpinInputCanvas(30,30);
	private final PowerInputCanvas power = new PowerInputCanvas(200,30);

	// table
	
	private final TableCanvas table = new TableCanvas(width,height);
	
	final Table t = new Table(true);
	
	
	  //timer refresh rate, in milliseconds
	  static final int refreshRate = 25;
	  
	  // canvas size, in px
	  static final int height = 600;
	  static final int width = 400;
	  
	  private long startTime;
	  
	//Timer timer;
	double time;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		PlotScale.setWindowInfo(width, height);
	    
		final Button sendButton = new Button("Replay");
	       
	    
	    RootPanel.get("tableContainer").add(table.getInitialisedCanvas());
	    RootPanel.get("spinContainer").add(spin.getInitialisedCanvas());
	    RootPanel.get("powerContainer").add(power.getInitialisedCanvas());
		RootPanel.get("hitContainer").add(sendButton);    
		

		// Add a handler to close the DialogBox
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
				
				Event cueBall = Interpolator.interpolate(t.ball(1), time);
				
				t.ball(1).setFirstEvent(UtilEvent.hit(cueBall.pos, table.getAim(), 320*power.getPower(), spin.getSpin()));
				t.ball(2).setFirstEvent(UtilEvent.stationary(new Vector3D(-Ball.R*0.46,+Ball.R*18,0)));
				t.ball(3).setFirstEvent(UtilEvent.stationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));
							
				t.generateSequence();

				startTime = System.currentTimeMillis();
				time = 0;
				
				
			}
		});
		
	

	   	
		t.ball(1).setFirstEvent(UtilEvent.hit(Vector3D.ZERO, Vector3D.PLUS_J, 250, 0.5));
		t.ball(2).setFirstEvent(UtilEvent.stationary(new Vector3D(-Ball.R*0.46,+Ball.R*18,0)));
		t.ball(3).setFirstEvent(UtilEvent.stationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));
					
		
		t.generateSequence();
		
		//final PlotScale ps = new PlotScale(t.getAllEvents());
		//ps.setWindowInfo(300, 600);
		

		time = 0;
		startTime = System.currentTimeMillis();
		
	    // setup timer
	    final Timer timer = new Timer() {
	      @Override
	      public void run() {
	        table.plotAtTime(t, time);
	        time += (System.currentTimeMillis() - startTime) / 1000000.0;
	      }
	    };
	    timer.scheduleRepeating(refreshRate);
	    	    
	}
	
	void doUpdate() 
	  {

	  }
	  
}
