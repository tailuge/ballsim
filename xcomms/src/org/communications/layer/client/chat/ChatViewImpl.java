package org.communications.layer.client.chat;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class ChatViewImpl implements ChatView {

	final Button sendButton = new Button("Send");	
	final Button loginButton = new Button("Login");	
	final TextArea messageArea = new TextArea();
	final TextBox userName = new TextBox();
	final TextBox inputText = new TextBox();
	final Label toLabelPrefix = new Label();
	final Label toLabel = new Label();
	final static String newline = "\n";
	
	public ChatViewImpl(RootPanel root,String defaultName,String target)
	{
		addElementsToRoot(root);
		userName.setText(defaultName);
		toLabelPrefix.setText("Send to:");
		toLabel.setText(target);
	}
	
	private void addElementsToRoot(RootPanel root)
	{
		messageArea.setSize("400px", "300px");		
		userName.setSize("400px", "20px");
		inputText.setSize("400px", "20px");
		
		root.add(userName);
		root.add(loginButton);
		root.add(messageArea);
		root.add(toLabelPrefix);		
		root.add(toLabel);		
		root.add(inputText);		
		root.add(sendButton);
	}
	
	@Override
	public void showMessage(String text) {		
		messageArea.setText(messageArea.getText() + newline + text);
	}

	@Override
	public void setInputCallBack(final TextInputNotify text) {
		sendButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				text.handle(userName.getText(), toLabel.getText(), inputText.getText());
				inputText.setText("");				
			}
		});
	}

	@Override
	public void setLoginNotify(final LoginInputNotify login) {
		loginButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				login.handle(userName.getText());
				userName.setEnabled(false);				
				loginButton.setEnabled(false);
			}
		});
		

	}

}
