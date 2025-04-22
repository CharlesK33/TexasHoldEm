package ServerCommunication;

import java.util.*;
import CardGameData.*;
import ocsf.server.ConnectionToClient;

public class GameServerControl {
    
    //Connection reference for sending data back to the client
    private ConnectionToClient client;

    //Game state
    private ArrayList<String> players;
    private Map<String, Hand> hands;
    private Deck deck;
    private int pot;
    private int currentBet;
    private int currentPlayerIndex;
    private int roundCount;
    private GameData gameData;

    // Constructor: initialize with client connection
    public GameServerControl() {
        players = new ArrayList<>();
        hands = new HashMap<>();
        deck = new Deck();
        pot = 0;
        currentBet = 0;
        currentPlayerIndex = 0;
        roundCount = 0;
    }
    
    public PokerHand determineWinner(ArrayList<PokerHand> hands)
    {
    	PokerHand winner = hands.get(0);
    	
    	for (int i = 0; i < hands.size(); i++)
    	{
    		if (hands.get(i).getHandType().getStrength() > winner.getHandType().getStrength())
    		{
    			winner = hands.get(i);
    		}
    		else if (hands.get(i).getHandType().getStrength() == winner.getHandType().getStrength())
    		{
    			List<Integer> values = hands.get(i).getTiebreakers();
    			for (int ii = 0; ii < hands.get(i).getTiebreakers().size(); ii++)
    			{
    				if (values.get(ii) > winner.getTiebreakers().get(ii))
    				{
    					winner = hands.get(i);
    				}
    			}
    		}
    	}
    	
    	return winner;
    }

    // Adds a new player joining an existing game
    public GameData joinGame(String username) {
        GameData gameData = new GameData();
        gameData.setScore(100);
        gameData.setUsername(username);
        gameData.setStart(false);
        return gameData;
    }

    // Deals 2 cards to each player
    public Hand dealHoleCards() 
    {
        Hand hand = new Hand();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(deck.drawCard());
        cards.add(deck.drawCard());
        hand.setHand(cards);
         
        return hand;
    }

    //Deals the flop (3 community cards)
    public ArrayList<Card> dealFlop() {
    	ArrayList<Card> flop = new ArrayList<>();
        flop.add(deck.drawCard());
        flop.add(deck.drawCard());
        flop.add(deck.drawCard());
   
        return flop;
    }

    //Deals the turn
    
    public Card dealTurn() {
    	Card turn = deck.drawCard();
  
        return turn;
    }
    

    //Deals the river
    public Card dealRiver() {
        Card river = deck.drawCard();
       
        return river;
    }
    
    
    
    public int takeSmallBlind(int score)
    {
    	return score - 5;
    }
    
    public int takeBigBlind(int score)
    {
    	return score - 10;
    }

    
    public ArrayList<Card> createPokerHand(ArrayList<Card> cards, Hand hand)
    {
    	cards.add(hand.getHand().getFirst());
    	cards.add(hand.getHand().getLast());
    	
    	return cards;
    }
    
    public PokerHand evaluateBestHand(ArrayList<Card> sevenCards) {
    	int size = sevenCards.size();
        ArrayList<ArrayList<Card>> allFiveCardCombos = getFiveCardCombos(sevenCards);
        PokerHand bestHand = null;

        for (ArrayList<Card> combo : allFiveCardCombos) {
            PokerHand hand = evaluateFiveCardHand(combo);
            if (bestHand == null || hand.compareTo(bestHand) > 0) {
                bestHand = hand;
            }
        }

        return bestHand;
    }
    
    public ArrayList<ArrayList<Card>> getFiveCardCombos(ArrayList<Card> cards) {
        ArrayList<ArrayList<Card>> combos = new ArrayList<>();
        combine(cards, 0, new ArrayList<>(), combos);
        return combos;
    }
    
    private PokerHand evaluateFiveCardHand(ArrayList<Card> hand) {
    	if (hand.size() != 5) {
            throw new IllegalArgumentException("Hand must contain exactly 5 cards.");
        }

        // Sort cards by rank in descending order
        hand.sort((a, b) -> Integer.compare(b.getValue().getNumValue(), a.getValue().getNumValue()));

        boolean isFlush = isFlush(hand);
        boolean isStraight = isStraight(hand);
        Map<Integer, Integer> rankCount = getRankCount(hand);

        List<Integer> ranksDescending = new ArrayList<>();
        for (Card card : hand) {
            ranksDescending.add(card.getValue().getNumValue());
        }

        HandType type;
        List<Integer> tiebreakers;

        if (isFlush && isStraight) {
            type = ranksDescending.get(0) == 14 ? HandType.ROYAL_FLUSH : HandType.STRAIGHT_FLUSH;
            tiebreakers = List.of(ranksDescending.get(0));
        } else if (rankCount.containsValue(4)) {
            type = HandType.FOUR_OF_A_KIND;
            int four = getKeyWithValue(rankCount, 4);
            int kicker = getHighestExcept(rankCount, four);
            tiebreakers = List.of(four, kicker);
        } else if (rankCount.containsValue(3) && rankCount.containsValue(2)) {
            type = HandType.FULL_HOUSE;
            int three = getKeyWithValue(rankCount, 3);
            int two = getKeyWithValue(rankCount, 2);
            tiebreakers = List.of(three, two);
        } else if (isFlush) {
            type = HandType.FLUSH;
            tiebreakers = new ArrayList<>(ranksDescending);
        } else if (isStraight) {
            type = HandType.STRAIGHT;
            tiebreakers = List.of(ranksDescending.get(0));
        } else if (rankCount.containsValue(3)) {
            type = HandType.THREE_OF_A_KIND;
            int three = getKeyWithValue(rankCount, 3);
            List<Integer> kickers = getHighestExcluding(rankCount, List.of(three), 2);
            tiebreakers = new ArrayList<>();
            tiebreakers.add(three);
            tiebreakers.addAll(kickers);
        } else if (Collections.frequency(rankCount.values(), 2) == 2) {
            type = HandType.TWO_PAIR;
            List<Integer> pairs = getKeysWithValue(rankCount, 2);
            Collections.sort(pairs, Collections.reverseOrder());
            int kicker = getHighestExcluding(rankCount, pairs, 1).get(0);
            tiebreakers = new ArrayList<>(pairs);
            tiebreakers.add(kicker);
        } else if (rankCount.containsValue(2)) {
            type = HandType.ONE_PAIR;
            int pair = getKeyWithValue(rankCount, 2);
            List<Integer> kickers = getHighestExcluding(rankCount, List.of(pair), 3);
            tiebreakers = new ArrayList<>();
            tiebreakers.add(pair);
            tiebreakers.addAll(kickers);
        } else {
            type = HandType.HIGH_CARD;
            tiebreakers = new ArrayList<>(ranksDescending);
        }

        return new PokerHand(hand, type, tiebreakers);
    }
    
