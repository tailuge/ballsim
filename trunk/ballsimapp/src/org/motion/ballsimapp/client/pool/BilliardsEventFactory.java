package org.motion.ballsimapp.client.pool;

import static org.motion.ballsimapp.shared.Events.*;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;

public class BilliardsEventFactory {

	public static GameEvent aimUpdate(Aim aim)
	{
		GameEvent aimUpdate = BilliardsMarshaller.eventFromAim(aim);
		aimUpdate.addAttribute(new GameEventAttribute(AIM_UPDATE, ""));
		return aimUpdate;
	}
	
	public static GameEvent aimComplete(Aim aim)
	{
		GameEvent aimComplete = BilliardsMarshaller.eventFromAim(aim);
		aimComplete.addAttribute(new GameEventAttribute(AIM_COMPLETE, ""));
		return aimComplete;
	}

	public static GameEvent placeBallUpdate(Vector3D pos)
	{
		GameEvent placeBallUpdate = BilliardsMarshaller.eventFromPlace(pos);
		placeBallUpdate.addAttribute(new GameEventAttribute(PLACEBALL_UPDATE, ""));
		return placeBallUpdate;
	}

	public static GameEvent placeBallComplete(Vector3D pos) {
		GameEvent placeBallComplete = BilliardsMarshaller.eventFromPlace(pos);
		placeBallComplete.addAttribute(new GameEventAttribute(PLACEBALL_COMPLETE, ""));
		return placeBallComplete;
	}

}