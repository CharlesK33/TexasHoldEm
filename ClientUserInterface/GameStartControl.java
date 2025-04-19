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
	private WaitingRoomControl wrc;

	
	public GameStartControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
	    String command = ae.getActionCommand();
	    LoginPanel loginPanel = (LoginPanel) container.getComponent(1);
	    String username = loginPanel.getUsername();

	    StartGameData startGameData = new StartGameData(username, false); // false = just joining

	    try {
	        if (command.equals("Start Game")) {
	            client.openConnection(); // Only host opens connection, fine
	        }

	        client.sendToServer(startGameData);

	        //Just update username on the already existing WaitingRoomControl
	        client.getWaitingRoomControl().setUsername(username);

	        //Show waiting room panel (already added in ClientGUI)
	        CardLayout cardLayout = (CardLayout) container.getLayout();
	        cardLayout.show(container, "6");

	    } catch (IOException e) {
	        e.printStackTrace();
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
