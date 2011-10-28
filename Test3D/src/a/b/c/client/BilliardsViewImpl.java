package a.b.c.client;

import static gwt.g3d.client.math.MatrixStack.MODELVIEW;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.gwtsafe.Vector3D;
import org.motion.ballsim.physics.util.Interpolate;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

public class BilliardsViewImpl extends Render implements 
MouseDownHandler, MouseUpHandler, MouseMoveHandler {


	public void showTable(Table table) {
		plotAtTime(table, 0);
	}

	public void plotAtTime(Table table, double t) {

		List<Event> events = new ArrayList<Event>();

		for (Ball ball : table.balls())
			events.add(Interpolate.toTime(ball, t));

		prepareDraw();

		MODELVIEW.push();

		for (Event event : events)
			plotBall(event);

		placeTable();
				placeCue(0,0);

		MODELVIEW.pop();


		setView((float) (Math.sin(t / 3) * 65.0),
				(float) (Math.cos(t / 3) * 50.0));
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		// TODO Auto-generated method stub
		
	}
}
