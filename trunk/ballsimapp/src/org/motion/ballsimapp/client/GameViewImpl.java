package org.motion.ballsimapp.client;

import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.canvas.Animation;
import org.motion.ballsimapp.canvas.PowerInputCanvas;
import org.motion.ballsimapp.canvas.SpinInputCanvas;
import org.motion.ballsimapp.canvas.TableCanvas;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

public class GameViewImpl implements GameView {

	RootPanel root;

	// inputs
	
	private  SpinInputCanvas spin;
	private  PowerInputCanvas power;
	final Button hitButton = new Button("Hit");	

	// outputs
	
	private  TableCanvas tableCanvas;
	final TextArea messageArea = new TextArea();
	final Label playerName = new Label();

	// callbacks
	
	AimHandler aimHandler;
	

	public GameViewImpl(int width, RootPanel root)
	{
		int height = width * 15 / 10;
		this.root= root;
		spin = new SpinInputCanvas(width/8,width/8);
		power = new PowerInputCanvas(width-width/8,width/16);
		tableCanvas = new TableCanvas(width,height);
		messageArea.setWidth(width +"px");	
		addElementsToRoot();
	}
	
	private void addElementsToRoot()
	{
		root.add(playerName);
		root.add(tableCanvas.getInitialisedCanvas());
		root.add(spin.getInitialisedCanvas());
		root.add(power.getInitialisedCanvas());
		root.add(hitButton);    
		root.add(messageArea);    		
	}
	
	@Override
	public void setPlayer(String playerId) {
		playerName.setText(playerId);		
	}



	@Override
	public void appendMessage(String message) {
		messageArea.setText(message + ", " + messageArea.getText());		
	}




	@Override
	public void addAimCompleteHandler(final AimHandler aimHandler) {
		hitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {				
				hitButton.setEnabled(false);
				aimHandler.handleAim();
			}
		});
	}



	/* (non-Javadoc)
	 * @see org.motion.ballsimapp.client.GameView#aim(int)
	 * 
	 * Place view is state where it gets users aiming input.
	 * 
	 */
	@Override
	public void aim(Table table, int timeout) {
		
		hitButton.setEnabled(true);
		tableCanvas.plotAtTime(table, 0);
		tableCanvas.beginAim(table);
		
	}



	@Override
	public void animate(Table table) {
		
		hitButton.setEnabled(false);
		
		@SuppressWarnings("unused")
		Animation showAnimation = new Animation(table,tableCanvas);
		
	}
	

	
}
