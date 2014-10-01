package com.thegame.common;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageMethods {
	public static BufferedImage horizontalflip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return dimg;
    }
}
