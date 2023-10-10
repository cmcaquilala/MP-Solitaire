package application.model;

import javafx.scene.layout.StackPane;

public class Waste extends CardPile {
	
	@Override
	public void addCard(Card card) {
		// add the card to the arraylist
		super.addCard(card);
		
		// add the display of the card to the waste pile
		card.faceUp();
		StackPane cardDisplay = card.getDisplay();
		this.getDisplay().getChildren().add(cardDisplay);
	}
	
	@Override
	public boolean checkValidDest(Card selectedCard, CardPile destPile, Card destCard) {
		// Check if a card can be transferred from this pile to another pile
		// selectedCard = Source card. The card to be transferred.
		// destPile = Destination pile. The pile where the card would be transferred
		// destCard = Destination card. The exact card in the destination pile where selectedCard will be stacked on.
		
		if(destPile instanceof Tableau) {
		// If the destination Pile is a tableau
		
			if(destCard == null) {
				// Destination is empty tableau
				if (selectedCard.getValue() == 13) {
					// If you selected a king
					return true;
				} else {
					return false;
				}
			}
			
			if(destCard != destPile.getTop()) {
				return false;
			}
			
			if (selectedCard == destCard) {
				// If reselecting card, check if you can place it in a foundation pile.
				return false;
			} else if(destCard.getValue() - selectedCard.getValue() == 1) {
				// If first card is less than 1
				if(destCard.isBlack && selectedCard.isBlack==false) {
					return true;
				}
				else if(destCard.isBlack==false && selectedCard.isBlack) {
					return true;
				}
				else {
					return false;
				}
			}
		
		} else if (destPile instanceof Foundation) {
			// If the destination pile is a foundation
			
			// Removes formatting
			if(destPile.getSize() == 0 && selectedCard.getValue() == 1) {
				// If no card is placed yet and selected card is an ace
				return true;
			} else if (destPile.getSize() == 0 && selectedCard.getValue() != 1) {
				// If no card is placed yet and selected card is not an ace
				return false;
			} else if (selectedCard.getSuit() == destCard.getSuit() && selectedCard.getValue() - destCard.getValue() == 1) {
				// If there is already a card and selected card is +1 and same suit
				return true;
//			} else if(destCard.getValue() < selectedCard.getValue()) {
//				// If first card is lower than second card
//				// for testing only
//				return true;
			}
		}
	// if destination is waste or stock, not allowed
	
		return false;
	}
	
	@Override
	public String whatAmI() {
		return "Waste";
	}
}
