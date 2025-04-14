package ClientUserInterface;

import javax.swing.*;
import java.awt.*;

public class InitialPanel extends JPanel
{
	public InitialPanel(InitialControl ic)
	{
		this.setBackground(new Color(30, 92, 58));
		this.setLayout(new GridBagLayout());
		
		JPanel titleScreen = new JPanel();
		titleScreen.setLayout(new GridLayout(2,1,0,0));
		titleScreen.setBackground(new Color(30, 92, 58));
		
		// Tile panel
		JPanel titlePanel = new JPanel(); 
		titlePanel.setBackground(new Color(30, 92, 58));
		JLabel title = new JLabel("Texas Hold 'Em");
		title.setFont(new Font("Times New Roman", Font.PLAIN, 60));
		title.setForeground(new Color(255, 215, 0));
		titlePanel.add(title);
		
		// Button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(30, 92, 58));
		JButton login = new JButton("Login");
		login.addActionListener(ic);
		JButton create = new JButton("Create");
		create.addActionListener(ic);
		buttonPanel.add(login);
		buttonPanel.add(create);
		
		titleScreen.add(titlePanel);
		titleScreen.add(buttonPanel);
		
		this.add(titleScreen);
	}
}
