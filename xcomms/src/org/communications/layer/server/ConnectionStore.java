package org.communications.layer.server;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionStore {
	private Set<String> friendsList = new HashSet<String>();
	private static ConnectionStore instance;
	private static final Logger logger = Logger.getLogger(ConnectionStore.class
			.getCanonicalName());


	private ConnectionStore() {
	}


	public static synchronized ConnectionStore getInstance() {
		if (instance == null)
			instance = new ConnectionStore();
		return instance;
	}


	synchronized void addNewConnection(String user) {
		logger.log(Level.INFO, "User {0} is added to the list", user);
		friendsList.add(user);
	}


	synchronized void removeConnection(String user) {
		logger.log(Level.INFO, "User {0} is removed from the list", user);
		friendsList.remove(user);
	}


	synchronized Set<String> getConnections() {
		logger.log(Level.INFO, "Users sorted and the set returned");
		return new TreeSet<String>(friendsList);
	}
}
