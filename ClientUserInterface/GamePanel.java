package ClientUserInterface;

import javax.imageio.ImageIO;
import javax.swing.*;

import CardGameData.Card;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class GamePanel extends JPanel
{
	private JLabel usernameLabel;
	private JLabel scoreLabel;
	private JLabel potLabel;
	private JLabel currentBet;
	private JLabel playerBet;
	private JLabel errorLabel;
	private JLabel player1;
	private JLabel player2;
	private JLabel player3;
	private JLabel player4;
	private JLabel player5;
	private JLabel player1Score;
	private JLabel player2Score;
	private JLabel player3Score;
	private JLabel player4Score;
	private JLabel player5Score;
	private JLabel playerTurnLabel;
	private JPanel player1Cards;
	private JPanel player2Cards;
	private JPanel player3Cards;
	private JPanel player4Cards;
	private JPanel player5Cards;
	private JLabel backOfCard1;
	private JLabel backOfCard2;
	private JButton checkButton;
	private JButton betButton;
	private JButton raiseButton;
	private JButton callButton;
	private JButton foldButton;
	private JButton increaseButton;
	private JButton decreaseButton;
	private JButton enterButton;
	private JButton dealCards;
	private JLabel card1;
	private JLabel card2;
	private JLabel flop1, flop2, flop3, turn, river;
	private Color background = new Color(30, 92, 58);


	
	public GamePanel(GameControl gc)
	{
		this.setLayout(new BorderLayout());
		// Game screen partitions
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1000, 300));
		JPanel east = new JPanel();
		JPanel center = new JPanel();
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(200, 600));
		JPanel south = new JPanel();
		
		// Player information (north partition)
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel playerPanel = new JPanel(new GridLayout(1, 5, 0, 0));
		playerPanel.setBackground(background);
		JPanel player1Panel = new JPanel(new GridBagLayout());
		player1Panel.setBackground(background);
		player1 = new JLabel("", JLabel.CENTER);
		player1.setForeground(new Color(255, 215, 0));
		player1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		player1Score = new JLabel("", JLabel.CENTER);
		player1Score.setForeground(new Color(255, 215, 0));
		player1Score.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		player1Cards = new JPanel();
		player1Cards.setBackground(background);
		backOfCard1 = new JLabel(new ImageIcon("images/back_of_card.png"));
		player1Cards.add(backOfCard1);
		backOfCard2 = new JLabel(new ImageIcon("images/back_of_card.png"));
		player1Cards.add(backOfCard2);
		gbc.gridy = 0;
		player1Panel.add(player1, gbc);
		gbc.gridy = 1;
		player1Panel.add(player1Score, gbc);
		gbc.gridy = 2;
		player1Panel.add(player1Cards, gbc);
		player1Cards.setVisible(false);
		JPanel player2Panel = new JPanel(new GridBagLayout());
		player2Panel.setBackground(background);
		player2 = new JLabel("", JLabel.CENTER);
		player2.setForeground(new Color(255, 215, 0));
		player2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		player2Score = new JLabel("", JLabel.CENTER);
		player2Score.setForeground(new Color(255, 215, 0));
		player2Score.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		player2Cards = new JPanel();
		player2Cards.setBackground(background);
		backOfCard1 = new JLabel(new ImageIcon("images/back_of_card.png"));
		player2Cards.add(backOfCard1);
		backOfCard2 = new JLabel(new ImageIcon("images/back_of_card.png"));
		player2Cards.add(backOfCard2);
		gbc.gridy = 0;
		player2Panel.add(player2, gbc);
		gbc.gridy = 1;
		player2Panel.add(player2Score, gbc);
		gbc.gridy = 2;
		player2Panel.add(player2Cards, gbc);
		player2Cards.setVisible(false);
		JPanel player3Panel = new JPanel(new GridBagLayout());
		player3Panel.setBackground(background);
		player3 = new JLabel("", JLabel.CENTER);
		player3.setForeground(new Color(255, 215, 0));
		player3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		player3Score = new JLabel("", JLabel.CENTER);
		player3Score.setForeground(new Color(255, 215, 0));
		player3Score.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		player3Cards = new JPanel();
		player3Cards.setBackground(background);
		backOfCard1 = new JLabel(new ImageIcon("images/back_of_card.png"));
		player3Cards.add(backOfCard1);
		backOfCard2 = new JLabel(new ImageIcon("images/back_of_card.png"));
		player3Cards.add(backOfCard2);
		gbc.gridy = 0;
		player3Panel.add(player3, gbc);
		gbc.gridy = 1;
		player3Panel.add(player3Score, gbc);
		gbc.gridy = 2;
		player3Panel.add(player3Cards, gbc);
		player3Cards.setVisible(false);
		JPanel player4Panel = new JPanel(new GridBagLayout());
		player4Panel.setBackground(background);
		player4 = new JLabel("", JLabel.CENTER);
		player4.setForeground(new Color(255, 215, 0));
		player4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		player4Score = new JLabel("", JLabel.CENTER);
		player4Score.setForeground(new Color(255, 215, 0));
		player4Score.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		player4Cards = new JPanel();
		player4Cards.setBackground(background);
		backOfCard1 = new JLabel(new ImageIcon("images/back_of_card.png"));
		player4Cards.add(backOfCard1);
		backOfCard2 = new JLabel(new ImageIcon("images/back_of_card.png"));
		player4Cards.add(backOfCard2);
		gbc.gridy = 0;
		player4Panel.add(player4, gbc);
		gbc.gridy = 1;
		player4Panel.add(player4Score, gbc);
		gbc.gridy = 2;
		player4Panel.add(player4Cards, gbc);
		player4Cards.setVisible(false);
		JPanel player5Panel = new JPanel(new GridBagLayout());
		player5Panel.setBackground(background);
		player5 = new JLabel("", JLabel.CENTER);
		player5.setForeground(new Color(255, 215, 0));
		player5.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		player5Score = new JLabel("", JLabel.CENTER);
		player5Score.setForeground(new Color(255, 215, 0));
		player5Score.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		player5Cards = new JPanel();
		player5Cards.setBackground(background);
		backOfCard1 = new JLabel(new ImageIcon("images/back_of_card.png"));
		player5Cards.add(backOfCard1);
		backOfCard2 = new JLabel(new ImageIcon("images/back_of_card.png"));
		player5Cards.add(backOfCard2);
		gbc.gridy = 0;
		player5Panel.add(player5, gbc);
		gbc.gridy = 1;
		player5Panel.add(player5Score, gbc);
		gbc.gridy = 2;
		player5Panel.add(player5Cards, gbc);
		player5Cards.setVisible(false);
		playerPanel.add(player1Panel);
		playerPanel.add(player2Panel);
		playerPanel.add(player3Panel);
		playerPanel.add(player4Panel);
		playerPanel.add(player5Panel);
		
		// Player information (south partition)
		
		JPanel cardPanel = new JPanel();
		cardPanel.setBackground(new Color(30, 92, 58));
		card1 = new JLabel();
		card2 = new JLabel();
		cardPanel.add(card1);
		cardPanel.add(card2);
		JPanel currentPlayerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		currentPlayerPanel.setBackground(background);
		usernameLabel = new JLabel("", JLabel.CENTER);
		usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		usernameLabel.setForeground(new Color(255, 215, 0));
		scoreLabel = new JLabel("", JLabel.CENTER);
		scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		scoreLabel.setForeground(new Color(255, 215, 0));
		currentPlayerPanel.add(usernameLabel);
		currentPlayerPanel.add(scoreLabel);
		
		
		// Bet information/Community cards (center partition)
		JPanel betInfoPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		JLabel descriptionLabel1 = new JLabel("Pot Amount: ");
		descriptionLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		descriptionLabel1.setForeground(new Color(255, 215, 0));
		potLabel = new JLabel("0");
		potLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        potLabel.setForeground(new Color(255, 215, 0));
        JLabel descriptionLabel2 = new JLabel("Bet Amount: ");
        descriptionLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        descriptionLabel2.setForeground(new Color(255, 215, 0));
		currentBet = new JLabel("0");
		currentBet.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        currentBet.setForeground(new Color(255, 215, 0));
        betInfoPanel.add(descriptionLabel1);
		betInfoPanel.add(potLabel);
		betInfoPanel.add(descriptionLabel2);
		betInfoPanel.add(currentBet);
		JPanel communityCardPanel = new JPanel();
		communityCardPanel.setBackground(background);
		flop1 = new JLabel(new ImageIcon("images/back_of_card.png"));
		flop2 = new JLabel();
		flop3 = new JLabel();
		turn = new JLabel();
		river = new JLabel();
		communityCardPanel.add(flop1);
		communityCardPanel.add(flop2);
		communityCardPanel.add(flop3);
		communityCardPanel.add(turn);
		communityCardPanel.add(river);
		
		// Buttons (east partition)
		JPanel buttonPanel = new JPanel(new GridLayout(6, 2, 5, 5));
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
		errorLabel = new JLabel("");
		errorLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		errorLabel.setForeground(new Color(255, 0, 0));
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
		buttonPanel.add(errorLabel);
		
		//Buttons (west partition)
		JPanel turnPanel = new JPanel(new GridBagLayout());
		turnPanel.setBackground(background);
		JLabel turnLabelP1 = new JLabel("It is ");
		turnLabelP1.setForeground(new Color(255, 215, 0));
		turnLabelP1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		playerTurnLabel = new JLabel("");
		playerTurnLabel.setForeground(new Color(255, 215, 0));
		playerTurnLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		JLabel turnLabelP2 = new JLabel("'s turn.");
		turnLabelP2.setForeground(new Color(255, 215, 0));
		turnLabelP2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		turnPanel.add(turnLabelP1);
		turnPanel.add(playerTurnLabel);
		turnPanel.add(turnLabelP2);
		
		// Game Screen
		JPanel border = new JPanel(new BorderLayout());
		JPanel southBorder = new JPanel(new BorderLayout());
		southBorder.add(cardPanel, BorderLayout.NORTH);
		southBorder.add(currentPlayerPanel, BorderLayout.SOUTH);
		JPanel centerBorder = new JPanel(new BorderLayout());
		centerBorder.add(betInfoPanel, BorderLayout.NORTH);
		centerBorder.add(communityCardPanel, BorderLayout.SOUTH);
		north.add(playerPanel);
		south.add(southBorder);
		center.add(centerBorder);
		east.add(buttonPanel);
		west.add(turnPanel);
		
		north.setBackground(background);
		south.setBackground(background);
		east.setBackground(background);
		west.setBackground(background);
		center.setBackground(background);
		centerBorder.setBackground(background);
		playerPanel.setBackground(background);
		betInfoPanel.setBackground(background);
		buttonPanel.setBackground(background);
		
		border.add(south, BorderLayout.SOUTH);
		border.add(center, BorderLayout.CENTER);
		border.add(east, BorderLayout.EAST);
		border.add(north, BorderLayout.NORTH);
		border.add(west, BorderLayout.WEST);
		
		this.add(border);
	}
	
	public void setPlayerTurnLabel(String playerTurn)
	{
		playerTurnLabel.setText(playerTurn);
	}
	
	public String getPlayerTurnLabel()
	{
		return playerTurnLabel.getText();
	}
	
	public void showPlayer1Cards()
	{
		player1Cards.setVisible(true);
	}
	
	public void showPlayer2Cards()
	{
		player2Cards.setVisible(true);
	}
	
	public void showPlayer3Cards()
	{
		player3Cards.setVisible(true);
	}
	
	public void showPlayer4Cards()
	{
		player4Cards.setVisible(true);
	}
	
	public void showPlayer5Cards()
	{
		player5Cards.setVisible(true);
	}
	
	public void hidePlayer1Cards()
	{
		player1Cards.setVisible(false);
	}
	
	public void hidePlayer2Cards()
	{
		player2Cards.setVisible(false);
	}
	
	public void hidePlayer3Cards()
	{
		player3Cards.setVisible(false);
	}
	
	public void hidePlayer4Cards()
	{
		player4Cards.setVisible(false);
	}
	
	public void hidePlayer5Cards()
	{
		player5Cards.setVisible(false);
	}
	
	public void setUsernameLabel(String username)
	{
		usernameLabel.setText(username);
	}
	
	public void setPlayer1Label(String player1)
    {
    	this.player1.setText(player1);
    }
    
    public void setPlayer2Label(String player2)
    {
    	this.player2.setText(player2);
    }
    
    public void setPlayer3Label(String player3)
    {
    	this.player3.setText(player3);
    }
    
    public void setPlayer4Label(String player4)
    {
    	this.player4.setText(player4);
    }
    
    public void setPlayer5Label(String player5)
    {
    	this.player5.setText(player5);
    }
    
    public void setPlayer1ScoreLabel(int score)
    {
    	this.player1Score.setText(Integer.toString(score));
    }
    
    public void setPlayer2ScoreLabel(int score)
    {
    	this.player2Score.setText(Integer.toString(score));
    }
    
    public void setPlayer3ScoreLabel(int score)
    {
    	this.player3Score.setText(Integer.toString(score));
    }
    
    public void setPlayer4ScoreLabel(int score)
    {
    	this.player4Score.setText(Integer.toString(score));
    }
    
    public void setPlayer5ScoreLabel(int score)
    {
    	this.player5Score.setText(Integer.toString(score));
    }
    
    public int getPlayer1ScoreLabel()
    {
    	return Integer.parseInt(player1Score.getText());
    }
    
    public int getPlayer2ScoreLabel()
    {
    	return Integer.parseInt(player2Score.getText());
    }
    
    public int getPlayer3ScoreLabel()
    {
    	return Integer.parseInt(player3Score.getText());
    }
    
    public int getPlayer4ScoreLabel()
    {
    	return Integer.parseInt(player4Score.getText());
    }
    
    public int getPlayer5ScoreLabel()
    {
    	return Integer.parseInt(player5Score.getText());
    }
	
	public void setScoreLabel(int score)
	{
		scoreLabel.setText(Integer.toString(score));
	}
	
	public int getScoreLabel()
	{
		return Integer.parseInt(scoreLabel.getText());
	}
	
	public void setPotLabel(int pot)
	{
		potLabel.setText(Integer.toString(pot));
	}
	
	public int getPot()
	{
		return Integer.parseInt(potLabel.getText());
	}
	
	public void setCurrentBetLabel(int bet)
	{
		currentBet.setText(Integer.toString(bet));
	}
	
	public void setPlayerBetLabel(int bet)
	{
		playerBet.setText(Integer.toString(bet));
	}
	
	public void showDealCards()
    {
    	dealCards.setVisible(true);
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
	
	public void setError(String error)
	{
		errorLabel.setText(error);
	}
	
	public void removeFoldedPlayer(ArrayList<String> players, String player)
	{
		int foldedPlayerIndex = 0;
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).equals(player))
			{
				foldedPlayerIndex = i;
				break;
			}
		}
		
		if (foldedPlayerIndex == 0)
		{
			hidePlayer1Cards();
		}
		else if (foldedPlayerIndex == 1)
		{
			hidePlayer2Cards();
		}
		else if (foldedPlayerIndex == 2)
		{
			hidePlayer3Cards();
		}
		else if (foldedPlayerIndex == 3)
		{
			hidePlayer4Cards();
		}
		else if (foldedPlayerIndex == 4)
		{
			hidePlayer5Cards();
		}
	}
	
	public void setCardImages(Card card1Obj, Card card2Obj) {
		
		String path1 = "images/" + card1Obj.getFileName();
		String path2 = "images/" + card2Obj.getFileName();

		ImageIcon image1 = new ImageIcon(path1);
		ImageIcon image2 = new ImageIcon(path2);

		card1.setIcon(image1);
		card2.setIcon(image2);
	}
	
	public void setCommunityCards(ArrayList<Card> board) {
	    JLabel[] labels = {flop1, flop2, flop3, turn, river};
	    for (int i = 0; i < 5; i++) {
	        if (i < board.size()) {
	            labels[i].setIcon(new ImageIcon("images/" + board.get(i).getFileName()));
	        } else {
	            labels[i].setIcon(null); //Clear if not yet dealt
	        }
	    }
	}
	
	public void showFlop(ArrayList<Card> flop)
	{
		String path1 = "images/" + flop.get(0).getFileName();
		String path2 = "images/" + flop.get(1).getFileName();
		String path3 = "images/" + flop.get(2).getFileName();
		
		ImageIcon card1 = new ImageIcon(path1);
		ImageIcon card2 = new ImageIcon(path2);
		ImageIcon card3 = new ImageIcon(path3);
		
		flop1.setIcon(card1);
		flop2.setIcon(card2);
		flop3.setIcon(card3);
	}
	
	public void showTurn(Card turn)
	{
		String path = "images/" + turn.getFileName();
		
		ImageIcon card = new ImageIcon(path);
		
		this.turn.setIcon(card);
	}
	
	public void showRiver(Card river)
	{
		String path = "images/" + river.getFileName();
		
		ImageIcon card = new ImageIcon(path);
		
		this.river.setIcon(card);
	}


	
}
