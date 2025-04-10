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
			LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
			//LoginPanel.setError("");
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container,  "2");
		}
		else if (command.equals("Create"))
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container,  "3");
		}
	}

}
