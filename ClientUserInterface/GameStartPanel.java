package ClientUserInterface;

import java.awt.*;
import javax.swing.*;

public class GameStartPanel extends JPanel
{
	private JButton startButton;
	private JButton joinButton;
	private JLabel errorLabel;
	
	public GameStartPanel(GameStartControl gsc)
	{
		this.setBackground(new Color(30, 92, 58));
		this.setLayout(new GridBagLayout());
		
		JPanel startScreen = new JPanel(new GridLayout(2, 1, 5, 5));
		startScreen.setBackground(new Color(30, 92, 58));
		
		// Title panel
		JPanel titlePanel = new JPanel(new GridLayout(2, 1));
		titlePanel.setBackground(new Color(30, 92, 58));
		JLabel title = new JLabel("Texas Hold 'Em");
		title.setFont(new Font("Times New Roman", Font.PLAIN, 60));
		title.setForeground(new Color(255, 215, 0));
		errorLabel = new JLabel("");
		errorLabel.setForeground(new Color(255, 0, 0));
		titlePanel.add(title);
		titlePanel.add(errorLabel);
		
		// Button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(30, 92, 58));
		startButton = new JButton("Start Game");
		startButton.addActionListener(gsc);
		joinButton = new JButton("Join Game");
		joinButton.addActionListener(gsc);
		buttonPanel.add(startButton);
		buttonPanel.add(joinButton);
		
		startScreen.add(titlePanel);
		startScreen.add(buttonPanel);
		
		this.add(startScreen);
	}
}
