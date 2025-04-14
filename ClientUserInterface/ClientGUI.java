package ClientUserInterface;

import javax.swing.*;
import java.awt.*;
import ClientCommunication.*;

public class ClientGUI extends JFrame
{
	public ClientGUI()
	{
		GameClient client = new GameClient();
		client.setHost("LocalHost");
		client.setPort(8300);
		this.setTitle("Texas Hold 'Em");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);
		
		InitialControl ic = new InitialControl(container);
		GameControl gc = new GameControl(container);
		LoginControl lc = new LoginControl(container);
		CreateAccountControl cac = new CreateAccountControl(container);
		GameStartControl gsc = new GameStartControl(container);
		
		JPanel view1 = new InitialPanel(ic);
		JPanel view2 = new LoginPanel(lc);
		JPanel view3 = new CreateAccountPanel(cac);
		JPanel view4 = new GamePanel(gc);
		JPanel view5 = new GameStartPanel(gsc);
		
		container.add(view1, "1");
		container.add(view2, "2");
		container.add(view3, "3");
		container.add(view4, "4");
		container.add(view5, "5");
		
		cardLayout.show(container,  "1");
		
		this.setLayout(new BorderLayout());
		this.add(container);
		
		this.setSize(1200, 675);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new ClientGUI();
	}
}
