package commands;

import server.ChatServer;

public class AddTellMessageCommand extends Command<ChatServer>  {
	
	private String message;
	private String targetName;
	
	public AddTellMessageCommand(String username, String targetName, String message) {
		super(username);
		this.targetName= targetName;
		this.message = message;		
	}

	@Override
	public void execute(ChatServer executeOn) {
		executeOn.addPersonalMessage(message, targetName, clientUserName);
		
	}

}
