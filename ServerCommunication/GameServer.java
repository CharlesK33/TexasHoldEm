package ServerCommunication;

import java.awt.Color;
import java.io.IOException;
import javax.swing.*;
import CardGameData.*;
import CardGameData.Error;
import Database.*;
import lab5out.BetData;
import ocsf.server.*;


public class GameServer extends AbstractServer
{
	private JTextArea log;
	private JLabel status;
	private Database database;
	private boolean running;
	
	public GameServer()
	{
		super(12345);
	    this.setTimeout(500);
	}
	
	public void setLog(JTextArea log)
	{
		this.log = log;
	}
	
	public JTextArea getLog()
	{
		return log;
	}
	
	public void setStatus(JLabel status)
	{
		this.status = status;
	}
	
	public JLabel getStatus()
	{
		return status;
	}
	
	public boolean isRunning()
	  {
	    return running;
	  }
	
	public void serverStarted()
	{
		running = true;
		status.setText("Listening");
	    status.setForeground(Color.GREEN);
	    log.append("Server started\n");
	}
	
	public void serverStopped()
	{
		 status.setText("Stopped");
	     status.setForeground(Color.RED);
	     log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
	}
	
	public void serverClosed()
	{
		running = false;
		status.setText("Close");
	    status.setForeground(Color.RED);
	    log.append("Server and all current clients are closed - press Listen to restart\n");
	}
	
	public void clientConnected(ConnectionToClient client)
	{
		log.append("Client " + client.getId() + " connected\n");
	}
	
	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		// If we received LoginData, verify the account information.
	    if (arg0 instanceof LoginData)
	    {
	      // Check the username and password with the database.
	      LoginData data = (LoginData)arg0;
	      Object result;
	      if (database.verifyAccount(data.getUsername(), data.getPassword()))
	      {
	        result = "LoginSuccessful";
	        log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
	      }
	      else
	      {
	        result = new Error("The username and password are incorrect.", "Login");
	        log.append("Client " + arg1.getId() + " failed to log in\n");
	      }
	      
	      // Send the result to the client.
	      try
	      {
	        arg1.sendToClient(result);
	      }
	      catch (IOException e)
	      {
	        return;
	      }
	    }
	    
	 // If we received CreateAccountData, create a new account.
	    else if (arg0 instanceof CreateAccountData)
	    {
	      // Try to create the account.
	      CreateAccountData data = (CreateAccountData)arg0;
	      Object result;
	      if (database.createNewAccount(data.getUsername(), data.getPassword()))
	      {
	        result = "CreateAccountSuccessful";
	        log.append("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
	      }
	      else
	      {
	        result = new Error("The username is already in use.", "CreateAccount");
	        log.append("Client " + arg1.getId() + " failed to create a new account\n");
	      }
	      
	      // Send the result to the client.
	      try
	      {
	        arg1.sendToClient(result);
	      }
	      catch (IOException e)
	      {
	        return;
	      }
	    }
	    else if (arg0 instanceof BetData)
	    {
	    	BetData data = (BetData)arg0;
	    }
	    else if (arg0 instanceof CallData)
	    {
	    	CallData data = (CallData)arg0;
	    }
	    else if (arg0 instanceof Card)
	    {
	    	Card card = (Card)arg0;
	    }
	    else if (arg0 instanceof CheckData)
	    {
	    	CheckData data = (CheckData)arg0;
	    }
	    else if (arg0 instanceof Deck)
	    {
	    	Deck data = (Deck)arg0;
	    }
	    else if (arg0 instanceof RecordScoreData)
	    {
	    	RecordScoreData data = (RecordScoreData)arg0;
	    }
	    else if (arg0 instanceof Error)
	    {
	    	Error data = (Error)arg0;
	    }
	    else if (arg0 instanceof FoldData)
	    {
	    	FoldData data = (FoldData)arg0;
	    }
	    else if (arg0 instanceof GameData)
	    {
	    	GameData data = (GameData)arg0;
	    }
	    else if (arg0 instanceof Hand)
	    {
	    	Hand data = (Hand)arg0;
	    }
	    else if (arg0 instanceof RaiseData)
	    {
	    	RaiseData data = (RaiseData)arg0;
	    }
	}
	
	public void listeningException(Throwable exception)
	{
		running = false;
		status.setText("Exception occurred while listening");
	    status.setForeground(Color.RED);
	    log.append("Listening exception: " + exception.getMessage() + "\n");
	    log.append("Press Listen to restart server\n");
	}
}
