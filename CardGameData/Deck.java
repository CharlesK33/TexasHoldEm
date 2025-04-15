package CardGameData;

import java.util.Random;

public class Deck {
    private Card[] cards = new Card[52];
    private int drawIndex = 0;
    private Random random = new Random();

    public Deck() {
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        int index = 0;
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Value value : Card.Value.values()) {
                if (index < cards.length) {
                    cards[index++] = new Card(suit, value);
                }
            }
        }
    }

    public void shuffle() {
        for (int i = cards.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
        drawIndex = 0;
    }

    public Card drawCard() {
        if (drawIndex < cards.length) {
            return cards[drawIndex++];
        }
        return null;
    }

    public int cardsRemaining() {
        return cards.length - drawIndex;
    }

   
    }

