package application.controller;

import application.Main;

import java.io.File;
import java.io.FileWriter;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class WinWindowController {
	
	private Main main;
	
	@FXML TextField nameField;
	@FXML Label scoreLabel;
	@FXML Label winOrNot;
	int score;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	public void submit() {
		if(nameField.getText() == null || nameField.getText() == "") {
			return;
		}
		
		addHighScores(nameField.getText(), score);
		
		Stage stage = (Stage) scoreLabel.getScene().getWindow();
		stage.close();
		
		main.mainMenu();
		
	}
	
	public void initialize(int score, boolean isWin) {
		if(isWin == true) {
			winOrNot.setText("You Win!");
			this.score = score;
			scoreLabel.setText(score + "");
		} else {
			this.score = score;
			scoreLabel.setText(score + "");
		}
	}
	
	private void addHighScores(String name, int score) {
		try {
			File highscores = new File("highscores.csv");
			FileWriter hs = new FileWriter(highscores, true);
			
			hs.write(name + "," + score + ", \n");
			
			hs.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		
	}
}
