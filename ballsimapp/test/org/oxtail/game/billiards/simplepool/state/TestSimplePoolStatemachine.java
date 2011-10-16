package org.oxtail.game.billiards.simplepool.state;

import org.junit.Before;
import org.junit.Test;
import org.oxtail.game.billiards.simplepool.model.SimplePoolGame;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.home.inmemory.InMemoryGameHome;
import org.oxtail.game.state.reflect.ReflectStateActionExecutor;
import org.oxtail.game.state.reflect.ReflectStateFactory;

public class TestSimplePoolStatemachine {

	private final GameHome gameHome = new InMemoryGameHome();
	private SimplePoolStatemachine statemachine;
	private SimplePoolPlayer bob, jim, tom, rob;

	private SimplePoolPlayer setUpPlayer(String alias) {
		SimplePoolPlayer player = new SimplePoolPlayer(alias, statemachine);
		PlayerState.LoggedOut.set(player);
		gameHome.storePlayer(player);
		return player;
	}

	@Before
	public void before() {
		SimplePoolGame.resetGameCount();
		SimplePoolPlayer.resetShotCount();
		statemachine = new SimplePoolStatemachine(gameHome,
				new ReflectStateFactory(), new ReflectStateActionExecutor());
		bob = setUpPlayer("bob");
		jim = setUpPlayer("jim");
		tom = setUpPlayer("tom");
		rob = setUpPlayer("rob");
	}

	@Test
	public void testPlayer1Login() {
		bob.login().assertLoggedIn();
		bob.assertAvaliableActions("chat,logout,requestGame,requestWatchGames");
	}

	@Test
	public void testPlayersLogin() {
		bob.login().assertLoggedIn();
		jim.login().assertLoggedIn();
	}

	@Test
	public void testPlayer1LogsInAndRequestsGame() {
		bob.login().requestGame().assertAwaitingGame();
	}

	@Test
	public void testBothPlayersLoginAndRequestsGame() {
		bob.login().requestGame().assertAwaitingGame();
		jim.login().requestGame().assertAiming();
		bob.assertViewing();
	}

	@Test
	public void testPlayerPotsABallAndContinues() {
		bob.login().requestGame().assertAwaitingGame();
		jim.login().requestGame().assertAiming().pot(1).assertAiming();
		bob.assertViewing();
	}

	@Test
	public void testPlayerMissesABallAndTurnOver() {
		bob.login().requestGame().assertAwaitingGame();
		jim.login().requestGame().assertAiming().miss().assertViewing();
	}

	@Test
	public void testPlayerPotsAllBallsAndWins() {
		bob.login().requestGame().assertAwaitingGame();
		jim.login().requestGame().assertAiming().pot(1, 2, 3, 4, 5, 6, 7, 8, 9)
				.assertWinner();
		bob.assertLoser();
	}

	@Test
	public void testPlayerPotsAllInOffAndLoses() {
		bob.login().requestGame().assertAwaitingGame();
		jim.login().requestGame().assertAiming()
				.pot(1, 2, 3, 4, 5, 6, 7, 8, 9, 0).assertLoser();
		bob.assertWinner();
	}

	@Test
	public void testLoginOnAwaitingGame() {
		bob.login().assertLoggedIn().requestGame().assertAwaitingGame().login()
				.assertLoggedIn();
	}

	@Test
	public void testLoginOnInPlay() {
		bob.login().requestGame().assertAwaitingGame();
		jim.login().requestGame().assertAiming();
		jim.loginDuringPlay().assertLoggedIn();
		bob.assertLoggedIn();
	}

	@Test
	public void testChatting() {
		bob.login();
		jim.login().chat(bob, "Gay");
		bob.assertChatting("Gay");
	}

	@Test
	public void testChattingToAll() {
		bob.login().assertLoggedIn();
		tom.login().assertLoggedIn();
		jim.login().chatToAll("Gay");
		bob.assertChatting("Gay");
		tom.assertChatting("Gay");
	}

	@Test
	public void testRequestWatchingGames() {
		bob.login().requestGame().assertAwaitingGame();
		jim.login().requestGame().assertAiming();
		tom.login();
		tom.requestWatchGames().assertRequestWatchGames();
		tom.assertAttribute("games.ids", "1");
		tom.assertAttribute("games.descriptions", "jim vs. bob");
		tom.watchGame("1");
		tom.assertGameType();
		jim.pot(1);
		assertTableState("1", bob, jim, tom);
		jim.pot(2);
		assertTableState("2", bob, jim, tom);
		jim.pot(3);
		assertTableState("3", bob, jim, tom);
		jim.pot(4, 5, 6, 7, 8, 9).assertWinner();
		bob.assertLoser();
		tom.assertRequestWatchGames();
	}

	@Test
	public void testRequestWatchingGamesNotifiedOfGameStartingAndEnding() {
		tom.login();
		tom.requestWatchGames().assertRequestWatchGames();
		bob.login().requestGame().assertAwaitingGame();
		jim.login().requestGame().assertAiming();
		tom.assertAttribute("games.ids", "1");
		tom.assertAttribute("games.descriptions", "jim vs. bob");
		jim.potAllBallsFromBreak();
		tom.assertAttribute("games.ids", "");
		tom.assertAttribute("games.descriptions", "");
	}

	@Test
	public void testWatchInPlayGameNotifiedOfCurrentTableState() {
		tom.login();
		tom.requestWatchGames().assertRequestWatchGames();

		rob.login();
		rob.requestWatchGames().assertRequestWatchGames();

		bob.login().requestGame().assertAwaitingGame();
		jim.login().requestGame().assertAiming();
		jim.pot(1);
		rob.watchGame("1");
		rob.assertTableState("1");
		jim.pot(2);
		tom.watchGame("1");
		tom.assertTableState("2");
		
	}

	private void assertTableState(String state, SimplePoolPlayer... players) {
		for (SimplePoolPlayer player : players)
			player.assertTableState(state);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalPlayOrder() {
		bob.login().requestGame().assertAwaitingGame();
		jim.login().requestGame().assertAiming();
		// bob can't pot he's not at the table !
		bob.pot(1);
	}
}
