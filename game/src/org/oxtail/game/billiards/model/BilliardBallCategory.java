package org.oxtail.game.billiards.model;

import java.util.Comparator;

public interface BilliardBallCategory {

	BilliardBallId getBallCategory();
	
	BilliardsGameCategory getGameCategory();

	static Comparator<BilliardBallCategory> EqualsComparator = new Comparator<BilliardBallCategory>() {

		public int compare(BilliardBallCategory o1, BilliardBallCategory o2) {
			return (100 * (o1.getGameCategory().ordinal() - o1.getGameCategory().ordinal())) + (o1.getBallCategory().ordinal() - o2.getBallCategory().ordinal());
		}
		
	};
}
