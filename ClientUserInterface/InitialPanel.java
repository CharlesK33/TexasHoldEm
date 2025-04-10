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
		titleScreen.setLayout(new GridLayout(3,1,10,10));
		titleScreen.setBackground(new Color(30, 92, 58));
		
		JLabel title = new JLabel("Texas Hold 'Em");
		title.setFont(new Font("Times New Roman", Font.PLAIN, 60));
		title.setForeground(new Color(255, 215, 0));
		
		JButton login = new JButton("Login");
		login.addActionListener(ic);
		
		JButton create = new JButton("Create");
		create.addActionListener(ic);
		
		titleScreen.add(title);
		titleScreen.add(login);
		titleScreen.add(create);
		
		this.add(titleScreen);
	}
}
