package org.communications.layer.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("gameserver")
public interface GWTGameServer extends RemoteService {
	String notify(String data) throws IllegalArgumentException;
}