package CardGameData;

import java.util.*;

public class PokerHand implements Comparable<PokerHand> {
    private List<Card> cards;      // The best 5-card hand
    private HandType handType;     // FLUSH, STRAIGHT, etc.
    private List<Integer> tiebreakers; // Used to compare hands of the same type

    public PokerHand(List<Card> cards, HandType handType, List<Integer> tiebreakers) {
        this.cards = cards;
        this.handType = handType;
        this.tiebreakers = tiebreakers;
    }

    public List<Card> getCards() {
        return cards;
    }

    public HandType getHandType() {
        return handType;
    }

    public List<Integer> getTiebreakers() {
        return tiebreakers;
    }

    @Override
    public int compareTo(PokerHand other) {
        // Compare hand type first (higher ordinal = better hand)
        int typeComparison = this.handType.compareTo(other.handType);
        if (typeComparison != 0) {
            return -typeComparison; // higher enum ordinal = stronger hand
        }

        // If same hand type, use tiebreakers (sorted descending)
        for (int i = 0; i < Math.min(this.tiebreakers.size(), other.tiebreakers.size()); i++) {
            int diff = this.tiebreakers.get(i) - other.tiebreakers.get(i);
            if (diff != 0) {
                return -diff; // higher value wins
            }
        }

        return 0; // completely tied
    }

    @Override
    public String toString() {
        return handType + " " + cards.toString();
    }
}
