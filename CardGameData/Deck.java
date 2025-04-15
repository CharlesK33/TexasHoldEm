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
	public static String getRandomString(String[] options) {
        Random random = new Random();
        int index = random.nextInt(options.length);
        return options[index];
    }

    public static void main(String[] args) {
        String[] stringOptions = {"Option 1", "Option 2", "Option 3", "Option 4"};
        String randomString = getRandomString(stringOptions);
        System.out.println("Randomly selected string: " + randomString);
    }

	public void shuffle() {
		random = new Random();
		String[] suitOptions= {"Diamonds", "Hearts", "Clubs", "Spades"};
		index = random.nextInt(suitOptions.length);
		card.suit = suitOptions[index];
		card.setSuit(card.suit);
	}

}
