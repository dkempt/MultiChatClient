package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;

import commands.*;
import setup.*;
import view.ClientGUI;

public class ChatClient {

	private static final int REGISTERUSERCOMMAND = 2;
	private static final int VALIDATEUSERCOMMAND = 3;
	private static final int CHECKUSERNAMECOMMANDNUMBER = 1;
	private static final String host = "PHXPC05CPWW"; // "192.168.1.2";//
	private static final int port = 9001;
	private String username;
	private Socket server;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ClientGUI view;
	private LinkedList<String> enteredCommands;

	public static void main(String args[]) {
		new ChatClient();
	}

	public ChatClient() {
		
		enteredCommands = new LinkedList<String>();
		view = new ClientGUI(this);
		
		// Set up the JProgressBar
		JProgressBar pb = new JProgressBar();
		pb.setIndeterminate(true);
		JLabel label = new JLabel("Connecting to server. Please wait.");
		pb.setPreferredSize(new Dimension(175, 20));

		JPanel center_panel = new JPanel();
		center_panel.add(label);
		center_panel.add(pb);
		JDialog dialog = new JDialog();
		dialog.setLocation(250, 300);
		dialog.getContentPane().add(center_panel, BorderLayout.CENTER);
		dialog.pack();
		dialog.setVisible(true);

		setUpServerConnection();
		dialog.dispose();
	}

	private void setUpServerConnection() {
		try {
			server = new Socket(host, port);
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());

		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(null,
							"Cannot establish connection with the game server. Please try again later.");
			view.close();
		}

	}

	private class ServerHandler implements Runnable {

		public void run() {
			try {
				while (true) {
					// read a command from server and execute it

					@SuppressWarnings("unchecked")
					Command<ChatClient> c = (Command<ChatClient>) in.readObject();
					c.execute(ChatClient.this);
				}
			} catch (SocketException e) {
				return; // "gracefully" terminate after disconnect
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean validateUser(String userName, String password) {

		this.username = userName;
		try {

			out.writeObject(VALIDATEUSERCOMMAND);
			out.writeObject(userName);
			out.writeObject(password);
			if ((boolean) in.readObject()) {
				new Thread(new ServerHandler()).start();
				return true;
			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public void sendDisconnectCommand() {

		try {
			out.writeObject(new DisconnectServerCommand(username));
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendCommand(String command) throws IOException {

		
		enteredCommands.add(command);
		if (!command.contains(" ")) {
			command += " ";
		}

		
		String theCommand = command.substring(0, command.indexOf(" "));
		String theApendage = command.substring(command.indexOf(" ") + 1);

		
		switch (theCommand) {

		case "help":
			view.updateGameConsole(printOutCommands());
			break;
		case "chat":
			out.writeObject(new AddOOCMessageCommand("ooc--" + username + ": "
					+ theApendage));
			break;
		case "who":
			out.writeObject(new WhoCommand(username));
			break;
		case "private":

			if (!theApendage.contains(" "))
				view.updateGameConsole("This is an invalid tell command. The command is: tell <player> message");
			else {
				String targetPlayer = theApendage.substring(0,
						theApendage.indexOf(' '));
				String message = theApendage
						.substring(theApendage.indexOf(' ') + 1);

				out.writeObject(new AddTellMessageCommand(username,
						targetPlayer, username + " tells you:  " + message));
			}
			break;
		case "logout":
			view.close();
			this.sendDisconnectCommand();
			break;
		case "shutdown":
			String input = JOptionPane
					.showInputDialog("You have asked to shut down the Chat server. Please validate admin credentials");
			out.writeObject(new ShutdownCommand(input));
			break;
		default:
			view.updateGameConsole("Invalid command");
		}

	}

	private String printOutCommands() {
		String result = "help: lists all the commands useable"
				+ "\n\nwho: lists all users that are logged in"
				+ "\n\nchat+ <message>: Global message to everyone currently connected(ooc-out of character)"
				+ "\n\nprivate+ <username> + <message>: sends a message only to the target username"
				+ "\n\nlogout: allows you to exit the system while saving your information"
				+ "\n\nshutdown: shuts the server down (only able to be used by admins)";
		return result;
	}

	public boolean checkNameAvaliability(String userName) {

		try {
			out.writeObject(CHECKUSERNAMECOMMANDNUMBER);
			out.writeObject(userName);
			Object temp = in.readObject();
			return (Boolean) temp;
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: finish
		return false;

	}

	public void registerUser(String userName, String password) {
		this.username = userName;
		try {
			out.writeObject(REGISTERUSERCOMMAND);
			out.writeObject(userName);
			out.writeObject(password);
			if ((Boolean) in.readObject()) {
				new Thread(new ServerHandler()).start();
			} else {
				// TODO: cannot register
			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void startServerHandler() {

		new Thread(new ServerHandler()).start();
	}

	public void updateChatMessage(String message) {
		view.updateChatMessage(message);
	}

	public void updateCommand(String text) {
		view.updateGameConsole(text);

	}

	public void disconnect() {
		JOptionPane
				.showMessageDialog(
						null,
						"The chat server admin is shutting down the server. \nWe apologize for the inconviencence");

		view.close();

	}

	public LinkedList<String> getCommandList() {
		
		return enteredCommands;
	}
}
