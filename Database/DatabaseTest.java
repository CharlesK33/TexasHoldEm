package Database;

public class DatabaseTest {

	 public static void main(String[] args) 
	 {
	        try 
	        {
	            Database db = new Database(); 

	            System.out.println("Creating new account...");
	            boolean created = db.createNewAccount("sofiane", "12345");
	            System.out.println("Account created: " + created);

	            System.out.println("Verifying account");
	            boolean verified = db.verifyAccount("sofiane", "12345");
	            System.out.println("Account verified: " + verified);

	            System.out.println("Updating score");
	            boolean updated = db.updateScore("sofiane", 2000);
	            System.out.println("Score updated: " + updated);

	            System.out.println("Fetching score");
	            int score = db.getScore("sofiane");
	            System.out.println("Sofiane's score: " + score);

	            db.close(); 
	            System.out.println("Connection closed");

	        } 
	        
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    }
	
}
