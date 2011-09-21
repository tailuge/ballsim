package org.motion.ballsimapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("gameServer")
public interface GWTGameServer extends RemoteService {
	
	void notify(String data) throws IllegalArgumentException;
	String connect(String event) throws IllegalArgumentException;

}