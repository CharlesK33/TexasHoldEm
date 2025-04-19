package ServerCommunication;

import java.util.*;
import CardGameData.*;
import ocsf.server.ConnectionToClient;

public class GameServerControl {
    
    //Connection reference for sending data back to the client
    private ConnectionToClient client;

    //Game state
    private List<String> players;
    private Map<String, Hand> hands;
    private Deck deck;
    private List<Card> board;
    private int pot;
    private int currentBet;
    private int currentPlayerIndex;
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
        board = new ArrayList<>();
        pot = 0;
        currentBet = 0;
        currentPlayerIndex = 0;
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
    public void dealHoleCards() {
        for (String player : players) {
            Hand hand = new Hand();
            ArrayList<Card> cards = new ArrayList<>();
            cards.add(deck.drawCard());
            cards.add(deck.drawCard());
            hand.setHand(cards);
            hands.put(player, hand);
        }
        System.out.println("Dealt hole cards to all players.");
    }

    //Deals the flop (3 community cards)
    public void dealFlop() {
        board.add(deck.drawCard());
        board.add(deck.drawCard());
        board.add(deck.drawCard());
        phase = GamePhase.FLOP;
        System.out.println("Flop dealt.");
    }

    //Deals the turn
    public void dealTurn() {
        board.add(deck.drawCard());
        phase = GamePhase.TURN;
        System.out.println("Turn dealt.");
    }

    //Deals the river
    public void dealRiver() {
        board.add(deck.drawCard());
        phase = GamePhase.RIVER;
        System.out.println("River dealt.");
    }

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

    //Placeholder for hand evaluation logic
    public void evaluateHands() {
        System.out.println("Evaluating hands...");
    }

    public void handleBet(String username, int amount) {
        currentBet = amount;
        pot += amount;
        System.out.println(username + " bet " + amount);
        nextTurn();
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
    
    
    public GameData updatePot() {
        GameData data = new GameData();
        data.setPot(pot);              //current pot size
        data.setCurrentBet(currentBet); 
        return data;
    }
    
    
    public void addPlayer(String username) {
        if (!players.contains(username)) {
            players.add(username);
        }
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
    
    public List<String> getPlayers() 
    {
        return players;
    }





}
