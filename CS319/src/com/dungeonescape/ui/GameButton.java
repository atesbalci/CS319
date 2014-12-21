package com.dungeonescape.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;

public class GameButton extends JButton {
	private static final long serialVersionUID = -2935417191687118265L;

	private final Color PRESSED = new Color(128, 21, 21);
	private final Color DEFAULT = PRESSED.brighter();
	private final Color HOVERED = DEFAULT.brighter();

	public GameButton() {
		this("");
	}

	public GameButton(String text) {
		super(text);
		super.setContentAreaFilled(false);
		setBackground(DEFAULT);
		setForeground(Color.WHITE);
		setFocusPainted(false);
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBorder(new BevelBorder(BevelBorder.RAISED));
	}

	@Override
	public void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(PRESSED);
		} else if (getModel().isRollover()) {
			g.setColor(HOVERED);
		} else {
			g.setColor(DEFAULT);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

	@Override
	public void setContentAreaFilled(boolean b) {
	}
}
