import java.sql.SQLException;
import javafx.event.ActionEvent;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangePassword {
	private static Stage mainWin;
	public static void display(){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Change Password");
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
		
		
		///////////
		Button enterPassword = new Button("Enter");
		GridPane.setConstraints(enterPassword, 2, 0);	
		// Placeholders are here because the the choiceboxes are not nested below, when the input is changed in the choiceboxes, the program throws exceptions.
		
		
		/////////////////
		Label passwordTitle = new Label("Change Password");
		GridPane.setConstraints(passwordTitle, 0, 0);

		TextField newPassword = new TextField();
		GridPane.setConstraints(newPassword, 1, 0);
		newPassword.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {

			@Override
			public void handle(KeyEvent key) {
				// TODO Auto-generated method stub
	            if (key.getCode().equals(KeyCode.ENTER))
	            {
	            	enterPassword.fire();
	            }
			}
	    });
		enterPassword.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				String sql = "UPDATE progdata SET data = '" + RSA.encrypt(newPassword.getText()) + "' WHERE id = '4'";
				try {
					Database.setQuery(sql);
					Database.prpStmt.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				window.close();
				
			}
			
		});			
		grid.getChildren().addAll(enterPassword, newPassword, passwordTitle);
		grid.setAlignment(Pos.CENTER);
		layout.setCenter(grid);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.show();
		newPassword.requestFocus();
	}
	
	static void setWindow(Stage w){
		mainWin = w;
	}
}