    private boolean isFlush(List<Card> hand) {
        String suit = hand.get(0).getSuit().toString();
        for (Card c : hand) {
            if (c.getSuit().toString() != suit) return false;
        }
        return true;
    }

    private boolean isStraight(List<Card> hand) {
        List<Integer> ranks = new ArrayList<>();
        for (Card c : hand) ranks.add(c.getValue().getNumValue());
        Set<Integer> set = new HashSet<>(ranks);

        // Handle Ace-low straight
        if (set.containsAll(List.of(14, 2, 3, 4, 5))) return true;

        Collections.sort(ranks);
        for (int i = 0; i < 4; i++) {
            if (ranks.get(i + 1) != ranks.get(i) + 1) return false;
        }
        return true;
    }

    private Map<Integer, Integer> getRankCount(List<Card> hand) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Card c : hand) {
            map.put(c.getValue().getNumValue(), map.getOrDefault(c.getValue().getNumValue(), 0) + 1);
        }
        return map;
    }

    private int getKeyWithValue(Map<Integer, Integer> map, int target) {
        return map.entrySet().stream()
            .filter(e -> e.getValue() == target)
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow();
    }

    private List<Integer> getKeysWithValue(Map<Integer, Integer> map, int target) {
        List<Integer> keys = new ArrayList<>();
        for (var entry : map.entrySet()) {
            if (entry.getValue() == target) keys.add(entry.getKey());
        }
        return keys;
    }

    private int getHighestExcept(Map<Integer, Integer> map, int exclude) {
        return map.keySet().stream()
            .filter(k -> k != exclude)
            .max(Integer::compareTo)
            .orElse(0);
    }

    private List<Integer> getHighestExcluding(Map<Integer, Integer> map, List<Integer> excludes, int count) {
        return map.keySet().stream()
            .filter(k -> !excludes.contains(k))
            .sorted(Comparator.reverseOrder())
            .limit(count)
            .toList();
    }
    
    
    public void combine(ArrayList<Card> cards, int start, List<Card> temp, List<ArrayList<Card>> result) {
        if (temp.size() == 5) {
            result.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i < cards.size(); i++) {
            temp.add(cards.get(i));
            combine(cards, i + 1, temp, result);
            temp.remove(temp.size() - 1);
        }
    }

    public GameData updateCurrentBet(int currentBet)
	{
		gameData = new GameData();
		gameData.setCurrentBet(currentBet);
		return gameData;
	}

    public String getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    public int getCurrentPlayerIndex() {
    	return currentPlayerIndex;
    }
    
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
    	this.currentPlayerIndex = currentPlayerIndex;
    }

    /*
    public GameData getGameDataForPlayer(String username) {
        GameData data = new GameData();
        data.setBoard(new ArrayList<>(board));
        data.setHand(hands.get(username));
        data.setUsername(username);
        data.setCurrentBet(currentBet);
        data.setScore(pot);
        data.setPlayers(new ArrayList<>(players));
        return data;
    }
    */
    
    
    public GameData updatePot() {
        GameData data = new GameData();
        data.setPot(pot);              //current pot size
        data.setCurrentBet(currentBet); 
        return data;
    }
    
    
    public void addPlayer(String username) {
        if (!players.contains(username)) {
            players.add(username);
            System.out.println("✅ Added player to list: " + username);
        }
        System.out.println("👥 Current player list: " + players);
    }
    
    public GameData startGame(String username) {
        // Optionally initialize or reset anything per-player here
        GameData data = new GameData();
        data.setUsername(username);
        data.setCurrentBet(currentBet);
        data.setScore(pot);
        data.setStart(true); // used to trigger GamePanel switch
        return data;
    }
    
    public int getCurrentBet() {
        return currentBet;
    }

    public int getPot() {
        return pot;
    }
    
    public ArrayList<String> getPlayers() 
    {
        return players;
    }
    
    public void removePlayer(String username) {
        players.remove(username);
    }

	public int getRoundCount() {
		return roundCount;
	}

	public void setRoundCount(int roundCount) {
		this.roundCount = roundCount;
	}






}
