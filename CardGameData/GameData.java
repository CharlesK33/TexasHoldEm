package CardGameData;

import java.io.Serializable;

public class GameData implements Serializable{
private String username;
private int score;
private int pot;
private int currentBet;
private boolean start;
private Hand hand;

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


}
