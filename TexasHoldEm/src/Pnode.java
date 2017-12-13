import java.util.List;

public class Pnode {
	public Card river;
	public Card[] oppHand;
	public List<Pnode> children;
	public double score;
	
	public Pnode(Card c, List<Pnode> children, double score){
		this.river = c;
		this.children = children;
		this.score = score;
	}
	
	public Pnode(Card[] c, List<Pnode> children, double score){
		this.oppHand = c;
		this.children = children;
		this.score = score;
	}

	public Card getRiver() {
		return river;
	}

	public void setRiver(Card river) {
		this.river = river;
	}

	public Card[] getOppHand() {
		return oppHand;
	}

	public void setOppHand(Card[] oppHand) {
		this.oppHand = oppHand;
	}

	public List<Pnode> getChildren() {
		return children;
	}

	public void setChildren(List<Pnode> children) {
		this.children = children;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
