package org.motion.ballsimapp.client.pool.mode;

import static org.motion.ballsimapp.shared.GameEventUtil.ANIMATION_COMPLETE;

import org.motion.ballsimapp.client.pool.BilliardsMarshaller;
import org.motion.ballsimapp.client.pool.BilliardsModel;
import org.motion.ballsimapp.client.pool.BilliardsView;
import org.motion.ballsimapp.shared.GameEvent;

import com.google.gwt.core.client.GWT;

public class AnimationMode extends BilliardsMode {

	private final BilliardsMode continuation;

	public AnimationMode(BilliardsModel model, BilliardsView view,
			GameEvent hitEvent, BilliardsMode continuation) {
		super(model, view);
		this.continuation = continuation;
		model.updateWithHit(BilliardsMarshaller.aimFromEvent(hitEvent));
		view.animate(model.table);
	}

	@Override
	public BilliardsMode handle(GameEvent event) {

		if (event.hasAttribute(ANIMATION_COMPLETE)) {
			model.resetForNextShot();
			return continuation;
		}

		GWT.log("AnimationMode handled unexpected event:" + event);

		return this;
	}

}
