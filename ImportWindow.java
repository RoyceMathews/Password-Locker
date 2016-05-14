import java.io.File;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ImportWindow{
	public static void display(MenuBar menuBar, HBox bot, Stage window){
		BorderPane importLayout = new BorderPane();
		importLayout.setTop(menuBar);
		GridPane importGrid = new GridPane();
		importGrid.setPadding(new Insets( 20, 20, 20, 20));
		importGrid.setVgap(8);
		importGrid.setHgap(10);
		//import public Label
		Label importPublic = new Label("Import Public Keys: ");
		GridPane.setConstraints(importPublic, 0, 0);
		
		//import public input
		TextField importInput = new TextField("publicKeys.txt");
		GridPane.setConstraints(importInput, 1, 0);
		
		//save private Label
		Label importPrivate = new Label("Import Private Key: ");
		GridPane.setConstraints(importPrivate, 0, 1);
				
		//save private input
		TextField importPrivateInput = new TextField("privateKey.txt");
		GridPane.setConstraints(importPrivateInput, 1, 1);
		
		Button importKeysButton = new Button("Import Keys");
		importKeysButton.setText("Import Keys");
		
		importPrivateInput.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent key) {
				// TODO Auto-generated method stub
	            if (key.getCode().equals(KeyCode.ENTER))
	            {
	            	importKeysButton.fire();
	            }
			}
	    });
		
		importKeysButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event) {
				
				try {
					File publicFile  = new File(importInput.getText());
					File privateFile = new File(importPrivateInput.getText());
					
					if(publicFile.exists() && (!privateFile.exists())){
						AlertBox.display("Error", "Invalid Private File");
					}
					
					else if((!publicFile.exists()) && (privateFile.exists())){
						AlertBox.display("Error", "Invalid Public File");
					}
					
					else if((!publicFile.exists()) && (!privateFile.exists())){
						AlertBox.display("Error", "Invalid Private and Public File");
					}
					
					else if(publicFile.exists() && privateFile.exists()){
						RSA newObject = new RSA(importInput.getText(), importPrivateInput.getText());
						
						Label imported = new Label("Keys have been imported.");
						GridPane.setConstraints(imported, 1 , 2);
						
						importGrid.getChildren().remove(importKeysButton);
						importGrid.getChildren().add(imported);
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
									
		});			
		GridPane.setConstraints(importKeysButton, 1, 2);
		
		importGrid.getChildren().addAll(importPublic, importInput, importPrivate, importKeysButton, importPrivateInput);
		importGrid.setAlignment(Pos.CENTER);
		
		importLayout.setCenter(importGrid);
		importLayout.setBottom(bot);
		Scene importScene = new Scene(importLayout);
		window.setScene(importScene);
		
	}
}
