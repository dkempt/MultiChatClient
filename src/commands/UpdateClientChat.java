package commands;
import client.ChatClient;

public class UpdateClientChat extends Command<ChatClient>{

	
	private static final long serialVersionUID = 6490476329037924947L;
	private String message;
	public UpdateClientChat(String message){
		super(null);
		this.message = message;
	}
	@Override
	public void execute(ChatClient executeOn) {
		executeOn.updateChatMessage(message);
		
	}

}
