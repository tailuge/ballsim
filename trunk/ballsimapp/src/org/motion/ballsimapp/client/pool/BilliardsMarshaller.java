package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;

public class BilliardsMarshaller {
	
	public final static GameEvent aimUpdate(Vector3D dir,Vector3D spin,double speed)
	{
		GameEvent event = new GameEvent();
	
//		JSONObject aim = new JSONObject();
		
		return event;
	}

	public static JSONObject marshal(double x,double y ,double z) {
		JSONObject vector = new JSONObject();
		vector.put("x", new JSONNumber(x));
		vector.put("y", new JSONNumber(y));
		vector.put("z", new JSONNumber(z));
		return vector;
	}
	
//	private final static GameEvent marshalVector(String name,Vector3D vec)
//	{
	//	GameEvent event = new GameEvent();
//		event.addAttribute(new GameEventAttribute(name+"x",vec.getX()));
		//return event;
	//}
}
