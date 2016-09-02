package listeners;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import view.ClientGUI;
import client.ChatClient;

public class RegisterListener implements ActionListener{

	
	private static final String theExplanation = "Please enter a user name and password you would like to use.";
	private ChatClient client;
	private JDialog popup;
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JTextField userName;
	private JTextField password;
	private JButton registerButton;
	private ButtonListener registerListener;
	private JLabel messageLabel;
	private ClientGUI view;

	public RegisterListener(ClientGUI view, ChatClient client) {
		this.client = client;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Create a popup for the registration
		setUpPopup();
		setUpRegisterListener();
				
	}

	private void setUpRegisterListener() {
		registerListener = new ButtonListener();
		registerButton.addActionListener(registerListener);
		
	}

	private void setUpPopup() {
		
		// Initiate fields
		userNameLabel = new JLabel("User Name:");
		passwordLabel = new JLabel("Password:");
		userName = new JTextField();
		userName.setPreferredSize(new Dimension(150,25));
		password = new JTextField();
		password.setPreferredSize(new Dimension(150,25));
		popup = new JDialog();
		registerButton = new JButton("Register");
		messageLabel = new JLabel("");
		
		// Set the layout
		popup.setLayout(new FlowLayout());
		popup.setSize(new Dimension(300,160));
		popup.setLocation(300,200);
		
		// Add the elements to the panel
		popup.add(messageLabel);
		popup.add(userNameLabel);
		popup.add(userName);
		popup.add(passwordLabel);
		popup.add(password);
		popup.add(registerButton);
		popup.setVisible(true);
	}

	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			
			if (userName.getText().length() <=3){
				messageLabel.setText("Invalid username. Please select another");
			}
			
			else if (password.getText().length() <= 3){
				messageLabel.setText("Invalid password. Please select another");
			}
			
			else if (client.checkNameAvaliability(userName.getText())) {
				messageLabel.setText("Name is already used. Please select another");
			}
			
			else {
				client.registerUser(userName.getText(), password.getText());
				popup.dispose();
				JOptionPane.showMessageDialog(null, "You have registered successfully");
				view.setUpUserView();
			}
		}
	}
}
