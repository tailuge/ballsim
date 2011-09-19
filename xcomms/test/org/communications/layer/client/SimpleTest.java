package org.communications.layer.client;

import org.communications.layer.shared.GameEvent;
import org.communications.layer.shared.GameEventAttribute;
import org.communications.layer.shared.GameEventUtil;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
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
	
		gameServer.notify(GameEventUtil.simpleEvent("message", "hi"),
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
	
		gameServer.connect(GameEventUtil.simpleEvent("user", "frank"),
				new AsyncCallback<GameEvent>() {
					public void onFailure(Throwable caught) {
						assertTrue(false);
						finishTest();
					}

					public void onSuccess(GameEvent object) {
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
