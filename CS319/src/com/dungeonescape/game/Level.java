package com.dungeonescape.game;

import java.util.ArrayList;
import java.util.List;

import com.dungeonescape.element.GameElement;

public class Level {
	private List<GameElement> elements;
	
	public Level() {
		elements = new ArrayList<GameElement>();
	}

	public List<GameElement> getElements() {
		return elements;
	}

	public void setElements(List<GameElement> elements) {
		this.elements = elements;
	}
	
	public void addElement(GameElement e) {
		elements.add(e);
	}
	
	public void removeElement(GameElement e) {
		elements.remove(e);
	}
}
