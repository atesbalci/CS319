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
		w.setPlayer(new Player(400, 300));
		w.addElement(new Obstacle(300, w.HEIGHT - 45, 200, 40));
		w.addElement(new Obstacle(70, w.HEIGHT - 90, 200, 40));
		w.getPlayer().takeRope();
		w.addElement(new MoveableObject(210, 100));
		w.addElement(new MoveableObject(310, 100));
		Door d = new Door(300, 300);
		w.addElement(d);
		Trigger t = new Trigger(240, 450);
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
