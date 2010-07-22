package org.motion.ballsimapp.server;

import java.util.ArrayList;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

import org.motion.ballsimapp.client.TableService;

import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TableServiceImpl extends RemoteServiceServlet implements TableService {

	@Override
	public ArrayList<String> getTables() throws IllegalArgumentException {


		PersistenceManager pm = PMF.get().getPersistenceManager();

		ArrayList<String> tables = Lists.newArrayList();

		try {
			Extent<Player> extent = pm.getExtent(Player.class, false);

			for (Player op : extent) {
				tables.add(op.getId());
			}
			extent.closeAll();
		} finally {
			pm.close();
		}
		
		return tables;
	}

	@Override
	public boolean joinTable(String id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

}
