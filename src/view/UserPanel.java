package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import listeners.ArrowKeyListener;


public class UserPanel extends JPanel {

	private static final long serialVersionUID = 1860979716621182121L;
	private static final int buffer = 10;
	private JTextArea userTextArea;
	private JTextField userInputField;

	public UserPanel(ArrowKeyListener arrowKeyListener) {
		setUpGUI();
		setUpListeners(arrowKeyListener);

	}

	private void setUpListeners(ArrowKeyListener arrowKeyListener) {
		userInputField.addKeyListener(arrowKeyListener);
		
	}

	private void setUpGUI() {
		// Sets the layout
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Initiate the objects
		userTextArea = new JTextArea();
		userTextArea.setBackground(Color.WHITE);
		userTextArea.setEditable(false);
		userTextArea.setLineWrap(true); 
		userTextArea.setWrapStyleWord(true);

		userInputField = new JTextField();
		userInputField.setBackground(Color.WHITE);

		// Sets the text area with constants
		c.gridx = 0; // First Column
		c.gridy = 0; // Top Row
		c.weightx = 1; // Takes up more space on x axis
		c.weighty = 1; // Takes up more space on y axis
		c.fill = GridBagConstraints.BOTH;
		this.add(new JScrollPane(userTextArea), c);

		// Sets the userInputArea with constants
		c.gridx = 0; // First Column
		c.gridy = 5; // 6th Row
		c.weightx = .2;
		c.weighty = .2;
		c.insets = new Insets(buffer, 0, 0, 0); // top padding
		this.add(userInputField, c);

	}

	public String getCommand() {
		String command = userInputField.getText();
		updateTextArea(command);
		userInputField.setText("");
		return command;
	}

	public void updateTextArea(String text) {
		userTextArea.append(text + "\n");
		userTextArea.setCaretPosition(userTextArea.getDocument().getLength());
		
	}

	public void clearTextArea() {
		userTextArea.setText("");
		this.repaint();
		
	}

	public void setCommandFromMemory(String savedCommand) {
		userInputField.setText(savedCommand);
		
	}
}
