package org.communications.layer.proxy;


/**
 * Proxy from GWT to game server
 */
public class ChatProxy {}
/*implements GameServer, org.oxtail.game.event.GameServer,
		GameEventCallback {

	private static final Logger log = Logger.getLogger(ChatProxy.class
			.getName());

	private final GameEventCallback clientCallback;

	public ChatProxy(GameEventCallback clientCallback) {
		this.clientCallback = clientCallback;
	}

	@Override
	public void notify(GameEvent event) {
		log.warning("Notify by client : " + event);
		notify(toServerEvent(event));
	}

	@Override
	public void notify(org.oxtail.game.event.GameEvent event) {
		log.warning("Notified to server event : " + event);
		// just loop back for now
		// really this would notify the state machine
		onEvent(toClientEvent(event));
	}

	private org.oxtail.game.event.GameEvent toServerEvent(GameEvent event) {
		org.oxtail.game.event.GameEvent ge = new org.oxtail.game.event.GameEvent();
		return ge;
	}

	private GameEvent toClientEvent(org.oxtail.game.event.GameEvent event) {
		GameEvent ge = new GameEvent();
		return ge;
	}

	@Override
	public void onEvent(GameEvent event) {
		clientCallback.onEvent(event);
	}

}
*/