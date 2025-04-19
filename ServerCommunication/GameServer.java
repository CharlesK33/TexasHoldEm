package ServerCommunication;

import java.awt.Color;
import java.io.IOException;
import javax.swing.*;
import CardGameData.*;
import CardGameData.Error;
import Database.*;
import ocsf.server.*;
import java.util.*;

public class GameServer extends AbstractServer {
	private JTextArea log;
	private JLabel status;
	private Database database = new Database();
	private boolean running;
	private ArrayList<String> players = new ArrayList<String>();
	private GameServerControl gameServerControl = new GameServerControl();
	
	//Track client to username mappings
	private Map<ConnectionToClient, String> clientToUsername = new HashMap<>();




	public GameServer() {
		super(12345);
		this.setTimeout(500);
	}

	public void setLog(JTextArea log) {
		this.log = log;
	}

	public JTextArea getLog() {
		return log;
	}

	public void setStatus(JLabel status) {
		this.status = status;
	}

	public JLabel getStatus() {
		return status;
	}

	public boolean isRunning() {
		return running;
	}
	
	

	public void serverStarted() {
		running = true;
		status.setText("Listening");
		status.setForeground(Color.GREEN);
		log.append("Server started\n");
	}

	public void serverStopped() {
		status.setText("Stopped");
		status.setForeground(Color.RED);
		log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
	}

	public void serverClosed() {
		running = false;
		status.setText("Close");
		status.setForeground(Color.RED);
		log.append("Server and all current clients are closed - press Listen to restart\n");
	}

	public void clientConnected(ConnectionToClient client) {
		log.append("Client " + client.getId() + " connected\n");
	}
	
	

	
	

	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// If we received LoginData, verify the account information.
		if (arg0 instanceof LoginData) {
			
			// Check the username and password with the database.
			LoginData data = (LoginData) arg0;
			Object result;
			if (database.verifyAccount(data.getUsername(), data.getPassword())) {
				result = "LoginSuccessful";
				log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
			} else {
				result = new Error("The username and password are incorrect.", "Login");
				log.append("Client " + arg1.getId() + " failed to log in\n");
			}

			// Send the result to the client.
			try {
				arg1.sendToClient(result);
			} catch (IOException e) {
				System.out.println("Failed");
				return;
			}
		}

