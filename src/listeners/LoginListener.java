package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.ClientGUI;
import client.ChatClient;

public class LoginListener implements ActionListener {
	
	// References to the view and client
	private ClientGUI view;
	private ChatClient client;

	public LoginListener(ClientGUI view, ChatClient client) {
		this.view = view;
		this.client = client;
	}

	/**
	 * Reads in the user name and password from the panel and sends it to the
	 * client to be verified. If not a valid login, the panel is reset after a prompt
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		// If the user is not valid, then reset the login.
		if (!client.validateUser(view.getUserName(), view.getPassword())){
			JOptionPane.showMessageDialog(null, "Incorrect user name or password. Please try again");
			view.resetLogin();
			return;
		}
		
		// If the user is valid, then set up the new view.
		view.setUpUserView();
	}
}