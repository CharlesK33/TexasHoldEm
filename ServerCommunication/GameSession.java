package ServerCommunication;

import java.util.*;
import CardGameData.*;

public class GameSession 
{
    private List<String> players;           // usernames
    private Map<String, Hand> hands;        // player → hand
    private Deck deck;
    private List<Card> board;               // flop, turn, river
    private int pot;
    private int currentBet;
    private int currentPlayerIndex;
    private GamePhase phase;

    
    //Enum showing the different stages of the poker game
    public enum GamePhase 
    {
        PRE_FLOP, FLOP, TURN, RIVER, SHOWDOWN
    }

    public GameSession(List<String> players) 
    {
        this.players = players;
        this.hands = new HashMap<>();
        this.deck = new Deck();
        this.board = new ArrayList<>();
        this.pot = 0;
        this.currentBet = 0;
        this.currentPlayerIndex = 0;
        this.phase = GamePhase.PRE_FLOP;

        dealHoleCards();
    }

    
    
 //Deals 2 cards to each player at game start
    public void dealHoleCards() 
    {
        for (String player : players) 
        {
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
    public void dealFlop() 
    {
        board.add(deck.drawCard());
        board.add(deck.drawCard());
        board.add(deck.drawCard());
        phase = GamePhase.FLOP;
        System.out.println("Flop dealt.");
    }

    
    //Deals the turn (4th community card)
    public void dealTurn() 
    {
        board.add(deck.drawCard());
        phase = GamePhase.TURN;
        System.out.println("Turn dealt.");
    }

    
    //Deals the river (5th community card)
    public void dealRiver() 
    {
        board.add(deck.drawCard());
        phase = GamePhase.RIVER;
        System.out.println("River dealt.");
    }

    
    //Determines the next game phase and moves to it
    public void advancePhase() 
    {
        switch (phase) 
        {
            case PRE_FLOP -> dealFlop();
            case FLOP -> dealTurn();
            case TURN -> dealRiver();
            case RIVER -> {
                phase = GamePhase.SHOWDOWN;
                evaluateHands();
            }
        }
    }

    
    
    //Evaluates hands and determines the winner (to be implemented)
    public void evaluateHands() 
    {
        // TODO: Evaluate player hands and determine winner
        System.out.println("Evaluating hands...");
    }

    
    //Handles when a player bets a specific amount
    public void handleBet(String username, int amount) 
    {
        currentBet = amount;
        pot += amount;
        System.out.println(username + " bet " + amount);
        nextTurn();
    }

    
    //Handles when a player calls the current bet
    public void handleCall(String username) 
    {
        pot += currentBet;
        System.out.println(username + " called " + currentBet);
        nextTurn();
    }

    
    //Handles when a player checks
    public void handleCheck(String username) 
    {
        System.out.println(username + " checked.");
        nextTurn();
    }

    //Handles when a player folds and removes them from the round
    public void handleFold(String username) 
    {
        System.out.println(username + " folded.");
        players.remove(username);
        nextTurn();
    }

    //Moves to the next player's turn
    public void nextTurn() 
    {
        if (players.size() == 1) 
        {
            System.out.println(players.get(0) + " wins by default!");
            return;
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        System.out.println("Next turn: " + players.get(currentPlayerIndex));
    }

    //Returns the current player
    public String getCurrentPlayer() 
    {
        return players.get(currentPlayerIndex);
    }

    
    //Returns a snap of the game state to be sent to the client
    public GameData getGameDataForPlayer(String username) 
    {
        GameData data = new GameData();
        data.setUsername(username);
        data.setCurrentBet(currentBet);
        data.setScore(pot); 
        return data;
    }
}
