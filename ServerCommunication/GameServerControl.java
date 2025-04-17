package ServerCommunication;

import java.util.HashMap;
import java.util.Map;

import CardGameData.*;
import ocsf.server.ConnectionToClient;

import javax.swing.*;

public class GameServerControl 
{
	//driver for server-side game control
	private HashMap<Integer, GameData> playerData;
	private GameData gameData;
	private ConnectionToClient client;
	
	public GameServerControl(ConnectionToClient client)
	{
		this.client = client;
	}
	
	public HashMap<Integer, GameData> getMap()
	{
		return playerData;
	}
	
	public void processGameData()
	{
		
	}
	
	public GameData updateGameData(String username, int score, int pot, int currentBet, boolean start)
	{
		gameData = new GameData();
		return gameData;
	}
	
	public void startHand()
	{
		
	}
	
	public GameData startGame(String username)
	{
		gameData = new GameData();
		gameData.setScore(100);
		gameData.setUsername(username);
		gameData.setStart(true);
		//playerData.put((int)client.getId(), gameData);
		return gameData;
	}
	
	
	public GameData joinGame(String username)
	{
		gameData = new GameData();
		gameData.setScore(100);
		gameData.setUsername(username);
		gameData.setStart(false);
		return gameData;
	}
	
	public void setPosition()
	{
		
	}
	
	public void dealCard()
	{
		
	}
	
	public void takeSB()
	{
		
	}
	
	public void takeBB()
	{
		
	}
	
	public int takeBet(int bet)
	{
		return bet;
	}
	
	public GameData updatePot()
	{
		gameData = new GameData();
		gameData.setPot(1500);
		return gameData;
	}
	
	public GameData updateCurrentBet(int currentBet)
	{
		gameData = new GameData();
		gameData.setCurrentBet(currentBet);
		return gameData;
	}
	
	public void updateTurn()
	{
		
	}
	
	public void updateScore()
	{
		
	}
	
	
}
