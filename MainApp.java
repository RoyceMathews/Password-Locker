import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.application.Application;
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
			closeProgram();
		});
		
		
		
		
		
		GridPane grid = new GridPane();
		
		HBox bot = new HBox();
		bot.setPadding(new Insets(15,12,15,12));
		bot.setSpacing(10);
		
		////// Buttons
		Button generateButton = new Button("Generate");
		generateButton.setText("Generate RSA Keys");
		bot.getChildren().add(generateButton);
		generateButton.setOnAction(e -> {
			BorderPane generateLayout = new BorderPane();
			generateLayout.setTop(menuBar);
			GridPane RSAGrid = new GridPane();
			RSAGrid.setPadding(new Insets( 20, 20, 20, 20));
			RSAGrid.setVgap(8);
			RSAGrid.setHgap(10);
			//save public Label
			Label save = new Label("Save Public Keys: ");
			GridPane.setConstraints(save, 0, 0);
			
			//save public input
			TextField saveInput = new TextField("publicKeys.txt");
			GridPane.setConstraints(saveInput, 1, 0);
			
			//save private Label
			Label savePrivate = new Label("Save Private Key: ");
			GridPane.setConstraints(savePrivate, 0, 1);
			
			Button saveButton = new Button("Submit");
			saveButton.setText("Save Keys");
			GridPane.setConstraints(saveButton, 1, 2);
			
			//save private input
			TextField savePrivateInput = new TextField("privateKey.txt");
			GridPane.setConstraints(savePrivateInput, 1, 1);
			
			RSAGrid.getChildren().addAll(save, saveInput, savePrivate, savePrivateInput, saveButton);
			RSAGrid.setAlignment(Pos.CENTER);
			
			generateLayout.setCenter(RSAGrid);
			generateLayout.setBottom(bot);
			Scene enterScene = new Scene(generateLayout);
			window.setScene(enterScene);
			
		});
		
		Button importButton = new Button("Import");
		importButton.setText("Import RSA Keys");
		bot.getChildren().add(importButton);
		
		Button enterButton = new Button("Enter");
		enterButton.setText("Enter New Data");
		enterButton.setOnAction(e -> {
			BorderPane enterLayout = new BorderPane();
			enterLayout.setTop(menuBar);
			enterLayout.setCenter(grid);
			enterLayout.setBottom(bot);
			Scene enterScene = new Scene(enterLayout);
			window.setScene(enterScene);
			
		});
		bot.getChildren().add(enterButton);
		
		Button retrieveButton = new Button("Retrieve");
		retrieveButton.setText("Retrieve Data");
		bot.getChildren().add(retrieveButton);
		
		///////Buttons
		
		grid.setPadding(new Insets( 20, 20, 20, 20));
		grid.setVgap(8);
		grid.setHgap(10);		
		
		//Website Label
		Label siteLabel = new Label("Website: ");
		GridPane.setConstraints(siteLabel, 0, 0);
		
		//Website input
		TextField siteInput = new TextField();
		siteInput.setPromptText("Website");
		GridPane.setConstraints(siteInput, 1, 0);
		
		//Name Label
		Label nameLabel = new Label("Username: ");
		GridPane.setConstraints(nameLabel, 0, 1);
		
		//Name input
		TextField nameInput = new TextField();
		nameInput.setPromptText("Username");
		GridPane.setConstraints(nameInput, 1, 1);
		
		//Password Label
		Label passwordLabel = new Label("Password: ");
		GridPane.setConstraints(passwordLabel, 0, 2);
				
		//Password input
		TextField passwordInput = new TextField();
		passwordInput.setPromptText("Password");
		GridPane.setConstraints(passwordInput, 1, 2);
		
		Button submitButton = new Button("Submit");
		submitButton.setText("Submit Data");
		GridPane.setConstraints(submitButton, 1, 3);
		
		grid.getChildren().addAll(siteLabel, siteInput, nameLabel, nameInput, passwordLabel, passwordInput, submitButton);
		grid.setAlignment(Pos.CENTER);
		/*
		 * 
		 * USE HBOX FOR THE BUTTONS AT THE BOTTOM
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		BorderPane layout = new BorderPane();
		layout.setTop(menuBar);
		//layout.setCenter(grid);
		layout.setBottom(bot);
		Scene scene = new Scene(layout);
		//scene.getStylesheets().add("style.css");
		window.setScene(scene);
		window.show();
		
		
		
		
		
		//Enter New Data Scene
		
		
	}

	private void closeProgram(){
		//System.out.println("Closed Properly");
		window.close();
	}
}
