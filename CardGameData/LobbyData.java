package CardGameData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LobbyData implements Serializable 
{
    private ArrayList<String> players;
    private String player1;
    private String player2;
    private String player3;
    private String player4;
    private String player5;
    private boolean start;

    public LobbyData(ArrayList<String> players, boolean start) 
    {
        this.players = players;
        this.start = start;
    }
    
    public boolean getStart()
    {
    	return start;
    }

    public ArrayList<String> getPlayers() 
    {
        return players;
    }
    
    public String getPlayer1()
    {
    	return player1;
    }
    
    public void setPlayer1(String player1)
    {
    	this.player1 = player1;
    }
    
    public String getPlayer2()
    {
    	return player2;
    }
    
    public void setPlayer2(String player2)
    {
    	this.player2 = player2;
    }
    
    public String getPlayer3()
    {
    	return player3;
    }
    
    public void setPlayer3(String player3)
    {
    	this.player3 = player3;
    }
    
    public String getPlayer4()
    {
    	return player4;
    }
    
    public void setPlayer4(String player4)
    {
    	this.player4 = player4;
    }
    
    public String getPlayer5()
    {
    	return player5;
    }
    
    public void setPlayer5(String player5)
    {
    	this.player5 = player5;
    }
}
