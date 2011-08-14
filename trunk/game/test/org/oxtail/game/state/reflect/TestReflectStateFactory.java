package org.oxtail.game.state.reflect;

import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.oxtail.exception.OxtailRuntimeException;
import org.oxtail.game.model.StateId;
import org.oxtail.game.state.AbstractGameState;
import org.oxtail.game.state.GameEventContext;
import org.oxtail.game.state.PlayerPendingLogin;
import org.oxtail.game.state.StateFactory;

/**
 * Tests {@link ReflectStateFactory}
 * 
 * @author liam knox
 */
public class TestReflectStateFactory {

	private StateFactory factory = new ReflectStateFactory();

	@Test
	public void testSuccessfulStateCreation() {
		assertNotNull(factory.createState(
				new StateId(PlayerPendingLogin.class), null));
	}

	@Test(expected = OxtailRuntimeException.class)
	public void testNoStateClass() {
		assertNotNull(factory.createState(new StateId("bollocks"), null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStateFailsInConstructor() throws Throwable {
		try {
			assertNotNull(factory.createState(new StateId(FailingState.class),
					null));
		} catch (OxtailRuntimeException e) {
			throw e.getCause();
		}
	}

	@SuppressWarnings("rawtypes")
	public static class FailingState extends AbstractGameState {

		@SuppressWarnings("unchecked")
		public FailingState(GameEventContext context) {
			super(context);
			throw new IllegalArgumentException();
		}

	}
}
