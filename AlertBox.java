import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	public static void display(String title, String message){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		Label label = new Label(message);
		Button closeButton = new Button("Close Message");	
		closeButton.setOnAction(e -> {
			window.close();
		});
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20, 20, 20, 20));
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.show();
	}
	public static void displayPassword(ArrayList<String> data){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Requested Data");
		window.setMinWidth(250);
		
		GridPane center = new GridPane();
		center.setPadding(new Insets( 20, 20, 20, 20));
		center.setVgap(8);
		center.setHgap(10);	
		
		Label websiteTitle = new Label("Website: ");	
		GridPane.setConstraints(websiteTitle, 0, 0);
		
		Label website = new Label(data.get(0));	
		GridPane.setConstraints(website, 1, 0);
		
		Label usernameTitle = new Label("Username: ");	
		GridPane.setConstraints(usernameTitle, 0, 1);
		
		Label username = new Label(data.get(1));	
		GridPane.setConstraints(username, 1, 1);
		
		Label passwordTitle = new Label("Password: ");	
		GridPane.setConstraints(passwordTitle, 0, 2);
		
		Label password = new Label(data.get(2));	
		GridPane.setConstraints(password, 1, 2);
		
		Button closeButton = new Button("Close Window");
		closeButton.setOnAction(e -> {
			data.clear();
			window.close();
		});
		GridPane.setConstraints(closeButton, 1, 3);
		
		
		center.getChildren().addAll(websiteTitle, usernameTitle, passwordTitle, website, username, password);
		center.setAlignment(Pos.CENTER);
		center.setPadding(new Insets(20, 20, 20, 20));
		
		BorderPane layout = new BorderPane();
		
		layout.setCenter(center);
		VBox bot = new VBox();
		bot.getChildren().add(closeButton);
		bot.setAlignment(Pos.CENTER);
		bot.setPadding(new Insets(20, 20, 20, 20));
		layout.setBottom(bot);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.show();
	}
}
