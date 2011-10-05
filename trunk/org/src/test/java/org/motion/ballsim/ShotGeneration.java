package org.motion.ballsim;

import junit.framework.Assert;

import org.junit.Test;
import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.State;
import org.motion.ballsim.physics.util.Rack;
import org.motion.ballsim.util.UtilEvent;

public class ShotGeneration {

	// easy way to copy in bugs from game play
	
	@Test
	public void redBallMustStayDown() {
		/*
		 * [attributes=[[aim={"pos":{"x":0, "y":15, "z":0}, "dir":{"x":0.11796112688568021, "y":-0.9930182136012714, "z":0}, "spin":{"x":0, "y":0, "z":0}, "speed":0.75}]
		 */
		
		Table t = new Table(true);
		Rack.rack(t, "SimplePool", "1");
		Vector3D pos = new Vector3D(0,15,0);
		Vector3D vel = new Vector3D(0.11796112688568021,-0.9930182136012714,0);		
		Aim aim = new Aim(1,pos,vel,Vector3D.ZERO,0.75);
		t.setAim(aim);
		t.generateNext();
		UtilEvent.prettyPrint(t.getAllEvents());
		System.out.println("events:"+t.getAllEvents().size());
		System.out.println(t.getChecksum());
		System.out.println(t);
		UtilEvent.prettyPrint(t.ball(2).getAllEvents());
		Assert.assertTrue("red potted",t.ball(2).lastEvent().state == State.Stationary);
	}

}
