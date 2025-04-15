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

        try (FileInputStream fis = new FileInputStream("db.properties")) 
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
		try (PreparedStatement insertStmt = con.prepareStatement("INSERT INTO users (username, password, score) VALUES (?, AES_ENCRYPT(?, 'secretkey'), 0)"))
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
		
		
		
		
		
	
	
				
	
	
}
