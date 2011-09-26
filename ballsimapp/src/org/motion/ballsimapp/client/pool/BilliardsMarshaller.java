package org.motion.ballsimapp.client.pool;

import org.motion.ballsim.game.Aim;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Table;
import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.Events;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class BilliardsMarshaller {
	
	public final static GameEvent eventFromAim(Aim aim)
	{
		JSONObject jsonAim = new JSONObject();
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
		JSONObject jdir = jsonAim.isObject().get("dir").isObject();
		JSONObject jspin = jsonAim.isObject().get("spin").isObject();
		
		return new Aim(deMarshal(jdir),deMarshal(jspin),jsonAim.isObject().get("speed").isNumber().doubleValue());
		
	}

	public static Vector3D placeFromEvent(GameEvent event)
	{
		String data = event.getAttribute("place").getValue();
		JSONValue jsonAim = JSONParser.parseStrict(data);		
		return deMarshal(jsonAim.isObject());
	}
	
	private static Vector3D deMarshal(JSONObject v)
	{
		return new Vector3D(
				v.get("x").isNumber().doubleValue(),
				v.get("y").isNumber().doubleValue(),
				v.get("z").isNumber().doubleValue());				
	}
	
	public static Table tableFromEvent()
	{
		return null;
	}
}
