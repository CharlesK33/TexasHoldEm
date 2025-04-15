package CardGameData;

public class Card {

public enum Suit{	
	 	CLUBS("clubs"),
	    DIAMONDS("diamonds"),
	    HEARTS("hearts"),
	    SPADES("spades"),
	    CARD("card");
	private final String CardValue;
	Suit(String CardValue){
		this.CardValue = CardValue;
	}
	
	
}
public enum Value{
	ACE("ace", 11), TWO("2", 2), THREE("3", 3), FOUR("4", 4),
    FIVE("5",5), SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8),
    NINE("9", 9), TEN("10", 10), JACK("jack", 10), QUEEN("queen", 10),
    KING("king", 10), BACK("back", 0);
private final String value;
private final int numValue;
	Value(String value, int numValue) {
		this.value = value;
		this.numValue = numValue;
	}
	
	
}
private String suit;
private String value;

public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public String getSuit()
{
	return suit;
	}
public void setSuit(String suit) {
	this.suit = suit;
}


}
