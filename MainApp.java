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
// Get rid of generate keys and import keys
// this should be done automatically
// if keys exist, import, else create new
// move update and delete to buttons
// generate keys and import keys should be in the menu bar

// Consolidate keys into one file
// add window at beginning that asks for a password
	// this password should be encrypted when stored with its own RSA keys
	// maybe make a table that store all the keys and password instead of text files DO THIS
	// should contain 2 columns, auto incrementing id and a column for the data
	// password should also encrypt the RSA keys

public class MainApp extends Application{

	
	Stage window;
	
	
	public static void main(String[] args) {
		
		
		try {
			onStart();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Password Locker");
		
		//File menu
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		
		MenuItem Exit = new MenuItem("Exit");
		Exit.setOnAction(e -> {
			closeProgram();
		});
		fileMenu.getItems().add(Exit);
		
		HBox bot = new HBox();
		bot.setPadding(new Insets(15,12,15,12));
		bot.setSpacing(10);
		
		//Main Menu Bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, editMenu);
		
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		
		MenuItem Generate = new MenuItem("Generate");	// Change to Generate Keys (was Update)
		Generate.setOnAction(e -> {
			GenerateWindow.display(menuBar, bot, window);
			//UpdateTable.display();
		});
		MenuItem Import = new MenuItem("Import");	// Change to Import Keys (was Delete)
		Import.setOnAction(e -> {
			ImportWindow.display(menuBar, bot, window);
			//DeleteFromTable.display();
		});
		MenuItem Change = new MenuItem("Change Password");	// Change to Import Keys (was Delete)
		Change.setOnAction(e -> {
			ChangePassword.display();
			//DeleteFromTable.display();
		});
		
		
		
		editMenu.getItems().addAll(Generate, Import, Change);
			
		////// Buttons
		Button generateButton = new Button("Generate");
		generateButton.setText("Update");	// Generate RSA Keys
		bot.getChildren().add(generateButton);
		generateButton.setOnAction(e -> {
			UpdateTable.display();
			//Displays the Generate Window
			//GenerateWindow.display(menuBar, bot, window);		// change to update was generate	
		});
		
		Button importButton = new Button("Import");
		importButton.setText("Delete");	// Import RSA Keys
		importButton.setOnAction(e -> { 
			DeleteFromTable.display();
			// Displays The Import Window
			//ImportWindow.display(menuBar, bot, window);		// Change to delete was import
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
		
		
		/// Implement both create and Enter password here
		if(!Database.exists){
			CreatePassword.setWindow(window);
			CreatePassword.display();
		}
		else{
			EnterPassword.setWindow(window);
			EnterPassword.display();
		}
		
	}
	
	private static void onStart() throws ClassNotFoundException, SQLException{
		Database.initialize();
		Database.connect();
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
