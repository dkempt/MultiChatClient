package commands;

import server.ChatServer;
import setup.User;

public class WhoCommand extends Command <ChatServer> {

	

	private static final long serialVersionUID = -1721117397500977628L;
	
	public WhoCommand(String username) {
		super(username);
		
	}

	@Override
	public void execute(ChatServer executeOn) {
	executeOn.whoCommand(clientUserName);
		
	}

}
