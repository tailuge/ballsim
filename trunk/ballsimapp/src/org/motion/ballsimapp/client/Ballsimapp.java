package org.motion.ballsimapp.client;




import org.motion.ballsimapp.canvas.CanvasTable;
import org.motion.ballsimapp.canvas.Interpolator;
import org.motion.ballsimapp.canvas.PlotEvent;
import org.motion.ballsimapp.canvas.PlotPaths;
import org.motion.ballsimapp.canvas.PlotScale;
import org.motion.ballsimapp.gwtsafe.Vector3D;
import org.motion.ballsimapp.logic.Ball;
import org.motion.ballsimapp.logic.ShotFinder;
import org.motion.ballsimapp.logic.Table;
import org.motion.ballsimapp.logic.ThreeCushionRuleSet;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ballsimapp implements EntryPoint {


	Timer timer;
	double time;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		final Button sendButton = new Button("Save");
		final TextBox nameField = new TextBox();
	       
	        
		nameField.setText("empty");
		final Label errorLabel = new Label();
		
		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		
		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		
	    // Make a new canvas 
		final GWTCanvas canvas = new GWTCanvas(300, 600);
		
	    final CanvasTable canvasTable = new CanvasTable(canvas);

	    RootPanel.get().add(canvas);
		
	    
    	
		Table t = new Table();
		t.ball(1).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*7,-Ball.R*18,0)));
		t.ball(2).setFirstEvent(Utilities.getStationary(new Vector3D(-Ball.R*6,-Ball.R*6,0)));
		t.ball(3).setFirstEvent(Utilities.getStationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));
					
		ShotFinder finder = new ShotFinder(new ThreeCushionRuleSet(),t);
		
		final Table tResult = finder.FindBest(t.ball(1),65);
		
		final PlotScale ps = new PlotScale(tResult.getAllEvents());
		ps.setWindowInfo(300, 600);
		
		nameField.setText("tmax:"+tResult.getMaxTime());

		time = 0;
		
	    timer = new Timer() {
	    	public void run() {
	    	    canvasTable.renderLoop(ps);
	    	    PlotPaths.plot(ps, canvas, tResult);
	    		for(Ball b: tResult.balls())
	    		{
	    			PlotEvent.plotEvent(b, Interpolator.interpolate(b, time), canvas, ps, true, true);
	    		}
	    		time = time + 0.0015;
	    		if (time > tResult.getMaxTime())
	    			timer.cancel();
	    	}
	    };

		timer.scheduleRepeating(20);
	    	    
	}
	
	
}
