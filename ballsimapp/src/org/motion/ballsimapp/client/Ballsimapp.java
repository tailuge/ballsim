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
import com.google.gwt.user.client.ui.TextBox;

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
	double maxt;

	final TextBox maxVel = new TextBox();
	final TextBox accelRoll = new TextBox();
	final TextBox accelSlide = new TextBox();
	final TextBox maxAngVel = new TextBox();
	final TextBox timeText = new TextBox();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		PlotScale.setWindowInfo(width, height);
	    
		final Button sendButton = new Button("Hit");
		sendButton.setEnabled(false);
	       
	    
	    RootPanel.get("tableContainer").add(table.getInitialisedCanvas());
	    RootPanel.get("spinContainer").add(spin.getInitialisedCanvas());
	    RootPanel.get("powerContainer").add(power.getInitialisedCanvas());
		RootPanel.get("hitContainer").add(sendButton);    
		

		maxVel.setWidth("4em");
		accelRoll.setWidth("4em");
		accelSlide.setWidth("4em");

		maxVel.setText(""+Table.maxVel);
		RootPanel.get("maxVel").add(maxVel);

		accelRoll.setText(""+Table.accelRoll);
		RootPanel.get("accelRoll").add(accelRoll);

		accelSlide.setText(""+Table.accelSlide);
		RootPanel.get("accelSlide").add(accelSlide);

		maxAngVel.setText(""+Table.maxAngVel);
		RootPanel.get("maxAngVel").add(maxAngVel);

		timeText.setText(""+time+"/"+maxt);
		RootPanel.get("time").add(timeText);
		
		t.ball(1).setFirstEvent(UtilEvent.hit(Vector3D.ZERO, Vector3D.PLUS_J, Table.maxVel*0.6, 0.0));
		t.ball(2).setFirstEvent(UtilEvent.stationary(new Vector3D(-Ball.R*0.46,+Ball.R*18,0)));
		t.ball(3).setFirstEvent(UtilEvent.stationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));
					
		
		t.generateSequence();
		maxt = t.getMaxTime();
		

		time = 0;
		startTime = System.currentTimeMillis();
		
	    // setup timer
	    final Timer timer = new Timer() {
	      @Override
	      public void run() {
	        time = (System.currentTimeMillis() - startTime) / 1000.0;
	        double plottime = time > maxt ? maxt : time;
	        table.plotAtTime(t, plottime);
			if (time > maxt) 
				sendButton.setEnabled(true);

			timeText.setText(""+(int)time+"/"+(int)maxt);
	      }
	    };
	    timer.scheduleRepeating(refreshRate);
	    	
	    
		// Add a handler to close the DialogBox
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				sendButton.setEnabled(false);
				sendButton.setFocus(true);
				timer.cancel();
				
				Event cueBall = Interpolator.interpolate(t.ball(1), time);
				Event b2 = Interpolator.interpolate(t.ball(2), time);
				Event b3 = Interpolator.interpolate(t.ball(3), time);
				
				Table.maxVel = Double.parseDouble(maxVel.getText());
				Table.maxAngVel = Double.parseDouble(maxAngVel.getText());
				Table.accelRoll = Double.parseDouble(accelRoll.getText());
				Table.accelSlide = Double.parseDouble(accelSlide.getText());

				t.ball(1).setFirstEvent(UtilEvent.hit(cueBall.pos, table.getAim(), Table.maxVel*power.getPower(), spin.getSpin()));				
				t.ball(2).setFirstEvent(UtilEvent.stationary(b2.pos));
				t.ball(3).setFirstEvent(UtilEvent.stationary(b3.pos));
				
				time = 0;
				startTime = System.currentTimeMillis();
				
				
				t.generateSequence();

				maxt = t.getMaxTime();				
				time = 0;
				startTime = System.currentTimeMillis();
				
			    timer.scheduleRepeating(refreshRate);
				
				
			}
		});	    
	}
	

	  
}
