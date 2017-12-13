
public class ProbCard {
	  public enum Suits{
		  diamonds,
		  hearts,
		  spades,
		  clubs
	  }
	  Suits suit;
	  int rank;
	  
	  public ProbCard(Suits suits, int rank){
		  this.suit = suits;
		  this.rank = rank;
	  }

	public Suits suit() { //getSuit
		return suit;
	}

	public void setSuit(Suits suit) {
		this.suit = suit;
	}

	public int face() { //getRank
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public boolean sameSuit(ProbCard s){
		return this.suit().equals(s.suit());
	}
	
	public boolean sameFace(ProbCard s){
		return this.face() == s.face();
	}
}
