package application.controller;

import application.Main;
import application.model.*;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
//import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
//import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import java.util.ArrayList;


public class MainMenuController {
	
	private Main main;
	
	@FXML private MenuItem Menu;
	@FXML private MenuItem Highscores;
	@FXML private HBox foundation;
	@FXML private HBox tableau;
	@FXML private StackPane waste;
	@FXML private StackPane stock;
	@FXML private Label scoreLabel;
	//@FXML private TextField nameField;
	
	private boolean isWin = false;
	private boolean isGameStart=false; 
	private Card selectedCard = null;
	private CardPile selectedPile = null;
	private ArrayList<Foundation> arrFoundation = new ArrayList<Foundation>();
	private ArrayList<Tableau> arrTableau = new ArrayList<Tableau>();
	
	private int score = 0;
	
	// Creating a new deck pile
	Stock deck = new Stock();
	Waste newWaste = new Waste();
	
	//checks if stock and waste are empty
	private boolean isGameNearEnd=false;
	
	
	// temp
	@FXML
	public void startGame() {
		//prevents duplication of Tableau, Foundation, atbp.
		if(isGameStart==true) {
			main.showWin(score, isWin);
			
			tableau.getChildren().clear();
			foundation.getChildren().clear();
			waste.getChildren().clear();
			stock.getChildren().clear();
			
			arrFoundation.clear();
			arrTableau.clear();
			
			isGameNearEnd = false;
			
			deck = new Stock();
			newWaste = new Waste();
			
			score = 0;
			scoreLabel.setText("Score: " + score);
		}
		
		// Creating a new deck pile
		 //deck = new Stock();
		
		// Setting up the Tableau
		for(int i = 0; i < 7; i++) {
			Tableau newTableau = new Tableau();
			Pane newDisplay = newTableau.displayPile();
			newDisplay.setOnMouseClicked(e -> selectCard(newTableau, null));
			
			for(int j = 0; j < i; j++) {
				Card newCard = deck.removeCard(deck.getTop());
				newCard.faceDown();
				
				StackPane newCardDisplay = newCard.getDisplay();
				newCardDisplay.setOnMouseClicked(e -> selectCard(newTableau, newCard));
				newCard.setDisplay(newCardDisplay);
				
				newTableau.addCard(newCard);
			}
			Card newCard = deck.removeCard(deck.getTop());
			
			StackPane newCardDisplay = newCard.getDisplay();
			newCardDisplay.setOnMouseClicked(e -> selectCard(newTableau, newCard));
			newCard.setDisplay(newCardDisplay);
			
			newTableau.addCard(newCard);
			tableau.getChildren().add(newTableau.displayPile());
			arrTableau.add(newTableau);
		}
		// Setting up the foundation piles
		for(int i = 0; i < 4; i++) {
			Foundation newFoundation = new Foundation();
			Pane newDisplay = newFoundation.displayPile();
			newDisplay.setOnMouseClicked(e -> selectCard(newFoundation, null));
//			newDisplay.setMaxHeight(Card.getSizeY());
			
			foundation.getChildren().add(newFoundation.displayPile());
			arrFoundation.add(newFoundation);
		}
		
		// Setting up the waste pile
		//newWaste = new Waste();
//		Pane newDisplay = newWaste.displayPile();
//		newDisplay.setOnMouseClicked(e -> selectCard(newWaste, null));			// unnecessary?
		
		waste.getChildren().add(newWaste.displayPile());
		
		// Setting up the stock pile using the remaining cards
		Pane newDisplay = deck.displayPile();
		newDisplay.setOnMouseClicked(e -> cycleStock(deck, newWaste));
		
		for (Card newCard : deck.getCards()) {
			newCard.faceDown();
			StackPane cardDisplay = newCard.getDisplay();
			deck.getDisplay().getChildren().add(cardDisplay);
		}
		
		stock.getChildren().add(deck.displayPile());
		
		isGameStart=true;
		
	}
	
	private void cycleStock(CardPile stock, CardPile waste) {
		if (selectedCard != null) {
			selectedCard.makeUnselected();
			selectedCard = null;
			selectedPile = null;
		}
		
		if (stock.getSize() == 0) {
			// If stock is already empty, recycle all from waste.
			int wasteSize = waste.getSize();
			for(int i = 0; i < wasteSize; i++) {
//				waste.getDisplay().getChildren().remove(0);
				waste.transferCard(stock, waste.getTop());
			}
//			for(Card card : waste.getCards()) {
//				card.getDisplay().setOnMouseClicked(e -> cycleStock(stock, waste));
//				waste.transferCard(stock, card);
//			}
			
			updateScore(stock, waste);
		}
		else {
			if(waste.getCards()==null){
				return;
			}else {
				Card card = stock.getTop();
				card.getDisplay().setOnMouseClicked(e -> selectCard(waste, card));
				stock.transferCard(waste, stock.getTop());
			}
			
		}
		
	}
	
