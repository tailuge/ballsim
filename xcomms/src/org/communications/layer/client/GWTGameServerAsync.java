package org.communications.layer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTGameServerAsync {

	void notify(String data, @SuppressWarnings("rawtypes") AsyncCallback callback);
	void connect(String event, AsyncCallback<String> callback);

}
