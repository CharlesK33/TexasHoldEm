package ClientUserInterface;

import java.awt.CardLayout;
import java.awt.event.*;
import javax.swing.*;

public class LoginControl implements ActionListener
{
	private JPanel container;
	
	public LoginControl(JPanel container)
	{
		this.container = container;
	}

	public void actionPerformed(ActionEvent ae) 
	{
		String command = ae.getActionCommand();
		
		if (command.equals("Submit"))
		{
			
		}
		else if (command.equals("Cancel"))
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
	}
	
}
