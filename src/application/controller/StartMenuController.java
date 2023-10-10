package application.controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartMenuController {
	
	private Main main;
	
	@FXML
	private Button NewGame;
	
	@FXML
	private Button HighScores;
	
	@FXML
	private Button Exit;
	
	
	public void setMain(Main main)
	{
		this.main = main;
	}
	
	@FXML
	private void newGame() //play a new game of Solitaire
	{
		main.openGame();
	}
	
	@FXML
	private void openScores() //display the top 10 scores in a new window
	{
		main.highScores();
	}
	
	@FXML
	private void exitGame() //exit the program
	{
		main.exitGame();
	}
}
