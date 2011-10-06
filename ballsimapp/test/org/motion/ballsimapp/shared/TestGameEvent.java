package org.motion.ballsimapp.shared;

import org.junit.Assert;
import org.junit.Test;

public class TestGameEvent {

	@Test
	public void testAddGetAndRemove() {
		GameEvent e = new GameEvent();
		e.addAttribute(new GameEventAttribute("a", "b"));
		Assert.assertEquals("b", e.getAttribute("a").getValue());
		Assert.assertEquals("a", e.getAttribute("a").getName());
		e.removeAttribute("a");
		Assert.assertNull(e.getAttribute("a"));

	}
}
