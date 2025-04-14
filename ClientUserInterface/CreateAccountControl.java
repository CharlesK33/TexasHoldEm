package ClientUserInterface;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CreateAccountControl implements ActionListener
{

	private JPanel container;
	
	public CreateAccountControl(JPanel container)
	{
		this.container = container;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		String command = ae.getActionCommand();
		
		if (command.equals("Enter"))
		{
			
		}
		else if (command.equals("Cancel"))
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "1");
		}
	}

}
