package CardGameData;
import java.util.Random;
public class Deck 
{
	private Random random;
	private int index;
	private Card card;
	
	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public void shuffle() {
		random = new Random();
		String[] suitOptions= {"Diamonds", "Hearts", "Clubs", "Spades"};
		index = random.nextInt(suitOptions.length);
		card.suit = suitOptions[index];
		card.setSuit(card.suit);
	}

}
