package ClientUserInterface;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

import ClientCommunication.*;
import CardGameData.*;

public class GameStartControl implements ActionListener {
    private JPanel container;
    private GameClient client;
    private boolean alreadySentStart = false;

    public GameStartControl(JPanel container, GameClient client) {
        this.container = container;
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("🧠 Client connected? " + client.isConnected());

        if (alreadySentStart) {
            System.out.println("⛔ StartGameData already sent — ignoring duplicate click");
            return;
        }

        alreadySentStart = true;

        String command = ae.getActionCommand();
        LoginPanel loginPanel = (LoginPanel) container.getComponent(1);
        String username = loginPanel.getUsername();

        WaitingRoomControl wrc = client.getWaitingRoomControl();
        wrc.setUsername(username);
        StartGameData startGameData = new StartGameData(username, false);

        try {
            System.out.println("🔌 Sending StartGameData...");
            client.sendToServer(startGameData);

            // Delay slightly to ensure registration before UI flips
            Thread.sleep(100); 

            CardLayout cardLayout = (CardLayout) container.getLayout();
            cardLayout.show(container, "6");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("🟢 UI-triggered GameClient: " + client);

    }

    public void startGame() {
        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, "5");
    }

    public void displayError(String error) {
        // Currently unused
    }
}
