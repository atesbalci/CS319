package com.dungeonescape.gameio.editor;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.dungeonescape.element.GameElement;

public class EditorSidebar extends JPanel {
	private static final long serialVersionUID = 4636906374046518685L;

	private EditorPanel ep;
	private ElementPropertiesPanel epp;
	private AddElementPanel aep;

	public EditorSidebar(EditorPanel ep) {
		this.setEditorPanel(ep);
		setLayout(new BorderLayout());
		epp = new ElementPropertiesPanel(ep);
		aep = new AddElementPanel(this);
		add(aep, "South");
		add(epp, "North");
	}

	public void setSelectedGameElement(GameElement selectedGameElement, int id) {
		epp.setSelectedGameElement(selectedGameElement, id);
	}

	public GameElement getSelectedGameElement() {
		return epp.getSelectedGameElement();
	}

	public void addElement(GameElement e) {
		ep.addElement(e);
	}

	public EditorPanel getEditorPanel() {
		return ep;
	}

	public void setEditorPanel(EditorPanel ep) {
		this.ep = ep;
	}

	public int getSelectedGameElementId() {
		return epp.getSelectedElementId();
	}
}
