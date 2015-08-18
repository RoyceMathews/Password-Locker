import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.sql.SQLException;


public class MainApp extends Application{

	
	Stage window;
	
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Password Encryption");
		
		//File menu
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		Menu connectMenu = new Menu("Connection");
		/*
		fileMenu.getItems().add(new MenuItem("Generate New Keys"));
		fileMenu.getItems().add(new MenuItem("Import Existing Keys"));
		fileMenu.getItems().add(new MenuItem("Enter New Data"));
		fileMenu.getItems().add(new MenuItem("Retrieve Data"));
		*/
		//Add an update and a delete menu item
		//Add Change Connection, Connect, and Disconnect
		MenuItem Exit = new MenuItem("Exit");
		Exit.setOnAction(e -> {
			closeProgram();
		});
		fileMenu.getItems().add(Exit);
		
		MenuItem Update = new MenuItem("Update");
		Update.setOnAction(e -> {
			UpdateTable.display();
		});
		MenuItem Delete = new MenuItem("Delete");
		Delete.setOnAction(e -> {
			DeleteFromTable.display();
		});
		editMenu.getItems().addAll(Update, Delete);
				
		MenuItem Connect = new MenuItem("Connect");
		Connect.setOnAction(e -> {
			try {
				Database.initialize();
				Database.connect();
			} catch (ClassNotFoundException e1) {
				AlertBox.display("Error", "Error Loading MySQL Driver");
			} catch (SQLException e2){
				AlertBox.display("Connection Error", "Error with Connection to Database");
			}
		});
		
		
		MenuItem Disconnect = new MenuItem("Disconnect");
		Disconnect.setOnAction(e -> {
			try {
				if(Database.connected == true){
					Database.closeConnection();
				}
			} catch (SQLException e1) {
				AlertBox.display("Error", "Error While Closing Connection");
			}
		});
		MenuItem Settings = new MenuItem("Settings");
		connectMenu.getItems().addAll(Connect, Disconnect, Settings);
		
		//Main Menu Bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, editMenu, connectMenu);
		
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		
		
		
		HBox bot = new HBox();
		bot.setPadding(new Insets(15,12,15,12));
		bot.setSpacing(10);
		
		////// Buttons
		Button generateButton = new Button("Generate");
		generateButton.setText("Generate RSA Keys");
		bot.getChildren().add(generateButton);
		generateButton.setOnAction(e -> {
			//Displays the Generate Window
			GenerateWindow.display(menuBar, bot, window);		
		});
		
		Button importButton = new Button("Import");
		importButton.setText("Import RSA Keys");
		importButton.setOnAction(e -> { 
			// Displays The Import Window
			ImportWindow.display(menuBar, bot, window);
		});
		bot.getChildren().add(importButton);
		
		Button enterButton = new Button("Enter");
		enterButton.setText("Enter New Data");
		enterButton.setOnAction(e -> {
			// Displays The Enter Window
			EnterWindow.display(menuBar, bot, window);
			
		});
		bot.getChildren().add(enterButton);
		
		Button retrieveButton = new Button("Retrieve");
		retrieveButton.setText("Retrieve Data");
		retrieveButton.setOnAction(e -> {
		
			RetrieveWindow.display(menuBar, bot, window);
		});
		bot.getChildren().add(retrieveButton);
				
		BorderPane layout = new BorderPane();
		layout.setTop(menuBar);
		//layout.setCenter(grid);
		layout.setBottom(bot);
		Scene scene = new Scene(layout);
		//scene.getStylesheets().add("style.css");
		window.setScene(scene);
		window.show();
		
		
		
	}

	private void closeProgram(){
		if(RSA.doesExist()){
		RSA.deleteKeys();
		}
		System.gc();
		try {
			if(Database.connected == true){
				Database.closeConnection();
			}
		} catch (SQLException e1) {
			AlertBox.display("Error", "Error While Closing Connection");
		}
		System.out.println("Properly Closed");
		window.close();
	}
}
