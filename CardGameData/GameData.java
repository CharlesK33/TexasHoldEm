package CardGameData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameData implements Serializable{
private String username;
private int score;
private int pot;
private int currentBet;
private int dealerIndex;
private int playerTurn;
private int betAmount;
private boolean start;
private boolean isFolding;
private boolean endOfHand;
private int turnCount;
private Hand hand;
private ArrayList<Card> flop;
private Card turn;
private Card river;
private boolean inGame;
private List<String> players;

public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public int getScore() {
	return score;
}
public void setScore(int score) {
	this.score = score;
}
public int getPot() {
	return pot;
}
public void setPot(int pot) {
	this.pot = pot;
}
public int getCurrentBet() {
	return currentBet;
}
public void setCurrentBet(int currentBet) {
	this.currentBet = currentBet;
}

public void setStart(boolean start)
{
	this.start = start;
}

public boolean getStart()
{
	return start;
}


public void setHand(Hand hand) 
{
	this.hand = hand; 
	
}
public Hand getHand() 
{ 
	return hand; 
}

public void setDealer(int dealerIndex)
{
	this.dealerIndex = dealerIndex;
}

public int getDealer()
{
	return dealerIndex;
}

public boolean isDealer(int dealerIndex)
{
	if(dealerIndex == this.dealerIndex)
		return true;
	else 
		return false;
}

public void setFlop(ArrayList<Card> flop) 
{
    this.flop = flop;
}

public ArrayList<Card> getFlop() 
{
    return flop;
}

public boolean isInGame() 
{ 
	return inGame; 
}

public void setInGame(boolean inGame) 
{ 
	this.inGame = inGame; 
}

public List<String> getPlayers() {
    return players;
}

public void setPlayers(List<String> players) {
    this.players = players;
}

public int getPlayerTurn() {
	return playerTurn;
}
public void setPlayerTurn(int playerTurn) {
	this.playerTurn = playerTurn;
}
public boolean isFolding() {
	return isFolding;
}
public void setFolding(boolean isFolding) {
	this.isFolding = isFolding;
}
public int getTurnCount() {
	return turnCount;
}
public void setTurnCount(int turnCount) {
	this.turnCount = turnCount;
}
public Card getTurn() {
	return turn;
}
public void setTurn(Card turn) {
	this.turn = turn;
}
public Card getRiver() {
	return river;
}
public void setRiver(Card river) {
	this.river = river;
}
public int getBetAmount() {
	return betAmount;
}
public void setBetAmount(int betAmount) {
	this.betAmount = betAmount;
}
public boolean isEndOfHand() {
	return endOfHand;
}
public void setEndOfHand(boolean endOfHand) {
	this.endOfHand = endOfHand;
}

}
