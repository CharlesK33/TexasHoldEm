package ClientUserInterface;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class InitialControl implements ActionListener
{
	private JPanel container;
	
	public InitialControl(JPanel container)
	{
		this.container = container;
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		String command = ae.getActionCommand();
		
		if (command.equals("Login"))
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container,  "2"); // LoginPanel
		}
		else if (command.equals("Create"))
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container,  "3"); // CreateAccountPanel
		}
	}


}
