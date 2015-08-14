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

public class EnterWindow {
	public static Scene display(MenuBar menuBar, HBox bot){
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
		
		Button submitButton = new Button("Submit");
		submitButton.setText("Submit Data");
		GridPane.setConstraints(submitButton, 1, 3);
		
		grid.getChildren().addAll(siteLabel, siteInput, nameLabel, nameInput, passwordLabel, passwordInput, submitButton);
		grid.setAlignment(Pos.CENTER);
		enterLayout.setTop(menuBar);
		enterLayout.setCenter(grid);
		enterLayout.setBottom(bot);
		Scene enterScene = new Scene(enterLayout);
		return enterScene;
	}
}
