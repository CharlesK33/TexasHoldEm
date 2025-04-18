package CardGameData;

import java.io.Serializable;
import java.util.ArrayList;

public class GameData implements Serializable{
private String username;
private int score;
private int pot;
private int currentBet;
private boolean start;
private Hand hand;
private ArrayList<Card> board;

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


public void setBoard(ArrayList<Card> board) 
{
    this.board = board;
}

public ArrayList<Card> getBoard() 
{
    return board;
}

}
