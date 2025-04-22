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
	private ArrayList<String> players;
	
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
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			String username = gamePanel.getUsername();
			String playerTurn = gamePanel.getPlayerTurnLabel();
			
			if (username.equals(playerTurn))
			{
				CheckData checkData = new CheckData(true);
			
				try {
					client.sendToServer(checkData);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				displayError("Out of turn.");
				
				new javax.swing.Timer(3000, e -> {
		            displayError(""); // Clear the error message
		        }).start();
				
			    return;
			}
			
		}
		else if (command == "Fold")
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			String username = gamePanel.getUsername();
			String playerTurn = gamePanel.getPlayerTurnLabel();
			
			if (username.equals(playerTurn))
			{
				username = gamePanel.getPlayerTurnLabel();
				FoldData foldData = new FoldData(true, username);
			
				try {
				client.sendToServer(foldData);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				displayError("Out of turn.");
				
				new javax.swing.Timer(3000, e -> {
		            displayError(""); // Clear the error message
		        }).start();
				
			    return;
			}
			
		}
		else if (command == "Call")
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			String username = gamePanel.getUsername();
			String playerTurn = gamePanel.getPlayerTurnLabel();
			
			if (username.equals(playerTurn))
			{
				username = gamePanel.getPlayerTurnLabel();
				int currentBet = gamePanel.getCurrentBet();
			
				CallData callData = new CallData(currentBet, username);
			
				try {
					client.sendToServer(callData);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				displayError("Out of turn.");
				
				new javax.swing.Timer(3000, e -> {
		            displayError(""); // Clear the error message
		        }).start();
				
			    return;
			}
			
		}
		else if (command == "Raise")
		{		
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			String username = gamePanel.getUsername();
			String playerTurn = gamePanel.getPlayerTurnLabel();
			
			if (username.equals(playerTurn))
			{
				int current = gamePanel.getCurrentBet();
				gamePanel.setPlayerBetLabel(current);
			}
			else
			{
				displayError("Out of turn.");
				
				new javax.swing.Timer(3000, e -> {
		            displayError(""); // Clear the error message
		        }).start();
				
			    return;
			}
			
		}
		else if (command == "Bet")
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			String username = gamePanel.getUsername();
			String playerTurn = gamePanel.getPlayerTurnLabel();
			
			if (username.equals(playerTurn))
			{
				gamePanel.setPlayerBetLabel(10);
			}
			else
			{
				displayError("Out of turn.");
				
				new javax.swing.Timer(3000, e -> {
		            displayError(""); // Clear the error message
		        }).start();
				
			    return;
			}
			
		}
		else if (command == "Increase +")
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			String username = gamePanel.getUsername();
			String playerTurn = gamePanel.getPlayerTurnLabel();
			
			if (username.equals(playerTurn))
			{
				int current = gamePanel.getPlayerBetAmt();
				gamePanel.setPlayerBetLabel(current + 10);
			}
			else
			{
				displayError("Out of turn.");
				
				new javax.swing.Timer(3000, e -> {
		            displayError(""); // Clear the error message
		        }).start();
				
			    return;
			}
			
		}
		else if (command == "Decrease -")
		{
			
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			String username = gamePanel.getUsername();
			String playerTurn = gamePanel.getPlayerTurnLabel();
			
			if (username.equals(playerTurn))
			{
				int current = gamePanel.getPlayerBetAmt();
				gamePanel.setPlayerBetLabel(current - 10);
			}
			else
			{
				displayError("Out of turn.");
				
				new javax.swing.Timer(3000, e -> {
		            displayError(""); // Clear the error message
		        }).start();
				
			    return;
			}
			
		}
		else if (command.equals("Enter"))
		{
			GamePanel gamePanel = (GamePanel)container.getComponent(4);
			int current = gamePanel.getPlayerBetAmt();
			String username = gamePanel.getUsername();
			String playerTurn = gamePanel.getPlayerTurnLabel();
			
			if (username.equals(playerTurn))
			{
				BetData betData = new BetData(current, username);
				try {
					client.sendToServer(betData);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				displayError("Out of turn.");
				
				new javax.swing.Timer(3000, e -> {
		            displayError(""); // Clear the error message
		        }).start();
				
			    return;
			}
		}
		else if (command.equals("Ready"))
		{
			
		}
		else if (command.equals("Start Hand"))
		{
			//GamePanel gamePanel = (GamePanel)container.getComponent(4);
			
			GameData gameData = new GameData();
        	gameData.setPlayers(players);
        	gameData.setCurrentBet(10);
        	gameData.setPot(15);
        	gameData.setStart(true);
        	gameData.setFlop(null);
        	gameData.setTurn(null);
        	gameData.setRiver(null);
        	
        	try {
				client.sendToServer(gameData);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void showGamePanel()
    {
    	CardLayout cardLayout = (CardLayout)container.getLayout();
    	cardLayout.show(container, "5");
    }
	
	public void initializePanel(GameData gameData)
	{
		GamePanel gamePanel = (GamePanel) container.getComponent(4);
		gamePanel.setPotLabel(gameData.getPot());
		gamePanel.setCurrentBetLabel(gameData.getCurrentBet());
		gamePanel.hideReadyButton();
		gamePanel.hideStartHand();
		gamePanel.resetBoard();
		players = (ArrayList<String>) gameData.getPlayers();
		
		if (players.size() == 1)
		{
			gamePanel.setPlayer1Label(players.get(0));
			gamePanel.setPlayer1ScoreLabel(100);
			gamePanel.showPlayer1Cards();
		}
		else if (players.size() == 2)
		{
			String player = players.get(0);
	    	gamePanel.setPlayerTurnLabel(player);
	    	
			gamePanel.setPlayer1Label(players.get(0));
			gamePanel.setPlayer1ScoreLabel(95);
			gamePanel.showPlayer1Cards();
			
			gamePanel.setPlayer2Label(players.get(1));
			gamePanel.setPlayer2ScoreLabel(90);
			gamePanel.showPlayer2Cards();
		}
		else if (players.size() == 3)
		{
			String player = players.get(0);
	    	gamePanel.setPlayerTurnLabel(player);
	    	
			gamePanel.setPlayer1Label(players.get(0));
			gamePanel.setPlayer1ScoreLabel(100);
			gamePanel.showPlayer1Cards();
			
			gamePanel.setPlayer2Label(players.get(1));
			gamePanel.setPlayer2ScoreLabel(95);
			gamePanel.showPlayer2Cards();
			
			gamePanel.setPlayer3Label(players.get(2));
			gamePanel.setPlayer3ScoreLabel(90);
			gamePanel.showPlayer3Cards();
		}
		else if (players.size() == 4)
		{
			String player = players.get(0);
	    	gamePanel.setPlayerTurnLabel(player + 2);
	    	
			gamePanel.setPlayer1Label(players.get(0));
			gamePanel.setPlayer1ScoreLabel(100);
			gamePanel.showPlayer1Cards();
			
			gamePanel.setPlayer2Label(players.get(1));
			gamePanel.setPlayer2ScoreLabel(95);
			gamePanel.showPlayer2Cards();
			
			gamePanel.setPlayer3Label(players.get(2));
			gamePanel.setPlayer3ScoreLabel(90);
			gamePanel.showPlayer3Cards();
			
			gamePanel.setPlayer4Label(players.get(3));
			gamePanel.setPlayer4ScoreLabel(100);
			gamePanel.showPlayer4Cards();
		}
		else if (players.size() == 5)
		{
			String player = players.get(0);
	    	gamePanel.setPlayerTurnLabel(player + 2);
	    	
			gamePanel.setPlayer1Label(players.get(0));
			gamePanel.setPlayer1ScoreLabel(100);
			gamePanel.showPlayer1Cards();
			
			gamePanel.setPlayer2Label(players.get(1));
			gamePanel.setPlayer2ScoreLabel(95);
			gamePanel.showPlayer2Cards();
			
			gamePanel.setPlayer3Label(players.get(2));
			gamePanel.setPlayer3ScoreLabel(90);
			gamePanel.showPlayer3Cards();
			
			gamePanel.setPlayer4Label(players.get(3));
			gamePanel.setPlayer4ScoreLabel(100);
			gamePanel.showPlayer4Cards();
			
			gamePanel.setPlayer5Label(players.get(4));
			gamePanel.setPlayer5ScoreLabel(100);
			gamePanel.showPlayer5Cards();
		}
	}
	
	public void updatePanel(GameData gameData) {
	   

	    GamePanel gamePanel = (GamePanel) container.getComponent(4);

	    // Score update
	    
	    if (gameData.getScore() != 0) {
	        gamePanel.setScoreLabel(gameData.getScore());
	    }
	    
	    if (!players.get(gameData.getPlayerTurn()).equals(gamePanel.getPlayerTurnLabel()))
	    {
	    	gamePanel.setPlayerTurnLabel(players.get(gameData.getPlayerTurn()));
	    }

	    // Username update
	 
	    if (gameData.getUsername() != null && gamePanel.getUsername() == "")
		{
			gamePanel.setUsernameLabel(gameData.getUsername());
		}

	    // Bet update
	   
	    if (gameData.getCurrentBet() != gamePanel.getCurrentBet() && gameData.getCurrentBet() != 0) {
	        gamePanel.setCurrentBetLabel(gameData.getCurrentBet());
	    }
	    
	    if (gameData.getHand() != null)
	    {
	    	gamePanel.setCardImages(gameData.getHand().getHand().getFirst(), gameData.getHand().getHand().getLast());
	    }
	    
	    if (gameData.isFolding()) 
	    {
	    	gamePanel.removeFoldedPlayer(players, gameData.getUsername());
	    }
	    
	    if (gameData.getFlop() != null)
	    {
	    	gamePanel.showFlop(gameData.getFlop());
	    }
	    
	    if (gameData.getTurn() != null)
	    {
	    	gamePanel.showTurn(gameData.getTurn());
	    }
	    
	    if (gameData.getRiver() != null)
	    {
	    	gamePanel.showRiver(gameData.getRiver());
	    }
	    
	    if (gameData.getUsername() != null && gameData.getUsername().equals(gamePanel.getUsername()) && gameData.getBetAmount() == gamePanel.getCurrentBet()) 
	    {
	    	int betAmount = gameData.getBetAmount();
	    	gamePanel.setScoreLabel(gamePanel.getScoreLabel() - betAmount);
	    	gamePanel.setPotLabel(gamePanel.getPot() + betAmount);
	    }
	    
	    if (gameData.getUsername() != null && gameData.getUsername().equals(gamePanel.getUsername()) && gameData.getBetAmount() != gamePanel.getCurrentBet()) 
	    {
	    	int betAmount = gameData.getBetAmount();
	    	gamePanel.setScoreLabel(gamePanel.getScoreLabel() - betAmount);
	    	gamePanel.setPotLabel(gamePanel.getPot() + betAmount);
	    }
	    
	    if (gameData.isEndOfHand())
	    {
	    	gamePanel.setPlayerTurnLabel("player 1");
	    	gamePanel.showReadyButton();
	    	gamePanel.showStartHand();
	    }
	}
	
	public void displayError(String error)
	{
	  GamePanel gamePanel = (GamePanel)container.getComponent(4);
	  gamePanel.setError(error);
	}

	


}
