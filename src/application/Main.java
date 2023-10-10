package application;
	
import application.controller.HighScoresController;
import application.controller.MainMenuController;
import application.controller.StartMenuController;
import application.controller.WinWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		mainMenu();
		
	}
	
	public void mainMenu() //main scene
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/StartMenu.fxml"));
			AnchorPane startMenuFXML = (AnchorPane) loader.load();
			
			Scene scene = new Scene(startMenuFXML);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Solitaire");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("solitaire.png")));
			primaryStage.show();
			
			StartMenuController startMenuController = loader.getController(); 
			startMenuController.setMain(this);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void openGame() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/PlayingField.fxml"));
			AnchorPane mainMenu = (AnchorPane) loader.load();
			
			Scene scene = new Scene(mainMenu);
			scene.getStylesheets().add("application/application.css");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Solitaire");
			primaryStage.show();
			MainMenuController mainMenuController = loader.getController();
			mainMenuController.startGame();
			mainMenuController.setMain(this);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Stage getStage() {
		return primaryStage;
	}
	
	public void highScores() //press the high scores button
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/HighScores.fxml"));
			AnchorPane highScoresFXML = (AnchorPane) loader.load();
			
			Scene scene = new Scene(highScoresFXML);
			Stage secondaryStage = new Stage();
			secondaryStage.initModality(Modality.WINDOW_MODAL);
			
			secondaryStage.initOwner(primaryStage);
			secondaryStage.setScene(scene);
			secondaryStage.setTitle("Highscores");
			secondaryStage.show();
			
			HighScoresController highScoresController = loader.getController();
			highScoresController.setHighScores();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	// PopUp window
		public void showWin(int score, boolean isWin) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("view/WinPopUp.fxml"));
				AnchorPane popUp = (AnchorPane) loader.load();
				
				Stage secondaryStage = new Stage();
				Scene scene = new Scene(popUp);
				secondaryStage.initModality(Modality.WINDOW_MODAL);
				
				secondaryStage.initOwner(primaryStage);
				secondaryStage.setScene(scene);
				
				WinWindowController winWindowController = loader.getController();
				winWindowController.initialize(score, isWin);
				secondaryStage.show();
				winWindowController.setMain(this);
				
			} catch(Exception ioe) {
				ioe.printStackTrace();
			}
		}
	
	public static void main(String[] args) {
		launch(args);
	}

	public void exitGame() {
		Platform.exit();
		
	}

}
