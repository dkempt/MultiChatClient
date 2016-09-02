package commands;


import client.ChatClient;

public class UpdateClientCommand extends Command<ChatClient> {

	private static final long serialVersionUID = 893308418146018094L;
	private String text; // the message log from the server

	public UpdateClientCommand(String messages) {
		super(null);
		this.text = messages;
	}
	
	
	@Override
	public void execute(ChatClient executeOn) {
		executeOn.updateCommand(text);
	}

}
