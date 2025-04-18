package CardGameData;


import java.io.Serializable;

public class Card implements Serializable {
	
	private static final long serialVersionUID = 1L;



    public enum Suit {
        CLUBS("clubs"),
        DIAMONDS("diamonds"),
        HEARTS("hearts"),
        SPADES("spades");

        private final String cardValue;

        Suit(String cardValue) {
            this.cardValue = cardValue;
        }

        public String getCardValue() {
            return cardValue;
        }
    }

    public enum Value {
        ACE("ace", 11), TWO("2", 2), THREE("3", 3), FOUR("4", 4),
        FIVE("5", 5), SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8),
        NINE("9", 9), TEN("10", 10), JACK("jack", 10),
        QUEEN("queen", 10), KING("king", 10);

        private final String value;
        private final int numValue;

        Value(String value, int numValue) {
            this.value = value;
            this.numValue = numValue;
        }

        public String getValue() {
            return value;
        }

        public int getNumValue() {
            return numValue;
        }
    }

    private Suit suit;
    private Value value;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.getValue() + " of " + suit.getCardValue();
    }
    
    public String getFileName() {
        return value.getValue() + "_of_" + suit.getCardValue() + ".png";
    }

}
