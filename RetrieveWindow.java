import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
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
		
		ChoiceBox websites = new ChoiceBox();
		ArrayList<String> fromDatabase = new ArrayList<>();
		try {
			if(Database.initialized == false){
				Database.initialize();
			}
			if(Database.connected == false){
				Database.connect();
			}
			
			String query = "SELECT website FROM credentials";
			Database.setQuery(query);
			Database.resultSet = Database.prpStmt.executeQuery();
			while(Database.resultSet.next()){
				if(!fromDatabase.contains(Database.resultSet.getString(1))){
					fromDatabase.add(Database.resultSet.getString(1));
				}
			}
		}catch (ClassNotFoundException e) {
			AlertBox.display("Error", "Error Loading MySQL Driver");
		}
		catch (SQLException e2) {
			AlertBox.display("Error", "Error with Connection to Database");
		}
		
		ObservableList<String> toBox = FXCollections.observableArrayList(fromDatabase);
		websites.setItems(toBox);
		GridPane.setConstraints(websites, 0, 0);
		
		grid.getChildren().add(websites);
		grid.setAlignment(Pos.CENTER_LEFT);
		
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
