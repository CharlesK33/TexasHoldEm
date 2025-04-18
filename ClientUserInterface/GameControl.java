package ClientUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import ClientCommunication.*;
import CardGameData.*;

public class GameControl implements ActionListener
{
	private JPanel container;
	private GameClient client;
	
	public GameControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		String command = ae.getActionCommand();
		
		if (command == "Check")
		{
			CheckData checkData = new CheckData(true);
			
			try {
				client.sendToServer(checkData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (command == "Fold")
		{
			FoldData foldData = new FoldData(true);
			
			try {
				client.sendToServer(foldData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (command == "Call")
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			int currentBet = gamePanel.getCurrentBet();
			
			CallData callData = new CallData(currentBet);
			
			try {
				client.sendToServer(callData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (command == "Raise")
		{		
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			int current = gamePanel.getCurrentBet();
			gamePanel.setPlayerBetLabel(current);
		}
		else if (command == "Bet")
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			gamePanel.setPlayerBetLabel(10);
		}
		else if (command == "Increase +")
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			int current = gamePanel.getPlayerBetAmt();
			gamePanel.setPlayerBetLabel(current + 10);
		}
		else if (command == "Decrease -")
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			int current = gamePanel.getPlayerBetAmt();
			gamePanel.setPlayerBetLabel(current - 10);
		}
		else if (command == "Enter")
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			int current = gamePanel.getPlayerBetAmt();
			
			BetData betData = new BetData(current);
			try {
				client.sendToServer(betData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	public void updatePanel(GameData gameData) {
	   

	    GamePanel gamePanel = (GamePanel) container.getComponent(4);

	    // Score update
	    
	    if (gameData.getScore() != 0) {
	        gamePanel.setScoreLabel(gameData.getScore());
	    }

	    // Username update
	 
	    if (!gameData.getUsername().equals("") && gamePanel.getUsername().equals("")) {
	        gamePanel.setUsernameLabel(gameData.getUsername());
	    }

	    // Bet update
	   
	    if (gameData.getCurrentBet() != gamePanel.getCurrentBet()) {
	        gamePanel.setCurrentBetLabel(gameData.getCurrentBet());
	    }

	    // Hand + Card debug
	    Hand hand = gameData.getHand();
	    ArrayList<Card> cards = hand.getHand();
	    if (cards == null || cards.size() < 2) {
	        System.out.println("Cards are missing or fewer than 2. Size: " + (cards == null ? "null" : cards.size()));
	        return;
	    }

	    Card card1 = cards.get(0);
	    Card card2 = cards.get(1);

	    if (card1 == null || card2 == null) {
	        System.out.println("One or both cards are null: " + card1 + ", " + card2);
	        return;
	    }
	    
	    ArrayList<Card> board = gameData.getBoard();
	    if (board != null) {
	        gamePanel.setCommunityCards(board);
	    }


	    System.out.println("Setting images: " + card1.getFileName() + ", " + card2.getFileName());

	    gamePanel.setCardImages(card1, card2);
	}

	


}
