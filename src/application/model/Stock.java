package application.model;
import java.util.Collections;

import javafx.scene.layout.StackPane;

public class Stock extends CardPile {

	public Stock() {
		for(int i = 0; i < 4; i++) {
			String suit = "";
			switch (i) {
			case 0:
				suit = "Spades";
				break;
			case 1:
				suit = "Clover";
				break;
			case 2:
				suit = "Hearts";				
				break;
			default:
				suit = "Diamonds";
				break;
			}
			
			for(int j = 0; j < 13; j++) {
				cards.add(new Card(j+1, suit));
			}
			
			this.shuffleDeck();
		}
	}
	
	@Override
	public void addCard(Card card) {
		// add the card to the arraylist
		super.addCard(card);
		
		// add the display of the card to the stock
		card.faceDown();
		StackPane cardDisplay = card.getDisplay();
		this.getDisplay().getChildren().add(cardDisplay);
	}
	
	public void shuffleDeck() {
		Collections.shuffle(cards);
	}
	
}
