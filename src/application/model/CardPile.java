package application.model;
import java.util.ArrayList;

import javafx.scene.layout.Pane;

public class CardPile{
	
	ArrayList<Card> cards;
	Pane display;
	double sizeX;
	
	public CardPile() {
		this.cards = new ArrayList<Card>();
		this.display = new Pane();
		this.display.setMinSize(Card.getSizeX(), Card.getSizeY());
		this.display.setMaxSize(Card.getSizeX(), Card.getSizeY());
//		this.display.setStyle("-fx-background-color: #000000");
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public int getSize() {
		return cards.size();
	}
	
	public Pane getDisplay() {
		return this.display;
	}
	
	// Three getters confusing???
	public int getCardIndex(Card card) {
		return this.getCards().indexOf(card);
	}
	
	public Card getCard(Card card) {
		return this.getCards().get(this.getCardIndex(card));
	}
	
	public Card getCardAtIndex(int index) {
		if (index > this.getSize() - 1 || index < 0) {
			return null;
		}
		return this.getCards().get(index);
	}
	
	public void addCard(Card card) {
		this.getCards().add(card);
	}
	
	public Card getTop() {
		if (cards.size() == 0) {
			return null;
		}
		return cards.get(this.getSize()-1);
	}

	public Card removeCard(Card card) {
		int index = this.getCardIndex(card);
		return this.getCards().remove(index);
	}
	
	public Pane displayPile() {
		// temp
//		this.display.setStyle("-fx-background-color: #CCCCCC");
//		this.display.setPrefSize(75, 50);
		return this.display;
	}
	
	public CardPile transferCard(CardPile dest, Card card) {
		// Transfer card from this pile to another
		dest.addCard(this.removeCard(card));
		return dest;
	}
	
	public boolean checkValidSelect(Card selectedCard, CardPile destPile, Card destCard) {
		//    Selection has no restrictions. Subclasses have, will be dealt with
		// in their respective subclasses
		
		return true;
	}
	
	public boolean checkValidDest(Card selectedCard, CardPile destPile, Card destCard) {
		//    Card pile to card pile has no restrictions. Subclasses such as
		// Tableau -> Foundation or Waste -> Tableau, however, have, and will
		// be dealt with in their respective subclasses.
		
		return true;
	}
	
	public String whatAmI() {
		return "CardPile";
	}
}
