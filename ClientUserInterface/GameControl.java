package ClientUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

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
	
	public void updatePanel(GameData gameData)
	{
		GamePanel gamePanel = (GamePanel)container.getComponent(4);
		
		if (gameData.getScore() != 0)
		{
			gamePanel.setScoreLabel(gameData.getScore());
		}
		
		if (gameData.getUsername() != "" && gamePanel.getUsername() == "")
		{
			gamePanel.setUsernameLabel(gameData.getUsername());
		}
		
		if (gameData.getCurrentBet() != gamePanel.getCurrentBet())
		{
			gamePanel.setCurrentBetLabel(gameData.getCurrentBet());
		}
	}
}
