import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class GenerateWindow{
	public static void display(MenuBar menuBar, HBox bot, Stage window){
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
		
		//save private input
		TextField savePrivateInput = new TextField("privateKey.txt");
		GridPane.setConstraints(savePrivateInput, 1, 1);
		
		Button saveButton = new Button("Save Keys");
		saveButton.setText("Save Keys");
		saveButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event) {
				try {
					RSA newObject =  new RSA();
					RSA.saveKeys(saveInput.getText(), savePrivateInput.getText());
					Label generated = new Label("Keys have been generated and saved.");
					GridPane.setConstraints(generated, 1 , 2);
					RSAGrid.getChildren().remove(saveButton);
					RSAGrid.getChildren().add(generated);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});					
		GridPane.setConstraints(saveButton, 1, 2);
		
		RSAGrid.getChildren().addAll(save, saveInput, savePrivate, savePrivateInput, saveButton);
		RSAGrid.setAlignment(Pos.CENTER);
		
		generateLayout.setCenter(RSAGrid);
		generateLayout.setBottom(bot);
		Scene enterScene = new Scene(generateLayout);
		window.setScene(enterScene);		
	}
}
