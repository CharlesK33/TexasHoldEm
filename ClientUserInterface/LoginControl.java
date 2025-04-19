package ClientUserInterface;

import java.awt.CardLayout;
import java.awt.event.*;
import java.io.IOException;
import ClientCommunication.*;
import javax.swing.*;
import CardGameData.*;


public class LoginControl implements ActionListener
{
	private JPanel container;
	private GameClient client;
	private LoginPanel loginPanel;
	
	public LoginControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}

	public void actionPerformed(ActionEvent ae) 
	{
		String command = ae.getActionCommand();
		
		if (command.equals("Submit"))
		{
			LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
			String username = loginPanel.getUsername();
			String password = loginPanel.getPassword();
			LoginData data = new LoginData(username, password);
			
			if (username.equals("") || password.equals(""))
			{
				displayError("You must enter a username and password");
			}
			
			try
		      {
		        client.sendToServer(data);
		      }
		      catch (IOException e)
		      {
		        displayError("Error connecting to the server.");
		      }
		}
		else if (command.equals("Cancel"))
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
	}
	
	public void displayError(String error)
	{
	  LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
	   
	  loginPanel.setError(error);
	}
	
	public void loginSuccess()
	{
	    LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
	    String username = loginPanel.getUsername();
	    client.getWaitingRoomControl().setUsername(username); // THIS MUST BE HERE 🔥

	    CardLayout cardLayout = (CardLayout)container.getLayout();
	    cardLayout.show(container, "4");
	}


	
	

	public void setLoginPanel(LoginPanel loginPanel) {
	    this.loginPanel = loginPanel;
	}

}
