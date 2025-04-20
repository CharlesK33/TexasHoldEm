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
        System.out.println("🧠 CONNECTED: ID=" + client.getId() + ", raw=" + client);
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
            
            
            if (data.getStart()) {
                System.out.println("Host clicked Start Hand — dealing cards and sending GameData");
                
                gameServerControl.startFullGame(); // Deal hole cards + flop etc.

                for (ConnectionToClient conn : getAllClients()) {
                    String user = getClientUsername(conn);
                    if (user == null) continue;

                    GameData gameData = gameServerControl.getGameDataForPlayer(user);
                    gameData.setStart(true);
                    gameData.setInGame(true);

                    try {
                        conn.sendToClient(gameData);
                        System.out.println("Sent GameData to: " + user);
                    } catch (IOException e) {
                        log.append("Failed to send GameData to " + user + "\n");
                    }
                }
            }


            else if (!data.getStart()) 
            {
            	LobbyData lobbyData = new LobbyData(gameServerControl.getPlayers(), false);
            	
            	List<String> players = lobbyData.getPlayers();
            	
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

        else if (arg0 instanceof BetData data) {
            String username = getClientUsername(client);
            gameServerControl.handleBet(username, data.getBetAmount());
            broadcastGameState();
        }

        else if (arg0 instanceof CallData) {
            String username = getClientUsername(client);
            gameServerControl.handleCall(username);
            broadcastGameState();
        }

        else if (arg0 instanceof CheckData) {
            String username = getClientUsername(client);
            gameServerControl.handleCheck(username);
            broadcastGameState();
        }

        else if (arg0 instanceof FoldData) {
            String username = getClientUsername(client);
            gameServerControl.handleFold(username);
            broadcastGameState();
        }
    }

    public String getClientUsername(ConnectionToClient client) {
        return (String) client.getInfo("username");
    }

    public void broadcastGameState() {
        for (ConnectionToClient conn : getAllClients()) {
            String username = getClientUsername(conn);
            if (username == null) continue;

            GameData data = gameServerControl.getGameDataForPlayer(username);
            
            data.setStart(true); 
            data.setInGame(true);
            
            
            try {
                conn.sendToClient(data);
                log.append("Sent GameData to " + username + "\n");
            } catch (IOException e) {
                log.append("Failed to send game state to " + username + ": " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        }
    }

    private List<ConnectionToClient> getAllClients() {
        List<ConnectionToClient> clients = new ArrayList<>();
        Thread[] threads = getClientConnections();

        for (Thread t : threads) {
            if (t instanceof ConnectionToClient client) {
                clients.add(client);
            }
        }

        return clients;
    }



}
