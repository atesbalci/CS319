package com.dungeonescape.game;

import java.util.List;

import com.dungeonescape.common.ContactConstants;
import com.dungeonescape.element.GameElement;

public class PhysicsEngine {
	public void timestep(double d, List<GameElement> elements, double gravity,
			double friction) {
		// a setting to improve game pacing
		d = d / 2;

		for (int i = 0; i < elements.size(); i++) {
			GameElement e1 = elements.get(i);
			// object specific actions to be taken
			e1.timestep(d, elements);

			if (!e1.isFixed()) {
				// apply friction and gravity depending on object type
				if (e1.isFricted())
					applyFriction(e1, d, friction);
				if (!e1.isFlying() && e1.getVerticalSpeed() < 20)
					e1.setVerticalSpeed(e1.getVerticalSpeed() + gravity * d);

				// move object depending on its existing velocity
				e1.moveX(e1.getHorizontalSpeed() * d);
				e1.moveY(e1.getVerticalSpeed() * d);

				// check the collision with each object and handle the possible
				// collisions
				for (int n = 0; n < elements.size(); n++) {
					GameElement e2 = elements.get(n);
					if (!(e2 == e1)) {
						detectAndHandleCollision(e1, e2, d);
					}
				}
			}

			// remove inactive elements
			if (!e1.isActive()) {
				elements.remove(e1);
				i--;
			}
		}
	}

	public void detectAndHandleCollision(GameElement e1, GameElement e2,
			double d) {
		if (e1.intersects(e2)) {
			e1.moveY(-e1.getVerticalSpeed() * d);
			if (!e1.intersects(e2)) {
				if (e1.getVerticalSpeed() > 0) {
					e1.contact(ContactConstants.BOTTOM, e2);
					e2.contact(ContactConstants.TOP, e1);
				} else {
					e1.contact(ContactConstants.TOP, e2);
					e2.contact(ContactConstants.BOTTOM, e1);
				}
				applyMomentum(e1, e2, true);
			} else {
				e1.moveY(e1.getVerticalSpeed() * d);
			}
		}
		if (e1.intersects(e2)) {
			e1.moveX(-e1.getHorizontalSpeed() * d);
			if (!e1.intersects(e2)) {
				if (e1.getHorizontalSpeed() > 0) {
					e1.contact(ContactConstants.RIGHT, e2);
					e2.contact(ContactConstants.LEFT, e1);
				} else {
					e1.contact(ContactConstants.LEFT, e2);
					e2.contact(ContactConstants.RIGHT, e1);
				}
				applyMomentum(e1, e2, false);
			} else {
				e1.moveX(e1.getHorizontalSpeed() * d);
			}
		}
		if (e1.intersects(e2)) {
			e1.contact(ContactConstants.IN, e2);
			e2.contact(ContactConstants.IN, e1);
		}
	}

	public void applyFriction(GameElement e, double d, double friction) {
		if (e.getHorizontalSpeed() > friction)
			e.setHorizontalSpeed(e.getHorizontalSpeed() - friction * d);
		else if (e.getHorizontalSpeed() < -1 * friction)
			e.setHorizontalSpeed(e.getHorizontalSpeed() + friction * d);
		else
			e.setHorizontalSpeed(0);
		if (e.getVerticalSpeed() > friction)
			e.setVerticalSpeed(e.getVerticalSpeed() - friction * d);
		else if (e.getVerticalSpeed() < -1 * friction)
			e.setVerticalSpeed(e.getVerticalSpeed() + friction * d);
		else
			e.setVerticalSpeed(0);
	}

	public void applyMomentum(GameElement e1, GameElement e2, boolean isVertical) {
		if (isVertical) {
			if (e2.isFixed()) {
				e1.setVerticalSpeed(-e1.getVerticalSpeed() * e1.getElasticity());
			} else {
				double ratio = e1.getWeight() / e2.getWeight();
				double tmp = e1.getVerticalSpeed();
				e1.setVerticalSpeed((e2.getVerticalSpeed() - e1
						.getVerticalSpeed()) * ratio * e1.getElasticity());
				e2.setVerticalSpeed((tmp - e2.getVerticalSpeed()) * (1 / ratio)
						* e2.getElasticity());
			}
		} else {
			if (e2.isFixed()) {
				e1.setHorizontalSpeed(-e1.getHorizontalSpeed()
						* e1.getElasticity());
			} else {
				double ratio = e1.getWeight() / e2.getWeight();
				double tmp = e1.getHorizontalSpeed();
				e1.setHorizontalSpeed((e2.getHorizontalSpeed() - e1
						.getHorizontalSpeed()) * ratio * e1.getElasticity());
				e2.setHorizontalSpeed((tmp - e2.getHorizontalSpeed())
						* (1 / ratio) * e2.getElasticity());
			}
		}
	}
}
