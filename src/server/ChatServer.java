package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import setup.Setup;
import commands.*;

public class ChatServer {

	private static final int PORT = 9001;
	private static final int ADMIN_PASSWORD = 857904985;
	private ServerSocket socket; // the server socket
	private HashMap<String, ObjectOutputStream> outputs;
	private Setup theClient;
	
	/**
	 * The constructor for the server
	 */
	public ChatServer(Setup game) {

		this.outputs = new HashMap<String, ObjectOutputStream>();
		this.theClient = game;

		try {
			// start a new server on port 9001
			socket = new ServerSocket(PORT);
			System.out.println("Chat Server started on port " + PORT
					+ " and address " + socket.getInetAddress());

			new Thread(new ClientAccepter()).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ClientAccepter
	 * 
	 * This thread listens for and sets up connections to new clients
	 */
	private class ClientAccepter implements Runnable {

		public void run() {
			try {

				while (true) {

					// accept a new client, get output & input streams
					Socket s = socket.accept();
					ObjectOutputStream output = new ObjectOutputStream(
							s.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(
							s.getInputStream());

					new Thread(new ClientLoginandRegister(s, input, output))
							.start();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private class ClientLoginandRegister implements Runnable {

		private Socket s;
		private ObjectInputStream input;
		private ObjectOutputStream output;

		public ClientLoginandRegister(Socket s, ObjectInputStream input,
				ObjectOutputStream output) {
			this.s = s;
			this.input = input;
			this.output = output;
		}

		@Override
		public void run() {

			try {

				boolean notloggedOn = true;
				do {

					Object command = input.readObject();
					
					while (command instanceof Command){
					
						return;
					}
					
					int commandNum = (int) command;
					
					switch (commandNum) {
					case 1:

						boolean avalibility = theClient
								.checkUserAvailability((String) input
										.readObject());
						output.writeObject(avalibility);
						break;

					case 2:

						String clientName = (String) input.readObject();
						String password = (String) input.readObject();
						theClient.registerUser(clientName, password);
						outputs.put(clientName, output);
						notloggedOn = false;
						output.writeObject(true);

						// spawn a thread to handle chat communication with this
						// client
						new Thread(new ClientHandler(input)).start();

						// Prints a message about a user connecting
						System.out.println(clientName + " has connected");

						break;

					case 3:

						clientName = (String) input.readObject();
						password = (String) input.readObject();

						if (theClient.validateUser(clientName, password)) {

							outputs.put(clientName, output);
							notloggedOn = false;
							output.writeObject(true);

							// spawn a thread to handle chat communication with
							// this
							// client
							new Thread(new ClientHandler(input)).start();

							// Prints a message about a user connecting
							System.out.println(clientName + " has connected");

						} else {
							output.writeObject(false);
						}
					default:
					}

				} while (notloggedOn);

			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * ClientHandler
	 * 
	 * This thread reads and executes commands sent by a client
	 */
	private class ClientHandler implements Runnable {

		private ObjectInputStream input; // the input stream from the client

		public ClientHandler(ObjectInputStream input) {
			this.input = input;
		}

		public void run() {
			try {
				while (true) {
					// read a command from the client, execute on the server
					Command<ChatServer> command = (Command<ChatServer>) input.readObject();
					command.execute(ChatServer.this);

					// terminate if client is disconnecting
					if (command instanceof DisconnectServerCommand) {
						input.close();
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addGlobalMessage(String message) {

		// make an UpdateClientCommmand, write to all connected users
		UpdateClientChat update = new UpdateClientChat(message);
		try {
			for (ObjectOutputStream out : outputs.values())
				out.writeObject(update);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void addPersonalMessage(String message, String targetUser,
			String username) {
		try {
			if (!theClient.checkUserAvailability(targetUser)) {

				outputs.get(username).writeObject(
						new UpdateClientCommand(
								"There is no user by the name of '"
										+ targetUser + "'"));
			}

			else if (!outputs.containsKey(targetUser)) {
				outputs.get(username).writeObject(
						new UpdateClientCommand((targetUser)
								+ "is not currently logged on."));
			}

			else {
				outputs.get(targetUser).writeObject(
						new UpdateClientChat(message));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void disconnect(String clientName) {
		try {
			theClient.disconnectUser(clientName);
			outputs.get(clientName).close(); // close output stream
			outputs.remove(clientName); // remove from map

			// add notification message
			System.out.println(clientName + " disconnected");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Shuts down the server
	 * 
	 * @param password
	 */
	public void shutDown(String Userpassword) {

		if (theClient.shutDownServer(ADMIN_PASSWORD, Userpassword)) {

			for (ObjectOutputStream out : outputs.values())
				try {
					out.writeObject(new DisconnectClientCommand(null));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.exit(0);
	}

		
	/**
	 * @param theUser
	 */
	
	public void whoCommand(String username) {
		String loggedinUsers = theClient.getLoggedInUsers(username);
		try {
			outputs.get(username).writeObject(
					new UpdateClientCommand(loggedinUsers));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
	}
}