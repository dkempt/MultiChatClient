package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import listeners.*;
import client.ChatClient;

public class ClientGUI{

	private static final long serialVersionUID = -4520094504783344689L;
	private static final String gameTitle = "Multi-Client Chat Server";
	private static final int buffer = 10;
	private ChatPanel chatPanel;
	private UserPanel userPanel;
	private LoginPanel loginPanel;
	private static JFrame clientWindow;
	private GridBagLayout theLayout;
	private GridBagConstraints c;
	private LoginListener loginListener;
	private RegisterListener registerListener;
	private WindowCloseListener windowListener;
	private ArrowKeyListener arrowKeyListener;
	
	public ClientGUI(ChatClient client) {
		setUpListeners(client);
		setUpView();		
	}

	private void setUpListeners(ChatClient client) {
		loginListener = new LoginListener(this, client);
		registerListener = new RegisterListener(this, client);
		windowListener = new WindowCloseListener(client);
		arrowKeyListener = new ArrowKeyListener(this, client);
	}

	private void setUpView() {

		// Create the JFrame and add the window listener
		clientWindow = new JFrame();
		clientWindow.addWindowListener(windowListener);

		// Define the layout manager
		theLayout = new GridBagLayout();
		c = new GridBagConstraints();

		// Define the window of the GUI
		clientWindow.setTitle(gameTitle);
		clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientWindow.setLayout(theLayout);
		clientWindow.setPreferredSize(new Dimension(800, 600));
		clientWindow.setResizable(false);
		clientWindow.setLocation(20, 20);

		// Set up and add the login panel
		loginPanel = new LoginPanel(loginListener, registerListener);
		c.weightx = 1; // Takes up more space on x axis
		c.weighty = 1; // Takes up more space on y axis
		c.fill = GridBagConstraints.BOTH;
		clientWindow.add(loginPanel, c);

		// Reset the constants
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;

		// Sets the visibility.. KEEP AT THE BOTTOM OF THE METHOD
		clientWindow.pack();
		clientWindow.setVisible(true);
		clientWindow.repaint();
		
	}

	public void setUpUserView() {

		// Creates the panels
		userPanel = new UserPanel(arrowKeyListener);
		chatPanel = new ChatPanel();
		
		// Removes the login panel
		clientWindow.remove(loginPanel);

		// Sets the layout of the panels
		c.weightx = 1; // Takes up more space on x axis
		c.weighty = 1; // Takes up more space on y axis
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 5;
		c.insets = new Insets(buffer, buffer, buffer, buffer);
		clientWindow.add(userPanel, c);
		
		c.weightx = 1; // Takes up more space on x axis
		c.weighty = 1; // Takes up more space on y axis
		c.gridheight = 2;
		c.gridx = 1;
		c.gridy = 3;
		clientWindow.add(chatPanel, c);
		
		
		clientWindow.pack();
		clientWindow.setVisible(true);
		clientWindow.repaint();
	}

	public String getUserName() {		
		return loginPanel.getUserName();

	}

	public String getPassword() {
		return loginPanel.getPassword();

	}
	
	public void resetLogin() {
		loginPanel.clearNameAndPassword();

	}
	
	public String getCommand() {
		return userPanel.getCommand();
	}

	public void updateChatMessage(String message) {
		chatPanel.updateChatLog(message);
	}

	public void updateGameConsole(String message) {
		userPanel.updateTextArea(message);
		
	}

	public void close() {
		clientWindow.dispose();		
	}

	public void clearConsole() {
		userPanel.clearTextArea();
		clientWindow.repaint();
	}

	public void setCommandFromMemory(String savedCommand) {
		userPanel.setCommandFromMemory(savedCommand);
		
	}
	
}