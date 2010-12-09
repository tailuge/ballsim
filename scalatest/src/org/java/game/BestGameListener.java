package org.java.game;

public interface BestGameListener {

	static final BestGameListener NOP = new BestGameListener() {
		@Override
		public void notifyBestGame(GameEvaluationResult result) {
		}
	};
	
	void notifyBestGame(GameEvaluationResult result);

}
