package application.model;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

public class Card {
	int value;
	String suit;
	boolean isBlack;
	StackPane display;
	boolean isFaceUp = true;
	static int sizeX = 60;
	static int sizeY = 85;
	
	public Card(int value, String suit) {
		this.value = value;
		this.suit = suit;
		
		if (suit == "Spades" || suit == "Clover") {
			this.isBlack = true;
		} else {
			this.isBlack = false;
		}
		
		StackPane myCard = new StackPane();
		Label cardName = new Label(this.toString());
		if(isBlack==true)	{
			cardName.setTextFill(Color.BLACK);
		}
		else{
			cardName.setTextFill(Color.RED);
		}	
	
		myCard.setAlignment(Pos.TOP_LEFT);;
		myCard.getChildren().add(cardName);
		myCard.setPrefSize(sizeX, sizeY);
		myCard.setMaxSize(sizeX, sizeY);
		myCard.setStyle("-fx-background-color: #FAFAFA;"
				+ "-fx-border-style: solid;"
				+ "-fx-border-width: 3px;");
		
		this.display = myCard;
			
	}	
	
	public static int getSizeX() {
		return sizeX;
	}
	
	public static int getSizeY() {
		return sizeY;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getSuit() {
		return this.suit;
	}
	
	public boolean getIsBlack() {
		return this.isBlack;
	}
	
	public boolean getIsFaceUp() {
		return this.isFaceUp;
	}
	
	public StackPane getDisplay() {
		return this.display;
	}
	
	public void setDisplay(StackPane display) {
		this.display = display;
	}
	
	public void faceUp() {
		StackPane myCard = this.getDisplay();
		Label cardName = new Label(this.toString());
		if(isBlack==true)	{
			cardName.setTextFill(Color.BLACK);
		}
		else{
			cardName.setTextFill(Color.RED);
		}
		
		myCard.getChildren().add(cardName);
		myCard.setStyle("-fx-background-color: #FAFAFA;"
				+ "-fx-border-style: solid;"
				+ "-fx-border-width: 3px;");
		
		this.display = myCard;
		this.isFaceUp = true;
	}
	
	public void faceDown() {
		StackPane myCard = this.getDisplay();
		
		// Removes the label
		myCard.getChildren().remove(myCard.getChildren().get(0));
		
		myCard.setStyle("-fx-background-color: #993232;"
				+ "-fx-border-style: solid;"
				+ "-fx-border-width: 3px;");
		
		this.display = myCard;
		this.isFaceUp = false;
	}
	
	public void makeSelected() {
		StackPane myCard = this.getDisplay();
		
		// Set card design
		myCard.setStyle("-fx-background-color: #444444;"
				+ "-fx-border-style: solid;"
				+ "-fx-border-width: 3px;");
		
		// Set label design
		Label selectedCardText = (Label)myCard.getChildren().get(0);
		if(selectedCardText instanceof Label) {
			selectedCardText.setTextFill(Color.WHITE);
		}
	}
	
	public void makeUnselected() {
		StackPane myCard = this.getDisplay();
		
		// Set card design
		myCard.setStyle("-fx-background-color: #FAFAFA;"
				+ "-fx-border-style: solid;"
				+ "-fx-border-width: 3px;");
		
		// Set label design
		Label selectedCardText = (Label)myCard.getChildren().get(0);
		if(selectedCardText instanceof Label) {
			if(isBlack==true)	{
				selectedCardText.setTextFill(Color.BLACK);
			}
			else{
				selectedCardText.setTextFill(Color.RED);
			}
		}
	}
	
	public String returnName() {
		String transVal;
		
		switch (this.getValue()) {
		case 1:
			transVal = "Ace";
			break;
		case 11:
			transVal = "Jack";
			break;
		case 12:
			transVal = "Queen";
			break;
		case 13:
			transVal = "King";
			break;
		default:
			transVal = "" + this.getValue();
			break;
		}
		
		return transVal + " of " + this.getSuit();
	}
	
	public String toString() {
		String transVal;
		
		switch (this.getValue()) {
		case 1:
			transVal = "A";
			break;
		case 11:
			transVal = "J";
			break;
		case 12:
			transVal = "Q";
			break;
		case 13:
			transVal = "K";
			break;
		default:
			transVal = "" + this.getValue();
			break;
		}
		
		return transVal + this.getSuit().substring(0,1);
	}
	
	
}
