import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RetrieveWindow {
	public static void display(MenuBar menuBar, HBox bot, Stage window){
		BorderPane retrieveLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.setPadding(new Insets( 20, 20, 20, 20));
		grid.setVgap(8);
		grid.setHgap(10);	
		
		Label chooseSite = new Label("Choose a Website");
		GridPane.setConstraints(chooseSite, 0, 0);
				
		ChoiceBox websiteList = new ChoiceBox();
		
		ArrayList<String> websiteFromDatabase = new ArrayList<>();
		try {
			if(Database.initialized == false){
				Database.initialize();
			}
			if(Database.connected == false){
				Database.connect();
			}
			if(Database.connected){
				String query = "SELECT website FROM credentials";
				Database.setQuery(query);
				Database.resultSet = Database.prpStmt.executeQuery();
				while(Database.resultSet.next()){
					if(!websiteFromDatabase.contains(Database.resultSet.getString(1))){
						websiteFromDatabase.add(Database.resultSet.getString(1));
					}
				}
			}
		}catch (ClassNotFoundException e) {
			AlertBox.display("Error", "Error Loading MySQL Driver");
		}
		catch (SQLException e2) {
			AlertBox.display("Error", "Error with Connection to Database");
		}
		
		ObservableList<String> toWebsiteBox = FXCollections.observableArrayList(websiteFromDatabase);
		websiteList.setItems(toWebsiteBox);
		websiteList.setMinWidth(100);
		GridPane.setConstraints(websiteList, 0, 1);
		
		ChoiceBox placeHolder = new ChoiceBox();
		placeHolder.setMinWidth(150);
		GridPane.setConstraints(placeHolder, 1, 1);
		
		Button placeHolder2 = new Button("Retrieve Password");
		GridPane.setConstraints(placeHolder2, 1, 2);	
		// Placeholders are here because the the choiceboxes are not nested below, when the input is changed in the choiceboxes, the program throws exceptions.
		
		
		ArrayList<String> usernameFromDatabase = new ArrayList<>();
		
		ArrayList<String> usernames = new ArrayList<>();
		ArrayList<String> requestedData = new ArrayList<>();
		websiteList.getSelectionModel().selectedIndexProperty().addListener(
				new ChangeListener<Number>(){

					@Override
					public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
						grid.getChildren().remove(placeHolder);
						grid.getChildren().remove(placeHolder2);
						ChoiceBox usernameList = new ChoiceBox();
						grid.getChildren().add(usernameList);
						//add another choicebox with a list of usernames, add a enter button, and display password below or in a new window
						if(requestedData.isEmpty()){
							requestedData.add(0, websiteFromDatabase.get(new_value.intValue()));
						}
						else{	
						requestedData.clear();
						requestedData.add(0, websiteFromDatabase.get(new_value.intValue()));
						}
						try {
							String query2 = "SELECT username FROM credentials WHERE website = '" + websiteFromDatabase.get(new_value.intValue()) + "'";
							
							Database.setQuery(query2);
							//
						
							Database.resultSet = Database.prpStmt.executeQuery();
							while(Database.resultSet.next()){
								usernameFromDatabase.add(Database.resultSet.getString(1));
							}
							ObservableList<String> toUsernameBox = FXCollections.observableArrayList(usernameFromDatabase);
							
							usernameList.setItems(toUsernameBox);
							if(!usernames.isEmpty()){
								usernames.clear();
							}
							usernames.addAll((Collection<? extends String>) usernameFromDatabase);
							usernameFromDatabase.clear(); // clear the list so if new website is picked, the usernames from the old website dont show in the box
						} catch (SQLException e) {
							AlertBox.display("Error", "Failed getting Usernames.");
						}
						usernameList.setMinWidth(150);
						GridPane.setConstraints(usernameList, 1, 1);
						
						usernameList.getSelectionModel().selectedIndexProperty().addListener(
								new ChangeListener<Number>(){

									@Override
									public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
										//Store username in requestedData, add submit button that will send the requestedData Arraylist to the displayPassword Method in AlertBox
										requestedData.add(1, usernames.get(new_value.intValue()));
									}
									
								});
						Button retrieve = new Button("Retrieve Password");
						GridPane.setConstraints(retrieve, 1, 2);
						grid.getChildren().add(retrieve);
						retrieve.setOnAction(e -> {
							
							try {
								String query3 = "SELECT encryptedpass FROM credentials WHERE website = '" + requestedData.get(0) + "' AND username = '" + requestedData.get(1) +"'";
								Database.setQuery(query3);
								Database.resultSet = Database.prpStmt.executeQuery();
								String encryptedPassword = "";
								while(Database.resultSet.next()){
									encryptedPassword = Database.resultSet.getString(1);
								}
								if(!RSA.doesExist()){
									AlertBox.display("Error", "No Keys Exist, Please Generate Keys or Import Keys");
								}
								else{
								
									requestedData.add(2, RSA.decrypt(encryptedPassword));
									AlertBox.displayPassword(requestedData);
									requestedData.clear();
									
									//The code below can be added if it is desired to close the program after displaying the password
									/*
									if(RSA.doesExist()){
										RSA.deleteKeys();
										}
										System.gc();
										try {
											if(Database.connected == true){
												Database.closeConnection();
											}
										} catch (SQLException e1) {
											AlertBox.display("error", "Error While Closing Connection");
										}
										System.out.println("Properly Closed");
										window.close();
										
										 * 
										 */
									
								}			
							} catch (SQLException e1) {
								AlertBox.display("Error", "Error Getting Password from Database.");
							}
						});
						
					}
					
					//If one website is picked then another one is picked, the username and password don't update
					
				});
		
		
		
		Label chooseUsername = new Label("Choose a Username");
		GridPane.setConstraints(chooseUsername, 1, 0);
		
		
		
		grid.getChildren().addAll(chooseSite, websiteList, chooseUsername, placeHolder, placeHolder2);
		grid.setAlignment(Pos.CENTER);
		
		retrieveLayout.setTop(menuBar);
		retrieveLayout.setCenter(grid);
		//user borderpane and gridpane for center?
		//and also VBox
		//Gridpane goes in center of borderpane
		//VBox goes in left of borderpane
		//the choice box and a label go in the VBox
		retrieveLayout.setBottom(bot);
		
		Scene retrieveScene = new Scene(retrieveLayout);
		window.setScene(retrieveScene);		
	}
}
