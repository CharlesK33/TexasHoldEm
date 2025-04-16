package ServerCommunication;

import java.util.HashMap;
import CardGameData.*;
import javax.swing.*;

public class GameServerControl 
{
	//driver for server-side game control
	private HashMap<Integer, GameData> playerData;
	private Object arg;
	private JLabel playerScoreLabel;
	private JLabel usernameLabel;
	private GameData gameData;
	
	public GameServerControl(Object arg)
	{
		this.arg = arg;
	}
	
	public void processGameData()
	{
		
	}
	
	public void updateGameData()
	{
		
	}
	
	public void startHand()
	{
		
	}
	
	public GameData startGame(String username)
	{
		playerScoreLabel = new JLabel("100");
		usernameLabel = new JLabel(username);
		gameData = new GameData();
		gameData.setScore();
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
	
	public void takeBet()
	{
		
	}
	
	public void updatePot()
	{
		
	}
	
	public void updateCurrentBet()
	{
		
	}
	
	public void updateTurn()
	{
		
	}
	
	public void updateScore()
	{
		
	}
	
	
}
