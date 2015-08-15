import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EnterWindow {
	public static void display(MenuBar menuBar, HBox bot, Stage window){
		
		BorderPane enterLayout = new BorderPane();
		GridPane grid = new GridPane();
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
		
		/*
		PasswordField passwordInput = new PasswordField();
		passwordField.setPromptText("Password");
		GridPane.setConstraints(passwordInput, 1, 2);
		 * Use this if you want 
		 */
		
		Button submitButton = new Button("Submit");
		submitButton.setText("Submit Data");
		submitButton.setOnAction(e -> {
			try {
				if(Database.initialized == false){
					Database.initialize();
				}
				if(Database.connected == false){
					Database.connect();
				}
				if(siteInput.getText().isEmpty() || nameInput.getText().isEmpty() || passwordInput.getText().isEmpty()){
					AlertBox.display("Empty Field", "Please fill the empty field.");
				}
				else{
				String query = "INSERT INTO credentials (website, username, encryptedpass) VALUES (?, ?, ?)";
				Database.setQuery(query);
				Database.prpStmt.setString(1, siteInput.getText());
				Database.prpStmt.setString(2, nameInput.getText());
				Database.prpStmt.setString(3, RSA.encrypt(passwordInput.getText()));
				Database.prpStmt.execute();
				
				AlertBox.display("Success", "Data has been Inserted!");
				siteInput.clear();
				nameInput.clear();
				passwordInput.clear();
				}
			}catch (ClassNotFoundException e1) {
				AlertBox.display("Error", "Error Loading MySQL Driver");
			}
			catch (SQLException e2) {
				AlertBox.display("Error", "Error with Connection to Database");
			}
		});
		GridPane.setConstraints(submitButton, 1, 3);
		
		grid.getChildren().addAll(siteLabel, siteInput, nameLabel, nameInput, passwordLabel, passwordInput, submitButton);
		grid.setAlignment(Pos.CENTER);
		enterLayout.setTop(menuBar);
		enterLayout.setCenter(grid);
		enterLayout.setBottom(bot);
		Scene enterScene = new Scene(enterLayout);
		window.setScene(enterScene);
	}
}
