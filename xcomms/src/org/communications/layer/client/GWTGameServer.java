package org.communications.layer.client;

import org.communications.layer.shared.GameEvent;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("gameServer")
public interface GWTGameServer extends RemoteService {
	
	void notify(GameEvent data) throws IllegalArgumentException;
	GameEvent connect(GameEvent event) throws IllegalArgumentException;

}