import java.util.ArrayList;
import java.util.List;

public class Ptree {
	Pnode head;
	Card[] myHand;
	Card[] oppHand;
	Card[] deck;
	Card[] table;
	double pot;
	double average;
	Hand p1Hand;

	public Ptree(Card[] myHand, Card[] oppHand, Card[] table, Card[] remaining, double pot){
		this.myHand = myHand;
		this.oppHand = oppHand;
		this.table = table;
		this.deck = remaining;
		this.pot = pot;
		Card c = null;
		head = new Pnode(c, null, pot);
		constructPTree(head);
	}

	public void constructPTree(Pnode head){

		double total = 0;
		for(Card c: deck){
			double average = getOppHand(c); 
			total = total + average;
		}

		total = total/deck.length;
		this.average = total;
	}

	public double getOppHand(Card c){
		int win = 0;
		int lose = 0;

		List<Pnode> children = new ArrayList<Pnode>();

		for(int i = 0; i < deck.length; i++){
			if(!c.hasSameRank(deck[i]) && !c.hasSameSuit(deck[i]) ){
				for(int j = 0; j < deck.length; j++){
					if(i != j && (!c.hasSameRank(deck[j]) || !c.hasSameSuit(deck[j])) && (!c.hasSameRank(deck[i]) || !c.hasSameSuit(deck[i]))){
						Card[] cards = new Card[2];
						cards[0] = deck[i];
						cards[1] = deck[j];		

						if(calculateWinner(c, cards)){
							win++;
						}else{
							lose++;
						}
					}
				}
			}
		}

		double average = calculateAverage(win, lose);
		return average;
	}

	public double calculateAverage(int win, int lose){
		double total = (pot*win) + ((pot - (2*pot))*lose);
		total = total / (win+lose);

		return total;
	}

	public boolean calculateWinner(Card c, Card[] p2){
		//find all combinations
		//change return value in poker
		Card[] all1 = combine(myHand, c, table);
		Hand one = new Hand(all1);

		Card[] all2 = combine(p2, c, table);
		Hand two = new Hand(all2);

		//System.out.println(one.getFinalScore());
		//System.out.println(two.getFinalScore());
		if(one.getFinalScore() > two.getFinalScore()){
			return true;
		}else if(one.hasCardHigherThan(two)){
			return true;
		}
		return false;
	}

	public void setPot(int pot){
		this.pot = pot;
	}

	public Card[] combine(Card[] c1, Card c3, Card[] c2){
		int x = c1.length + c2.length + 1;
		Card[] c = new Card[x];

		for(int i = 0; i < x; i++){ //this needs to be changed from 5 eventually
			if(i < 2){
				c[i] = c1[i];
			}else if(i < 6){
				c[i] = c2[i - 2];
			}else{
				c[i] = c3;
			}
		}
		return c;
	}

	public double getAverage(){
		return this.average;
	}
}
