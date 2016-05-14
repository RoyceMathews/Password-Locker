import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EnterPassword {
	static boolean correct = false;
	private static Stage mainWin;
	public static void display(){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Enter Password");
		window.setMinWidth(250);
		window.setOnCloseRequest(e -> {
			e.consume();
			if(!correct){
				mainWin.close();
			}
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
		Label passwordTitle = new Label("Enter Password");
		GridPane.setConstraints(passwordTitle, 0, 0);
		
		PasswordField newPassword = new PasswordField();
		GridPane.setConstraints(newPassword, 1, 0);
//		grid.getChildren().addAll(newPassword, passwordTitle);
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
				String enteredPassword = newPassword.getText();
				String password = null;
				String sql = "SELECT data FROM progdata WHERE id = '4'";
				try {
					Database.setQuery(sql);
					Database.resultSet = Database.prpStmt.executeQuery();
					password = Database.resultSet.getString(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				password = RSA.decrypt(password);
				
				if(enteredPassword.equals(password)){
					correct = true;
					window.close();
				}
				else{
					AlertBox.display("Invalid", "Wrong Password");
					correct = false;
				}
				
				
				
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
