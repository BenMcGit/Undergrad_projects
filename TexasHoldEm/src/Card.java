
public class Card {
	public String rank;
	public String suit;
	
	public Card(String rank, String suit){
		this.rank = rank;
		this.suit = suit;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public boolean hasSameRank(Card c){
		return this.getRank().equals(c.getRank());
	}
	
	public boolean hasSameSuit(Card c){
		return this.getSuit().equals(c.getSuit());
	}
	
}
