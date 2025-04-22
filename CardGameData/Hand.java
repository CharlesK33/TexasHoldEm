package CardGameData;

import java.io.Serializable;
import java.util.ArrayList;

public class Hand implements Serializable {
    private ArrayList<Card> cards = new ArrayList<>();
    private HandRank rank;
    
    public Hand(ArrayList<Card> cards, HandRank rank)
    {
    	this.cards = cards;
    	this.rank = rank;
    }
    
    public Hand()
    {
    	
    }
    
    enum HandRank 
    {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH }
    

    public void setHand(ArrayList<Card> hand) {
        this.cards = hand;
    }

    public ArrayList<Card> getHand() {
        return cards;
    }
    
    public int size()
    {
    	return cards.size();
    }
}
