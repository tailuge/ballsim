package org.communications.layer;

import static org.junit.Assert.assertTrue;

import org.communications.layer.server.GameEventMarshaller;
import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventAttribute;
import org.communications.layer.shared.GameEventUtil;
import org.junit.Test;

public class NonGWT {

	@Test
	public void testServerMarshalling() {
		GameEvent testEvent = GameEventUtil.simpleEvent("user", "frank");
		testEvent.addAttribute(new GameEventAttribute("destination", "mark"));
		String testData = GameEventMarshaller.marshal(testEvent);
		GameEvent result = GameEventMarshaller.deMarshal(testData);
		assertTrue(testEvent.equals(result));
	}

	@Test
	public void testServerMarshallingSimple() {
		GameEvent testEvent = GameEventUtil.simpleEvent("user", "frank");
		String testData = GameEventMarshaller.marshal(testEvent);
		GameEvent result = GameEventMarshaller.deMarshal(testData);
		assertTrue(testEvent.equals(result));
	}

	@Test
	public void testServerMarshallingEmpty() {
		GameEvent testEvent = new GameEvent();
		String testData = GameEventMarshaller.marshal(testEvent);
		GameEvent result = GameEventMarshaller.deMarshal(testData);
		assertTrue(testEvent.equals(result));
	}



}
