package org.java.util;

public class ProcessingThread extends Thread {

	private transient boolean isProcessing; 
	
	private long startTime;
	
	public ProcessingThread() {
		setPriority(MIN_PRIORITY);
		setDaemon(true);
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
			startTime = System.currentTimeMillis();
			System.err.println("Please Wait, I am Thinking ");
		}
		if (isProcessing == false) {
			System.err.println("("+(System.currentTimeMillis()-startTime)+"ms)");
		}
	}
	
}