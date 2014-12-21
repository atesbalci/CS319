package com.dungeonescape.gameio.editor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dungeonescape.ui.GameButton;

public class SaveBar extends JPanel implements ActionListener {
	private static final long serialVersionUID = 7496162042016776991L;

	private EditorPanel ep;
	private JTextField tf;

	public SaveBar(EditorPanel ep) {
		this.ep = ep;
		tf = new JTextField();
		tf.setPreferredSize(new Dimension(100, 25));
		add(tf);
		GameButton save = new GameButton("Save");
		save.addActionListener(this);
		add(save);
		GameButton load = new GameButton("Load");
		load.addActionListener(this);
		add(load);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.getComponent(1))
			ep.getLevel().saveLevel(new File("levels/" + tf.getText() + ".level"));
		if (e.getSource() == this.getComponent(2))
			ep.getLevel().loadLevel(new File("levels/" + tf.getText() + ".level"));
	}
}
