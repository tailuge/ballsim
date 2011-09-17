package org.oxtail.game.numberguess.simulation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.oxtail.game.event.GameEvent;
import org.oxtail.game.event.GameEventAttribute;
import org.oxtail.game.event.GameEventCallback;
import org.oxtail.game.model.Player;
import org.oxtail.game.numberguess.state.PlayerState;
import org.oxtail.game.server.event.GameEventHelper;
import org.oxtail.game.state.GameStatemachine;

import com.google.common.base.Supplier;

public class NumberGuessPlayer implements Runnable, GameEventCallback {

	private static final String[] messages = { "Arse Face", "God your Great",
			"You smell of piss", "I Love You", "Chees and Beans", "Sheep",
			"Gary Glitter", "Your Mother Sucks Cocks in Hell" };

	private static final Random random = new Random();

	private volatile boolean isRunning;

	private Player player;

	private volatile String gameId;

	private GameStatemachine statemachine;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

	private Supplier<Long> thinkTime;

	private ExecutorService onEventExecutor = Executors
			.newSingleThreadExecutor();

	public NumberGuessPlayer(Supplier<Long> thinkTime, Player player,
			GameStatemachine statemachine) {
		this.thinkTime = thinkTime;
		this.player = player;
		this.statemachine = statemachine;
		this.player.setCallbackDelegate(this);
	}

	@Override
	public void run() {
		isRunning = true;
		log("starting...");
		while (isRunning) {
			runLoop();
		}
		log("stopping...");
		stop();
		log("stopped");
	}

	private void stop() {
		onEventExecutor.shutdown();
	}

	private void runLoop() {
		try {
			log("thinking...");
			think();
			maybeChat();
			PlayerState state = PlayerState.valueOf(player.getState());
			switch (state) {
			case LoggedOut:
				doWhenLoggedOut();
			}
		} catch (InterruptedException e) {
			log("interuppted...");
			isRunning = false;
		} catch (Exception e) {
			log("fuck... " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	private void maybeChat() {
		GameEvent chat = newActionEvent("chat");
		if (gameId != null) {
			if (random.nextDouble() < 0.3) {
				chat.addAttribute(new GameEventAttribute("game.id", gameId));
				chat.addAttribute(new GameEventAttribute("chat.message",
						pickMessage()));
				chat.addAttribute(new GameEventAttribute("move", "-1"));
				notify(chat);
			}
		}
	}

	private String pickMessage() {
		int index = random.nextInt(messages.length);
		return messages[index];
	}

	private void doWhenLoggedOut() {
		log("trying to log in...");
		doLogin();
	}

	private void doLogin() {
		notifyAction("start");
	}

	private void notifyAction(String action) {
		notify(newActionEvent(action));
	}

	private void notify(GameEvent gameEvent) {
		statemachine.execute(gameEvent);
	}

	private GameEvent newActionEvent(String action) {
		GameEvent gameEvent = new GameEvent();
		gameEvent.addAttribute(new GameEventAttribute("player.alias", player
				.getAlias()));
		gameEvent.addAttribute(new GameEventAttribute("action", action));
		return gameEvent;
	}

	private void log(String message) {
		System.err.println(format(message));
	}

	private void log(String gameId, String message) {
		System.err.println(format(gameId, message));
	}

	private String format(String message) {
		return now() + "[" + Thread.currentThread().getName() + "]["
				+ player.getAlias() + "] " + message;
	}

	private String format(String gameId, String message) {
		return now() + "[" + Thread.currentThread().getName() + "]["
				+ player.getAlias() + "][" + gameId + "]" + message;
	}

	private String now() {
		return dateFormat.format(new Date());
	}

	private void think() throws InterruptedException {
		sleep(thinkTime.get());
	}

	private void sleep(long duration) throws InterruptedException {
		Thread.sleep(duration);
	}

	@Override
	public void onEvent(GameEvent event) {
		onEventExecutor.execute(new OnEventTask(event));
	}

	private void handleEvent(GameEvent event) {
		log("event received " + event.toString());
		try {
			GameEventHelper helper = new GameEventHelper(event);

			if (helper.hasValue("game.id")) {

				gameId = helper.getString("game.id");

				if (chat(helper)) {
					String message = helper.getString("chat.message");
					String from = helper.getString("chat.from");
					String to = helper.getString("chat.to");
					log(gameId, "{" + from + "@" + to + "} '" + message + "'");
				}
				if (gameOver(helper)) {
					String winner = helper.getString("player.winner");
					if (isMe(winner))
						log(gameId, "Hurray, I have Won!");
					else
						log(gameId, "FUCK, I am Loser!");
					isRunning = false;
				} else if (inPlay(helper)) {
					log(gameId, "to play, thinking...");
					think();
					int number = guessNumber();
					log(gameId, "to play, guessed... " + number);
					notify(newMoveEvent(gameId, String.valueOf(number)));
				}

			} else {
				gameId = null;
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private boolean chat(GameEventHelper helper) {
		return helper.hasValue("chat.message");
	}

	private boolean gameOver(GameEventHelper helper) {
		return helper.hasValue("player.winner");
	}

	private int guessNumber() {
		return random.nextInt(9) + 1;
	}

	private GameEvent newMoveEvent(String gameId, String move) {
		GameEvent gameEvent = new GameEvent();
		gameEvent.addAttribute(new GameEventAttribute("player.alias",
				getAlias()));
		gameEvent.addAttribute(new GameEventAttribute("move", move));
		gameEvent.addAttribute(new GameEventAttribute("action", "move"));
		gameEvent.addAttribute(new GameEventAttribute("game.id", gameId));
		return gameEvent;
	}

	private boolean inPlay(GameEventHelper helper) {
		String inplay = helper.getString("player.inplay");
		if (getAlias().equals(inplay)) {
			return true;
		}
		return false;
	}

	private String getAlias() {
		return player.getAlias();
	}

	public void requestStop() {
		isRunning = false;
	}

	private boolean isMe(String alias) {
		return getAlias().equals(alias);
	}

	private class OnEventTask implements Runnable {
		private GameEvent event;

		public OnEventTask(GameEvent event) {
			this.event = event;
		}

		@Override
		public void run() {
			handleEvent(event);
		}
	}

}
