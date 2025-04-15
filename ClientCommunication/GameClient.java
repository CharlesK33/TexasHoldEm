package ClientCommunication;

import ClientUserInterface.*;
import CardGameData.*;
import CardGameData.Error;
import ocsf.client.AbstractClient;
import ocsf.server.ConnectionToClient;

public class GameClient extends AbstractClient
{
	private LoginControl lc;
	private CreateAccountControl cac;
	
	public GameClient()
	{
		super("localhost", 8300);
	}
	
	public void setLoginControl(LoginControl lc)
	{
		this.lc = lc;
	}
	
	public void setCreateAccountControl(CreateAccountControl cac)
	{
		this.cac = cac;
	}
	
	public void handleMessageFromServer(Object arg0)
	{
		// If we received a String, figure out what this event is.
	    if (arg0 instanceof String)
	    {
	      // Get the text of the message.
	      String message = (String)arg0;
	      
	      // If we successfully logged in, tell the login controller.
	      if (message.equals("LoginSuccessful"))
	      {
	        lc.loginSuccess();
	      }
	      
	      // If we successfully created an account, tell the create account controller.
	      else if (message.equals("CreateAccountSuccessful"))
	      {
	        cac.createAccountSuccess();
	      }
	    }
	    
	    // If we received an Error, figure out where to display it.
	    else if (arg0 instanceof Error)
	    {
	      // Get the Error object.
	      Error error = (Error)arg0;
	      
	      // Display login errors using the login controller.
	      if (error.getType().equals("Login"))
	      {
	        lc.displayError(error.getMessage());
	      }
	      
	      // Display account creation errors using the create account controller.
	      else if (error.getType().equals("CreateAccount"))
	      {
	        cac.displayError(error.getMessage());
	      }
	    }
	}
	
	public void connectionException(Throwable exception)
	{
		
	}
	
	public void connectionEstablished()
	{
		
	}
}
