package commands;

import client.ChatClient;

public class DisconnectClientCommand extends Command<ChatClient>{

	
	public DisconnectClientCommand(String username) {
		super(null);
		// TODO Auto-generated constructor stub
	}
	
	

	private static final long serialVersionUID = 7490925032506284790L;

	@Override
	public void execute(ChatClient executeOn) {
		executeOn.disconnect();
		
	}

}
