package com.dungeonescape.element;

import java.util.List;

public abstract class Trigger extends StaticElement implements Triggerable {
	private Triggerable triggerable;
	private double triggerActive;
	private int triggerDuration;
	private int delay;
	private double remainingDelay;

	public Trigger(double x, double y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
		smooth = false;
		triggerActive = 0;
		triggerDuration = 1;
		delay = 0;
		remainingDelay = 0;
	}

	@Override
	public void timestep(double d, List<GameElement> elementsInWorld) {
		super.timestep(d, elementsInWorld);
		if (triggerable != null) {
			if (triggerActive > 0) {
				if (remainingDelay <= 0) {
					triggerable.trigger(true);
					triggerActive -= d;
				} else {
					remainingDelay -= d;
				}
			} else {
				triggerable.trigger(false);
				remainingDelay = delay;
			}
		}
	}

	public void activate() {
		triggerActive = triggerDuration;
	}

	public void trigger(boolean b) {
		if (b)
			activate();
	}

	public Triggerable getTriggerable() {
		return triggerable;
	}

	public void setTriggerable(Triggerable triggerable) {
		this.triggerable = triggerable;
	}

	public int getTriggerDuration() {
		return triggerDuration;
	}

	public void setTriggerDuration(int triggerDuration) {
		this.triggerDuration = triggerDuration;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
		remainingDelay = delay;
	}

	public boolean hasTriggerable() {
		return triggerable != null;
	}
}
