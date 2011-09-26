package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.Events.ANIMATION_COMPLETE;
import static org.motion.ballsimapp.shared.Events.BEGIN_AIMING;
import static org.motion.ballsimapp.shared.Events.BEGIN_VIEWING;

import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.Events;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class AnimationMode extends BilliardsMode {

	private boolean animationComplete = false;
	private Boolean aimNotView;

	public AnimationMode(BilliardsModel model, BilliardsView view) {
		super(model, view);
		view.animate(model.table);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (event.hasAttribute(ANIMATION_COMPLETE)) {
			model.table.beginNewShot();
			view.showTable(model.table);
			animationComplete = true;
			if (aimNotView != null)
				return aimNotView ? new AimingMode(model,view) : new ViewingMode(model,view);
		}

		if (Events.isState(event,BEGIN_AIMING))
		{			
			aimNotView = new Boolean(true);
			if (animationComplete)
				return new AimingMode(model,view);
			else
				return this;
		}

		if (Events.isState(event,BEGIN_VIEWING))
		{			
			aimNotView = new Boolean(false);
			if (animationComplete)
				return new ViewingMode(model,view);
			else
				return this;
		}
		
		GWT.log("AnimationMode handled unexpected event:" + event);

		return this;
	}
	


}
