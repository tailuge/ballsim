package org.motion.ballsimapp.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("tables")
public interface TableService extends RemoteService {
	ArrayList<String> getTables() throws IllegalArgumentException;
	boolean joinTable(String id) throws IllegalArgumentException;
}
