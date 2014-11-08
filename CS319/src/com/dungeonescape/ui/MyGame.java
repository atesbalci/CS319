package com.dungeonescape.ui;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.dungeonescape.element.Door;
import com.dungeonescape.element.MoveableObject;
import com.dungeonescape.element.Obstacle;
import com.dungeonescape.element.Platform;
import com.dungeonescape.element.Trigger;
import com.dungeonescape.tool.BoomerangTool;
import com.dungeonescape.world.World;

public class MyGame {

	public static void main(String[] args) {
		JFrame f = new JFrame("My Game");
		WorldPanel wp = new WorldPanel();
		World w = new World(wp);
		wp.setWorld(w);
		w.getPlayer().setTool(new BoomerangTool());
		w.addElement(new Obstacle(-5000, 600, 5500, 10));
		w.addElement(new Obstacle(355, 540, 100, 10));
		w.addElement(new MoveableObject(100, 100));
		w.addElement(new MoveableObject(200, 100));
		Door d = new Door(400, 550);
		w.addElement(d);
		Trigger t = new Trigger(355, 550, 100, 50);
		t.setTriggerDuration(40);
		t.setTriggerable(d);
		w.addElement(t);
		Platform p = new Platform(500, 600, 100, 10, 100, false);
		w.addElement(p);
		Trigger t2 = new Trigger(500, 550, 100, 50);
		t2.setTriggerDuration(40);
		t2.setTriggerable(p);
		w.addElement(t2);
		// Laser l = new Laser(200, 600, 300, 500);
		// l.setTriggerable(p);
		// w.addElement(l);
		w.start();
		f.add(wp);
		f.pack();
		f.setVisible(true);
		f.setSize(new Dimension(800, 650));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
