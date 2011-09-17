package org.communications.layer.client.chat;


public interface ChatView {
	
	void showMessage(String text);
	void setInputCallBack(TextInputNotify text);
	void setLoginNotify(LoginInputNotify login);
	
}
