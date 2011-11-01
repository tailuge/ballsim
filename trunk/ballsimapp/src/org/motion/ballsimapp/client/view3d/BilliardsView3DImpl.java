package org.motion.ballsimapp.client.view3d;

import static gwt.g3d.client.math.MatrixStack.MODELVIEW;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.game.Aim;
import org.motion.ballsim.physics.gwtsafe.Vector3D;
import org.motion.ballsim.physics.util.Interpolate;
import org.motion.ballsimapp.client.comms.GWTGameEventHandler;
import org.motion.ballsimapp.client.pool.TableView;

public class BilliardsView3DImpl extends Inputs implements TableView {

	private Table table;
	
	public BilliardsView3DImpl(String layoutId) {
		super(layoutId);
	}

	public void showTable(Table table) {
		this.table = table;
		plotAtTime(table, 0);
	}

	public void plotAtTime(Table table, double t) {
		this.table = table;
		List<Event> events = new ArrayList<Event>();

		for (Ball ball : table.balls())
			events.add(Interpolate.toTime(ball, t));
		
		prepareDraw();

		inputPos = events.get(0).pos;
		//Aim aim = new Aim(0, inputPos, inputDir, inputSpin,	0);

		//setAim(aim);
		
		MODELVIEW.push();

		for (Event event : events)
			plotBall(event);

		placeTable();

		updateThrust(t);
		placeCue(inputPos, inputAngle, inputSpin, cueSwing);

		MODELVIEW.pop();

	}

	private void plotBall(Event event) {
		Vector3D a = Vector3D.crossProduct(event.angularPos,
				event.angularPosPerp);
		placeBall(event.ballId - 1, event.pos.getX(), event.pos.getY(),
				event.angularPos.getX(), event.angularPos.getY(),
				event.angularPos.getZ(), event.angularPosPerp.getX(),
				event.angularPosPerp.getY(), event.angularPosPerp.getZ(),
				a.getX(), a.getY(), a.getZ());
	}

	@Override
	public void setAim(Aim aim) {
		camera.setAimView(aim.pos, aim.dir);
		if (table != null)
			showTable(table);
	}

	@Override
	public void setEventHandler(GWTGameEventHandler eventHandler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aim(int timeout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void place(int timeout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void watch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void animate(Table shotSequence) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAim() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showPlacer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVisibility(boolean visible) {
		// TODO Auto-generated method stub
		
	}

}
