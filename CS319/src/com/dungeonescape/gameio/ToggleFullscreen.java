package com.dungeonescape.gameio;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public class ToggleFullscreen extends JFrame {
	private static final long serialVersionUID = 6075801570706118769L;

	private boolean fullscreen;
	private Point prevLocation;
	private Dimension prevSize;

	public ToggleFullscreen() {
		super();
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fullscreen = false;
		prevLocation = getLocation();
		JRootPane rootPane = getRootPane();
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "goFullscreen");
		rootPane.getActionMap().put("goFullscreen", new AbstractAction() {
			private static final long serialVersionUID = 9145906117511743707L;

			public void actionPerformed(ActionEvent arg0) {
				toggle();
			}
		});
	}

	public void toggle() {
		dispose();
		if (!fullscreen) {
			prevLocation = getLocation();
			prevSize = getSize();
			setLocation(0, 0);
			setUndecorated(true);
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else {
			setLocation(prevLocation);
			setUndecorated(false);
			setSize(prevSize);
		}
		setVisible(true);
		fullscreen = !fullscreen;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
}