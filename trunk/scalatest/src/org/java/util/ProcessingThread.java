package org.java.util;

public class ProcessingThread extends Thread {

	private transient boolean isProcessing; 
	
	public ProcessingThread() {
		setPriority(MIN_PRIORITY);
	}
	
	public void run() {
		while(true) {
			if (isProcessing) {
				System.err.print(".");
			}
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e){
			}
		}
	}
	
	public void setProcessing(boolean processing) {
		
		this.isProcessing = processing;
		if (isProcessing) {
			System.err.println("Please Wait, I am Thinking ");
		}
	}
	
}