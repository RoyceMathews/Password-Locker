import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.sql.SQLException;


public class MainApp extends Application{

	//private RSA object = null;
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
		fileMenu.getItems().add(new MenuItem("Generate New Keys"));
		fileMenu.getItems().add(new MenuItem("Import Existing Keys"));
		fileMenu.getItems().add(new MenuItem("Enter New Data"));
		fileMenu.getItems().add(new MenuItem("Retrieve Data"));
		fileMenu.getItems().add(new MenuItem("Exit"));
		
		//Main Menu Bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);
		
		window.setOnCloseRequest(e -> {
			e.consume();
			RSA.deleteKeys();
			System.gc();
			System.out.println("Properly Closed");
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
			
			window.setScene(EnterWindow.display(menuBar, bot));
			
		});
		bot.getChildren().add(enterButton);
		
		Button retrieveButton = new Button("Retrieve");
		retrieveButton.setText("Retrieve Data");
		retrieveButton.setOnAction(e -> {
			
		System.out.println(RSA.N);
		System.out.println(RSA.encrypt("hello"));
		
		
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
		//System.out.println("Closed Properly");
		window.close();
	}
}
