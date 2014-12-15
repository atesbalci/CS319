package com.dungeonescape.gameio;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dungeonescape.game.Game;
import com.dungeonescape.gameio.editor.EditorPanel;

public class Main {
	private static JFrame frame;

	public static void main(String[] args) {
		frame = new JFrame("Select Mode");
		JPanel panel = new JPanel();
		JButton gameButton = new JButton("Game");
		JButton editorButton = new JButton("Editor");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		gameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ToggleFullscreen f = new ToggleFullscreen();
				GamePanel wp = new GamePanel();
				Game w = new Game(wp);
				wp.setGame(w);
				w.loadLevel(new File("levels/level.level"));
				f.add(wp);
				wp.requestFocus();
				w.start();
				frame.dispose();
			}
		});
		editorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ToggleFullscreen f = new ToggleFullscreen();
				EditorPanel ep = new EditorPanel();
				f.add(ep);
				f.setVisible(true);
				frame.dispose();
			}
		});
		frame.add(panel);
		panel.setPreferredSize(new Dimension(250, 250));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(gameButton);
		panel.add(editorButton);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(new Point(screen.width / 2 - 125,
				screen.height / 2 - 125));
		frame.setVisible(true);
	}
}
