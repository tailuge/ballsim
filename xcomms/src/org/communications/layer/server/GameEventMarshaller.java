package org.communications.layer.server;

import java.util.Map;

import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventAttribute;
import org.mortbay.util.ajax.JSON;
import org.mortbay.util.ajax.JSONObjectConvertor;

public class GameEventMarshaller {

	public static String marshal(GameEvent event) {
		JSON.registerConvertor(GameEvent.class, new JSONObjectConvertor(false));
		JSON.registerConvertor(GameEventAttribute.class,
				new JSONObjectConvertor(false));
		return JSON.toString(event);
	}

	@SuppressWarnings("rawtypes")
	public static GameEvent deMarshal(String data) {
		GameEvent event = new GameEvent();

		try {
			Object eventObject = JSON.parse(data);
			Object attributes = ((Map) eventObject).get("attributes");
			for (Object j : (Object[]) attributes) {
				Map attribute = (Map) j;
				event.addAttribute(new GameEventAttribute(attribute.get("name")
						.toString(), attribute.get("value").toString()));
			}
		} catch (Exception e) {
			// failed to parse
			e.printStackTrace();
		}
		return event;
	}
}
