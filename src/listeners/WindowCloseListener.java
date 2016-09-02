package listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import view.ClientGUI;
import client.ChatClient;

public class WindowCloseListener implements WindowListener {

	// References to the client
	private ChatClient client;

	public WindowCloseListener(ChatClient client) {
		
		this.client = client;
	}

	@Override
		public void windowClosing(WindowEvent arg0) {
			client.sendDisconnectCommand();
			JOptionPane.showMessageDialog(null,"You have disconnected from the Chat Server.");
		}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
