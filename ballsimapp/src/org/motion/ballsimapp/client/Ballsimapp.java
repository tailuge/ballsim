package org.motion.ballsimapp.client;




import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
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
import com.google.gwt.user.client.ui.Label;
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
	  static final int refreshRate = 30;
	  
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
		final TextBox nameField = new TextBox();
	       
	    
	    RootPanel.get().add(table.getInitialisedCanvas());
	    RootPanel.get().add(spin.getInitialisedCanvas());
	    RootPanel.get().add(power.getInitialisedCanvas());
	    
		nameField.setText("empty");
		final Label errorLabel = new Label();
		
		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add a handler to close the DialogBox
		sendButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
				
				t.ball(1).setFirstEvent(UtilEvent.hit(Vector3D.ZERO, Vector3D.PLUS_J, 320*power.getPower(), spin.getSpin()));
				t.ball(2).setFirstEvent(UtilEvent.stationary(new Vector3D(-Ball.R*0.46,+Ball.R*18,0)));
				t.ball(3).setFirstEvent(UtilEvent.stationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));
							
				startTime = System.currentTimeMillis();
				time = 0;
				
				t.generateSequence();
				
			}
		});
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		
		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

	   	
		t.ball(1).setFirstEvent(UtilEvent.hit(Vector3D.ZERO, Vector3D.PLUS_J, 250, 0.5));
		t.ball(2).setFirstEvent(UtilEvent.stationary(new Vector3D(-Ball.R*0.46,+Ball.R*18,0)));
		t.ball(3).setFirstEvent(UtilEvent.stationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));
					
		
		t.generateSequence();
		
		//final PlotScale ps = new PlotScale(t.getAllEvents());
		//ps.setWindowInfo(300, 600);
		
		nameField.setText("tmax:");

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
