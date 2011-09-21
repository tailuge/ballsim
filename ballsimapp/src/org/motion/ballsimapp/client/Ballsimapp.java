package org.motion.ballsimapp.client;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsPresenter;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.client.pool.BilliardsViewImpl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ballsimapp implements EntryPoint {

	private BilliardsPresenter gamePresenterP1;
	private BilliardsPresenter gamePresenterP2;
	private BilliardsPresenter gamePresenterP3;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		int width = 400;
		
		// player 1
		BilliardsModel gameModelP1 = new BilliardsModel("bobby");
		BilliardsView gameViewP1 = new BilliardsViewImpl(width/2, RootPanel.get("player1"));
		
		gamePresenterP1 = new BilliardsPresenter(gameModelP1, gameViewP1);
		gamePresenterP1.beginAim();
		
		
		// player 2
		BilliardsModel gameModelP2 = new BilliardsModel("frank");
		BilliardsView gameViewP2 = new BilliardsViewImpl(width/2, RootPanel.get("player2"));
		
		gamePresenterP2 = new BilliardsPresenter(gameModelP2, gameViewP2);
		gamePresenterP2.beginAim();
		

		// player 3
		BilliardsModel gameModelP3 = new BilliardsModel("spectator");
		BilliardsView gameViewP3 = new BilliardsViewImpl(width/3, RootPanel.get("player3"));
		
		gamePresenterP3 = new BilliardsPresenter(gameModelP3, gameViewP3);
		gamePresenterP3.beginAim();

		gamePresenterP1.forceLogin();
		gamePresenterP2.forceLogin();
		gamePresenterP3.forceLogin();
		
	}
	

	  
}
