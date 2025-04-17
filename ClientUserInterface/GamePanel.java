package ClientUserInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GamePanel extends JPanel
{
	private JLabel usernameLabel;
	private JLabel scoreLabel;
	private JLabel potLabel;
	private JLabel currentBet;
	private JLabel playerBet;
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
		//north.setPreferredSize(new Dimension(600, 300));
		JPanel east = new JPanel();
		JPanel center = new JPanel();
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(200, 600));
		JPanel south = new JPanel();
		
		// Player information (south partition)
		JPanel cardPanel = new JPanel();
		cardPanel.setBackground(new Color(30, 92, 58));
		ImageIcon image1 = new ImageIcon("images/ace_of_spades.png");
		JLabel card1 = new JLabel(image1);
		ImageIcon image2 = new ImageIcon("images/ace_of_clubs.png");
		JLabel card2 = new JLabel(image2);
		cardPanel.add(card1);
		cardPanel.add(card2);
		JPanel playerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		usernameLabel = new JLabel("", JLabel.CENTER);
		usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		usernameLabel.setForeground(new Color(255, 215, 0));
		scoreLabel = new JLabel("", JLabel.CENTER);
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
		currentBet = new JLabel("0");
		currentBet.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        currentBet.setForeground(new Color(255, 215, 0));
        betInfoPanel.add(descriptionLabel1);
		betInfoPanel.add(potLabel);
		betInfoPanel.add(descriptionLabel2);
		betInfoPanel.add(currentBet);
		
		// Buttons (east partition)
		JPanel buttonPanel = new JPanel(new GridLayout(5, 2, 5, 5));
		checkButton = new JButton("Check");
		checkButton.addActionListener(gc);
		betButton = new JButton("Bet");
		betButton.addActionListener(gc);
		raiseButton = new JButton("Raise");
		raiseButton.addActionListener(gc);
		callButton = new JButton("Call");
		callButton.addActionListener(gc);
		foldButton = new JButton("Fold");
		foldButton.addActionListener(gc);
		increaseButton = new JButton("Increase +");
		increaseButton.addActionListener(gc);
		decreaseButton = new JButton("Decrease -");
		decreaseButton.addActionListener(gc);
		enterButton = new JButton("Enter");
		enterButton.addActionListener(gc);
		JLabel playerBetLabel = new JLabel("Bet Amount: ");
		playerBetLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		playerBetLabel.setForeground(new Color(255, 215, 0));
		playerBet = new JLabel("");
		playerBet.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		playerBet.setForeground(new Color(255, 215, 0));
		buttonPanel.add(checkButton);
		buttonPanel.add(foldButton);
		buttonPanel.add(callButton);
		buttonPanel.add(raiseButton);
		buttonPanel.add(betButton);
		buttonPanel.add(increaseButton);
		buttonPanel.add(decreaseButton);
		buttonPanel.add(enterButton);
		buttonPanel.add(playerBetLabel);
		buttonPanel.add(playerBet);
		
		// Game Screen
		JPanel border = new JPanel(new BorderLayout());
		JPanel southBorder = new JPanel(new BorderLayout());
		southBorder.add(cardPanel, BorderLayout.NORTH);
		southBorder.add(playerPanel, BorderLayout.SOUTH);
		//southBorder.setPreferredSize(new Dimension(1200,200));
		south.add(southBorder);
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
	
	public void setUsernameLabel(String username)
	{
		usernameLabel.setText(username);
	}
	
	public void setScoreLabel(int score)
	{
		scoreLabel.setText(Integer.toString(score));
	}
	
	public void setPotLabel(int pot)
	{
		potLabel.setText(Integer.toString(pot));
	}
	
	public void setCurrentBetLabel(int bet)
	{
		currentBet.setText(Integer.toString(bet));
	}
	
	public void setPlayerBetLabel(int bet)
	{
		playerBet.setText(Integer.toString(bet));
	}
	
	
	//Parses the best amount entered by the player
	//Returns 0 if the label is blank 
	public int getPlayerBetAmt() 
	{
	    String betText = playerBet.getText().trim();
	    if (betText.equals("")) 
	    {
	        return 0;
	    }
	    return Integer.parseInt(betText);
	}
	
	public int getCurrentBet()
	{
		return Integer.parseInt(currentBet.getText());
	}
	
	public String getUsername()
	{
		return usernameLabel.getText();
	}
	
}
