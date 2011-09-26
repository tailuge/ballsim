package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.ANIMATION_COMPLETE;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class AnimationMode extends BilliardsMode {

	private final boolean aimNotView;

	public AnimationMode(BilliardsModel model, BilliardsView view,
			boolean aimNotView) {
		super(model, view);
		this.aimNotView = aimNotView;
		view.animate(model.table);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (event.hasAttribute(ANIMATION_COMPLETE)) {
			model.table.beginNewShot();
			view.showTable(model.table);
			return aimNotView ? new AimingMode(model,view) : new ViewingMode(model,view);
		}

		// may listen for server choice on next state here
		
		GWT.log("AnimationMode handled unexpected event:" + event);

		return this;
	}

}
