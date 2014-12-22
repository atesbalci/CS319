package com.dungeonescape.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
	public static BufferedImage BUTTON_ON;
	public static BufferedImage BUTTON_OFF;
	public static BufferedImage LEVER_ON;
	public static BufferedImage LEVER_OFF;
	public static BufferedImage OBSTACLE;
	public static BufferedImage DOOR;
	public static BufferedImage MOVEABLE_OBJECT;

	public static void loadImages() {
		try {
			BUTTON_OFF = ImageIO.read(new File("img/buttonoff.png"));
			BUTTON_ON = ImageIO.read(new File("img/buttonon.png"));
			LEVER_ON = ImageIO.read(new File("img/leveroff.png"));
			LEVER_OFF = ImageIO.read(new File("img/leveron.png"));
			OBSTACLE = ImageIO.read(new File("img/obstacle.png"));
			DOOR = ImageIO.read(new File("img/door.png"));
			MOVEABLE_OBJECT = ImageIO.read(new File("img/moveableobject.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
