package org.communications.layer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTGameServerAsync {

	void notify(String data, AsyncCallback<String> callback);

}
