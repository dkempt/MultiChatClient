package commands;
import java.io.Serializable;

public abstract class Command<T> implements Serializable {
	
	
	private static final long serialVersionUID = 55021346472981073L;
	protected String clientUserName;
	
	public Command(String clientUsername){
		this.clientUserName = clientUsername;
	}	
	
	public abstract void execute(T executeOn);
	
	
}
