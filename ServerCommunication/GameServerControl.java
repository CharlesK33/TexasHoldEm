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
    private int dealerIndex;
    private GameData gameData;
    private GamePhase phase;
    private boolean gameStarted = false;

    public enum GamePhase {
        PRE_FLOP, FLOP, TURN, RIVER, SHOWDOWN
    }

    // Constructor: initialize with client connection
    public GameServerControl() {
        players = new ArrayList<>();
        hands = new HashMap<>();
        deck = new Deck();
        pot = 0;
        currentBet = 0;
        currentPlayerIndex = 0;
        roundCount = 0;
        phase = GamePhase.PRE_FLOP;
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
        phase = GamePhase.FLOP;
        return flop;
    }

    //Deals the turn
    
    public Card dealTurn() {
    	Card turn = deck.drawCard();
  
        phase = GamePhase.TURN;
        return turn;
    }
    

    //Deals the river
    public Card dealRiver() {
        Card river = deck.drawCard();
        phase = GamePhase.RIVER;
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

    /*
    //Progresses to the next game phase
    public void advancePhase() {
        switch (phase) {
            case PRE_FLOP -> dealFlop();
            case FLOP -> dealTurn();
            case TURN -> dealRiver();
            case RIVER -> {
                phase = GamePhase.SHOWDOWN;
                evaluateHands();
            }
        }
    }
    */

    //Placeholder for hand evaluation logic
    public void evaluateHands() {
        System.out.println("Evaluating hands...");
    }

    public GameData updateCurrentBet(int currentBet)
	{
		gameData = new GameData();
		gameData.setCurrentBet(currentBet);
		return gameData;
	}

    public void handleCall(String username) {
        pot += currentBet;
        System.out.println(username + " called " + currentBet);
        nextTurn();
    }

    public void handleCheck(String username) {
        System.out.println(username + " checked.");
        nextTurn();
    }

    public void handleFold(String username) {
        System.out.println(username + " folded.");
        players.remove(username);
        nextTurn();
    }

    public void nextTurn() {
        if (players.size() == 1) {
            System.out.println(players.get(0) + " wins by default!");
            return;
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        System.out.println("Next turn: " + players.get(currentPlayerIndex));
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
    
    public void setDealerIndex(int index)
    {
    	dealerIndex = index;
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
    
    public void startFullGame() {
        dealHoleCards();
        dealFlop();
        setGameStarted(true);
    }

    
    public int getCurrentBet() {
        return currentBet;
    }

    public int getPot() {
        return pot;
    }
    
    public boolean hasGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean started) {
        this.gameStarted = started;
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
