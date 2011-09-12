package org.communications.layer.client;


import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class SimpleTest extends GWTTestCase{

	public String getModuleName() {                                         // (2)
	    return "org.communications.layer.Comms";
	  }
	

	@Test
	public void testFirst()
	{
		assertTrue(true);
	}
}
