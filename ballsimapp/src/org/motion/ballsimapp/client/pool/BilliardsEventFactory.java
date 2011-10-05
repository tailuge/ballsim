package org.motion.ballsimapp.client.pool;

import static org.motion.ballsimapp.shared.Events.*;

import java.util.ArrayList;
import java.util.List;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.game.Outcome;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.client.util.StringUtil;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;

public class BilliardsEventFactory {

	public static GameEvent aimUpdate(Aim aim) {
		GameEvent aimUpdate = BilliardsMarshaller.eventFromAim(aim);
		aimUpdate.addAttribute(new GameEventAttribute(AIM_UPDATE, ""));
		aimUpdate.addAttribute(new GameEventAttribute(ACTION, "aim"));
		return aimUpdate;
	}

	public static GameEvent inputComplete(Aim aim) {
		GameEvent aimComplete = BilliardsMarshaller.eventFromAim(aim);
		aimComplete.addAttribute(new GameEventAttribute(CURSOR_INPUT_COMPLETE,
				""));
		aimComplete.addAttribute(new GameEventAttribute(ACTION, "shot"));
		return aimComplete;
	}

	public static GameEvent placeBallUpdate(Aim aim) {
		GameEvent placeBallUpdate = BilliardsMarshaller.eventFromAim(aim);
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

	public static GameEvent cursorInput(Aim aim) {
		GameEvent cursorInput = BilliardsMarshaller.eventFromAim(aim);
		cursorInput.addAttribute(new GameEventAttribute(CURSOR_INPUT, ""));
		return cursorInput;
	}

	public static GameEvent beginLogin() {
		return Events.event(INITIATE_CONNECT, "");
	}

	public static GameEvent sendChat(String message) {
		GameEvent chat = Events.event(ACTION, CHAT);
		chat.addAttribute(new GameEventAttribute(CHAT_TO, "*"));
		chat.addAttribute(new GameEventAttribute(CHAT_MESSAGE, message));
		return chat;
	}

	public static GameEvent hitOutcome(Table table, Aim aim) {
		Outcome outcome = Outcome.evaluate(table);

		// modified for cue ball to be 0, refactor
		List<String> potted = new ArrayList<String>();

		for (int i : outcome.ballsPotted)
			potted.add("" + (i - 1));

		GameEvent hitEvent = BilliardsMarshaller.eventFromAim(aim);
		hitEvent.addAttribute(new GameEventAttribute(AIM_COMPLETE, ""));
		hitEvent.addAttribute(new GameEventAttribute(ACTION, "shot"));
		hitEvent.addAttribute(new GameEventAttribute(GAME_SHOT_BALLSPOTTED,
				StringUtil.toString(potted)));
		hitEvent.addAttribute(new GameEventAttribute(GAME_SHOT_FIRST_BALL_HIT,
				"" + (outcome.firstBallHit - 1)));
		hitEvent.addAttribute(new GameEventAttribute(
				GAME_SHOT_TOTAL_BALLS_HITTING_CUSHION, ""
						+ outcome.totalBallsHittingCushion));
		hitEvent.addAttribute(new GameEventAttribute(
				GAME_SHOT_CUSHION_BEFORE_SECOND_BALL, ""
						+ outcome.cushionsBeforeSecondBall));
		hitEvent.addAttribute(new GameEventAttribute(GAME_SHOT_ANYBALLHIT,
				outcome.firstBallHit != 0 ? "true" : "false")); // remove
		
		// for debug
		hitEvent.addAttribute(new GameEventAttribute(TABLE_CHECKSUM,table.getChecksum()));
		
		
		return hitEvent;
	}

}
