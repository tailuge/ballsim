package org.motion.ballsim.physics;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.util.EventPair;
import org.motion.ballsim.physics.util.Interpolate;
import org.motion.ballsim.physics.util.Position;
import org.motion.ballsim.util.Guard;
import org.motion.ballsim.util.Logger;
import org.motion.ballsim.util.UtilEvent;

/**
 * @author luke
 * 
 *         Table holds a list of balls. By calling the generate method all
 *         future events for these balls are computed. This allows an initial
 *         position to be evaluated to the point all balls are at rest.
 * 
 */
public final class Table {

	private final static Logger logger = new Logger("Table", false);

	public static final double maxVel = 60.0;
	public static final double maxAngVel = 220.0;
	public static final double accelRoll = -0.8;
	public static final double accelSlide = -15.0;

	private final Map<Integer, Ball> ballMap = new TreeMap<Integer, Ball>();

	public final boolean hasPockets;

	public Table() {
		hasPockets = false;
	}

	public Table(boolean hasPockets_) {
		hasPockets = hasPockets_;
	}

	/**
	 * Given all balls that are on the table check each to see when the next
	 * rolling or stationary transition will occur
	 * 
	 * @return
	 */
	public Event nextNatural() {
		Event next = null;
		for (Ball ball : balls()) {
			Event e = ball.lastEvent();
			if (e.state.isMotionlessEndState())
				continue;

			Event eNext = e.next();
			if ((next == null) || (eNext.t < next.t)) {
				next = eNext;
				Guard.isTrue(Guard.active && (next.t > e.t));
			}
		}

		return next;
	}

	public String toString() {
		return balls().toString();
	}

	public Collection<Event> getAllEvents() {
		Collection<Event> all = new LinkedList<Event>();

		for (Ball ball : balls()) {
			all.addAll(ball.getAllEvents());
		}

		return all;
	}

	/**
	 * given a table with balls (possibly in motion) this method expands the
	 * event list until all balls are at rest.
	 * 
	 */
	public int generateSequence() {
		int count = 0;
		while (generateNext())
			count++;
		return count;
	}

	public void setAim(Aim aim) {
		Event hit = UtilEvent.hit(aim.pos, aim.dir, aim.speed,
				aim.spin.getY());
		ball(1).setFirstEvent(hit);
	}

	/**
	 * Compare times of all possible next events and add only the first to occur
	 * in the time line.
	 * 
	 * @return true if there was a new event added
	 */
	public boolean generateNext() {
		// next natural event
		if (logger.isEnabled()) {
			logger.info("Initial conditions for iteration:");
			for (Ball ball : balls()) {
				logger.info("Ball {} : {}", ball.id, ball.lastEvent().format());
			}
		}
		Event next = nextNatural();
		if (next == null)
			return false;

		// use bounds of this to look for next cushion collision

		Event nextCushion = Cushion.nextCushionHit(this, next.t);
		if ((nextCushion != null) && (nextCushion.t < next.t))
			next = nextCushion;

		// use bounds of this to look for pocket collisions

		if (hasPockets) {
			Event nextKnuckle = Knuckle.nextKnuckleCollision(this, next.t);
			if ((nextKnuckle != null) && (nextKnuckle.t < next.t))
				next = nextKnuckle;

			Event nextPocket = Pocket.nextPot(this, next.t);
			if ((nextPocket != null) && (nextPocket.t < next.t))
				next = nextPocket;
		}

		// use bounds of these to look for next ball/ball collision

		EventPair nextCollision = Collision.nextBallCollision(this, next.t);

		// add the soonest of these outcomes

		if ((nextCollision == null) || (next.t < nextCollision.first.t)) {
			if (logger.isEnabled()) {
				logger.info("Single event");
				logger.info("Ball {} : {}", next.ballId, next.format());
				logger.info("nextCollision {}", nextCollision);
			}
			Guard.isTrue(Guard.active && Position.onTable(next.pos));
			this.ball(next.ballId).add(next);
		} else {
			if (logger.isEnabled()) {
				logger.info("Collision event: {}", nextCollision);
				logger.info("Collision event time: {}", nextCollision.first.t);
				logger.info("Discarded single event: {}", next);
				logger.info("Discarded single event time: {}", next.t);
				logger.info("Collision 1: {}", nextCollision.first.format());
				logger.info("Collision 2: {}", nextCollision.second.format());
			}
			Guard.isTrue(Guard.active && Position.onTable(nextCollision.first.pos));
			Guard.isTrue(Guard.active && Position.onTable(nextCollision.second.pos));
			this.ball(nextCollision.first.ballId).add(nextCollision.first);
			this.ball(nextCollision.second.ballId).add(nextCollision.second);
		}
		if (logger.isEnabled()) {
			logger.info("Table:{}", this);
		}
		Guard.isTrue(Guard.active && Position.validPosition(this));
		return true;
	}

	public void reset() {
		for (Ball ball : balls()) {
			ball.resetToFirst();
		}
	}

	public void beginNewShot() {
		for (Ball ball : balls()) {
			Event e = Interpolate.toTime(ball, getMaxTime());

			e.t = 0;
			e.vel = Vector3D.ZERO;
			e.angularVel = Vector3D.ZERO;
			ball.setFirstEvent(e);
		}
	}

	public double getMaxTime() {
		double latest = 0;
		for (Ball a : balls()) {
			if (a.lastEvent().t > latest)
				latest = a.lastEvent().t;
		}
		return latest;
	}

	public Collection<Ball> balls() {
		return ballMap.values();
	}

	public Ball ball(int id) {
		if (!ballMap.containsKey(id))
			ballMap.put(id, new Ball(id));

		return ballMap.get(id);
	}

	public void placeBall(int ballId, Vector3D pos) {
		ball(ballId).setFirstEvent(UtilEvent.stationary(pos));
	}


	public String getChecksum()
	{
		double pre = 0;
		double post = 0;
		
		for(Ball ball : balls())
		{
			pre += ball.firstEvent().pos.getNormSq();
			pre += ball.firstEvent().vel.getNormSq();
//			pre += ball.firstEvent().angularPos.getNormSq();
//			pre += ball.firstEvent().angularVel.getNormSq();
			
			post += ball.lastEvent().pos.getNormSq();
			post += ball.lastEvent().vel.getNormSq();
//			post += ball.lastEvent().angularPos.getNormSq();
//			post += ball.lastEvent().angularVel.getNormSq();
			
		}
		return "B-"+pre+" A-"+post;
	}
}
