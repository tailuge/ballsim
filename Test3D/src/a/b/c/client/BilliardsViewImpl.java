package a.b.c.client;

import static gwt.g3d.client.math.MatrixStack.MODELVIEW;

import gwt.g3d.client.Surface3D;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.game.Aim;
import org.motion.ballsim.physics.gwtsafe.Vector3D;
import org.motion.ballsim.physics.util.Interpolate;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class BilliardsViewImpl extends Render implements 
MouseDownHandler, MouseUpHandler, MouseMoveHandler, BilliardsView {

	/** Mouse handler registration */
	private HandlerRegistration mouseDownRegistration, mouseUpRegistration,
			mouseMoveRegistration;

	double angle = 0;
	
	public BilliardsViewImpl(Surface3D surface)
	{
		mouseDownRegistration = surface.addMouseDownHandler(this);
		mouseUpRegistration = surface.addMouseUpHandler(this);
		mouseMoveRegistration = surface.addMouseMoveHandler(this);		
	}

	
	public void showTable(Table table) {
		plotAtTime(table, 0);
	}

	public void plotAtTime(Table table, double t) {

		List<Event> events = new ArrayList<Event>();

		for (Ball ball : table.balls())
			events.add(Interpolate.toTime(ball, t));

		prepareDraw();

		Vector3D aimDir = new Vector3D(Math.sin(angle),Math.cos(angle),0);
		Aim aim = new Aim(0, events.get(0).pos, aimDir, new Vector3D(0,0,0), 0);

		setAim(aim);

		MODELVIEW.push();

		for (Event event : events)
			plotBall(event);

		placeTable();
				
		placeCue(events.get(0).pos.getX(),events.get(0).pos.getY(),angle,Math.sin(t*3));


		MODELVIEW.pop();


	}

	private void plotBall(Event event) {
		Vector3D a = Vector3D.crossProduct(event.angularPos, event.angularPosPerp);
		placeBall(event.ballId-1,event.pos.getX(), event.pos.getY(),
				event.angularPos.getX(),event.angularPos.getY(),event.angularPos.getZ(),
				event.angularPosPerp.getX(),event.angularPosPerp.getY(),event.angularPosPerp.getZ(),
				a.getX(),a.getY(),a.getZ());
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		angle = ((double)event.getX() / 500.0)*2.0*Math.PI;
			}

	@Override
	public void onMouseUp(MouseUpEvent event) {
			}

	@Override
	public void onMouseDown(MouseDownEvent event) {		
	}


	@Override
	public void setAim(Aim aim) {
		
		setAimView(aim.pos,aim.dir);
		
		
	}
	
	
}
