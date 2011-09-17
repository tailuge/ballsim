package org.oxtail.game.numberguess.simulation;

import java.util.concurrent.ExecutorService;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.state.GameStatemachine;

/**
 * Every {@link GameEvent} is queued for execution
 */
public class TaskBasedStatemachine implements GameStatemachine {

	private GameStatemachine delegate;

	private ExecutorService executorService;

	public TaskBasedStatemachine(GameStatemachine delegate,
			ExecutorService executorService) {
		this.delegate = delegate;
		this.executorService = executorService;
	}

	@Override
	public void execute(GameEvent event) {
		executorService.submit(new GameEventTask(event));
	}

	private class GameEventTask implements Runnable {

		private final GameEvent event;

		public GameEventTask(GameEvent event) {
			this.event = event;
		}

		@Override
		public void run() {
			delegate.execute(event);
		}
	}
}
