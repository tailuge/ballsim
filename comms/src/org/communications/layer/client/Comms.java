package org.communications.layer.client;

import org.communications.layer.client.ChannelFactory.ChannelCreatedCallback;
import org.communications.layer.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Comms implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button("Login");
		final Button echoButton = new Button("Echo");
		final TextBox nameField = new TextBox();
		nameField.setText("bobby");


		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("sendButtonContainer").add(echoButton);

		echoButton.setEnabled(false);
		
		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
	
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
		
		echoButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				echoButton.setEnabled(false);

				greetingService.echo(nameField.getText(), "-a message in channel-", 
						new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Could not send to server:"+caught.getMessage());
								
							}

							@Override
							public void onSuccess(String result) {
								echoButton.setEnabled(true);
								
							}
						}
						);
			}
		});

		
		
		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					Window.alert("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								Window.alert("problem:"+caught.getMessage());
							}

							public void onSuccess(String result) {
								
								createNamedChannelListner(result);
								Window.alert("created a channel:"+result);
								echoButton.setEnabled(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
	
	public void createNamedChannelListner(String name)
	{
		final String channelName = name;
		ChannelFactory.createChannel(channelName, 
				
				new ChannelCreatedCallback() {
			  @Override
			  public void onChannelCreated(Channel channel) {
			    channel.open(new SocketListener() {
			      @Override
			      public void onOpen() {
			        Window.alert(channelName+" Channel opened!");
			      }
			      @Override
			      public void onMessage(String message) {
			        Window.alert("Received: " + message);
			      }
			      @Override
			      public void onError(SocketError error) {
			        Window.alert("Error: " + error.getDescription());
			      }
			      @Override
			      public void onClose() {
			        Window.alert("Channel closed!");
			      }
			    });
			  }
			});
				
				
	}
}
