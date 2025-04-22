package CardGameData;

import java.io.Serializable;

public class CallData implements Serializable{

	private int currentBetAmount;
	private String username;
	
	public CallData(int bet, String username)
	{
		setCurrentBet(bet);
		setUsername(username);
	}
	
	public void setCurrentBet(int currentBetAmount) {
		this.currentBetAmount = currentBetAmount;
	}
	public int getCurrentBet() {
		return currentBetAmount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
