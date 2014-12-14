package com.dungeonescape.gameio.editor;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dungeonescape.element.Button;
import com.dungeonescape.element.ContactTrigger;
import com.dungeonescape.element.Door;
import com.dungeonescape.element.GameElement;
import com.dungeonescape.element.Lever;
import com.dungeonescape.element.LinearTrigger;
import com.dungeonescape.element.MoveableObject;
import com.dungeonescape.element.Obstacle;
import com.dungeonescape.element.Platform;
import com.dungeonescape.element.PlayerTrigger;
import com.dungeonescape.element.SavedTriggerable;
import com.dungeonescape.element.Trigger;
import com.dungeonescape.element.Triggerable;

public class ElementPropertiesPanel extends JPanel {
	private static final long serialVersionUID = -5207694422232205757L;

	private GameElement element;
	private int elementId;
	private EditorPanel ep;

	public ElementPropertiesPanel(EditorPanel ep) {
		this.ep = ep;
		setLayout(new GridLayout(0, 1));
		elementId = -10;
	}

	public void setSelectedGameElement(GameElement selectedGameElement, int id) {
		this.element = selectedGameElement;
		elementId = id;
		refresh();
	}

	public void refresh() {
		removeAll();
		List<JPanel> fields = generateFields();
		for (JPanel tf : fields)
			add(tf);
		updateUI();
	}

	public GameElement getSelectedGameElement() {
		return element;
	}

	private JPanel generateField(String label, String defaultValue,
			ActionListener al) {
		JTextField tf = new JTextField(defaultValue);
		tf.addActionListener(al);
		tf.setPreferredSize(new Dimension(50, 25));
		JPanel result = new JPanel();
		result.add(new JLabel(label + ": "));
		result.add(tf);
		return result;
	}

	private List<JPanel> generateFields() {
		List<JPanel> result = new LinkedList<JPanel>();

		if (element != null) {
			result.add(generateField("x", "" + (int) element.getX(),
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							element.setX(Integer.parseInt(((JTextField) (e
									.getSource())).getText()));
							ep.refresh();
						}
					}));
			result.add(generateField("y", "" + (int) element.getY(),
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							element.setY(Integer.parseInt(((JTextField) (e
									.getSource())).getText()));
							ep.refresh();
						}
					}));

			if (element instanceof Obstacle) {
				result.add(generateField("width", "" + element.getWidth(),
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								element.setWidth(Integer
										.parseInt(((JTextField) (e.getSource()))
												.getText()));
								ep.refresh();
							}
						}));
				result.add(generateField("height", "" + element.getHeight(),
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								element.setHeight(Integer
										.parseInt(((JTextField) (e.getSource()))
												.getText()));
								ep.refresh();
							}
						}));
			} else if (element instanceof MoveableObject) {
			} else if (element instanceof Door) {
			} else if (element instanceof Platform) {
				result.add(generateField("width", "" + element.getWidth(),
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								element.setWidth(Integer
										.parseInt(((JTextField) (e.getSource()))
												.getText()));
								ep.refresh();
							}
						}));
				result.add(generateField("height", "" + element.getHeight(),
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								element.setHeight(Integer
										.parseInt(((JTextField) (e.getSource()))
												.getText()));
								ep.refresh();
							}
						}));
				result.add(generateField("maxTravel",
						"" + ((Platform) element).getMaxTravel(),
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								((Platform) element).setMaxTravel(Integer
										.parseInt(((JTextField) (e.getSource()))
												.getText()));
								ep.refresh();
							}
						}));
				result.add(generateField("verticalTravel", ""
						+ ((Platform) element).isVerticalTravel(),
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								((Platform) element).setVerticalTravel(Boolean
										.parseBoolean(((JTextField) (e
												.getSource())).getText()));
								ep.refresh();
							}
						}));
			} else if (element instanceof Trigger) {
				if (element instanceof ContactTrigger) {
					result.add(generateField("width", "" + element.getWidth(),
							new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									element.setWidth(Integer
											.parseInt(((JTextField) (e
													.getSource())).getText()));
									ep.refresh();
								}
							}));
					result.add(generateField("height",
							"" + element.getHeight(), new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									element.setHeight(Integer
											.parseInt(((JTextField) (e
													.getSource())).getText()));
									ep.refresh();
								}
							}));
				} else if (element instanceof Button) {
				} else if (element instanceof Lever) {
				} else if (element instanceof LinearTrigger) {
				} else if (element instanceof PlayerTrigger) {
					result.add(generateField("width", "" + element.getWidth(),
							new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									element.setWidth(Integer
											.parseInt(((JTextField) (e
													.getSource())).getText()));
									ep.refresh();
								}
							}));
					result.add(generateField("height",
							"" + element.getHeight(), new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									element.setHeight(Integer
											.parseInt(((JTextField) (e
													.getSource())).getText()));
									ep.refresh();
								}
							}));
				} else {
				}
				result.add(generateField("triggerDuration", ""
						+ ((Trigger) element).getTriggerDuration(),
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								((Trigger) element).setTriggerDuration(Integer
										.parseInt(((JTextField) (e.getSource()))
												.getText()));
								ep.refresh();
							}
						}));
				result.add(generateField("delay",
						"" + ((Trigger) element).getDelay(),
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								((Trigger) element).setDelay(Integer
										.parseInt(((JTextField) (e.getSource()))
												.getText()));
								ep.refresh();
							}
						}));
				result.add(generateField("triggerable", "",
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								((Trigger) element).setTriggerable(new SavedTriggerable(
										Integer.parseInt(((JTextField) (e
												.getSource())).getText())));
								ep.refresh();
							}
						}));
			}
			if(element instanceof Triggerable) {
				JPanel panel = new JPanel();
				panel.add(new JLabel("Triggerable No: " + elementId));
				result.add(panel);
			}
		} else {
			result.add(generateField("fallHeight", ""
					+ ep.getLevel().getFallHeight(), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ep.getLevel().setFallHeight(
							Integer.parseInt(((JTextField) (e.getSource()))
									.getText()));
				}
			}));
			result.add(generateField("Spawn X", ""
					+ ep.getLevel().getSpawnPoint().x, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ep.getLevel().getSpawnPoint().x = (Integer
							.parseInt(((JTextField) (e.getSource())).getText()));
				}
			}));
			result.add(generateField("Spawn Y", ""
					+ ep.getLevel().getSpawnPoint().y, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ep.getLevel().getSpawnPoint().y = (Integer
							.parseInt(((JTextField) (e.getSource())).getText()));
				}
			}));
		}
		return result;
	}

	public int getSelectedElementId() {
		return elementId;
	}
}