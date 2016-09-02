package commands;

import server.ChatServer;

public class DisconnectServerCommand extends Command<ChatServer>{


	private static final long serialVersionUID = 3960381176210782250L;

	
	public DisconnectServerCommand(String name){
		super(name);
	}
	
	@Override
	public void execute(ChatServer executeOn) {
		// disconnect client
		executeOn.disconnect(clientUserName);
	}
	
}
