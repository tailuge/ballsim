package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.ball.Ball;
import org.motion.ballsim.physics.ball.Event;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class BilliardsMarshaller {
	
	public final static GameEvent eventFromAim(Aim aim)
	{
		JSONObject jsonAim = new JSONObject();
		jsonAim.put("pos", marshal(aim.pos));
		jsonAim.put("dir", marshal(aim.dir));
		jsonAim.put("spin", marshal(aim.spin));
		jsonAim.put("speed", new JSONNumber(aim.speed));		
		return Events.event("aim", jsonAim.toString());
	}

	public final static GameEvent eventFromPlace(Vector3D place)
	{
		return Events.event("place", marshal(place).toString());
	}

	public final static GameEvent eventFromTable(Table table)
	{
		return null;
	}
	
	private static JSONObject marshal(Vector3D v) {
		JSONObject vector = new JSONObject();
		vector.put("x", new JSONNumber(v.getX()));
		vector.put("y", new JSONNumber(v.getY()));
		vector.put("z", new JSONNumber(v.getZ()));
		return vector;
	}

	public static Aim aimFromEvent(GameEvent event)
	{
		String data = event.getAttribute("aim").getValue();
		JSONValue jsonAim = JSONParser.parseStrict(data);
		JSONObject jpos = jsonAim.isObject().get("pos").isObject();
		JSONObject jdir = jsonAim.isObject().get("dir").isObject();
		JSONObject jspin = jsonAim.isObject().get("spin").isObject();
		
		return new Aim(1,deMarshal(jpos),deMarshal(jdir),deMarshal(jspin),jsonAim.isObject().get("speed").isNumber().doubleValue());
		
	}
	
	private static Vector3D deMarshal(JSONObject v)
	{
		return new Vector3D(
				v.get("x").isNumber().doubleValue(),
				v.get("y").isNumber().doubleValue(),
				v.get("z").isNumber().doubleValue());				
	}
	
	public static JSONObject marshal(Ball ball) 
	{
		Event event = ball.firstEvent();		
		JSONObject jball = new JSONObject();
		jball.put("pos", marshal(event.pos));
		jball.put("vel", marshal(event.vel));
		jball.put("angularVel", marshal(event.angularVel));
		jball.put("ballId", new JSONNumber(ball.id));		
		jball.put("state", new JSONString(event.state.toString()));		
		return jball;
	}

	public static String marshal(Table table) 
	{
		JSONArray ballArray = new JSONArray();
		int index = 0;
		for (Ball ball : table.balls())
		{
			ballArray.set(index++, marshal(ball));
		}
		return ballArray.toString();
	}
}
