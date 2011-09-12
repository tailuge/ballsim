package org.communications.layer.client;


import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SimpleTest extends GWTTestCase{

	public String getModuleName() {                                         
	    return "org.communications.layer.Comms";
	  }
	

	@Test
	public void testFirst()
	{		
		assertTrue(new Integer(1) != null);
	}

	

	@Test
	public void testLogin()
	{
		GreetingServiceAsync greetingService = GWT
		.create(GreetingService.class);

		assertNotNull(greetingService);
		
		delayTestFinish(1000);
		
		greetingService.greetServer("username1",
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						System.out.println("caught:"+caught.getMessage());
						assertTrue(false);
						finishTest();
					}

					public void onSuccess(String result) {
						System.out.println("ok");
						assertTrue(true);
						finishTest();
					}
				});
		
	}


}
