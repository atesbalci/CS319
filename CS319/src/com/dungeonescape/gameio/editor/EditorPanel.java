package com.dungeonescape.gameio.editor;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.dungeonescape.element.GameElement;
import com.dungeonescape.game.Level;
import com.dungeonescape.gameio.ToggleFullscreen;

public class EditorPanel extends JPanel {
	private static final long serialVersionUID = 864102639139728889L;

	private EditorSidebar es;
	private EditorMainPanel emp;
	private Level level;

	public EditorPanel() {
		setLevel(new Level());
		setLayout(new BorderLayout());
		es = new EditorSidebar(this);
		emp = new EditorMainPanel(level.getElements(), this);
		add(emp, BorderLayout.CENTER);
		add(es, "East");
		es.setSelectedGameElement(null, -10);
	}

	public void addElement(GameElement e) {
		level.addElement(e);
		refresh();
	}

	public void refresh() {
		emp.updateUI();
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void setSelectedElement(GameElement e, int id) {
		es.setSelectedGameElement(e, id);
	}
	
	public GameElement getSelectedElement() {
		return es.getSelectedGameElement();
	}
	
	public int getSelectedElementId() {
		return es.getSelectedGameElementId();
	}

	public static void main(String[] args) {
		EditorPanel ep = new EditorPanel();
		ToggleFullscreen f = new ToggleFullscreen();
		f.add(ep);
		f.setVisible(true);
	}
}
