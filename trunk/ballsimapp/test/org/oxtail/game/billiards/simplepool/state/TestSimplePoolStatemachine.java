package org.oxtail.game.billiards.simplepool.state;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.oxtail.game.home.GameHome;
import org.oxtail.game.home.inmemory.InMemoryGameHome;
import org.oxtail.game.state.reflect.ReflectStateActionExecutor;
import org.oxtail.game.state.reflect.ReflectStateFactory;

public class TestSimplePoolStatemachine {

	private SimplePoolStatemachine statemachine;
	private GameHome gameHome = new InMemoryGameHome();
	private SimplePoolPlayer bob, jim, tom;

	@Before
	public void before() {
		statemachine = new SimplePoolStatemachine(gameHome,
				new ReflectStateFactory(), new ReflectStateActionExecutor());
		bob = new SimplePoolPlayer("bob", statemachine);
		jim = new SimplePoolPlayer("jim", statemachine);
		tom = new SimplePoolPlayer("tom", statemachine);
		PlayerState.LoggedOut.set(bob, jim, tom);
		GameHome.Util.storePlayers(gameHome, bob, jim, tom);
	}

	@Test
	public void testPlayer1Login() {
		bob.login().assertLoggedIn();
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
		// assert table state is maintained over shots
		Assert.assertEquals("jim.table.state",bob.lastAttributeValue("game.table.state"));
		bob.assertLoser();
	}

	@Test
	public void testPlayerPotsAllInOffAndLoses() {
		bob.login().requestGame().assertAwaitingGame();
		jim.login().requestGame().assertAiming().pot(1, 2,3,4,5,6,7,8,9, 0).assertLoser();
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

}
