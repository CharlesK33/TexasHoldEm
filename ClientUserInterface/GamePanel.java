package ClientUserInterface;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel
{
	private JLabel usernameLabel;
	private JLabel scoreLabel;
	private JLabel potLabel;
	private JLabel currentBet;
	private JButton checkButton;
	private JButton betButton;
	private JButton raiseButton;
	private JButton callButton;
	private JButton foldButton;
	private JButton increaseButton;
	private JButton decreaseButton;
	private JButton enterButton;
	
	public GamePanel(GameControl gc)
	{
		this.setLayout(new BorderLayout());
		
		// Game screen partitions
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(600, 300));
		JPanel east = new JPanel();
		JPanel center = new JPanel();
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(200, 600));
		JPanel south = new JPanel();
		
		// Player information (south partition)
		JPanel playerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		usernameLabel = new JLabel("ExampleUser", JLabel.CENTER);
		usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		usernameLabel.setForeground(new Color(255, 215, 0));
		scoreLabel = new JLabel("100", JLabel.CENTER);
		scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		scoreLabel.setForeground(new Color(255, 215, 0));
		playerPanel.add(usernameLabel);
		playerPanel.add(scoreLabel);
		
		// Bet information (center partition)
		JPanel betInfoPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		JLabel descriptionLabel1 = new JLabel("Pot Amount: ");
		descriptionLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		descriptionLabel1.setForeground(new Color(255, 215, 0));
		potLabel = new JLabel("50");
		potLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        potLabel.setForeground(new Color(255, 215, 0));
        JLabel descriptionLabel2 = new JLabel("Bet Amount: ");
        descriptionLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        descriptionLabel2.setForeground(new Color(255, 215, 0));
		currentBet = new JLabel("10");
		currentBet.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        currentBet.setForeground(new Color(255, 215, 0));
        betInfoPanel.add(descriptionLabel1);
		betInfoPanel.add(potLabel);
		betInfoPanel.add(descriptionLabel2);
		betInfoPanel.add(currentBet);
		
		// Buttons (east partition)
		JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 5, 5));
		checkButton = new JButton("Check");
		betButton = new JButton("Bet");
		raiseButton = new JButton("Raise");
		callButton = new JButton("Call");
		foldButton = new JButton("Fold");
		increaseButton = new JButton("Increase +");
		decreaseButton = new JButton("Decrease -");
		enterButton = new JButton("Enter");
		buttonPanel.add(checkButton);
		buttonPanel.add(foldButton);
		buttonPanel.add(callButton);
		buttonPanel.add(raiseButton);
		buttonPanel.add(betButton);
		buttonPanel.add(increaseButton);
		buttonPanel.add(decreaseButton);
		buttonPanel.add(enterButton);
		
		// Game Screen
		JPanel border = new JPanel(new BorderLayout());
		south.add(playerPanel);
		center.add(betInfoPanel);
		east.add(buttonPanel);
		
		north.setBackground(new Color(30, 92, 58));
		south.setBackground(new Color(30, 92, 58));
		east.setBackground(new Color(30, 92, 58));
		west.setBackground(new Color(30, 92, 58));
		center.setBackground(new Color(30, 92, 58));
		playerPanel.setBackground(new Color(30, 92, 58));
		betInfoPanel.setBackground(new Color(30, 92, 58));
		buttonPanel.setBackground(new Color(30, 92, 58));
		
		border.add(south, BorderLayout.SOUTH);
		border.add(center, BorderLayout.CENTER);
		border.add(east, BorderLayout.EAST);
		border.add(north, BorderLayout.NORTH);
		border.add(west, BorderLayout.WEST);
		
		this.add(border);
				
	}
	
	public void setUsernameLabel()
	{
		
	}
	
	public void setScoreLabel()
	{
		
	}
	
	public void setPotLabel()
	{
		
	}
	
	public void setCurrentBetLabel()
	{
		
	}
	
}
