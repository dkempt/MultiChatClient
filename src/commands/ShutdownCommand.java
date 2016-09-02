package commands;
import server.ChatServer;

public class ShutdownCommand extends Command<ChatServer> {

	
	private static final long serialVersionUID = 4888626927792332965L;

	private String password;
	
	public ShutdownCommand(String input) {
		super(null);
		this.password = input;
	}

	@Override
	public void execute(ChatServer executeOn) {
		executeOn.shutDown(password);
		
	}

}
