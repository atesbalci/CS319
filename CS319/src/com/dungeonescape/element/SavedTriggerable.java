package com.dungeonescape.element;

public class SavedTriggerable implements Triggerable {
	private int gameElementNo;
	
	public SavedTriggerable(int no) {
		gameElementNo = no;
	}

	@Override
	public void trigger(boolean b, Trigger t) {
	}

	public int getGameElementNo() {
		return gameElementNo;
	}

	public void setGameElementNo(int gameElementNo) {
		this.gameElementNo = gameElementNo;
	}
}
