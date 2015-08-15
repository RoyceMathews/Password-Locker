import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	static private String url;
	static private String username;
	static private String password;
	static private Connection connection;
	static private Statement statement;
	static PreparedStatement prpStmt;
	static boolean initialized = false;
	static boolean connected = false;
	static ResultSet resultSet;
	
		
	static void initialize() throws ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		url = "jdbc:mysql://localhost:3306/password_encryption";
		username = "root";
		password = "";
		initialized = true;
	}
	
	static void connect() throws SQLException {
		System.out.println("Connecting database...");
		connection = DriverManager.getConnection(url, username, password);
		System.out.println("Database connected!");
		statement = connection.createStatement();
		connected = true;
	}
	
	static void setQuery(String query) throws SQLException{
		//resultSet = statement.executeQuery(query);
		prpStmt = connection.prepareStatement(query);
	}
	
	static void closeConnection() throws SQLException{
		System.out.println("Connection Closed");
		connection.close();
		connected = false;
	}
			
}
