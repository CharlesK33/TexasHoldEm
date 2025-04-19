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
            broadcastLobby();
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

            System.out.println("🧾 StartGameData received:");
            System.out.println("Username: " + username);
            System.out.println("Start flag: " + data.getStart());

            client.setInfo("username", username);
            gameServerControl.addPlayer(username);

            if (data.getStart()) {
                System.out.println("🎮 Host starting the full game...");
                gameServerControl.startFullGame();
                broadcastLobby();

                for (ConnectionToClient conn : getAllClients()) {
                    String user = (String) conn.getInfo("username");
                    if (user == null) continue;

                    GameData gameData = gameServerControl.getGameDataForPlayer(user);
                    gameData.setStart(true);
                    gameData.setInGame(true);

                    try {
                        conn.sendToClient(gameData);
                        System.out.println("📤 Sent GameData to: " + user);
                    } catch (IOException e) {
                        log.append("Failed to send GameData to " + user + "\n");
                    }
                }
            } else {
                System.out.println("👥 Player " + username + " joined the waiting room.");

                GameData gameData = gameServerControl.getGameDataForPlayer(username);
                gameData.setStart(false);
                gameData.setInGame(false);

                try {
                    client.sendToClient(gameData);
                    System.out.println("📤 Sent waiting room GameData to " + username);
                } catch (IOException e) {
                    log.append("Failed to send GameData to " + username + "\n");
                }

                LobbyData lobbyData = new LobbyData(gameServerControl.getPlayers());
                try {
                    client.sendToClient(lobbyData);
                    System.out.println("📤 Sent LobbyData to " + username);
                } catch (IOException e) {
                    log.append("Failed to send LobbyData to " + username + "\n");
                }

                broadcastLobby();
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
            try {
                conn.sendToClient(data);
                log.append("Sent GameData to " + username + "\n");
            } catch (IOException e) {
                log.append("Failed to send game state to " + username + ": " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        }
    }

    public void broadcastLobby() {
        LobbyData lobbyData = new LobbyData(gameServerControl.getPlayers());
        System.out.println("📡 Broadcasting to players:");

        for (ConnectionToClient conn : getAllClients()) {
            String username = getClientUsername(conn);
            System.out.println("   " + username + " → " + conn);

            if (username != null) {
                try {
                    conn.sendToClient(lobbyData);
                } catch (IOException e) {
                    log.append("Failed to send to " + username + "\n");
                }
            }
        }

        System.out.println("📢 Player list in lobby broadcast: " + lobbyData.getPlayers());
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
