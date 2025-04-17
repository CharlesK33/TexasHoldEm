package ClientUserInterface;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import ClientCommunication.*;
import CardGameData.*;

public class GameStartControl implements ActionListener
{
	private JPanel container;
	private GameClient client;
	private boolean start;
	
	public GameStartControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		
		if (command.equals("Start Game"))
		{
			LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
			String username = loginPanel.getUsername();
			start = true;
			StartGameData startGameData = new StartGameData(username, start);
			
			try {
				client.sendToServer(startGameData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (command.equals("Join Game"))
		{
			LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
			String username = loginPanel.getUsername();
			start = false;
			StartGameData startGameData = new StartGameData(username, start);
			
			try {
				client.sendToServer(startGameData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void startGame()
	{
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "5");
	}
	
	public void displayError(String error)
	{
		
	}

}
