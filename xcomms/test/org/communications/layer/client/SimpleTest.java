package org.communications.layer.client;

import org.communications.layer.shared.GameEvent;
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
		
		GameEvent event = new GameEvent();
		
		gameServer.notify(event,
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
	public void testLogin()
	{
		GreetingServiceAsync greetingService = GWT
		.create(GreetingService.class);

		delayTestFinish(1000);
		
		greetingService.greetServer("bobby",
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						assertTrue(false);
						finishTest();
					}

					public void onSuccess(String result) {
						System.out.println("result:"+result);
						assertTrue(result.length() > 0);
						finishTest();
					}
				});		
	}


	@Test
	public void testInvalidLogin()
	{
		GreetingServiceAsync greetingService = GWT
		.create(GreetingService.class);

		delayTestFinish(1000);
		
		greetingService.greetServer("",
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						assertTrue(true);
						finishTest();
					}

					public void onSuccess(String result) {
						assertTrue(false);
						finishTest();
					}
				});		
	}


}
