package CardGameData;

import java.io.Serializable;
import java.util.ArrayList;

public class PokerHandData implements Serializable
{
	private ArrayList<Card> playerCards;
	private int playerIndex;
	
	public PokerHandData(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}

	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}
	public void setPlayerCards(ArrayList<Card> playerCards) {
		this.playerCards = playerCards;
	}
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
}
