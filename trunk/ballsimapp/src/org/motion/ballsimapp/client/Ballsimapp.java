package org.motion.ballsimapp.client;




import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.canvas.PlotCushion;
import org.motion.ballsimapp.canvas.PlotScale;
import org.motion.ballsimapp.canvas.PowerInputCanvas;
import org.motion.ballsimapp.canvas.SpinInputCanvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ballsimapp implements EntryPoint {

	// main table
	
	Canvas canvas;
	Canvas backBuffer;
	
	// inputs
	
	private final SpinInputCanvas spin = new SpinInputCanvas(50,50);
	private final PowerInputCanvas power = new PowerInputCanvas(400,50);

	
	
	  //timer refresh rate, in milliseconds
	  static final int refreshRate = 25;
	  
	  // canvas size, in px
	  static final int height = 600;
	  static final int width = 400;
	  
	  final CssColor redrawColor = CssColor.make("rgba(255,255,255,0.6)");
	  final CssColor redColor = CssColor.make("rgba(255,0,0,0.6)");
	  Context2d context;
	  Context2d backBufferContext;
	  	  
	//Timer timer;
	double time;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		PlotScale.setWindowInfo(width, height);
		canvas = Canvas.createIfSupported();
	    backBuffer = Canvas.createIfSupported();
	    
		final Button sendButton = new Button("Save");
		final TextBox nameField = new TextBox();
	       
	    // initialise the canvases
	    canvas.setWidth(width + "px");
	    canvas.setHeight(height + "px");
	    canvas.setCoordinateSpaceWidth(width);
	    canvas.setCoordinateSpaceHeight(height);
	    backBuffer.setCoordinateSpaceWidth(width);
	    backBuffer.setCoordinateSpaceHeight(height);
	    RootPanel.get().add(canvas);
	    context = canvas.getContext2d();
	    backBufferContext = backBuffer.getContext2d();
		    
	    
	    RootPanel.get().add(spin.getInitialisedCanvas());
	    RootPanel.get().add(power.getInitialisedCanvas());
	    
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

	   	
		final Table t = new Table();
	//	t.ball(1).setFirstEvent(UtilEvent.stationary(new Vector3D(Ball.R*7,-Ball.R*18,0)));
	//	t.ball(2).setFirstEvent(UtilEvent.stationary(new Vector3D(-Ball.R*6,-Ball.R*6,0)));
	//	t.ball(3).setFirstEvent(UtilEvent.stationary(new Vector3D(Ball.R*8,-Ball.R*3,0)));
					
		
	//	t.generateSequence();
		
		//final PlotScale ps = new PlotScale(t.getAllEvents());
		//ps.setWindowInfo(300, 600);
		
		nameField.setText("tmax:");

		time = 0;
		
	    // setup timer
	    final Timer timer = new Timer() {
	      @Override
	      public void run() {
	        doUpdate();
	      }
	    };
	    timer.scheduleRepeating(refreshRate);
	    	    
	}
	
	void doUpdate() 
	  {

		  moveBackBufferToFront(backBufferContext,context);
		    // update the back canvas
		    backBufferContext.setFillStyle(redrawColor);
		    backBufferContext.fillRect(0, 0, width, height);
		    PlotCushion.plot(backBufferContext);	
		    drawItem(backBufferContext);
	  }
	  
  public void moveBackBufferToFront(Context2d back, Context2d front) 
  {
	    
	  front.drawImage(back.getCanvas(), 0, 0);
  }
  public void drawItem(Context2d context) {
	    context.setFillStyle(redColor);
	    context.beginPath();
	    context.arc(0, 0, 10, 0, Math.PI * 2.0, true);
	    context.closePath();
	    context.fill();
	    
	  }
}
