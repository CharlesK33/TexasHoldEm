package CardGameData;

import java.io.Serializable;

public class BetData implements Serializable{
public int betAmount;
public void setBetAmount(int betAmount)
{
	this.betAmount = betAmount;
}
public int getBetAmount()
{
	return betAmount;
}

public BetData(int bet)
{
	setBetAmount(bet);
}

}
