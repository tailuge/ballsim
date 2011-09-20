package org.communications.layer.client.comms;

import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventAttribute;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class GameEventMarshaller {

	final private static String ATTRIBUTES = "attributes";

	public static String marshal(GameEvent event) {
		JSONArray attributes = new JSONArray();
		int index = 0;
		for (GameEventAttribute attribute : event.getAttributes()) {
			JSONObject nameValuePair = new JSONObject();
			nameValuePair.put("name", new JSONString(attribute.getName()));
			nameValuePair.put("value", new JSONString(attribute.getValue()));
			attributes.set(index++, nameValuePair);
		}
		JSONObject jsonGameEvent = new JSONObject();
		jsonGameEvent.put(ATTRIBUTES, attributes);
		return jsonGameEvent.toString();
	}

	public static GameEvent deMarshal(String data) {
		GameEvent event = new GameEvent();

		try {
			JSONValue jsonEvent = JSONParser.parseStrict(data);
			JSONArray attributes = jsonEvent.isObject().get(ATTRIBUTES)
					.isArray();

			for (int i = 0; i < attributes.size(); i++) {
				JSONObject attribute = attributes.get(i).isObject();

				event.addAttribute(new GameEventAttribute(attribute.get("name")
						.isString().stringValue(), attribute.get("value")
						.isString().stringValue()));
			}
		} catch (Exception exception) {
			// failed to parse
			exception.printStackTrace();
			return event;
		}

		return event;
	}
}
