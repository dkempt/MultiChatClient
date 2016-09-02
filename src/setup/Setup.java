package setup;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import server.ChatServer;

import java.util.Timer;
import java.util.TimerTask;

public class Setup implements Serializable {
	private static final int ADMIN_PASSWORD = 857904985;
	private static final String SHUTDOWN_PASSWORD = "please";
	private static final String saveFile = "ChatServer.sav";
	public static final int DELAY = 30;
	private Timer timer;
	private ChatServer server;
	private HashMap<String, User> registeredUser;
	private HashMap<String, User> usersLoggedOn;

	public static void main(String args[]) {
		Setup theClient = new Setup();
	}

	public Setup() {

		server = new ChatServer(this);
		usersLoggedOn = new HashMap<String, User>();

		try {
			loadData();
		} catch (ClassNotFoundException | IOException e) {
			registeredUser = new HashMap<String, User>();
		}

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
			}
		}, 1000, 60000);

	}

	public void loadData() throws IOException, ClassNotFoundException {
		FileInputStream filein = new FileInputStream(saveFile);
		ObjectInputStream objectin = new ObjectInputStream(filein);
		registeredUser = (HashMap<String, User>) objectin.readObject();
		objectin.close();

	}

	public boolean checkUserAvailability(String userName) {

		if (registeredUser.containsKey(userName))
			return true;

		return false;
	}

	public void registerUser(String userName, String password) {
		User aPlayer = new User(userName, password);
		registeredUser.put(userName, aPlayer);
		usersLoggedOn.put(userName, aPlayer);
	}

	public boolean validateUser(String userName, String password) {

		// then check to see if username/passcode combo exists

		if (registeredUser.containsKey(userName)
				&& !usersLoggedOn.containsKey(userName)) {
			User playerAccount = registeredUser.get(userName);

			if (playerAccount.retrievePasscode(ADMIN_PASSWORD).equals(password)) {
				usersLoggedOn.put(userName, playerAccount);
				return true;
			}

			return false;
		}

		return false;
	}

	public void disconnectUser(String clientName) {

		User player = usersLoggedOn.get(clientName);
		usersLoggedOn.remove(clientName);

	}

	public User getPlayer(String userName) {

		return usersLoggedOn.get(userName);
	}

	public boolean shutDownServer(int adminPassword, String userpassword) {

		if (adminPassword == ADMIN_PASSWORD) {
			if (userpassword.equals(SHUTDOWN_PASSWORD)) {

				try {
					ObjectOutputStream fileSave = new ObjectOutputStream(
							new FileOutputStream(new File(saveFile)));
					fileSave.writeObject(registeredUser);
					fileSave.close();

					return true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public String getLoggedInUsers(String username) {
		String temp = "";
		if (usersLoggedOn.size() == 1) {
			temp += "You are the only user logged on";
		} else {
			temp += "Users currently logged on:";

			Iterator<Entry<String, User>> iterator = usersLoggedOn
					.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<String, User> playerEntry = iterator.next();
				String name = playerEntry.getKey();
				if (!name.equals(username))
					temp += " " + name;
			}

		}
		return temp;
	}

}