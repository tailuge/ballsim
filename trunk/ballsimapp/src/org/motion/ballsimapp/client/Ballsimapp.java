package org.motion.ballsimapp.client;



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
		GWTCanvas canvas = new GWTCanvas(300, 600);
		
	    final CanvasTable canvasTable = new CanvasTable(canvas);

	    RootPanel.get().add(canvas);
		
	    timer = new Timer() {
	    	public void run() {
	    	    canvasTable.renderLoop();
	    	}
	    };
	    
	    timer.scheduleRepeating(25);
	    	    
	}
	
	
}
