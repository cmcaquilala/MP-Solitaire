package application.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import application.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HighScoresController {
	@FXML VBox hsVBoxName;
	@FXML VBox hsVBoxScores;
	
	String hs = "";
	ArrayList<Player> players = new ArrayList<Player>();
	
	
	public void setHighScores() throws FileNotFoundException{
	
		try {
			BufferedReader br = new BufferedReader(new FileReader("highscores.csv"));
			while ((hs = br.readLine()) != null) {
				String[] highscores = hs.split(",");
				
				Player player = new Player(highscores[0], Integer.parseInt(highscores[1]));
				players.add(player);
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.sort(players);
		
		for (int i = 0; i < players.size(); i++) {
			if (i == 10) {
				break;
			}
			
			hsVBoxName.getChildren().add(new Label(players.get(i).getName()));
			hsVBoxScores.getChildren().add(new Label(players.get(i).getScore().toString()));
		}
		
	}
}