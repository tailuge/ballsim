package org.oxtail.game.state.reflect;

import org.junit.Test;
import org.mockito.Mockito;
import org.oxtail.game.state.AbstractGameState;

/**
 * Tests {@link ReflectStateActionExecutor}
 * 
 * @author liam knox
 */
public class TestReflectStateActionExecutor {

	@Test
	public void testActionExecution() {
		ReflectStateActionExecutor executor = new ReflectStateActionExecutor();
		DummyState state = Mockito.mock(DummyState.class);
		executor.execute(state, "performAction");
		Mockito.verify(state).performAction();
	}

	@SuppressWarnings("rawtypes")
	private static class DummyState extends AbstractGameState {

		@SuppressWarnings("unchecked")
		public DummyState() {
			super(null);
		}

		public void performAction() {
		}
	}
}
