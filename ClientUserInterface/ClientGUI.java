package ClientUserInterface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import ClientCommunication.*;

public class ClientGUI extends JFrame 

{
    private GameClient client; 
    
    public ClientGUI() {
        client = new GameClient();
        client.setHost("localhost");
        client.setPort(8300);

        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Client is connected: " + client.isConnected());

        this.setTitle("Texas Hold 'Em");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel container = new JPanel(cardLayout);

        InitialControl ic = new InitialControl(container);
        GameControl gc = new GameControl(container, client);
        LoginControl lc = new LoginControl(container, client);
        CreateAccountControl cac = new CreateAccountControl(container, client);
        GameStartControl gsc = new GameStartControl(container, client);
        WaitingRoomControl wrc = new WaitingRoomControl(container, client, "");

        client.setLoginControl(lc);
        client.setCreateAccountControl(cac);
        client.setGameStartControl(gsc);
        client.setWaitingRoomControl(wrc);
        client.setGameControl(gc);

        JPanel view1 = new InitialPanel(ic);
        JPanel view2 = new LoginPanel(lc);
        JPanel view3 = new CreateAccountPanel(cac);
        JPanel view4 = new GameStartPanel(gsc);
        JPanel view5 = new GamePanel(gc);
        JPanel view6 = new WaitingRoomPanel(wrc);

        container.add(view1, "1");
        container.add(view2, "2");
        container.add(view3, "3");
        container.add(view4, "4");
        container.add(view5, "5");
        container.add(view6, "6");

        cardLayout.show(container, "1");

        this.setLayout(new BorderLayout());
        this.add(container);

        //this.setSize(1200, 775);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

	
	public static void main(String[] args)
	{
		new ClientGUI();
	}
}
