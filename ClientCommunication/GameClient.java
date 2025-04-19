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
	private GameStartControl gsc;
	private GameControl gc;
	private WaitingRoomControl wrc;
	
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
	
	public void setGameStartControl(GameStartControl gsc)
	{
		this.gsc = gsc;
	}
	
	public void setGameControl(GameControl gc)
	{
		this.gc = gc;
	}
	
	public void setWaitingRoomControl(WaitingRoomControl wrc) 
	{
	    this.wrc = wrc;
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
	    
	    else if (arg0 instanceof GameData) {
	        GameData gameData = (GameData) arg0;
	        System.out.println("📬 GameData received from server: start=" + gameData.getStart() + ", inGame=" + gameData.isInGame() + ", user=" + gameData.getUsername());

	        if(gameData.getStart() && gameData.isInGame()) {
	            gsc.startGame();  // Show GamePanel
	        } else {
	            wrc.showWaitingRoom(); // Show waiting room
	            wrc.updatePlayerList(gameData.getPlayers());
	        }

	        gc.updatePanel(gameData); // or wrc.updatePanel(gameData) if needed
	    }


	    
	    else if (arg0 instanceof LobbyData) 
	    {
	    	LobbyData lobbyData = (LobbyData) arg0;
	    	System.out.println("Received LobbyData: " + lobbyData.getPlayers());
	        wrc.updatePlayerList(lobbyData.getPlayers());

	        // Show Start Game button if player is the host (first in the list)
	        String myUsername = wrc.getUsername();
	        if (!lobbyData.getPlayers().isEmpty() && lobbyData.getPlayers().get(0).equals(myUsername)) 
	        {
	            wrc.setAsHost(true);
	        }
	    }

	    
	}
	
	public void connectionException(Throwable exception)
	{
		
	}
	
	public void connectionEstablished()
	{
		
	}
	
	public WaitingRoomControl getWaitingRoomControl() 
	{
	    return wrc;
	}

}
