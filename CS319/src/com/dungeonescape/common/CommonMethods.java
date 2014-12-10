package com.dungeonescape.common;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class CommonMethods {
	public static BufferedImage horizontalflip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();
		return dimg;
	}
	
	private static class SoundThread extends Thread {
		String file;

		public SoundThread(String file) {
			this.file = file;
		}

		public void run() {
			File yourFile = new File(file);
			AudioInputStream stream = null;
			AudioFormat format;
			DataLine.Info info;
			Clip clip = null;

			try {
				stream = AudioSystem.getAudioInputStream(yourFile);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			try {
				clip = (Clip) AudioSystem.getLine(info);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
			try {
				clip.open(stream);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			clip.start();
		}
	}

	public static void playSound(String file) {
		(new SoundThread(file)).start();
	}
}
