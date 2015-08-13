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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ImportWindow{
	private static RSA importedObject;
	public static RSA display(MenuBar menuBar, HBox bot, RSA object, Stage window){
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
		importKeysButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle (ActionEvent event) {
				try {
					importedObject = new RSA(new File(importInput.getText()), new File(importPrivateInput.getText()));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Label imported = new Label("Keys have been generated and saved.");
				GridPane.setConstraints(imported, 1 , 2);
				importGrid.getChildren().remove(importKeysButton);
				importGrid.getChildren().add(imported);
							
			}
		});			
		GridPane.setConstraints(importKeysButton, 1, 2);
		
		importGrid.getChildren().addAll(importPublic, importInput, importPrivate, importKeysButton, importPrivateInput);
		importGrid.setAlignment(Pos.CENTER);
		
		importLayout.setCenter(importGrid);
		importLayout.setBottom(bot);
		Scene importScene = new Scene(importLayout);
		window.setScene(importScene);
		
		return importedObject;
	}
}
