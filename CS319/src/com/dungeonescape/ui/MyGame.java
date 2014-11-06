package com.dungeonescape.ui;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.dungeonescape.element.Door;
import com.dungeonescape.element.MoveableObject;
import com.dungeonescape.element.Obstacle;
import com.dungeonescape.element.Player;
import com.dungeonescape.element.Trigger;
import com.dungeonescape.world.World;

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
		w.setPlayer(new Player(300, 300));
		w.getPlayer().takeRope();
		w.addElement(new Obstacle(-5000, 600, 10000, 10));
		w.addElement(new Obstacle(300, 540, 200, 10));
		w.addElement(new MoveableObject(100, 100));
		w.addElement(new MoveableObject(200, 100));
		Door d = new Door(400, 550);
		w.addElement(d);
		Trigger t = new Trigger(355, 550, 100, 50);
		t.setTriggerDuration(40);
		t.setWidth(100);
		t.setTriggerable(d);
		w.addElement(t);
		w.start();
		f.add(wp);
		f.pack();
		f.setVisible(true);
		f.setSize(new Dimension(800, 650));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
