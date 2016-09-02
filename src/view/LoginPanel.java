package view;

import javax.swing.*;
import listeners.LoginListener;
import listeners.RegisterListener;


public class LoginPanel extends JPanel {


	private static final long serialVersionUID = -1888517983726514560L;
	private JTextField userName;
	private JPasswordField password;
	private JButton loginButton;
	private JButton registerButton;
	private JLabel userNameLabel;
	private JLabel passwordLabel;

	
	private static final int labelXLocation = 250;
	private static final int labelYLocation = 210;
	private static final int labelXSize = 120;
	private static final int labelYSize = 20;
	private static final int textFieldXSize = 150;
	private static final int textFieldYSize = 20;
	private static final int xFieldOffset = 130;
	private static final int yFieldOffset = 30;
	private static final int buffer = 10;
	private static final int xButtonSize = 130;
	private static final int yButtonSize = 50;

	public LoginPanel(LoginListener loginButtonListener, RegisterListener registerListener) {
		setUpGUI();
		setUpListeners(loginButtonListener, registerListener);
	}

	private void setUpListeners(LoginListener loginButtonListener, RegisterListener registerListener) {
		//TODO: Create listener for registerButton
		loginButton.addActionListener(loginButtonListener);
		registerButton.addActionListener(registerListener);
	}

	private void setUpGUI() {

		// All the components to the panel
		userName = new JTextField();
		password = new JPasswordField();
		userNameLabel = new JLabel("UserName");
		passwordLabel = new JLabel("Password");
		loginButton = new JButton("Login");
		registerButton = new JButton("Register");

		// Null layout for custom layout
		this.setLayout(null);

		// Sets the size and location of all the labels, fields, and buttons
		userNameLabel.setLocation(labelXLocation, labelYLocation);
		userNameLabel.setSize(labelXSize, labelYSize);

		userName.setLocation(labelXLocation + xFieldOffset, labelYLocation);
		userName.setSize(textFieldXSize, textFieldYSize);

		passwordLabel.setLocation(labelXLocation, labelYLocation + labelYSize
				+ buffer);
		passwordLabel.setSize(labelXSize, labelYSize);

		password.setLocation(labelXLocation + xFieldOffset, labelYLocation
				+ yFieldOffset);
		password.setSize(textFieldXSize, textFieldYSize);

		loginButton.setLocation(labelXLocation, labelYLocation + labelYSize
				+ textFieldYSize + buffer * 3);
		loginButton.setSize(xButtonSize, yButtonSize);

		registerButton.setLocation(labelXLocation + xFieldOffset + buffer
				+ buffer, labelYLocation + labelYSize + textFieldYSize + buffer
				* 3);
		registerButton.setSize(xButtonSize, yButtonSize);

		
		// Adds everything to the panel
		this.add(userNameLabel);
		this.add(userName);
		this.add(passwordLabel);
		this.add(password);
		this.add(loginButton);
		this.add(registerButton);
		this.repaint();
	}

	public String getUserName() {
		return userName.getText();
		
	}

	public String getPassword() {
		// Get the password
		char []code = password.getPassword();
		
		// Make the password string
		String password = "";
		
		// convert the char[] to string
		for (char c: code){
			password += c;
		}
		
		// Return the password string
		return password;
	}

	public void clearNameAndPassword() {
		userName.setText("");
		password.setText(null);
		
	}
}