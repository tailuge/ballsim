package org.motion.ballsim;

import junit.framework.Assert;

import org.junit.Test;
import org.motion.ballsim.game.Outcome;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.ball.State;
import org.motion.ballsim.physics.util.Interpolate;
import org.motion.ballsim.physics.util.Position;
import org.motion.ballsim.physics.util.Rack;
import org.motion.ballsim.util.UtilEvent;

public class ShotGeneration {

	// easy way to copy in bugs from game play, type 'dump' into chat box
    // process with :
	
	/*
	 * cat p1 \ | sed 's/"//g' \ | sed 's/pos:/new Vector3D/g' \ | sed
	 * 's/vel:/new Vector3D/g' \ | sed 's/angularVel:/new Vector3D/g' \ | sed
	 * 's/x://g' \ | sed 's/y://g' \ | sed 's/z://g' \ | sed 's/{/(/g' \ | sed
	 * 's/}/)\n/g' \ | sed 's/\[//g' \ | sed 's/\]//g' \ | sed 's/ballId://g' \
	 * | sed 's/state:/State./g' \ | sed 's/(new/tableSetup(new/g' \ | sed
	 * 's/,tableSetup/;\ntableSetup/g'
	 */

	@Test
	public void behaviourAtKnuckleTest() {
		
		Table table = new Table(true);
		Rack.rack(table,"1","");		
		table.generateSequence();
		Assert.assertTrue("tenth ball is on table",table.ball(10).lastEvent().state == State.Stationary);
		Assert.assertTrue("tenth ball is on table",Position.onTable(table.ball(10).lastEvent().pos));
	}

	@Test
	public void ballOverlap() {
		
		Table table = new Table(true);
		Rack.rack(table,"3","");		
		table.generateSequence();
		UtilEvent.prettyPrint(table.getAllEvents());
		Outcome o = Outcome.evaluate(table);
		System.out.println("fisrtballhit:" + o.firstBallHit);
		System.out.println("potted:" + o.ballsPotted);
		for(double t = 0; t<3; t += 0.2)
		{
			Event b1 = Interpolate.toTime(table.ball(1), t);
			Event b10 = Interpolate.toTime(table.ball(10), t);
			Assert.assertTrue("balls 1 and 10 seperated",Vector3D.distance(b1.pos,b10.pos) > 2*Ball.R);
		}
	}

}
