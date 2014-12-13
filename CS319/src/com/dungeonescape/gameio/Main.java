package com.dungeonescape.gameio;

import java.io.File;

import com.dungeonescape.game.Game;

public class Main {

	public static void main(String[] args) {
		ToggleFullscreen f = new ToggleFullscreen();
		GamePanel wp = new GamePanel();
		Game w = new Game(wp);
		wp.setGame(w);
		w.loadLevel(new File("levels/level.level"));
		f.add(wp);
		wp.requestFocus();
		w.start();
	}
}
