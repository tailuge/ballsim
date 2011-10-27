package a.b.c.client;

import static gwt.g3d.client.math.MatrixStack.MODELVIEW;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.gwtsafe.Vector3D;
import org.motion.ballsim.physics.util.Interpolate;

public class BilliardsViewImpl extends Assets {


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


		setView((float) (Math.sin(t / 10) * 45.0),
				(float) (Math.cos(t / 10) * 30.0));
	}

	private void plotBall(Event event) {
		Vector3D a = Vector3D.crossProduct(event.angularPos, event.angularPosPerp);
		placeBall(event.pos.getX(), event.pos.getY(),
				event.angularPos.getX(),event.angularPos.getY(),event.angularPos.getZ(),
				event.angularPosPerp.getX(),event.angularPosPerp.getY(),event.angularPosPerp.getZ(),
				a.getX(),a.getY(),a.getZ());
	}
}