	private void selectCard(CardPile pile, Card card) {
		if (card == null && pile.getSize() != 0) {
			// If you select a pile with cards in it,
			// Card will handle it and not the pile (???)
			return;
		}
		
		if (checkValidSelect(pile, card)) {
			// If selecting this card is valid
			if (selectedCard == null) {
				// If no cards are selected yet, select this card.
				selectedCard = card;
				selectedPile = pile;
				
				// Change appearance of card
				selectedCard.makeSelected();
			} else {
//				 If a card was already selected.
				
				if (selectedCard == card) {
					// If you double-clicked a card

					for (int i = 0; i < arrFoundation.size(); i++) {
						// Check all foundations if you can move it there.
						Foundation curr = arrFoundation.get(i);
						if (selectedPile.checkValidDest(selectedCard, curr, curr.getTop())) {
							// If you can, move it.
							updateScore(selectedPile, curr);
							selectedCard.getDisplay().setTranslateY(0);
							selectedPile.transferCard(curr, selectedCard);
							
							// Rebind the card's pane to the class
							Card newCard = curr.getTop();
							StackPane cardDisplay = newCard.getDisplay();
							cardDisplay.setOnMouseClicked(e -> selectCard(curr, newCard));
							curr.getCard(newCard).setDisplay(cardDisplay);
							
							selectedCard.makeUnselected();
							selectedCard = null;
							selectedPile = null;
							
							isGameEnd();
							
							return;
						}
					}
					
					for (int i = 0; i < arrTableau.size(); i++) {
						// Check all tableau if you can move it there.
						Tableau curr = arrTableau.get(i);
						if (selectedPile.checkValidDest(selectedCard, curr, curr.getTop())) {
							// If you can, move it.
							updateScore(selectedPile, curr);
							selectedCard.getDisplay().setTranslateY(0);
//							selectedPile.transferCard(curr, selectedCard);
							
							int start = selectedPile.getCardIndex(selectedCard);
							while (selectedPile.getCardAtIndex(start) != null) {
								
								// Transfer the card(s)
								selectedPile.transferCard(curr, selectedPile.getCardAtIndex(start));
								
								// Rebind the card's pane to the class
								Card newCard = curr.getTop();
								StackPane cardDisplay = newCard.getDisplay();
								cardDisplay.setOnMouseClicked(e -> selectCard(curr, newCard));
								curr.getCard(newCard).setDisplay(cardDisplay);
							}
							
							selectedCard.makeUnselected();
							selectedCard = null;
							selectedPile = null;
							
							isGameEnd();
							
							return;
						}
					}
					
				} else if (selectedPile.checkValidDest(selectedCard, pile, card)) {
					// If the second card is a valid spot
					updateScore(selectedPile, pile);
					// Resets formatting
					selectedCard.getDisplay().setTranslateY(0);
					
					// Get the selected card's index as starting point of transfer
					int start = selectedPile.getCardIndex(selectedCard);
					while (selectedPile.getCardAtIndex(start) != null) {
						
						// Transfer the card(s)
						selectedPile.transferCard(pile, selectedPile.getCardAtIndex(start));
						
						// Rebind the card's pane to the class
						Card newCard = pile.getTop();
						StackPane cardDisplay = newCard.getDisplay();
						cardDisplay.setOnMouseClicked(e -> selectCard(pile, newCard));
						pile.getCard(newCard).setDisplay(cardDisplay);
					}
					
					isGameEnd();
					
				} else {
					// If the second card is not a valid spot
					
					// do nothing
				}
				
				// Deselect cards.

				selectedCard.makeUnselected();
				selectedCard = null;
				selectedPile = null;
				
				return;
			}
			
		}
		// Select is invalid. Do nothing.
	}
	
	private boolean checkValidSelect(CardPile pile, Card card) {
		// selectedCard = global variable for previously selected card
		// selectedPile = global variable for pile of selectedCard
		// card = currently selected card
		// pile = pile of card
		
		// add conditions if you can select the card
		// (ex. - it is at the top of the tableau,
		// - it is at the top of the waste or foundation, or
		// - it is at the middle of the tableau, but the 
		//      cards below it are in Red-Black descending order.
		
		// temp
		
		if(card == null && pile.getSize() == 0) {
			// If it is an empty pile
			
			if (selectedCard == null) {
				// If you select it first
				return false;
			}
			return true;
		}			
		
		if(card.getIsFaceUp() == false) {
			// If it is face down
			return false;
		} else if(card == pile.getTop()) {
			// If it is at the top of the pile
			return true;
		} else {
			// If it is not at the top of the pile
			
			// Check all cards above selected card.
			int start = pile.getCardIndex(card);
			if (pile.getCardAtIndex(start + 1) == null) {
				return true;
			}
			
			for(int i = start; i < pile.getSize()-1; i++) {
				if(pile.getCardAtIndex(i).getValue() - pile.getCardAtIndex(i+1).getValue() != 1) {
					// If a card is not in descending order it is invalid
					return false;
				} else if (!pile.getCardAtIndex(i).getIsBlack() && !pile.getCardAtIndex(i+1).getIsBlack()) {
					// If there are two consecutive reds, it is invalid
					return false;
				} else if (pile.getCardAtIndex(i).getIsBlack() && pile.getCardAtIndex(i+1).getIsBlack()) {
					// If there are two consecutive blacks, it is invalid
					return false;
				}
			}
			
			return true;
			
		}

	}
	
