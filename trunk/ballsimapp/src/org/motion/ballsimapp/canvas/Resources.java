package org.motion.ballsimapp.canvas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {
	
	public static final Resources INSTANCE =  GWT.create(Resources.class);
	
	@Source("table.jpg")
	public ImageResource logo();
}
