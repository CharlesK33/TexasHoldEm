package Database;


import java.sql.*;
import java.util.*;
import java.io.*;


public class Database {
 //database class
	

	private Connection con;
	
	
	//Constructor
	public Database() {
        Properties prop = new Properties();

        try (FileInputStream fis = new FileInputStream("Database/db.properties")) 
        {
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String pass = prop.getProperty("password");

            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to database");
        } 
        
        catch (IOException | SQLException e) 
        {
            e.printStackTrace();
        }
    }
	
	
	

	 public ArrayList<String> query(String query) 
	 {
	        ArrayList<String> list = new ArrayList<>();
	        
	        
	        try (Statement stmt = con.createStatement();
	             ResultSet rs = stmt.executeQuery(query)) 
	        {

	            ResultSetMetaData rsmd = rs.getMetaData();
	            int noFields = rsmd.getColumnCount();

	            while (rs.next()) 
	            {
	                StringBuilder record = new StringBuilder();
	                
	                for (int i = 1; i <= noFields; i++) 
	                {
	                    record.append(rs.getString(i)).append(",");
	                }
	                list.add(record.toString());
	            }

	            return list.isEmpty() ? null : list;
	        } 
	        
	        
	        catch (SQLException e) 
	        {
	            e.printStackTrace();
	            return null;
	        }
	    }
	 
	 
	 
	 //Execute (INSERT, UPDATE, DELETE)
	 public void executeDML(String dml) throws SQLException {
	        try (Statement stmt = con.createStatement()) {
	            stmt.execute(dml);
	        }
	    }
	 
	 
	 
	 //Verify account
	 public boolean verifyAccount(String username, String password)
	 {
		 try (PreparedStatement stmt = con.prepareStatement("SELECT AES_DECRYPT(password, 'secretkey') AS decrypted_password FROM users WHERE username = ?"))
		 {
			 stmt.setString(1, username);
			 ResultSet rs = stmt.executeQuery();
			 
			 
			 if (rs.next())
			 {
				 String decryptedPass = rs.getString("decrypted_password").trim();
				 return decryptedPass.equals(password);
			 }
			 
			 
		 }
		 
		 catch (SQLException e) 
		 {
			 e.printStackTrace();
		 }
		 
		 return false;
	 }
	 
	 
	 
	 //Create a new user
	public boolean createNewAccount(String username, String password) 
	{
		//Check if user exists already
		
		try (PreparedStatement checkStmt = con.prepareStatement("SELECT username FROM users WHERE username = ?"))
		{
			checkStmt.setString(1, username);
			ResultSet rs = checkStmt.executeQuery();
			
			if (rs.next()) 
			{
				return false; //User already exists
			}
			
			
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
		
		//Insert new user
		try (PreparedStatement insertStmt = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, AES_ENCRYPT(?, 'secretkey'))"))
		{
			
			insertStmt.setString(1,username);
			insertStmt.setString(2, password);
			insertStmt.executeUpdate();
			
			return true;
			
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	//Update user score
	public boolean updateScore(String username, int score)
	{
		try (PreparedStatement stmt = con.prepareStatement("UPDATE users SET SCORE = ? WHERE username = ?"))
		{
			stmt.setInt(1, score);
			stmt.setString(2, username);
			return stmt.executeUpdate() > 0;
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	//Get score for a user
	public int getScore(String username)
	{
		try(PreparedStatement stmt = con.prepareStatement("SELECT score FROM users WHERE username = ?"))
		{
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next())
			{
				return rs.getInt("score");
			}
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return -1; //error
	}
	
	
	//Shuts down DB connection
	public void close()
	{
		try 
		{
			if (con != null && !con.isClosed())
				con.close();
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
		
		

/*	
	private Connection conn;
	//Add any other data fields you like – at least a Connection object is mandatory
	public Database()
	{
		FileInputStream fis = null;
	    //1. create a properties object
		Properties prop = new Properties();
		
		//2. Open the db.properties with FileInputStream
		try 
		{
			fis = new FileInputStream("Database/db.properties");
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Parse the properties file (key/value)
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Get the url, the user, the password
		String url = prop.getProperty("url");
		String user = prop.getProperty("user");
		String pass = prop.getProperty("password");
		
		try
		{
			conn = DriverManager.getConnection(url, user, pass);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	  
	public ArrayList<String> query(String query)
	{
		//Declare the return value first
		ArrayList<String> list = new ArrayList<>();
		String record = "";
		
		//Process the query
		try
		{
			//1. Create a Statement object
			Statement stmt = conn.createStatement();
			
			//2. Execute the query on the statement
			ResultSet rs = stmt.executeQuery(query);
			
			//3. Get the Meta Data
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int noFields = rsmd.getColumnCount();
			
			//4. Iterate through each record
			while(rs.next())
			{
				record = "";
				for(int i = 0; i < noFields; i++)
				{
					record += rs.getString(i + 1);
					record += ",";
				}
				list.add(record);
			}
			//Check for empty result
			if (list.isEmpty())
			{
				return null;
			}
		}
		catch(SQLException e)
		{
			return null;
		}
		
		return list;
	}
	  
	public void executeDML(String dml) throws SQLException
	{
	    //Create a statement form the connection
		Statement stmt = conn.createStatement();
		
		//Execute the DML
		stmt.execute(dml);
	}
	
	// Method for verifying a username and password.
	public boolean verifyAccount(String username, String password)
	{
		ArrayList<String> users = query("select username from user");
		
		// Stop if this account doesn't exist.
		boolean exists = false;
		
		for (String user : users)
		{
			String trimmed = user.substring(0, user.length()-1).trim();
			
			if (trimmed.equals(username))
			{
				exists = true;
				break;
			}
		}
		
		if (exists == false)
		{
			return false;
		}
		
		ArrayList<String> result = query("select aes_decrypt(password, 'secretkey') AS decrypted_password from user where username='" + username + "'");
		String pass = result.get(0);
		pass = pass.substring(0, pass.length()-1);
		System.out.println(pass + " : " + password);
	    // Check the username and password.
		if (pass.equals(password))
		{
	    	return true;
		}
	    else
	    	return false;
	 }
	  
	 // Method for creating a new account.
	 public boolean createNewAccount(String username, String password)
	 {
	    ArrayList<String> users = query("select username from users");
	    
	    // Stop if this account already exists.
	    boolean exists = users.contains(username);
	    
	    for (String user : users)
		{
			String trimmed = user.substring(0, user.length()-1).trim();
			
			if (trimmed.equals(username))
			{
				exists = true;
				break;
			}
		}
	    
	    if (exists == true)
		{
			return false;
		}
	    
	    else
	    {
	    	try {
				executeDML("insert into users (username, password) values('" + username + "', aes_encrypt('" + password + "','secretkey'))");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return true;
	    }
	    
	 }
		
*/		
	
	
				
	
	
}
