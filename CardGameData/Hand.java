package CardGameData;

import java.io.Serializable;
import java.util.ArrayList;

public class Hand implements Serializable {
    private ArrayList<Card> cards = new ArrayList<>();

    public void setHand(ArrayList<Card> hand) {
        this.cards = hand;
    }

    public ArrayList<Card> getHand() {
        return cards;
    }
}
