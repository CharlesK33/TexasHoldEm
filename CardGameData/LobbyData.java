package CardGameData;

import java.io.Serializable;
import java.util.List;

public class LobbyData implements Serializable 
{
    private List<String> players;

    public LobbyData(List<String> players) 
    {
        this.players = players;
    }

    public List<String> getPlayers() 
    {
        return players;
    }
}
