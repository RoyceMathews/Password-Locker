import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	public static void display(String title, String message){	// standard window that will display a message
		Stage window = new Stage();
		//Creates a new stage
		window.initModality(Modality.APPLICATION_MODAL);
		//Prevents User from using main application until this window is closed
		window.setTitle(title);	// Sets the Window Title
		window.setMinWidth(250);	// Sets the minimum width of the window

		Label label = new Label(message);	// creates a new label which contains the message
		Button closeButton = new Button("Close Message");
		//Button that upon pressing, closes the window
		
		closeButton.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {

			@Override
			public void handle(KeyEvent key) {
				// TODO Auto-generated method stub
	            if (key.getCode().equals(KeyCode.ENTER))
	            {
	            	closeButton.fire();
	            }
			}
	    });
		
		closeButton.setOnAction(e -> {
			window.close();
		});

		VBox layout = new VBox(10); // Creats a VBox with 10 spacing between elements
		layout.getChildren().addAll(label, closeButton); // adds the label and button to the layout
		layout.setAlignment(Pos.CENTER);	//centers VBox
		layout.setPadding(new Insets(20, 20, 20, 20));	// sets the padding to 20 on all sides
		Scene scene = new Scene(layout);	// adds the VBox to the scene
		window.setScene(scene);	//sets the scene
		window.show();	// displays the scene to the window
	}
	public static void displayPassword(ArrayList<String> data){
		// this method will display the website, username, and unencrypted password the user requests
		Stage window = new Stage();
		//Creates a new stage
		window.initModality(Modality.APPLICATION_MODAL);
		//Prevents User from using main application until this window is closed
		window.setTitle("Requested Data");	// Sets the title
		window.setMinWidth(250);	// sets the minimum width

		GridPane center = new GridPane();	// creates a gridpane that will display the information
		// Its called center because it will be in the center of a borderpane
		center.setPadding(new Insets( 20, 20, 20, 20));	//sets the padding to 20 on all sides
		center.setVgap(8);
		center.setHgap(10);
		// Sets the vertical and horizontal spacing inbetween elements

		//////////////// Creates the labels that describe the requested data ////////////////////////
		/////////////// Also creates labels that display the requested data  ////////////////////////
		Label websiteTitle = new Label("Website: ");
		GridPane.setConstraints(websiteTitle, 0, 0);
		////// gridpane.setConstraints assigns where on the gridpane the node will be displayed

		Label website = new Label(data.get(0));
		GridPane.setConstraints(website, 1, 0);

		Label usernameTitle = new Label("Username: ");
		GridPane.setConstraints(usernameTitle, 0, 1);

		TextField username = new TextField(data.get(1));
		username.setEditable(false);
		GridPane.setConstraints(username, 1, 1);

		Label passwordTitle = new Label("Password: ");
		GridPane.setConstraints(passwordTitle, 0, 2);

		TextField password = new TextField(data.get(2));
		password.setEditable(false);
		GridPane.setConstraints(password, 1, 2);

		Button closeButton = new Button("Close Window");	// upon pressing this button, the window closes
		
		closeButton.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {

			@Override
			public void handle(KeyEvent key) {
				// TODO Auto-generated method stub
	            if (key.getCode().equals(KeyCode.ENTER))
	            {
	            	closeButton.fire();
	            }
			}
	    });
		
		closeButton.setOnAction(e -> {
			data.clear();
			window.close();
		});
		GridPane.setConstraints(closeButton, 1, 3);


		center.getChildren().addAll(websiteTitle, usernameTitle, passwordTitle, website, username, password);
		center.setAlignment(Pos.CENTER);
		center.setPadding(new Insets(20, 20, 20, 20));
		////// adds the nodes to the gridpane (center) aligns them to the center and sets the padding to 20

		BorderPane layout = new BorderPane();	// the borderpane will hold the gridpane in the center, and the close button will be in a VBox in the bottom

		layout.setCenter(center);	// gridpane is set to the center of the borderpane
		VBox bot = new VBox();	// VBox that will hold the close button is created
		bot.getChildren().add(closeButton);	// adds the close button to the VBox
		bot.setAlignment(Pos.CENTER);	// aligns the button to the center
		bot.setPadding(new Insets(20, 20, 20, 20));	// padding set to 20
		layout.setBottom(bot);	// VBox set to the bottom of the borderpane

		Scene scene = new Scene(layout);	//new scene that has the borderpane is created
		window.setScene(scene);	// the scene is set to the new scene
		window.show();	// the window is displayed
	}
}
