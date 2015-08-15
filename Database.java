import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	static private String url;
	static private String username;
	static private String password;
	static private Connection connection;
	static Statement statement;
	static ResultSet resultSet;
	
		
	static void initialize() throws ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		url = "jdbc:mysql://localhost:3306/shoutit";
		username = "root";
		password = "";
	}
	
	static void connect() throws SQLException {
		System.out.println("Connecting database...");
		connection = DriverManager.getConnection(url, username, password);
		System.out.println("Database connected!");
		statement = connection.createStatement();
	}
	
	static void closeConnection() throws SQLException{
		System.out.println("Connection Closed");
		connection.close();
	}
			
}

/*
 * try {
			Database.initialize();
			Database.connect();
			Database.resultSet = Database.statement.executeQuery("SELECT user, id FROM shouts");
			while(Database.resultSet.next()){
				// when the query string has multiple columns, the int in getString below corresponds to the 
				//column
				System.out.println(Database.resultSet.getString(1));
			} 
			Database.closeConnection();
		}catch (ClassNotFoundException e1) {
			AlertBox.display("Error", "Error Loading MySQL Driver");
		}
		catch (SQLException e1) {
			AlertBox.display("Error", "Error with Connection to Database");
		}
 * 
 * 
 * 
 */
