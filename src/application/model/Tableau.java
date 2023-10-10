package application.model;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Tableau extends CardPile {

	public Tableau() {
		super();
		this.display.setMaxHeight(1000);
//		this.display.setStyle("-fx-background-color: #000000");
	}
	
	@Override
	public void addCard(Card card) {
		// add the card to the arraylist
		super.addCard(card);
		
		// offset it and display it at the tableau
		StackPane cardDisplay = card.getDisplay();
		cardDisplay.setTranslateY((this.getCards().size() - 1) * 15);
		this.getDisplay().getChildren().add(cardDisplay);
		
	}
	
	@Override
	public CardPile transferCard(CardPile dest, Card card) {
		// Transfer card from this pile to another
		dest = super.transferCard(dest, card);	
		
		if (this.getCards().size() != 0) {
			this.getTop().faceUp();
		}
		
		return dest;
	}
	
//	@Override
//	public boolean checkValidSelect(Card selectedCard, CardPile destPile, Card destCard) {
//		if(destCard == null && destPile.getSize() == 0) {
//			// If it is an empty tableau
//			
//			if (selectedCard == null) {
//				// If you select it first
//				return false;
//			}
//			return true;
//		}
//		
//		if(destCard.getIsFaceUp() == false) {
//			// If it is face down
//			return false;
//		} else if(destCard == destPile.getCards().get(destPile.getSize()-1)) {
//			// If it is at the top of the destPile
//			return true;
//		} else {
//			// If it is not at the top of the destPile
//			
//			// Check all cards above selected destCard.
//			int start = destPile.getCardIndex(destCard);
//			
//			for(int i = start; i < destPile.getSize()-1; i++) {
//				if(destPile.getCardAtIndex(i).getValue() - destPile.getCardAtIndex(i+1).getValue() != 1) {
//					// If a destCard is not in descending order it is invalid
//					
//					return false;
//					
//				}
//			}
//			
//			return true;
//		}
//	}
	
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
			/*if(destCard.isBlack && selectedCard.isBlack==false) {
				return true;
			}
			if(destCard.isBlack==false && selectedCard.isBlack) {
				return true;
			}*/
			if(destCard != destPile.getTop()) {
				return false;
			}
			
			if (selectedCard == destCard) {
				// If reselecting card, deselect it.
				return false;
			} else if(destCard.getValue() - selectedCard.getValue() == 1) {
				// If first card is less than 1
				if(destCard.getIsBlack() && selectedCard.getIsBlack()==false) {
					return true;
				}
				else if(destCard.getIsBlack()==false && selectedCard.getIsBlack()) {
					return true;
				}
				else {
					return false;
				}
				
				
//			} else if(destCard.getValue() > selectedCard.getValue()) {
//				// If first card is lower than second card
//				// for testing only
//				return true;
			}
		
		} else if (destPile instanceof Foundation) {
			// If the destination pile is a foundation
			
			if(selectedCard != this.getTop()) {
				return false;
			} else if(destPile.getSize() == 0 && selectedCard.getValue() == 1) {
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
		return "Tableau";
	}
}