		// If we received CreateAccountData, create a new account.
		else if (arg0 instanceof CreateAccountData) {
			// Try to create the account.
			CreateAccountData data = (CreateAccountData) arg0;
			Object result;
			if (database.createNewAccount(data.getUsername(), data.getPassword())) {
				result = "CreateAccountSuccessful";
				log.append("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
			} else {
				result = new Error("The username is already in use.", "CreateAccount");
				log.append("Client " + arg1.getId() + " failed to create a new account\n");
			}

			// Send the result to the client.
			try {
				arg1.sendToClient(result);
			} catch (IOException e) {
				return;
			}
			
			
			
			
		} 
		
		
		else if (arg0 instanceof StartGameData) {
		    StartGameData data = (StartGameData) arg0;
		    
		    // 🔥 DEBUG PRINT
		    System.out.println("📨 StartGameData received:");
		    System.out.println("   → Username: " + data.getUsername());
		    System.out.println("   → Start flag: " + data.getStart());

		    clientToUsername.put(arg1, data.getUsername());
		    gameServerControl.addPlayer(data.getUsername());

		    if (data.getStart()) {
		        // ✅ Host clicked Start Game
		        System.out.println("🚀 Starting the full game...");

		        gameServerControl.startFullGame();

		        for (ConnectionToClient client : clientToUsername.keySet()) {
		            String username = clientToUsername.get(client);
		            GameData gameData = gameServerControl.getGameDataForPlayer(username);
		            gameData.setStart(true);    // show GamePanel
		            gameData.setInGame(true);   // real game now

		            try {
		                client.sendToClient(gameData);
		                System.out.println("✅ Sent GameData to: " + username);
		            } catch (IOException e) {
		                log.append("Failed to send GameData to " + username + "\n");
		            }
		        }
		    } else {
		        // Player is just joining the lobby
		        System.out.println("👥 Player " + data.getUsername() + " joined the waiting room.");

		        GameData gameData = gameServerControl.getGameDataForPlayer(data.getUsername());
		        gameData.setStart(false);      // show waiting room panel
		        gameData.setInGame(false);     // not actually playing yet

		        try {
		            arg1.sendToClient(gameData);
		            System.out.println("📤 Sent waiting room GameData to " + data.getUsername());
		        } catch (IOException e) {
		            log.append("Failed to send GameData to " + data.getUsername() + "\n");
		        }

		        broadcastLobby();
		    }
		}




		else if (arg0 instanceof BetData) {
		    BetData data = (BetData) arg0;
		    String username = getClientUsername(arg1);
		    gameServerControl.handleBet(username, data.getBetAmount());
		    broadcastGameState();
		}
		else if (arg0 instanceof CallData) {
		    String username = getClientUsername(arg1);
		    gameServerControl.handleCall(username);
		    broadcastGameState();
		}
		else if (arg0 instanceof CheckData) {
		    String username = getClientUsername(arg1);
		    gameServerControl.handleCheck(username);
		    broadcastGameState();
		}
		else if (arg0 instanceof FoldData) {
		    String username = getClientUsername(arg1);
		    gameServerControl.handleFold(username);
		    broadcastGameState();
		}
		else if (arg0 instanceof Card) 
		{
			Card card = (Card) arg0;
		} 
		else if (arg0 instanceof Deck) 
		{
			Deck data = (Deck) arg0;
		} 
		else if (arg0 instanceof RecordScoreData) 
		{
			RecordScoreData data = (RecordScoreData) arg0;
		} 
		else if (arg0 instanceof Error) 
		{
			Error data = (Error) arg0;
		} 
		else if (arg0 instanceof GameData) 
		{
			GameData data = (GameData) arg0;
		} 
		else if (arg0 instanceof Hand) 
		{
			Hand data = (Hand) arg0;
		} 
		else if (arg0 instanceof RaiseData) 
		{
			RaiseData data = (RaiseData) arg0;
			GameData gameData;

		
			gameData = gameServerControl.updatePot();

			/*
			 * for (Map.Entry<Integer, GameData> entry :
			 * gameServerControl.getMap().entrySet()) { try {
			 * entry.getKey().sendToClient(entry.getValue()); } catch (IOException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); } }
			 */
			sendToAllClients(gameData);

		}
	}

	public void listeningException(Throwable exception) {
		running = false;
		status.setText("Exception occurred while listening");
		status.setForeground(Color.RED);
		log.append("Listening exception: " + exception.getMessage() + "\n");
		log.append("Press Listen to restart server\n");
	}
	
	
	
	//Sends the current game state to all clients
	//Updated to send player-specific data
	public void broadcastGameState() {
	    if (gameServerControl == null) return;

	    for (ConnectionToClient client : clientToUsername.keySet()) {
	        String username = clientToUsername.get(client);
	        GameData data = gameServerControl.getGameDataForPlayer(username);
	        try {
	            client.sendToClient(data);
	            log.append("Sent GameData to " + username + "\n");
	        } catch (IOException e) {
	            log.append("Failed to send game state to " + username + ": " + e.getMessage() + "\n");
	            e.printStackTrace(); // show detailed error
	        
	        }
	    }

	    
	}
	
	
	
	//Get username of client's player
	public String getClientUsername(ConnectionToClient client) {
	    return clientToUsername.getOrDefault(client, "Unknown");
	}
	
	
	public void broadcastLobby() {
	    LobbyData lobbyData = new LobbyData(gameServerControl.getPlayers());
	    for (ConnectionToClient client : clientToUsername.keySet()) {
	        try {
	            client.sendToClient(lobbyData);
	        } catch (IOException e) {
	            log.append("Failed to send lobby data\n");
	        }
	    }
	}
	
	


}




