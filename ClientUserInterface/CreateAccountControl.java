package ClientUserInterface;

import java.awt.CardLayout;
import ClientCommunication.*;
import CardGameData.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class CreateAccountControl implements ActionListener
{

	private JPanel container;
	private GameClient client;
	
	public CreateAccountControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		String command = ae.getActionCommand();
		
		if (command.equals("Submit"))
		{
			CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
			String username = createAccountPanel.getUsername();
			String password1 = createAccountPanel.getPassword1();
			String password2 = createAccountPanel.getPassword2();
			
			// Check the validity of the information locally first.
		    if (username.equals("") || password1.equals(""))
		    {
		      displayError("You must enter a username and password.");
		      return;
		    }
		    else if (!password1.equals(password2))
		    {
		      displayError("The two passwords did not match.");
		      return;
		    }
		    if (password1.length() < 6)
		    {
		      displayError("The password must be at least 6 characters.");
		      return;
		    }
		      
		    // Submit the new account information to the server.
		    CreateAccountData data = new CreateAccountData(username, password1);
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
	  CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
	  createAccountPanel.setError(error);
	}
	
	public void createAccountSuccess()
	{
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "2");
	}

}
