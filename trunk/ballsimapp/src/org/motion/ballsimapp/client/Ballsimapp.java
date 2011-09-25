package org.motion.ballsimapp.client;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsPresenter;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.client.pool.BilliardsViewImpl;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ballsimapp implements EntryPoint {

	@SuppressWarnings("unused")
	private BilliardsPresenter gamePresenterP1;
	@SuppressWarnings("unused")
	private BilliardsPresenter gamePresenterP2;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		int width = 400;

		// player 1
		BilliardsModel gameModelP1 = new BilliardsModel();
		BilliardsView gameViewP1 = new BilliardsViewImpl(width / 2, "bob");
		gamePresenterP1 = new BilliardsPresenter(gameModelP1, gameViewP1);

		// player 2
		BilliardsModel gameModelP2 = new BilliardsModel();
		BilliardsView gameViewP2 = new BilliardsViewImpl(width / 2, "jim");
		gamePresenterP2 = new BilliardsPresenter(gameModelP2, gameViewP2);

	}

}
