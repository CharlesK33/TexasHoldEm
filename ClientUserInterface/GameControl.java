package ClientUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import ClientCommunication.*;

public class GameControl implements ActionListener
{
	private JPanel container;
	private GameClient client;
	
	public GameControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		
	}
}
