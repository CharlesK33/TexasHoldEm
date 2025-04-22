package CardGameData;

import java.io.Serializable;

public class BetData implements Serializable
{
	private int betAmount;
	private String username;
	
	public BetData(int bet, String username)
	{
		setBetAmount(bet);
		setUsername(username);
	}
	
	public void setBetAmount(int betAmount)
	{
		this.betAmount = betAmount;
	}
	
	public int getBetAmount()
	{
		return betAmount;
	}

	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username) 
	{
		this.username = username;
	}

}
