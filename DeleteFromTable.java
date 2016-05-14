import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteFromTable {
	public static void display(){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Update Data");
		window.setMinWidth(250);
		window.setOnCloseRequest(e -> {
			e.consume();
			
			System.out.println("Properly Closed");
			window.close();
		});
		
		BorderPane layout = new BorderPane();
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
		finally{
			try{
			if(Database.resultSet != null){
				Database.resultSet.close();
			}
			Database.prpStmt.close();
			} catch (SQLException e){
				AlertBox.display("Error", "SQL Error");
			}
		}
		ObservableList<String> toWebsiteBox = FXCollections.observableArrayList(websiteFromDatabase);
		websiteList.setItems(toWebsiteBox);
		websiteList.setMinWidth(100);
		GridPane.setConstraints(websiteList, 0, 1);
		
		ChoiceBox placeHolder = new ChoiceBox();
		placeHolder.setMinWidth(150);
		GridPane.setConstraints(placeHolder, 1, 1);
		
		Button placeHolder2 = new Button("Delete Entry");
		GridPane.setConstraints(placeHolder2, 1, 2);	
		// Placeholders are here because the the choiceboxes are not nested below, when the input is changed in the choiceboxes, the program throws exceptions.
		
			
		ArrayList<String> usernameFromDatabase = new ArrayList<>();
		
		ArrayList<String> usernames = new ArrayList<>();
		ArrayList<String> toBeDeleted = new ArrayList<>();
		websiteList.getSelectionModel().selectedIndexProperty().addListener(
				new ChangeListener<Number>(){

					@Override
					public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
						grid.getChildren().remove(placeHolder);
						grid.getChildren().remove(placeHolder2);
						ChoiceBox usernameList = new ChoiceBox();
						grid.getChildren().add(usernameList);
						//add another choicebox with a list of usernames, add a enter button, and display password below or in a new window
						if(toBeDeleted.isEmpty()){
							toBeDeleted.add(0, websiteFromDatabase.get(new_value.intValue()));
						}
						else{	
						toBeDeleted.clear();
						toBeDeleted.add(0, websiteFromDatabase.get(new_value.intValue()));
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
						}finally{
							try{
								if(Database.resultSet != null){
									Database.resultSet.close();
								}
								Database.prpStmt.close();
								} catch (SQLException e){
									AlertBox.display("Error", "SQL Error");
								}
							}
						usernameList.setMinWidth(150);
						GridPane.setConstraints(usernameList, 1, 1);
						
						usernameList.getSelectionModel().selectedIndexProperty().addListener(
								new ChangeListener<Number>(){

									@Override
									public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
										//Store username in toBeDeleted, add submit button that will send the toBeDeleted Arraylist to the displayPassword Method in AlertBox
										toBeDeleted.add(1, usernames.get(new_value.intValue()));
									}
									
								});
		
						Button delete = new Button("Delete Entry");
						GridPane.setConstraints(delete, 1, 2);
						grid.getChildren().add(delete);
						
						usernameList.setOnKeyPressed(new EventHandler<KeyEvent>()
					    {
				
							@Override
							public void handle(KeyEvent key) {
								// TODO Auto-generated method stub
					            if (key.getCode().equals(KeyCode.ENTER))
					            {
					            	delete.fire();
					            }
							}
					    });	
						
						delete.setOnAction(e -> {
							
							try {
									String query3 = "DELETE FROM credentials WHERE website = '" + toBeDeleted.get(0) + "' AND username = '" + toBeDeleted.get(1) + "' ";
									Database.setQuery(query3);
									Database.prpStmt.execute();
									AlertBox.display("Success", "Entry Deleted");
									toBeDeleted.clear();
									
									//change conditionals
									//if not changed, keep the window.close
									
									System.out.println("Properly Closed");
									window.close();
									
								
							} catch (SQLException e1) {
								AlertBox.display("Error", "Error Deleting Entry from Database.");
							}finally{
								try{
									if(Database.resultSet != null){
										Database.resultSet.close();
									}
									Database.prpStmt.close();
									} catch (SQLException e1){
										AlertBox.display("Error", "SQL Error");
									}
								}
						});
						
					}
					
					//If one website is picked then another one is picked, the username and password don't update
					
				});
		
		
		
		Label chooseUsername = new Label("Choose a Username");
		GridPane.setConstraints(chooseUsername, 1, 0);
		
		grid.getChildren().addAll(chooseSite, websiteList, chooseUsername, placeHolder, placeHolder2);
		grid.setAlignment(Pos.CENTER);
		layout.setCenter(grid);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.show();
		websiteList.requestFocus();
	}
}
