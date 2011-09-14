package org.communications.layer.client;

import org.communications.layer.client.ChannelFactory.ChannelCreatedCallback;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private AsyncCallback<String> showErrorCallback(final Button b) {
		return new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Could not send to server:" + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				b.setEnabled(true);
			}
		};
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button buttonP1 = new Button("Send");
		final Button buttonP2 = new Button("Send");
		final TextBox msgP1 = new TextBox();
		final TextBox msgP2 = new TextBox();
		final Label recvP1 = new Label();
		final Label recvP2 = new Label();
		
		msgP1.setText("message from benny");
		msgP2.setText("message from frank");
		
		RootPanel.get("msgP1").add(msgP1);
		RootPanel.get("msgP1").add(recvP1);
		RootPanel.get("buttonP1").add(buttonP1);

		RootPanel.get("msgP2").add(msgP2);
		RootPanel.get("msgP2").add(recvP2);
		RootPanel.get("buttonP2").add(buttonP2);

		buttonP1.setEnabled(false);
		buttonP2.setEnabled(false);

		buttonP1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				buttonP1.setEnabled(false);
				msgP1.setText(msgP1.getText()+".");
				greetingService.echo("frank", msgP1.getText(),
						showErrorCallback(buttonP1));
			}
		});

		buttonP2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				buttonP2.setEnabled(false);
				msgP2.setText(msgP2.getText()+".");
				greetingService.echo("benny", msgP2.getText(),
						showErrorCallback(buttonP2));
			}
		});

		loginUser("benny", buttonP1, recvP1);
		loginUser("frank", buttonP2, recvP2);

	}

	/**
	 * Send the name from the nameField to the server and wait for a response.
	 */
	private void loginUser(final String user, final Button b, final Label recv) {

		greetingService.greetServer(user, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				Window.alert("problem:" + caught.getMessage());
			}

			public void onSuccess(String result) {
				createNamedChannelListner(result,recv);
				b.setEnabled(true);
			}
		});
	}

	public void createNamedChannelListner(String name,final Label recv) {
		final String channelName = name;
		ChannelFactory.createChannel(channelName,

		new ChannelCreatedCallback() {
			@Override
			public void onChannelCreated(Channel channel) {
				channel.open(new SocketListener() {
					@Override
					public void onOpen() {
						recv.setText(channelName + " Channel opened!");
					}

					@Override
					public void onMessage(String message) {
						recv.setText("recv:"+message);
					}

					@Override
					public void onError(SocketError error) {
						recv.setText("Error: " + error.getDescription());
					}

					@Override
					public void onClose() {
						recv.setText("Channel closed!");
					}
				});
			}
		});

	}
}
