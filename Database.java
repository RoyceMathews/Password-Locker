import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	static private Connection connection;
	static private Statement statement;
	static PreparedStatement prpStmt;
	static boolean initialized = false;
	static boolean connected = false;
	static ResultSet resultSet;
	static boolean exists;


	static void initialize() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");

		initialized = true;
	}

	static void connect() throws SQLException {
		System.out.println("Connecting database...");
		
		File f = new File("passwordDB.db");
		String [] keyArray;
		String sql;
		if(!f.isFile()){// if file does not exist
			exists = false;
			try {
				f.createNewFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println("Error Creating Database File.");
			}
		}
		else{
			exists = true;
		}
		
		connection = DriverManager.getConnection("jdbc:sqlite:passwordDB.db");
		
		// Create the tables
		statement = connection.createStatement();
		if(!exists){
			sql = "CREATE TABLE IF NOT EXISTS progdata " +
					"(id INTEGER PRIMARY KEY AUTOINCREMENT 	NOT NULL," +
					" data	TEXT	NOT NULL)";
			Database.setQuery(sql);
			Database.prpStmt.execute();	
			RSA keys = new RSA();
			keyArray = RSA.saveKeysDatabase();
			// Insert created keys into database
			String query = "INSERT INTO progdata (data) VALUES (?)";
			Database.setQuery(query);
			Database.prpStmt.setString(1, keyArray[0]);
			Database.prpStmt.execute();
			
			query = "INSERT INTO progdata (data) VALUES (?)";
			Database.setQuery(query);
			Database.prpStmt.setString(1, keyArray[1]);
			Database.prpStmt.execute();
			
			query = "INSERT INTO progdata (data) VALUES (?)";
			Database.setQuery(query);
			Database.prpStmt.setString(1, keyArray[2]);
			Database.prpStmt.execute();
			
			keyArray = null;
			sql = null;
		}
		else{
			// if database does exist, import RSA keys
			keyArray = new String [3];
			sql = "SELECT ALL data FROM progdata";
			Database.setQuery(sql);
			Database.resultSet = Database.prpStmt.executeQuery();
			for(int i = 0; i < 3; i++){
				Database.resultSet.next();
				keyArray[i] = Database.resultSet.getString(1);
			}
			RSA keys = new RSA(keyArray[0], keyArray[1], keyArray[2]);	
		}
		
		sql = "CREATE TABLE IF NOT EXISTS credentials " +
						"(id INTEGER PRIMARY KEY AUTOINCREMENT 	NOT NULL," +
						" website	TEXT	NOT NULL," +
						" username	TEXT	NOT NULL," +
						" encryptedpass	TEXT	NOT NULL)";
		
		setQuery(sql);
		prpStmt.execute();		
		System.out.println("Database connected!");
		connected = true;
	}

		// this method probably needs to be changed to work
	static void setQuery(String query) throws SQLException{
		//resultSet = statement.executeQuery(query);
		prpStmt = connection.prepareStatement(query);
	}
	
	static void closeConnection() throws SQLException{
		System.out.println("Connection Closed");
		statement.close();
		connection.close();
		connected = false;
	}
	
	static void deleteDatabase(){
		File f = new File("passwordDB.db");
		try {
			closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f.delete();
	}
}
