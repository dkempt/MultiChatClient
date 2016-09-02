package setup;

import java.io.Serializable;


public class User implements Serializable{

	private static final long serialVersionUID = -6631177862059410824L;
	private static final int ADMIN_PASSWORD = 857904985;
	private String password;	

	public User(String playerName, String password) {
		super();		
		this.password = password;		
	}

	public String retrievePasscode(int adminCredentials) {
		if (adminCredentials == ADMIN_PASSWORD)
			return password;
		else return "";		
	}		
}