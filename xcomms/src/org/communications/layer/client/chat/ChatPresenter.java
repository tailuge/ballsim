package org.communications.layer.client.chat;

public class ChatPresenter implements TextInputNotify{

	final ChatView view;
	final ChatModel model;
	
	public ChatPresenter(ChatView view,ChatModel model)
	{
		this.view=view;
		this.model = model;
		view.setLoginNotify(getLoginHandler());
		view.setInputCallBack(getInputHandler());
		model.setNetworkMessageHandler(this);
	}
	
	private LoginInputNotify getLoginHandler()
	{
		return new LoginInputNotify () {

			@Override
			public void handle(String user) {
				model.login(user);
				
			}
		};
	}
	
	private TextInputNotify getInputHandler()
	{
		return new TextInputNotify() {

			@Override
			public void handle(String text) {
				model.broadcastMessage(text);
			}
			
		};
	}


	@Override
	public void handle(String text) {
		view.showMessage(text);
	}
	

}
