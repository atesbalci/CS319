package com.thegame.ui;
import javax.swing.JFrame;

import com.thegame.element.Enemy;
import com.thegame.element.Guy;
import com.thegame.element.Obstacle;
import com.thegame.world.World;

/**
 * @(#)MyGame.java
 * 
 *                 MyGame application
 * 
 * @author
 * @version 1.00 2013/5/14
 */

public class MyGame {

	public static void main(String[] args) {
		JFrame f = new JFrame("My Game");
		WorldPanel wp = new WorldPanel();
		World w = new World(wp);
		wp.setWorld(w);
		w.addElement(new Obstacle(300, w.HEIGHT - 45, 200, 40));
		w.addElement(new Obstacle(70, w.HEIGHT - 90, 200, 40));
		w.setGuy(new Guy(400, 300));
		w.getPlayer().takeRope();
		w.addElement(new Enemy(310, 100));
		w.start();
		f.add(wp);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
