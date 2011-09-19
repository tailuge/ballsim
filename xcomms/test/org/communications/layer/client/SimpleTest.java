package org.communications.layer.client;

import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventAttribute;
import org.communications.layer.shared.GameEventUtil;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SimpleTest extends GWTTestCase{

	public String getModuleName() {                                         
	    return "org.communications.layer.Xcomms";
	  }
	

	@Test
	public void testRPC()
	{		
		GWTGameServerAsync gameServer = GWT.create(GWTGameServer.class);
		assertNotNull(gameServer);
	}


	@SuppressWarnings("rawtypes")
	@Test
	public void testNotify()
	{		
		GWTGameServerAsync gameServer = GWT.create(GWTGameServer.class);

		delayTestFinish(1000);
	
		gameServer.notify(GameEventMarshaller.marshal(GameEventUtil.simpleEvent("message", "hi")),
				new AsyncCallback() {
					public void onFailure(Throwable caught) {
						assertTrue(false);
						finishTest();
					}

					public void onSuccess(Object object) {
						finishTest();
					}
				});		
		
	}

	@Test
	public void testConnect()
	{		
		GWTGameServerAsync gameServer = GWT.create(GWTGameServer.class);

		delayTestFinish(1000);
	
		gameServer.connect(GameEventMarshaller.marshal(GameEventUtil.simpleEvent("user", "frank")),
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						assertTrue(false);
						finishTest();
					}

					public void onSuccess(String object) {
						finishTest();
					}
				});		
		
	}

	@Test
	public void testClientMarshallingEmpty()
	{
		GameEvent testEvent = new GameEvent();
		GameEvent result = GameEventMarshaller.deMarshal(GameEventMarshaller.marshal(testEvent));
		assertTrue(testEvent.equals(result));
	}

	@Test
	public void testClientMarshallingSimple()
	{
		GameEvent testEvent = GameEventUtil.simpleEvent("user", "frank");
		GameEvent result= GameEventMarshaller.deMarshal(GameEventMarshaller.marshal(testEvent));
		assertTrue(testEvent.equals(result));
	}		

	@Test
	public void testClientMarshalling()
	{
		GameEvent testEvent = GameEventUtil.simpleEvent("user", "frank");
		testEvent.addAttribute(new GameEventAttribute("destination","mark"));		
		GameEvent result= GameEventMarshaller.deMarshal(GameEventMarshaller.marshal(testEvent));
		assertTrue(testEvent.equals(result));
	}
	
	
}
