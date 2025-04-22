package ClientCommunication;

import ClientUserInterface.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
	        
	        

	        if (gameData.getStart()) 
	        {
	        	SwingUtilities.invokeLater(() -> {
	        	    gc.showGamePanel();
	        	    gc.initializePanel(gameData);
	        	});
	        }
	        else
	        {
	        	gc.updatePanel(gameData);
	        }
	        
	    }


	    
	    else if (arg0 instanceof LobbyData) {
	        LobbyData lobbyData = (LobbyData) arg0;
	        
	        wrc.showWaitingRoom();
	        wrc.updatePanel(lobbyData);
	        wrc.setPlayers(lobbyData);
	        
	        if(lobbyData.getStart()) 
	        {
	        	
	        }
	        

	        /*
	        System.out.println("Received LobbyData: " + lobbyData.getPlayers());
	        System.out.println("Host GameClient instance: " + this);

	        // ⛔ PROBLEM: wrc might still be null when this hits
	        if (wrc == null) {
	            System.out.println("wrc was null when LobbyData arrived. Delaying 100ms and retrying...");
	            try {
	                Thread.sleep(100);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }

	        if (wrc != null) {
	            wrc.showWaitingRoom();
	            wrc.updatePlayerList(lobbyData.getPlayers());
	        } else {
	            System.out.println("STILL NULL. wrc never initialized.");
	        }
	        */
	    }
	    
	}


	
	public void connectionException(Throwable exception)
	{
		System.out.println("[GameClient] 💥 Connection exception occurred:");
	    exception.printStackTrace();
	}
	
	public void connectionEstablished()
	{
		
	}
	
	public WaitingRoomControl getWaitingRoomControl() 
	{
	    return wrc;
	}
	
	@Override
    protected void connectionClosed() {
        System.out.println("[GameClient] Connection closed.");
    }


}
