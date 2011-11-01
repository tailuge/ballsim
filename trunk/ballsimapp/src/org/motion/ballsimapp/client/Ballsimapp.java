package org.motion.ballsimapp.client;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsPresenter;
import org.motion.ballsimapp.client.pool.BilliardsViewImpl;
import org.motion.ballsimapp.client.pool.InfoView;
import org.motion.ballsimapp.client.pool.InfoViewImpl;
import org.motion.ballsimapp.client.pool.TableView;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ballsimapp implements EntryPoint {

	@SuppressWarnings("unused")
	private BilliardsPresenter gamePresenterP1;
	@SuppressWarnings("unused")
	private BilliardsPresenter gamePresenterP2;
	@SuppressWarnings("unused")
	private BilliardsPresenter gamePresenterP3;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		int width = 400;

		// player 1
		BilliardsModel gameModelP1 = new BilliardsModel();
		TableView tableViewP1 = new BilliardsViewImpl(width / 2, "player1","bob");
		InfoView infoViewP1 = new InfoViewImpl(width / 2, "player1","bob");
		gamePresenterP1 = new BilliardsPresenter(gameModelP1, infoViewP1, tableViewP1);
		/*						
		// player 2
		BilliardsModel gameModelP2 = new BilliardsModel();
		BilliardsView gameViewP2 = new BilliardsViewImpl(width / 2, "player2","jim");
		gamePresenterP2 = new BilliardsPresenter(gameModelP2, gameViewP2);
							
		// player 3
		BilliardsModel gameModelP3 = new BilliardsModel();
		BilliardsView gameViewP3 = new BilliardsViewImpl(width / 4, "player3","spec");
		gamePresenterP3 = new BilliardsPresenter(gameModelP3, gameViewP3);
*/
	}

}
