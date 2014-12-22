package com.dungeonescape.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.dungeonescape.common.CommonMethods;
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
import com.dungeonescape.element.Triggerable;
import com.dungeonescape.game.Game.CheckPoint;
import com.dungeonescape.game.Game.GameEnder;

public class Level {
	private List<GameElement> elements;
	private Point spawnPoint;
	private int fallHeight, tool;
	private BufferedImage background;
	private String backgroundUrl, tip;
	private File file;

	public Level() {
		elements = new ArrayList<GameElement>();
		spawnPoint = new Point(0, 0);
		fallHeight = 1000;
		tool = ToolConstants.NONE;
		tip = "";
		setBackgroundUrl("back.png");
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

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		if (!backgroundUrl.isEmpty()) {
			this.backgroundUrl = backgroundUrl;
			try {
				background = CommonMethods.convert(ImageIO.read(new File("img/"
						+ backgroundUrl)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void paintBackground(Graphics2D g, Point camera, Dimension size) {
		TexturePaint tp = new TexturePaint(background, new Rectangle2D.Double(
				-camera.x / 3, -camera.y / 3, background.getWidth(),
				background.getHeight()));
		Paint prevPaint = g.getPaint();
		g.setPaint(tp);
		g.fillRect(0, 0, (int) size.getWidth(), (int) size.getHeight());
		g.setPaint(prevPaint);
		Color prevColor = g.getColor();
		g.setColor(new Color(0, 0, 0, 125));
		g.fillRect(0, 0, (int) size.getWidth(), (int) size.getHeight());
		g.setColor(prevColor);
	}
	
	public File getFile() {
		return file;
	}

	public void saveLevel(File file) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
			String toolString = "none";
			if (tool == ToolConstants.BOOMERANG)
				toolString = "boomerang";
			else if (tool == ToolConstants.ROPE)
				toolString = "rope";
			bw.write(fallHeight + "");
			bw.newLine();
			bw.write(spawnPoint.x + " " + spawnPoint.y);
			bw.newLine();
			bw.write(toolString);
			bw.newLine();
			bw.write(tip);
			bw.newLine();
			bw.write(backgroundUrl);
			bw.newLine();
			bw.newLine();
			for (GameElement e : elements) {
				if (e instanceof Obstacle) {
					bw.write("Obstacle " + (int) e.getX() + " "
							+ (int) e.getY() + " " + e.getWidth() + " "
							+ e.getHeight());
					bw.newLine();
				} else if (e instanceof MoveableObject) {
					bw.write("MoveableObject " + (int) e.getX() + " "
							+ (int) e.getY());
					bw.newLine();
				} else if (e instanceof Door) {
					bw.write("Door " + (int) e.getX() + " " + (int) e.getY());
					bw.newLine();
				} else if (e instanceof Platform) {
					int vertical = ((Platform) e).isVerticalTravel() ? 1 : 0;
					bw.write("Platform " + (int) e.getX() + " "
							+ (int) e.getY() + " " + e.getWidth() + " "
							+ e.getHeight() + " "
							+ ((Platform) e).getMaxTravel() + " " + vertical);
					bw.newLine();
				} else if (e instanceof Trigger) {
					Trigger t = (Trigger) e;
					if (t instanceof ContactTrigger
							&& !(t instanceof PlayerTrigger)) {
						bw.write("ContactTrigger " + (int) e.getX() + " "
								+ (int) e.getY() + " " + e.getWidth() + " "
								+ e.getHeight());
					} else if (t instanceof Button) {
						bw.write("Button " + (int) e.getX() + " "
								+ (int) e.getY());
					} else if (t instanceof Lever) {
						bw.write("Lever " + (int) e.getX() + " "
								+ (int) e.getY());
					} else if (t instanceof LinearTrigger) {
						LinearTrigger lt = (LinearTrigger) t;
						bw.write("LinearTrigger " + (int) lt.getX() + " "
								+ (int) lt.getY() + " " + lt.getxEnd() + " "
								+ lt.getyEnd());
					} else if (t instanceof PlayerTrigger) {
						bw.write("PlayerTrigger " + (int) e.getX() + " "
								+ (int) e.getY() + " " + e.getWidth() + " "
								+ e.getHeight());
					} else {
						bw.write("Trigger " + (int) e.getX() + " "
								+ (int) e.getY());
					}
					bw.write(" " + t.getTriggerDuration() + " " + t.getDelay()
							+ " ");
					Triggerable tbl = t.getTriggerable();
					if (tbl instanceof SavedTriggerable) {
						int tid = ((SavedTriggerable) tbl).getGameElementNo();
						if (tid >= 0)
							bw.write(tid);
						else if (tid == TriggerConstants.CHECKPOINT)
							bw.write("checkpoint");
						else if (tid == TriggerConstants.ENDER)
							bw.write("ender");
					} else {
						int index = elements.indexOf(tbl);
						if (index >= 0)
							bw.write(index + "");
						else if (tbl instanceof GameEnder)
							bw.write("ender");
						else if (tbl instanceof CheckPoint)
							bw.write("checkpoint");
					}
					bw.newLine();
				}
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void connectSavedTriggers() {
		for (GameElement e : elements) {
			if (e instanceof Trigger) {
				Trigger t = (Trigger) e;
				if (t.getTriggerable() instanceof SavedTriggerable) {
					SavedTriggerable st = (SavedTriggerable) t.getTriggerable();
					if (st.getGameElementNo() >= 0)
						t.setTriggerable((Triggerable) elements.get(st
								.getGameElementNo()));
				}
			}
		}
	}

	public void reload(boolean preserveCheckpoint) {
		Point tmp = spawnPoint;
		loadLevel(file);
		if (preserveCheckpoint) {
			spawnPoint = tmp;
		}
	}

	public void loadLevel(File file) {
		this.file = file;
		try {
			elements.clear();
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			fallHeight = readFallHeight(br.readLine());
			spawnPoint = readSpawnPoint(br.readLine());
			tool = readTool(br.readLine());
			tip = br.readLine();
			setBackgroundUrl(br.readLine());

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
							|| type.equalsIgnoreCase("PlayerTrigger")
							|| type.equalsIgnoreCase("LinearTrigger")) {
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
			fr.close();
			connectSavedTriggers();
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
