package org.motion.ballsimapp.client.pool;

import static org.motion.ballsimapp.shared.Events.*;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.game.Outcome;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;

public class BilliardsEventFactory {

	public static GameEvent aimUpdate(Aim aim) {
		GameEvent aimUpdate = BilliardsMarshaller.eventFromAim(aim);
		aimUpdate.addAttribute(new GameEventAttribute(AIM_UPDATE, ""));
		aimUpdate.addAttribute(new GameEventAttribute(ACTION, "aim"));
		return aimUpdate;
	}

	public static GameEvent aimComplete(Aim aim) {
		GameEvent aimComplete = BilliardsMarshaller.eventFromAim(aim);
		aimComplete.addAttribute(new GameEventAttribute(AIM_COMPLETE, ""));
		aimComplete.addAttribute(new GameEventAttribute(ACTION, "shot"));
		return aimComplete;
	}

	public static GameEvent placeBallUpdate(Vector3D pos) {
		GameEvent placeBallUpdate = BilliardsMarshaller.eventFromPlace(pos);
		placeBallUpdate.addAttribute(new GameEventAttribute(PLACEBALL_UPDATE,
				""));
		placeBallUpdate.addAttribute(new GameEventAttribute(ACTION, "aim"));
		return placeBallUpdate;
	}

	public static GameEvent placeBallComplete(Vector3D pos) {
		GameEvent placeBallComplete = BilliardsMarshaller.eventFromPlace(pos);
		placeBallComplete.addAttribute(new GameEventAttribute(
				PLACEBALL_COMPLETE, ""));
		placeBallComplete.addAttribute(new GameEventAttribute(ACTION, "aim"));
		return placeBallComplete;
	}

	public static GameEvent hitOutcome(Table table, Aim aim) {
		Outcome outcome = Outcome.evaluate(table);
		GameEvent hitEvent = BilliardsMarshaller.eventFromAim(aim);
		hitEvent.addAttribute(new GameEventAttribute(AIM_COMPLETE, ""));
		hitEvent.addAttribute(new GameEventAttribute(ACTION, "shot"));
		hitEvent.addAttribute(new GameEventAttribute(GAME_SHOT_BALLSPOTTED,
				outcome.ballsPotted.toString()));
		hitEvent.addAttribute(new GameEventAttribute(GAME_SHOT_FIRST_BALL_HIT,
				"" + outcome.firstBallHit));
		hitEvent.addAttribute(new GameEventAttribute(
				GAME_SHOT_TOTAL_BALLS_HITTING_CUSHION, ""
						+ outcome.totalBallsHittingCushion));
		hitEvent.addAttribute(new GameEventAttribute(
				GAME_SHOT_CUSHION_BEFORE_SECOND_BALL, ""
						+ outcome.cushionsBeforeSecondBall));
		hitEvent.addAttribute(new GameEventAttribute(GAME_SHOT_ANYBALLHIT,
				outcome.firstBallHit != 0 ? "true" : "false")); // remove
		return hitEvent;
	}

}
