package com.dungeonescape.gameio.editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.dungeonescape.element.Button;
import com.dungeonescape.element.ContactTrigger;
import com.dungeonescape.element.Door;
import com.dungeonescape.element.Lever;
import com.dungeonescape.element.LinearTrigger;
import com.dungeonescape.element.MoveableObject;
import com.dungeonescape.element.Obstacle;
import com.dungeonescape.element.Platform;
import com.dungeonescape.element.PlayerTrigger;
import com.dungeonescape.element.Trigger;
import com.dungeonescape.ui.GameButton;

public class AddElementPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -2835564701992806419L;

	private EditorSidebar es;

	public AddElementPanel(EditorSidebar es) {
		this.es = es;
		setLayout(new GridLayout(0, 1));
		add(generateButton("Obstacle"));
		add(generateButton("MoveableObject"));
		add(generateButton("Door"));
		add(generateButton("Platform"));
		add(generateButton("ContactTrigger"));
		add(generateButton("Button"));
		add(generateButton("Lever"));
		add(generateButton("PlayerTrigger"));
		add(new SaveBar(es.getEditorPanel()));
	}

	private GameButton generateButton(String s) {
		GameButton result = new GameButton(s);
		result.addActionListener(this);
		return result;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String type = ((GameButton) e.getSource()).getText();
		if (type.equals("Obstacle")) {
			es.addElement(new Obstacle(0, 0, 100, 100));
		} else if (type.equals("MoveableObject")) {
			es.addElement(new MoveableObject(0, 0));
		} else if (type.equals("Door")) {
			es.addElement(new Door(0, 0));
		} else if (type.equals("Platform")) {
			es.addElement(new Platform(0, 0, 100, 100, 100, false));
		} else if (type.equals("Trigger")) {
			es.addElement(new Trigger(0, 0));
		} else if (type.equals("ContactTrigger")) {
			es.addElement(new ContactTrigger(0, 0, 100, 100));
		} else if (type.equals("Button")) {
			es.addElement(new Button(0, 0));
		} else if (type.equals("Lever")) {
			es.addElement(new Lever(0, 0));
		} else if (type.equals("LinearTrigger")) {
			es.addElement(new LinearTrigger(0, 0, 100, 100));
		} else if (type.equals("PlayerTrigger")) {
			es.addElement(new PlayerTrigger(0, 0, 100, 100));
		}
	}
}