	private void updateScore(CardPile selectedPile, CardPile pile) {
		if (selectedPile instanceof Waste && pile instanceof Foundation) {
			score += 10;
			scoreLabel.setText("Score: " + score);
		} else if (selectedPile instanceof Tableau && pile instanceof Foundation) {
			if (selectedPile.getSize() > 1){
				score += 15;
				scoreLabel.setText("Score: " + score);
			} else if (selectedPile.getSize() == 1){
				
			} else if (!selectedPile.getCards().get(selectedPile.getSize() - 2).getIsFaceUp()){
				score += 15;
				scoreLabel.setText("Score: " + score);
			}
		} else if (selectedPile instanceof Waste && pile instanceof Tableau) {
			score += 5;
			scoreLabel.setText("Score: " + score);
		} else if (selectedPile instanceof Tableau && pile instanceof Tableau) {
			if (selectedPile.getSize() > 1){
				score += 5;
				scoreLabel.setText("Score: " + score);
			} else if (selectedPile.getSize() == 1){
				
			} else if (!selectedPile.getCards().get(selectedPile.getSize() - 2).getIsFaceUp()){
				score += 5;
				scoreLabel.setText("Score: " + score);
			}
		} else if (selectedPile instanceof Foundation && pile instanceof Tableau) {
			score -= 15;
			if (score < 0) {
				score = 0;
			}
			scoreLabel.setText("Score: " + score);
		} else if (selectedPile instanceof CardPile && pile instanceof Waste) {
			score -= 100;
			if (score < 0) {
				score = 0;
			}
			scoreLabel.setText("Score: " + score);
		}		
	}
	
	private void isGameEnd() {
		boolean isfacedown=false;
		for (int i = 0; i < arrTableau.size(); i++) {
			Tableau curr= arrTableau.get(i);
			for(int j=0; j<curr.getCards().size();j++) {
				//Checks all the cards if it is face up or facedown
				Card card =  curr.getCardAtIndex(j);
				if(card.getIsFaceUp()==true) {
				}else {
					isfacedown=true;
				}
				
			}
		}
		if(deck.getSize()==0 && newWaste.getSize()==0) {
			isGameNearEnd=true;
		}
		if (isfacedown==false && isGameNearEnd==true) {
			int tcardcount=0; // counts the cards in the tableau
			
			for (int i = 0; i < arrTableau.size(); i++) {
				Tableau curr= arrTableau.get(i);
				tcardcount+=curr.getCards().size();
			}
			
			while(tcardcount!=0) {
				for (int i = 0; i < arrTableau.size(); i++) {
					Tableau curr= arrTableau.get(i);
				
					for(int j=0; j<curr.getCards().size();j++) {
						
						Card card =  curr.getCardAtIndex(j);
						selectedCard=card;
						selectedPile=curr;
						
						for (int k = 0; k < arrFoundation.size(); k++) {
						// Check all foundations if you can move it there.
							Foundation newcurr = arrFoundation.get(k);
							if (selectedPile.checkValidDest(selectedCard, newcurr, newcurr.getTop())) {
							// If you can, move it.
								selectedCard.getDisplay().setTranslateY(0);
								selectedPile.transferCard(newcurr, selectedCard);
							
							// Rebind the card's pane to the class
							//Card newCard = newcurr.getTop();
							//StackPane cardDisplay = newCard.getDisplay();
							//curr.getCard(newCard).setDisplay(cardDisplay);
							
								updateScore(selectedPile, newcurr);
								
								selectedCard.makeUnselected();
								tcardcount--;
							}
						}
					}
				}
				
			}
			isWin = true;
			main.showWin(score, isWin);
			//addHighScores(score);
			
			System.out.println("WINNER WINNER CHICKEN DINNER!");
		}
	}
	
	public void setMain(Main main)
	{
		this.main = main;
	}
	
	@FXML
	private void backMenu() {
		main.mainMenu();
	}
	
	@FXML
	private void openScores() //display the top 10 scores in a new window
	{
		main.highScores();
	}
	
}
