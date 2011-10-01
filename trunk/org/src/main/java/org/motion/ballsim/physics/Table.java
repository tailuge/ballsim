package org.motion.ballsim.physics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsim.physics.ball.State;
import org.motion.ballsim.physics.util.EventPair;
import org.motion.ballsim.physics.util.Interpolate;
import org.motion.ballsim.physics.util.Position;
import org.motion.ballsim.util.Assert;
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
public final class Table implements Serializable {

	private static final long serialVersionUID = -811803763165641433L;

	private final static Logger logger = new Logger("Table", false);

	public static double maxVel = 60.0;
	public static double maxAngVel = 220.0;
	public static double accelRoll = -0.8;
	public static double accelSlide = -15.0;

	private final Map<Integer, Ball> ballMap = new HashMap<Integer, Ball>();

	public boolean hasPockets;

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
				Assert.isTrue(next.t > e.t);
			}
		}

		return next;
	}

	public String toString() {
		return balls().toString();
	}

	public Collection<Event> getAllEvents() {
		Collection<Event> all = new ArrayList<Event>();

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
		Event cueBall = Interpolate.toTime(ball(1), 0);
		Event hit = UtilEvent.hit(cueBall.pos, aim.dir, aim.speed,
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
			Event nextKnuckle = Pocket.nextKnuckleCollision(this, next.t);
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
			Assert.isTrue(Position.onTable(next));
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
			Assert.isTrue(Position.onTable(nextCollision.first));
			Assert.isTrue(Position.onTable(nextCollision.second));
			this.ball(nextCollision.first.ballId).add(nextCollision.first);
			this.ball(nextCollision.second.ballId).add(nextCollision.second);
		}
		if (logger.isEnabled()) {
			logger.info("Table:{}", this);
		}
		Assert.isTrue(Position.validPosition(this));
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
			e.state = State.Stationary;

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


}
