import java.sql.SQLException;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
		 * Use this if you want password to be hidden
		 */
		
		Button submitButton = new Button("Submit");
		submitButton.setText("Submit Data");
		
		passwordInput.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent key) {
				// TODO Auto-generated method stub
	            if (key.getCode().equals(KeyCode.ENTER))
	            {
	            	submitButton.fire();
	            }
			}
	    });
		
		submitButton.setOnAction(e -> {
			
			if(!RSA.doesExist()){
				AlertBox.display("Error", "No Keys Exist, Please Generate Keys or Import Keys");
			}
			else{
				
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
						
						dataEntry(siteInput, nameInput, passwordInput);
						//System.out.println("Data Entered");
						siteInput.clear();
						nameInput.clear();
						passwordInput.clear();
						
						//grid.getChildren().remove(submitButton);
						//Label success = new Label("Data has been entered.");
						//GridPane.setConstraints(success, 1, 3);
						//grid.getChildren().add(success);
						
						
					}
				}catch (ClassNotFoundException e1) {
					AlertBox.display("Error", "Error Loading MySQL Driver");
				}
				catch (SQLException e2) {
					e2.printStackTrace();
					AlertBox.display("Error", "Error with Connection to Database");
				}
				
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
		siteInput.requestFocus();
	}
	static private void dataEntry(TextField siteInput, TextField nameInput, TextField passwordInput) throws SQLException{
		String checkQuery = "SELECT username FROM credentials WHERE website = '" + siteInput.getText() + "'";
		Database.setQuery(checkQuery);
		Database.resultSet = Database.prpStmt.executeQuery();
		//System.out.println(Database.resultSet);
		String name = nameInput.getText();
		ArrayList<Boolean> status = new ArrayList<>();
		//System.out.println(Database.resultSet.next());
		boolean nextQ = false;
		ArrayList<String> allFromD = new ArrayList<>();
		if(Database.resultSet.next()){
			nextQ = true;
			//fromD = Database.resultSet.getString(3);
			//allFromD.add(fromD);			//Database.resultSet.beforeFirst();// goes to last, move to first
		}
		if(!nextQ){
			//System.out.println("if");
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
			Database.prpStmt.clearBatch();
			
		}
		else if(nextQ){
			//System.out.println("In else if");
			//Database.resultSet.beforeFirst();
			//status.clear();
			allFromD.add(Database.resultSet.getString(1));
			while(Database.resultSet.next()){
				allFromD.add(Database.resultSet.getString(1));
				//System.out.println(allFromD);
			}
			for(int i = 0; i < allFromD.size();i++){
				//System.out.println("In while");
				//System.out.println(name + " " + Database.resultSet.getString(3));
				if(name.equals(allFromD.get(i))){
					
					status.add(false);
					//System.out.println(status + "if");
				}
				else{
					if(!status.contains(true) && !status.contains(false)){
						status.add(true);
						//System.out.println(status + "else");
					}
				}
			}
			//System.out.println(status + "Line 177");
			if(status.contains(true)){
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
				query = "";
			}
			else{
				//Something wrong with this?
				AlertBox.display("Error", "Username already exists for this Website");
			}
		}
		status.clear();
		checkQuery = "";
		allFromD = null;
		
		
		try{
			if(Database.resultSet != null){
				Database.resultSet.close();
			}
			Database.prpStmt.close();
			} catch (Exception e3){
				AlertBox.display("Error", "SQL Error");
			}
	}
}
