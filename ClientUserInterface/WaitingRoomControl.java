package ClientUserInterface;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;

import ClientCommunication.GameClient;
import CardGameData.StartGameData;

public class WaitingRoomControl implements ActionListener 
{
    private JPanel container;
    private GameClient client;
    private String username;

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

    
    public void showWaitingRoom() {
        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container, "6");
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
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
        if (e.getActionCommand().equals("StartGame")) {
            try {
                client.sendToServer(new StartGameData(username, true));  // true = now REALLY starting
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

   
}
