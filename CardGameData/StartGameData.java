package CardGameData;

public class StartGameData 
{
	private String username;
	private boolean start;
	
	public StartGameData(String username, boolean start)
	{
		this.username = username;
		this.start = start;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public boolean getStart()
	{
		return start;
	}
}
