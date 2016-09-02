package listeners;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import view.ClientGUI;
import client.ChatClient;

public class ArrowKeyListener implements KeyListener {

	private ClientGUI view;
	private ChatClient client;
	private LinkedList<String> commandList;
	private ListIterator<String> iterator;

	public ArrowKeyListener(ClientGUI chatgui, ChatClient client) {
		this.view = chatgui;
		this.client = client;
		this.commandList = client.getCommandList();
		this.iterator = commandList.listIterator();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP && iterator.hasPrevious())
			view.setCommandFromMemory(iterator.previous());
	

		else if (e.getKeyCode() == KeyEvent.VK_DOWN && iterator.hasNext())
			view.setCommandFromMemory(iterator.next());

		else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			String command = view.getCommand();
			
			try {
				client.sendCommand(command);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			
			}
			
			iterator = commandList.listIterator(commandList.size()-1);
			
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
