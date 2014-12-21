package com.dungeonescape.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

public class GameMenu extends JLayeredPane implements ComponentListener {
	private static final long serialVersionUID = 4807364471121343782L;
	
	private final GameMenu thisMenu = this;

	private GameDialog dialog;
	private BufferedImage background;

	public static void main(String[] args) {
		Images.loadImages();
		System.setProperty("sun.java2d.opengl", "True");
		ToggleFullscreen f = new ToggleFullscreen();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameMenu gm = new GameMenu(f.getRootPane());
		f.add(gm, BorderLayout.CENTER);
		f.setSize(1000, 800);
	}

	public GameMenu(JRootPane rootPane) {
		dialog = null;
		try {
			background = ImageIO.read(new File("img/menu_back.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setBackground(Color.black);
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "dialog");
		rootPane.getActionMap().put("dialog", new AbstractAction() {
			private static final long serialVersionUID = 9145906117511743707L;

			public void actionPerformed(ActionEvent e) {
				if (getLayout() == null) {
					if (getComponent(0) instanceof GamePanel) {
						if (!(dialog instanceof LevelCompleteMenu))
							showHideGameDialog(new PauseMenuWithRestart(
									getSize(), (GamePanel) (getComponent(0))));
					} else
						showHideGameDialog(new PauseMenu(getSize()));
				}
			}
		});
		addComponentListener(this);
		navigateTo(new MainMenu());
	}

	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}

	public void navigateTo(Component panel) {
		removeAll();
		dialog = null;
		if (!(panel instanceof GamePanel) && !(panel instanceof EditorPanel)) {
			setLayout(new GridBagLayout());
			add(panel);
		} else {
			setLayout(null);
			panel.setSize(getSize());
			panel.setLocation(new Point(0, 0));
			add(panel, JLayeredPane.DEFAULT_LAYER);
		}
		revalidate();
		repaint();
	}

	public void showHideGameDialog(GameDialog panel) {
		if (dialog == null) {
			dialog = panel;
			add(panel, JLayeredPane.PALETTE_LAYER);
		} else {
			remove(dialog);
			dialog = null;
		}
		revalidate();
		repaint();
	}

	public void levelComplete() {
		showHideGameDialog(new LevelCompleteMenu(getSize()));
	}

	public void componentResized(ComponentEvent e) {
		Component[] cmps = getComponents();
		for (int i = 0; i < cmps.length; i++) {
			if (cmps[i] != null) {
				if (cmps[i] instanceof GamePanel
						|| cmps[i] instanceof EditorPanel
						|| cmps[i] instanceof GameDialog) {
					cmps[i].setSize(getSize());
				}
			}
		}
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
			GameButton options = new GameButton("Options");
			options.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					OptionsMenu om = new OptionsMenu();
					navigateTo(om);
					om.requestFocus();
				}
			});
			options.setPreferredSize(buttonSize);
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
			add(options);
			add(credits);
			add(exit);
		}
	}

	private class OptionsMenu extends JPanel implements ActionListener,
			KeyListener {
		private static final long serialVersionUID = -2411265021454995104L;

		private GameButton jump, left, right, use, restart, selected;

		public OptionsMenu() {
			setFocusable(true);
			addKeyListener(this);
			setBackground(new Color(0, 0, 0, 125));
			setLayout(new GridLayout(0, 1, 10, 10));
			setBorder(new EmptyBorder(new Insets(40, 40, 40, 40)));
			Dimension buttonSize = new Dimension(200, 50);
			jump = new GameButton("Jump Button: "
					+ KeyEvent.getKeyText(GamePanel.jumpButton));
			jump.addActionListener(this);
			jump.setPreferredSize(buttonSize);
			add(jump);
			left = new GameButton("Left Button: "
					+ KeyEvent.getKeyText(GamePanel.leftButton));
			left.addActionListener(this);
			left.setPreferredSize(buttonSize);
			add(left);
			right = new GameButton("Right Button: "
					+ KeyEvent.getKeyText(GamePanel.rightButton));
			right.addActionListener(this);
			right.setPreferredSize(buttonSize);
			add(right);
			use = new GameButton("Interaction Button: "
					+ KeyEvent.getKeyText(GamePanel.useButton));
			use.addActionListener(this);
			use.setPreferredSize(buttonSize);
			add(use);
			restart = new GameButton("Restart Button: "
					+ KeyEvent.getKeyText(GamePanel.restartButton));
			restart.addActionListener(this);
			restart.setPreferredSize(buttonSize);
			add(restart);

			GameButton back = new GameButton("Back");
			back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					navigateTo(new MainMenu());
				}
			});
			back.setPreferredSize(buttonSize);
			add(back);
		}

		public void resetButtons() {
			jump.setText("Jump Button: "
					+ KeyEvent.getKeyText(GamePanel.jumpButton));
			left.setText("Left Button: "
					+ KeyEvent.getKeyText(GamePanel.leftButton));
			right.setText("Right Button: "
					+ KeyEvent.getKeyText(GamePanel.rightButton));
			use.setText("Interaction Button: "
					+ KeyEvent.getKeyText(GamePanel.useButton));
			restart.setText("Restart Button: "
					+ KeyEvent.getKeyText(GamePanel.restartButton));
		}

		public void actionPerformed(ActionEvent e) {
			if (selected != null)
				resetButtons();
			selected = (GameButton) (e.getSource());
			selected.setText("Press any key to set");
			requestFocus();
		}

		public void keyPressed(KeyEvent e) {
			if (selected != null) {
				if (selected == jump)
					GamePanel.jumpButton = e.getKeyCode();
				else if (selected == left)
					GamePanel.leftButton = e.getKeyCode();
				else if (selected == right)
					GamePanel.rightButton = e.getKeyCode();
				else if (selected == use)
					GamePanel.useButton = e.getKeyCode();
				else if (selected == restart)
					GamePanel.restartButton = e.getKeyCode();
				selected = null;
				resetButtons();
			}
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
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
			gp.setGameMenu(thisMenu);
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

	private class GameDialog extends JPanel {
		private static final long serialVersionUID = -3244577989685663540L;

		protected final Dimension BUTTON_SIZE = new Dimension(150, 30);

		protected JPanel panel;

		public GameDialog(Dimension size) {
			setOpaque(false);
			setLayout(new GridBagLayout());
			setSize(size);
			panel = new JPanel();
			panel.setBackground(new Color(0, 0, 0, 75));
			panel.setLayout(new GridLayout(0, 1, 10, 10));
			panel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
			add(panel);
		}

		public void addButton(String text, ActionListener al) {
			GameButton gb = new GameButton(text);
			gb.addActionListener(al);
			gb.setPreferredSize(BUTTON_SIZE);
			panel.add(gb);
		}

		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(new Color(0, 0, 0, 175));
			g.fillRect(getLocation().x, getLocation().y, getWidth(),
					getHeight());
		}
	}

	private class PauseMenu extends GameDialog {
		private static final long serialVersionUID = 3340418322179036264L;

		public PauseMenu(Dimension size) {
			super(size);
			addButton("Resume", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showHideGameDialog(new GameDialog(new Dimension()));
				}
			});
			addButton("Exit", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					navigateTo(new PlayMenu());
				}
			});
		}
	}

	private class LevelCompleteMenu extends GameDialog {
		private static final long serialVersionUID = -7873635041415097811L;

		public LevelCompleteMenu(Dimension size) {
			super(size);
			panel.add(new JLabel("Level Complete!"));
			addButton("Exit", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					navigateTo(new PlayMenu());
				}
			});
		}
	}

	private class PauseMenuWithRestart extends GameDialog {
		private static final long serialVersionUID = 3340418322179036264L;

		private GamePanel gamePanel;

		public PauseMenuWithRestart(Dimension size, GamePanel gp) {
			super(size);
			this.gamePanel = gp;
			addButton("Resume", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showHideGameDialog(new GameDialog(new Dimension()));
				}
			});
			addButton("Restart", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gamePanel.end(false);
					showHideGameDialog(new GameDialog(new Dimension()));
				}
			});
			addButton("Exit", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					navigateTo(new PlayMenu());
				}
			});
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
}
