package ClientUserInterface;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import CardGameData.*;

import javax.swing.JPanel;

import ClientCommunication.GameClient;
import CardGameData.StartGameData;

public class WaitingRoomControl implements ActionListener 
{
    private JPanel container;
    private GameClient client;
    private String username;
    private JPanel waitingRoom;

    public WaitingRoomControl(JPanel container, GameClient client, String username) 
    {
        this.container = container;
        this.client = client;
        this.username = username;
    }
    
    private WaitingRoomPanel findWaitingRoomPanel() {
        for (Component comp : container.getComponents()) {
            if (comp instanceof WaitingRoomPanel) {
                return (WaitingRoomPanel) comp;
            }
        }
        System.out.println("Couldn't find WaitingRoomPanel in container");
        return null;
    }
    
    public void updatePanel(LobbyData lobbyData)
    {
    	WaitingRoomPanel waitingRoom = (WaitingRoomPanel)container.getComponent(5);
    	
    	if (lobbyData.getPlayer1() != null)
    	{
    		waitingRoom.setPlayer1Label(lobbyData.getPlayer1());
    	}
    	
    	if (lobbyData.getPlayer2() != null)
    	{
    		waitingRoom.setPlayer2Label(lobbyData.getPlayer2());
    	}
    	
    	if (lobbyData.getPlayer3() != null)
    	{
    		waitingRoom.setPlayer3Label(lobbyData.getPlayer3());
    	}
    	
    	
    	if (lobbyData.getPlayer4() != null)
    	{
    		waitingRoom.setPlayer4Label(lobbyData.getPlayer4());
    	}
    	
    	if (lobbyData.getPlayer5() != null)
    	{
    		waitingRoom.setPlayer5Label(lobbyData.getPlayer5());
    	}
    }

    public void updatePlayerList(List<String> players) {
        System.out.println("Updating player list: " + players);
        WaitingRoomPanel panel = findWaitingRoomPanel();
        if (panel != null) {
            panel.updatePlayerList(players);
            System.out.println("Player list updated");
        } else {
            System.out.println("Couldn't find WaitingRoomPanel to update players");
        }
    }

    
    public void showWaitingRoom() 
    {
        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container, "6");
    }
    
    public void setUsername(String username) {
        System.out.println("Setting username in WRC: " + username);
        this.username = username;
    }

    public String getUsername() {
        System.out.println("getUsername() called, returning: " + username);
        return username;
    }
    
    public void setAsHost(boolean isHost) {
        WaitingRoomPanel panel = findWaitingRoomPanel();
        if (panel != null) {
            panel.setAsHost(isHost);
        } else {
            System.out.println("Couldn't find WaitingRoomPanel to set host");
        }
    }



    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

   
}
