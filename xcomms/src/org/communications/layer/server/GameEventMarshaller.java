package org.communications.layer.server;

import java.util.ArrayList;
import java.util.List;

import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventAttribute;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

public class GameEventMarshaller {

	public static String marshal(GameEvent event) {
		try {

			List<JSONObject> list = new ArrayList<JSONObject>();
			
			for(GameEventAttribute a : event.getAttributes())
			{
				JSONObject attribute = new JSONObject();
				attribute.put("name",a.getName());
				attribute.put("value",a.getValue());
				list.add(attribute);
			}			
			
			JSONObject jsonEvent = new JSONObject();
			jsonEvent.put("attributes", list);
			
			return jsonEvent.toString();
			

		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static GameEvent deMarshal(String data) {
		GameEvent event = new GameEvent();
		try {
			JSONObject jsonEvent = new JSONObject(data);
			JSONArray attributes = (JSONArray)jsonEvent.get("attributes");
			for(int i =0 ; i < attributes.length(); i++)
			{
				JSONObject attribute = attributes.getJSONObject(i);
				event.addAttribute(new GameEventAttribute(attribute.getString("name"),attribute.getString("value")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return event;
	}
}
