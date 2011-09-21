package org.motion.ballsimapp.client;

import org.motion.ballsimapp.client.pool.GameModel;
import org.motion.ballsimapp.client.pool.GamePresenter;
import org.motion.ballsimapp.client.pool.GameView;
import org.motion.ballsimapp.client.pool.GameViewImpl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ballsimapp implements EntryPoint {

	private GamePresenter gamePresenterP1;
	private GamePresenter gamePresenterP2;
	private GamePresenter gamePresenterP3;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		int width = 500;
		
		// player 1
		GameModel gameModelP1 = new GameModel("bobby");
		GameView gameViewP1 = new GameViewImpl(width/2, RootPanel.get("player1"));
		
		gamePresenterP1 = new GamePresenter(gameModelP1, gameViewP1);
		gamePresenterP1.beginAim();
		
		
		// player 2
		GameModel gameModelP2 = new GameModel("frank");
		GameView gameViewP2 = new GameViewImpl(width/2, RootPanel.get("player2"));
		
		gamePresenterP2 = new GamePresenter(gameModelP2, gameViewP2);
		gamePresenterP2.beginAim();
		

		// player 3
		GameModel gameModelP3 = new GameModel("spectator");
		GameView gameViewP3 = new GameViewImpl(width/3, RootPanel.get("player3"));
		
		gamePresenterP3 = new GamePresenter(gameModelP3, gameViewP3);
		gamePresenterP3.beginAim();

	}
	

	  
}
