package org.communications.layer.client;

import org.communications.layer.client.chat.ChatModel;
import org.communications.layer.client.chat.ChatPresenter;
import org.communications.layer.client.chat.ChatViewImpl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Xcomms implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */

	ChatPresenter presenter1;
	ChatPresenter presenter2;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		presenter1 = new ChatPresenter(new ChatViewImpl(RootPanel.get("chat1"),"frank","benny"), new ChatModel());
		presenter2 = new ChatPresenter(new ChatViewImpl(RootPanel.get("chat2"),"benny","frank"), new ChatModel());
		
	}


}
