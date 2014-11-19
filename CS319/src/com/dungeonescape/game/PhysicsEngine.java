package com.dungeonescape.game;

import java.util.List;

import com.dungeonescape.element.GameElement;

public class PhysicsEngine {
	public void timestep(double d, List<GameElement> elements, double gravity, double friction) {
		d = d / 2;

		for (int i = 0; i < elements.size(); i++) {
			GameElement e = elements.get(i);

			if (!e.isFixed()) {
				if (e.isFricted())
					applyFriction(e, d, friction);
				if (!e.isFlying() && e.getVerticalSpeed() < 20)
					e.setVerticalSpeed(e.getVerticalSpeed() + gravity * d);
				e.moveX(e.getHorizontalSpeed() * d);
				e.moveY(e.getVerticalSpeed() * d);

				for (int n = 0; n < elements.size(); n++) {
					GameElement o = elements.get(n);
					if (!(o == e)) {
						if (e.intersects(o)) {
							e.moveY(-e.getVerticalSpeed() * d);
							if (!e.intersects(o)) {
								if (e.getVerticalSpeed() > 0) {
									e.contact("bottom", o);
									o.contact("top", e);
								} else {
									e.contact("top", o);
									o.contact("bottom", e);
								}
								if (o.isFixed()) {
									e.setVerticalSpeed(-e.getVerticalSpeed()
											* e.getElasticity());
								} else {
									double ratio = e.getWeight()
											/ o.getWeight();
									double tmp = e.getVerticalSpeed();
									e.setVerticalSpeed((o.getVerticalSpeed() - e
											.getVerticalSpeed())
											* ratio
											* e.getElasticity());
									o.setVerticalSpeed((tmp - o
											.getVerticalSpeed())
											* (1 / ratio)
											* o.getElasticity());
								}
							} else {
								e.moveY(e.getVerticalSpeed() * d);
							}
						}
						if (e.intersects(o)) {
							e.moveX(-e.getHorizontalSpeed() * d);
							if (!e.intersects(o)) {
								if (e.getHorizontalSpeed() > 0) {
									e.contact("right", o);
									o.contact("left", e);
								} else {
									e.contact("left", o);
									o.contact("right", e);
								}
								if (o.isFixed()) {
									e.setHorizontalSpeed(-e
											.getHorizontalSpeed()
											* e.getElasticity());
								} else {
									double ratio = e.getWeight()
											/ o.getWeight();
									double tmp = e.getHorizontalSpeed();
									e.setHorizontalSpeed((o
											.getHorizontalSpeed() - e
											.getHorizontalSpeed())
											* ratio * e.getElasticity());
									o.setHorizontalSpeed((tmp - o
											.getHorizontalSpeed())
											* (1 / ratio) * o.getElasticity());
								}
							} else {
								e.moveX(e.getHorizontalSpeed() * d);
							}
						}
					}
				}
			}

			if (!e.isActive()) {
				elements.remove(e);
				i--;
			}
			e.timestep(d, elements);
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
}
