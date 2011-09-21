package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.util.UtilEvent;
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

public class BilliardsViewImpl implements BilliardsView {

	RootPanel root;

	// inputs
	
	private  SpinInputCanvas spin;
	private  PowerInputCanvas power;
	final Button hitButton = new Button("Hit");	

	// outputs
	
	private  TableCanvas tableCanvas;
	final TextArea messageArea = new TextArea();
	final Label playerName = new Label();
	final static String newline = "\n";

	// callbacks
	
	AimNotify aimHandler;
	ViewNotify animationComplete;

	public BilliardsViewImpl(int width, RootPanel root)
	{
		int height = width * 15 / 10;
		this.root= root;
		spin = new SpinInputCanvas(width/8,width/8);
		power = new PowerInputCanvas(width-width/4,width/10);
		tableCanvas = new TableCanvas(width/2,height/2);
		messageArea.setWidth(width*3 +"px");	
		messageArea.setHeight(width +"px");	
		
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
		messageArea.setText(messageArea.getText() + newline + message);		
	}




	@Override
	public void setAimCompleteHandler(final AimNotify aimHandler) {
		hitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {				
				hitButton.setEnabled(false);
				aimHandler.handle(
						UtilEvent.hit(tableCanvas.getAimPoint(),tableCanvas.getAim(),Table.maxVel*power.getPower(), spin.getSpin()));
			}
		});
	}

	@Override
	public void setAnimationCompleteHandler(final ViewNotify animationComplete) {
		this.animationComplete = animationComplete;
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
		Animation showAnimation = new Animation(table,tableCanvas,animationComplete);
		
	}
	

	
}
