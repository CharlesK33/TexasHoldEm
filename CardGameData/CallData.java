package CardGameData;

import java.io.Serializable;

public class CallData implements Serializable{

	int currentBetAmount;
	public void setCurrentBet(int currentBetAmount) {
		this.currentBetAmount = currentBetAmount;
	}
	public int getCurrentBet() {
		return currentBetAmount;
	}
	
	public CallData(int bet)
	{
		setCurrentBet(bet);
	}
}
