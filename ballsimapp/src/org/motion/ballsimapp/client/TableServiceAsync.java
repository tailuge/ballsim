package org.motion.ballsimapp.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TableServiceAsync {


	void getTables(AsyncCallback<ArrayList<String>> callback);

	void joinTable(String id, AsyncCallback<Boolean> callback);

}
