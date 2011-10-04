package org.motion.ballsim;

import static org.junit.Assert.*;
import org.junit.Test;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Cushion;
import org.motion.ballsim.physics.Knuckle;
import org.motion.ballsim.physics.Pocket;
import org.motion.ballsim.physics.PocketGeometry;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.ball.State;
import org.motion.ballsim.physics.ball.Transition;
import org.motion.ballsim.util.UtilEvent;


public class PocketTest {

	
	@Test
	public final void testPocketKnuckle() 
	{
		// roll to middle right pocket knuckle
		
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(50));
		e.pos = PocketGeometry.p6k1.add(new Vector3D(-3*Ball.R,+0.2,0));
		Event c1 = Knuckle.nextKnuckleCollision(e, e.next().t);

		assertNotNull("Collides",c1);
		assertTrue("event is after time zero",c1.t > 0);
		assertTrue("velocity is reflected",c1.vel.getX() < 0);
		assertTrue("velocity is reflected partially downward",c1.vel.getY() > 0);
		assertTrue("ball is sliding",c1.state == State.Sliding);
		assertTrue("event type is knuckle collision",c1.type == Transition.KnuckleCushion);
		
		System.out.println(e);
		System.out.println(c1);
		
	}
	
	@Test
	public final void testPocketPot() 
	{
		// roll to middle right pocket knuckle
		
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(50));
		e.pos = PocketGeometry.p6.add(new Vector3D(-6*Ball.R,+0.2,0));
		Event c1 = Pocket.nextPot(e, e.next().t);

		assertNotNull("Collides",c1);
		assertTrue("event is after time zero",c1.t > 0);
		assertTrue("ball is potted",c1.state == State.FallingInPocket);
		assertTrue("event type is knuckle collision",c1.type == Transition.Potting);
		
		System.out.println(e);
		System.out.println(c1);
		
	}
	
	private Table getTableWithPot()
	{
		Table table = new Table(true);
		
		// target middle right pocket  1->2->3->Pocket

		placeBallAtDistanceFromMiddleRightPocket(table,1, 12 * Ball.R);
		placeBallAtDistanceFromMiddleRightPocket(table,2, 9 * Ball.R);
		placeBallAtDistanceFromMiddleRightPocket(table,3, 6 * Ball.R);

		return table;
	}

	private void hitWhite(Table table)
	{
		table.ball(1).setFirstEvent(UtilEvent.hit(table.ball(1).lastEvent().pos, Vector3D.PLUS_I, 1, 0));		
	}
	
	private void placeBallAtDistanceFromMiddleRightPocket(Table table, int ballId, double distance)
	{
		table.ball(ballId).setFirstEvent(UtilEvent.stationary(PocketGeometry.p6.add(-distance, Vector3D.PLUS_I)));		
	}
	
	@Test
	public final void testSinglePot() 
	{
		Table table = getTableWithPot();
		hitWhite(table);
		table.generateSequence();
		UtilEvent.prettyPrint(table.getAllEvents());
		assertTrue("potted ball 3",table.ball(3).lastEvent().state == State.InPocket);
		assertTrue("on table ball 1",table.ball(1).lastEvent().state == State.Stationary);
	}
	
	@Test
	public final void testStateAfterPot() 
	{
		Table table = getTableWithPot();
		hitWhite(table);
		table.generateSequence();
		table.beginNewShot();
		UtilEvent.prettyPrint(table.getAllEvents());
		assertTrue("potted ball 3",table.ball(3).lastEvent().state == State.InPocket);
		assertTrue("on table ball 1",table.ball(1).lastEvent().state == State.Stationary);
	}

	@Test
	public final void testSecondPot() 
	{
		Table table = getTableWithPot();
		hitWhite(table);
		table.generateSequence();
		table.beginNewShot();
		hitWhite(table);
		table.generateSequence();
		
		UtilEvent.prettyPrint(table.getAllEvents());
		assertTrue("potted ball 2",table.ball(2).lastEvent().state == State.InPocket);
		assertTrue("on table ball 1",table.ball(1).lastEvent().state == State.Stationary);
	}

	@Test
	public final void testBallIsInJaws() 
	{
		Event e = getBallInPocketJaws();
		assertTrue("is beyond cushion in jaw",e.pos.getX() > Cushion.xp);
		System.out.println(e);		
	}

	@Test
	public final void testRollOutOfJaws() 
	{
		Event e = getBallInPocketJaws();
		e.vel=Vector3D.MINUS_I.scalarMultiply(50);
		e.state=State.deriveStateOf(e);
		
		Event c1 = e.next();
		System.out.println(c1);				
		assertTrue("on table after next state",c1.pos.getX() < Cushion.xp);		
		
		assertNull(Cushion.hit(e, c1.t, true));
	}

	private final Event getBallInPocketJaws()
	{
		Event e = Utilities.getRolling(Vector3D.PLUS_I.scalarMultiply(250));
		e.pos = PocketGeometry.p6k1.add(new Vector3D(-3*Ball.R,+0.2,0));
		Event c1 = Knuckle.nextKnuckleCollision(e, e.next().t);
		
		return UtilEvent.stationary(c1.pos);
	}
}
