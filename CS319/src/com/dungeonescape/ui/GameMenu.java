package com.dungeonescape.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.dungeonescape.common.Images;
import com.dungeonescape.game.Game;
import com.dungeonescape.gameio.GamePanel;
import com.dungeonescape.gameio.ToggleFullscreen;
import com.dungeonescape.gameio.editor.EditorPanel;

public class GameMenu extends JLayeredPane {
	private static final long serialVersionUID = 4807364471121343782L;

	public static void main(String[] args) {
		Images.loadImages();
		System.setProperty("sun.java2d.opengl", "True");
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "True");
		ToggleFullscreen f = new ToggleFullscreen();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameMenu gm = new GameMenu(f.getRootPane());
		f.add(gm, BorderLayout.CENTER);
		f.setSize(1000, 800);
	}

	public GameMenu(JRootPane rootPane) {
		setBackground(Color.black);
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "dialog");
		rootPane.getActionMap().put("dialog", new AbstractAction() {
			private static final long serialVersionUID = 9145906117511743707L;

			public void actionPerformed(ActionEvent e) {
				if (getComponent(0) instanceof GamePanel) {

				} else if (getComponent(0) instanceof GamePanel) {

				}
			}
		});
		navigateTo(new MainMenu());
	}

	@Override
	public void paintComponent(Graphics g) {

	}

	public void navigateTo(Component panel) {
		removeAll();
		if (!(panel instanceof GamePanel) && !(panel instanceof EditorPanel)) {
			setLayout(new GridBagLayout());
		} else {
			setLayout(new BorderLayout());
		}
		add(panel, 100);
		revalidate();
		repaint();
	}

	public void showDialog(JPanel panel) {
		removeAll();
		add(panel, 200);
		revalidate();
		repaint();
	}

	private class MainMenu extends JPanel {
		private static final long serialVersionUID = -8701274909161560797L;

		public MainMenu() {
			setBackground(new Color(0, 0, 0, 125));
			setLayout(new GridLayout(0, 1, 10, 10));
			setBorder(new EmptyBorder(new Insets(40, 40, 40, 40)));
			Dimension buttonSize = new Dimension(200, 50);
			GameButton play = new GameButton("Play");
			play.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					navigateTo(new PlayMenu());
				}
			});
			play.setPreferredSize(buttonSize);
			GameButton editor = new GameButton("Editor");
			editor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					navigateTo(new EditorPanel());
				}
			});
			editor.setPreferredSize(buttonSize);
			GameButton credits = new GameButton("Credits");
			credits.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				}
			});
			credits.setPreferredSize(buttonSize);
			GameButton exit = new GameButton("Exit");
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			exit.setPreferredSize(buttonSize);
			add(play);
			add(editor);
			add(credits);
			add(exit);
		}
	}

	private class PlayOrEditActionListener implements ActionListener {
		private String filename;

		public PlayOrEditActionListener(String fileName) {
			this.filename = fileName;
		}

		public void actionPerformed(ActionEvent e) {
			File file = new File("levels/" + filename);
			GamePanel gp = new GamePanel();
			Game g = new Game(gp);
			gp.setGame(g);
			g.loadLevel(file);
			navigateTo(gp);
			gp.requestFocus();
			g.start();
		}
	}

	private class PlayMenu extends JPanel {
		private static final long serialVersionUID = -7010447333195916816L;
		private final Dimension size = new Dimension(600, 500);
		private JList<String> list;

		public PlayMenu() {
			setPreferredSize(size);
			setBackground(new Color(0, 0, 0, 125));
			setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
			setLayout(new BorderLayout());
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(0, 2, 10, 10));
			panel.setOpaque(false);
			JPanel premade = new JPanel();
			premade.setOpaque(false);
			premade.setLayout(new GridLayout(3, 3, 10, 10));
			premade.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
			for (int i = 0; i < 9; i++) {
				premade.add(new GameButton("" + (i + 1)));
				((GameButton) premade.getComponent(i))
						.addActionListener(new PlayOrEditActionListener(
								"premade/" + (i + 1) + ".level"));
			}
			JPanel custom = new JPanel();
			custom.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
			custom.setLayout(new BorderLayout());
			custom.setOpaque(false);
			list = listCustoms();
			custom.add(list, BorderLayout.CENTER);
			GameButton playCustom = new GameButton("Play");
			playCustom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (list.getSelectedValue() != null) {
						(new PlayOrEditActionListener(list.getSelectedValue()))
								.actionPerformed(e);
					}
				}
			});
			playCustom.setPreferredSize(new Dimension(100, 30));
			custom.add(playCustom, "South");
			panel.add(premade);
			panel.add(custom);
			add(panel, "Center");
			GameButton backButton = new GameButton("Back");
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					navigateTo(new MainMenu());
				}
			});
			backButton.setPreferredSize(new Dimension(100, 30));
			JPanel backPanel = new JPanel();
			backPanel.setOpaque(false);
			backPanel.add(backButton);
			add(backPanel, "South");
		}

		public JList<String> listCustoms() {
			File[] files = (new File("levels/"))
					.listFiles(new FilenameFilter() {
						public boolean accept(File dir, String name) {
							return name.toLowerCase().endsWith(".level");
						}
					});
			String[] names = new String[files.length];
			for (int i = 0; i < files.length; i++) {
				names[i] = files[i].getName();
			}
			JList<String> result = new JList<String>(names);
			result.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
			result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			return result;
		}

		@Override
		public Dimension getPreferredSize() {
			return size;
		}

		@Override
		public Dimension getMinimumSize() {
			return size;
		}
	}
}
