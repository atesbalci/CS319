package com.dungeonescape.game;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dungeonescape.common.ToolConstants;
import com.dungeonescape.common.TriggerConstants;
import com.dungeonescape.element.Button;
import com.dungeonescape.element.ContactTrigger;
import com.dungeonescape.element.Door;
import com.dungeonescape.element.GameElement;
import com.dungeonescape.element.Lever;
import com.dungeonescape.element.LinearTrigger;
import com.dungeonescape.element.MoveableObject;
import com.dungeonescape.element.Obstacle;
import com.dungeonescape.element.Platform;
import com.dungeonescape.element.PlayerTrigger;
import com.dungeonescape.element.SavedTriggerable;
import com.dungeonescape.element.Trigger;

public class Level {
	private List<GameElement> elements;
	private Point spawnPoint;
	private int fallHeight;
	private int tool;

	public Level() {
		elements = new ArrayList<GameElement>();
		spawnPoint = new Point(300, 300);
		fallHeight = 1000;
		tool = ToolConstants.NONE;
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

	public Point getSpawnPoint() {
		return spawnPoint;
	}

	public void setSpawnPoint(Point spawnPoint) {
		this.spawnPoint = spawnPoint;
	}

	public int getFallHeight() {
		return fallHeight;
	}

	public void setFallHeight(int fallHeight) {
		this.fallHeight = fallHeight;
	}

	public int getTool() {
		return tool;
	}

	public void setTool(int tool) {
		this.tool = tool;
	}

	public void loadLevel(File file) {
		try {
			elements.clear();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			fallHeight = readFallHeight(br.readLine());
			spawnPoint = readSpawnPoint(br.readLine());
			tool = readTool(br.readLine());
			while ((line = br.readLine()) != null) {
				if (line.length() > 6) {
					int splitter = line.indexOf(' ');
					String type = line.substring(0, splitter);
					String argsString = line.substring(splitter, line.length());
					List<Integer> args = readArgs(argsString);
					if (type.equalsIgnoreCase("Obstacle")) {
						Obstacle e = new Obstacle(args.get(0), args.get(1),
								args.get(2), args.get(3));
						elements.add(e);
					} else if (type.equalsIgnoreCase("MoveableObject")) {
						elements.add(new MoveableObject(args.get(0), args
								.get(1)));
					} else if (type.equalsIgnoreCase("Door")) {
						elements.add(new Door(args.get(0), args.get(1)));
					} else if (type.equalsIgnoreCase("Platform")) {
						boolean verticalTravel = args.get(5) != 0;
						elements.add(new Platform(args.get(0), args.get(1),
								args.get(2), args.get(3), args.get(4),
								verticalTravel));
					} else if (type.equalsIgnoreCase("Trigger")
							|| type.equalsIgnoreCase("ContactTrigger")
							|| type.equalsIgnoreCase("Button")
							|| type.equalsIgnoreCase("Lever")
							|| type.equalsIgnoreCase("PlayerTrigger")) {
						Trigger t = null;
						int curArg = 0;
						if (type.equalsIgnoreCase("Trigger")) {

						} else if (type.equalsIgnoreCase("ContactTrigger")) {
							t = new ContactTrigger(args.get(0), args.get(1),
									args.get(2), args.get(3));
							curArg = 4;
						} else if (type.equalsIgnoreCase("Button")) {
							t = new Button(args.get(0), args.get(1));
							curArg = 2;
						} else if (type.equalsIgnoreCase("Lever")) {
							t = new Lever(args.get(0), args.get(1));
							curArg = 2;
						} else if (type.equalsIgnoreCase("LinearTrigger")) {
							t = new LinearTrigger(args.get(0), args.get(1),
									args.get(2), args.get(3));
							curArg = 4;
						} else if (type.equalsIgnoreCase("PlayerTrigger")) {
							t = new PlayerTrigger(args.get(0), args.get(1),
									args.get(2), args.get(3));
							curArg = 4;
						}
						t.setTriggerDuration(args.get(curArg));
						t.setDelay(args.get(curArg + 1));
						int triggerable = 0;
						if (args.size() <= curArg + 2) {
							if (argsString.contains("checkpoint"))
								triggerable = TriggerConstants.CHECKPOINT;
							else if (argsString.contains("ender"))
								triggerable = TriggerConstants.ENDER;
						} else
							triggerable = args.get(curArg + 2);
						t.setTriggerable(new SavedTriggerable(triggerable));
						elements.add(t);
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<Integer> readArgs(String args) {
		List<Integer> result = new ArrayList<Integer>();
		args += " ";
		String current = "";
		for (int i = 0; i < args.length(); i++) {
			char ch = args.charAt(i);
			if ((ch >= '0' && ch <= '9') || ch == '-') {
				current += ch;
			} else {
				if (!current.isEmpty()) {
					result.add(Integer.parseInt(current));
					current = "";
				}
			}
		}
		return result;
	}

	private int readFallHeight(String line) {
		String fall = "";
		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if ((ch >= '0' && ch <= '9') || ch == '-') {
				fall += ch;
			}
		}
		return Integer.parseInt(fall);
	}

	private Point readSpawnPoint(String line) {
		String spawn[] = new String[2];
		spawn[0] = "";
		spawn[1] = "";
		int current = 0;
		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if ((ch >= '0' && ch <= '9') || ch == '-') {
				spawn[current] += ch;
			} else {
				current++;
				if (current > 1)
					break;
			}
		}
		return new Point(Integer.parseInt(spawn[0]), Integer.parseInt(spawn[1]));
	}

	private int readTool(String line) {
		if (line.equalsIgnoreCase("boomerang"))
			return ToolConstants.BOOMERANG;
		else if (line.equalsIgnoreCase("rope"))
			return ToolConstants.ROPE;
		return ToolConstants.NONE;
	}
}
