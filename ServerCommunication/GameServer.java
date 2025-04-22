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
    private GameServerControl gameServerControl = new GameServerControl();

    public GameServer() {
        super(8300);
        this.setTimeout(500);
    }

    public void setLog(JTextArea log) { this.log = log; }
    public JTextArea getLog() { return log; }
    public void setStatus(JLabel status) { this.status = status; }
    public JLabel getStatus() { return status; }
    public boolean isRunning() { return running; }
    

    @Override
    public void serverStarted() {
        running = true;
        status.setText("Listening");
        status.setForeground(Color.GREEN);
        log.append("Server started\n");
    }

    @Override
    public void serverStopped() {
        status.setText("Stopped");
        status.setForeground(Color.RED);
        log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
    }

    @Override
    public void serverClosed() {
        running = false;
        status.setText("Close");
        status.setForeground(Color.RED);
        log.append("Server and all current clients are closed - press Listen to restart\n");
    }

    @Override
    public void clientConnected(ConnectionToClient client) {
    	log.append("Client " + client.getId() + " connected\n");
    }

    @Override
    protected void clientDisconnected(ConnectionToClient client) {
        String username = (String) client.getInfo("username");
        System.out.println("💀 DISCONNECTED: " + username);

        if (username != null) {
            gameServerControl.removePlayer(username);
            //broadcastLobby();
        }
    }

    public void handleMessageFromClient(Object arg0, ConnectionToClient client) {
    	
    	
        if (arg0 instanceof LoginData data) {
            Object result;
            if (database.verifyAccount(data.getUsername(), data.getPassword())) {
                result = "LoginSuccessful";
                log.append("Client " + client.getId() + " successfully logged in as " + data.getUsername() + "\n");
            } else {
                result = new Error("The username and password are incorrect.", "Login");
                log.append("Client " + client.getId() + " failed to log in\n");
            }
            try {
                client.sendToClient(result);
            } catch (IOException e) {
                System.out.println("Failed");
            }
        }

        else if (arg0 instanceof CreateAccountData data) {
            Object result;
            if (database.createNewAccount(data.getUsername(), data.getPassword())) {
                result = "CreateAccountSuccessful";
                log.append("Client " + client.getId() + " created a new account called " + data.getUsername() + "\n");
            } else {
                result = new Error("The username is already in use.", "CreateAccount");
                log.append("Client " + client.getId() + " failed to create a new account\n");
            }
            try {
                client.sendToClient(result);
            } catch (IOException e) {
                return;
            }
        }

        else if (arg0 instanceof StartGameData data) {
            String username = data.getUsername();
            
            client.setInfo("username", username);
            gameServerControl.addPlayer(username);
            
            if (data.getStart()) 
            {
            	LobbyData lobbyData = new LobbyData(gameServerControl.getPlayers(), true);
            	
            	ArrayList<String> players = lobbyData.getPlayers();
            	
            	lobbyData.setPlayer1(players.get(0));
            	
            	try {
					client.sendToClient(lobbyData);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else if (!data.getStart()) 
            {
            	LobbyData lobbyData = new LobbyData(gameServerControl.getPlayers(), false);
            	
            	ArrayList<String> players = lobbyData.getPlayers();
            	
            	lobbyData.setPlayer1(players.get(0));
            	
            	if (players.size() > 1)
            	{
            		lobbyData.setPlayer2(players.get(1));
            	}
            	
            	if (players.size() > 2)
            	{
            		lobbyData.setPlayer3(players.get(2));
            	}
            	
            	if (players.size() > 3)
            	{
            		lobbyData.setPlayer4(players.get(3));
            	}
            	
            	if (players.size() > 4)
            	{
            		lobbyData.setPlayer5(players.get(4));
            	}
            	
            	sendToAllClients(lobbyData);
            }
        }
        
        else if(arg0 instanceof GameData data)
        {
        	GameData updatedData = new GameData();
        	if (data.getStart())
        	{
        		// Initialize game panel
        		updatedData.setPot(data.getPot());
        		updatedData.setCurrentBet(data.getCurrentBet());
        		updatedData.setStart(true);
        		updatedData.setPlayers(data.getPlayers());
        		updatedData.setDealer(0);
        		gameServerControl.setCurrentPlayerIndex(0);
        		gameServerControl.setRoundCount(0);
        		
        		sendToAllClients(updatedData);
        		
        		ArrayList<ConnectionToClient> clients = getAllClients();
        		
        		int i = 0;
        		for (ConnectionToClient conn : clients)
        		{
        			Hand hand = gameServerControl.dealHoleCards();
            		GameData handData = new GameData();
            		handData.setHand(hand);
            		handData.setUsername(data.getPlayers().get(i));
            		
            		if (updatedData.getDealer() + 1 == i)
            		{
            			handData.setScore(95);
            		}
            		else if (updatedData.getDealer() + 2 == i)
            		{
            			handData.setScore(90);
            		}
            		else
            		{
            			handData.setScore(100);
            		}
            		
            		
            		try {
    					conn.sendToClient(handData);
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
            		
            		i++;
        		}
        		
        	}
        }

        else if (arg0 instanceof BetData data) 
        {
			GameData gameData;
			
			int currentBet = data.getBetAmount();
			gameData = gameServerControl.updateCurrentBet(currentBet);
			gameData.setBetAmount(data.getBetAmount());
			gameData.setUsername(data.getUsername());
			
			int currentPlayerIndex = gameServerControl.getCurrentPlayerIndex();
			
            int roundCount = gameServerControl.getRoundCount();
            int numPlayers = gameServerControl.getPlayers().size();
            
            if (currentPlayerIndex + 1 < numPlayers)
            {
            	gameServerControl.setCurrentPlayerIndex(currentPlayerIndex + 1);
            }
            else
            {
            	gameServerControl.setCurrentPlayerIndex(0);
            	gameServerControl.setRoundCount(roundCount + 1);
            	roundCount = gameServerControl.getRoundCount();
            	if (roundCount == 1)
            	{
            		gameData.setFlop(gameServerControl.dealFlop());
            	}
            	else if (roundCount == 2)
            	{
            		gameData.setTurn(gameServerControl.dealTurn());
            	}
            	else if (roundCount == 3)
            	{
            		gameData.setRiver(gameServerControl.dealRiver());
            	}
            	else if (roundCount > 3)
            	{
            		gameData.setEndOfHand(true);
            	}
            	
            }
            
            gameData.setPlayerTurn(gameServerControl.getCurrentPlayerIndex());
			
			sendToAllClients(gameData);
        }

        
        else if (arg0 instanceof CallData) 
        {
        	CallData callData = (CallData)arg0;
            GameData gameData = new GameData();
            
            gameData.setBetAmount(callData.getCurrentBet());
            gameData.setUsername(callData.getUsername());
            
            int currentPlayerIndex = gameServerControl.getCurrentPlayerIndex();
            int roundCount = gameServerControl.getRoundCount();
            int numPlayers = gameServerControl.getPlayers().size();
            
            if (currentPlayerIndex + 1 < numPlayers)
            {
            	gameServerControl.setCurrentPlayerIndex(currentPlayerIndex + 1);
            }
            else
            {
            	gameServerControl.setCurrentPlayerIndex(0);
            	gameServerControl.setRoundCount(roundCount + 1);
            	roundCount = gameServerControl.getRoundCount();
            	if (roundCount == 1)
            	{
            		gameData.setFlop(gameServerControl.dealFlop());
            	}
            	else if (roundCount == 2)
            	{
            		gameData.setTurn(gameServerControl.dealTurn());
            	}
            	else if (roundCount == 3)
            	{
            		gameData.setRiver(gameServerControl.dealRiver());
            	}
            	else if (roundCount > 3)
            	{
            		gameData.setEndOfHand(true);
            	}
            	
            }
            
            gameData.setPlayerTurn(gameServerControl.getCurrentPlayerIndex());
            
            sendToAllClients(gameData);
        }

        else if (arg0 instanceof CheckData) 
        {
        	CheckData checkData = (CheckData)arg0;
            GameData gameData = new GameData();
            
            int currentPlayerIndex = gameServerControl.getCurrentPlayerIndex();
            int roundCount = gameServerControl.getRoundCount();
            int numPlayers = gameServerControl.getPlayers().size();
            
            if (currentPlayerIndex + 1 < numPlayers)
            {
            	gameServerControl.setCurrentPlayerIndex(currentPlayerIndex + 1);
            }
            else
            {
            	gameServerControl.setCurrentPlayerIndex(0);
            	gameServerControl.setRoundCount(roundCount + 1);
            	roundCount = gameServerControl.getRoundCount();
            	if (roundCount == 1)
            	{
            		gameData.setFlop(gameServerControl.dealFlop());
            	}
            	else if (roundCount == 2)
            	{
            		gameData.setTurn(gameServerControl.dealTurn());
            	}
            	else if (roundCount == 3)
            	{
            		gameData.setRiver(gameServerControl.dealRiver());
            	}
            	else if (roundCount > 3)
            	{
            		gameData.setEndOfHand(true);
            	}
            	
            }
            
            gameData.setPlayerTurn(gameServerControl.getCurrentPlayerIndex());
            
            sendToAllClients(gameData);
        }
        
        

        else if (arg0 instanceof FoldData) 
        {
            FoldData foldData = (FoldData)arg0;
            GameData gameData = new GameData();
            gameData.setUsername(foldData.getUsername());
            gameData.setFolding(true);
            
            int currentPlayerIndex = gameServerControl.getCurrentPlayerIndex();
            int roundCount = gameServerControl.getRoundCount();
            int numPlayers = gameServerControl.getPlayers().size();
            
            if (currentPlayerIndex + 1 < numPlayers)
            {
            	gameServerControl.setCurrentPlayerIndex(currentPlayerIndex + 1);
            }
            else
            {
            	gameServerControl.setCurrentPlayerIndex(0);
            	gameServerControl.setRoundCount(roundCount + 1);
            	roundCount = gameServerControl.getRoundCount();
            	if (roundCount == 1)
            	{
            		gameData.setFlop(gameServerControl.dealFlop());
            	}
            	else if (roundCount == 2)
            	{
            		gameData.setTurn(gameServerControl.dealTurn());
            	}
            	else if (roundCount == 3)
            	{
            		gameData.setRiver(gameServerControl.dealRiver());
            	}
            	else if (roundCount > 3)
            	{
            		gameData.setEndOfHand(true);
            	}
            	
            }
            
            gameData.setPlayerTurn(gameServerControl.getCurrentPlayerIndex());
            
            
            sendToAllClients(gameData);
            
            
        }
    }

    public String getClientUsername(ConnectionToClient client) {
        return (String) client.getInfo("username");
    }

    /*
    public void broadcastGameState() {
        for (ConnectionToClient conn : getAllClients()) {
            String username = getClientUsername(conn);
            if (username == null) continue;

            GameData data = gameServerControl.getGameDataForPlayer(username);
            try {
                conn.sendToClient(data);
                log.append("Sent GameData to " + username + "\n");
            } catch (IOException e) {
                log.append("Failed to send game state to " + username + ": " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        }
    }
    */

    private ArrayList<ConnectionToClient> getAllClients() {
        ArrayList<ConnectionToClient> clients = new ArrayList<>();
        Thread[] threads = getClientConnections();

        for (Thread t : threads) {
            if (t instanceof ConnectionToClient client) {
                clients.add(client);
            }
        }

        return clients;
    }



}
