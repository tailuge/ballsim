package org.oxtail.game.server.event;

import org.motion.ballsimapp.shared.GameEvent;
import org.motion.ballsimapp.shared.GameEventAttribute;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Server side helper for the {@link GameEvent}, to simplify access without
 * complicating the communication classes
 */
public class GameEventHelper {

	private final GameEvent event;

	public GameEventHelper(GameEvent event) {
		this.event = event;
	}

	public String getString(String name) {
		GameEventAttribute attribute = event.getAttribute(name);
		if (attribute == null)
			throw new IllegalArgumentException("no such attribute " + name);
		return attribute.getValue();
	}

	public Integer getInt(String name) {
		return Integer.parseInt(getString(name));
	}

	public boolean hasValue(String name) {
		return event.hasAttribute(name);
	}

	public <T> void setValue(String name, T value) {
		event.addAttribute(new GameEventAttribute(name, String.valueOf(value)));
	}

	public <T> void setValues(String name, T value, T... rest) {
		Iterable<T> all = Lists.asList(value, rest);
		setValue(name, Joiner.on(",").join(all).toString());
	}

	public <T> void setConvertedValues(String name, Iterable<T> values,
			Function<T, String> conversion) {
		Iterable<String> all = Iterables.transform(values, conversion);
		String value = Joiner.on(",").join(all).toString();
		setValue(name, value);
	}

	public GameEvent getEvent() {
		return event;
	}

}
