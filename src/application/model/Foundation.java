package application.model;

import javafx.scene.layout.StackPane;

public class Foundation extends CardPile {
	
	@Override
	public void addCard(Card card) {
		// add the card to the arraylist
		super.addCard(card);
		
		// display it at the foundation
		StackPane cardDisplay = card.getDisplay();
		
		this.getDisplay().getChildren().add(cardDisplay);
		
		// note: kailangan bang i-delete yung cards sa likod niya
		// or ok nang hindi kasi natatakpan naman at unclickable? haha
	}
	
	@Override
	public boolean checkValidDest(Card selectedCard, CardPile destPile, Card destCard) {
		// Check if a card can be transferred from this pile to another pile
		// selectedCard = Source card. The card to be transferred.
		// destPile = Destination pile. The pile where the card would be transferred
		// destCard = Destination card. The exact card in the destination pile where selectedCard will be stacked on.
		
		if(destPile instanceof Tableau) {
			// If the destination pile is a tableau
			if (destCard.getValue() - selectedCard.getValue() == 1) {
				if (destCard.getIsBlack() && selectedCard.getIsBlack() == false) {
					return true;
				} else if (destCard.getIsBlack() == false && selectedCard.getIsBlack()) {
					return true;
				} else {
					return false;
				}
			}
		}
		// If destination is waste, stock, or other foundation, not allowed
		
		return false;
	}
	
	@Override
	public String whatAmI() {
		return "Foundation";
	}
}
