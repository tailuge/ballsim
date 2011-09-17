package org.communications.layer.client;

import org.communications.layer.shared.GameEvent;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTGameServerAsync {

	void notify(GameEvent data, @SuppressWarnings("rawtypes") AsyncCallback callback);
	void connect(GameEvent event, AsyncCallback<GameEvent> callback);

}
