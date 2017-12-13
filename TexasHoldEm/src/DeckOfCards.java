
public class DeckOfCards{
	public Card[] deck; 
	private int deckPosition;

	public DeckOfCards(){
		deck = new Card[52]; // how many total
		deckPosition = 0;
		createDeck();
	}

	private void createDeck(){

		String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
		String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

		// intitialize cards
		int counter = 0;
		for(int i = 0; i < suits.length; i++){
			for(int j = 0; j < ranks.length; j++){
				Card c = new Card(ranks[j],suits[i]);
				deck[counter] = c;
				counter++;
			}
		}

		shuffle();

	}

	public void shuffle(){
		// shuffle cards
		for (int i = 0; i < deck.length; i++){
			int index = (int)(Math.random() * deck.length);
			Card x = deck[i]; // x now has all 52 cards?
			deck[i] = deck[index]; // pick a random card 
			deck[index] = x; // pick a radom card

		}
	}

	public Card[] takeXCards(int x){
		Card[] cards = new Card[x];
		int count = 0;
		
		for(int i = deckPosition; i < deckPosition + x; i++){
			Card c = new Card(deck[i].getRank(), deck[i].getSuit());
			//System.out.println("You have a " + deck[i].getRank()+ " of " +  deck[i].getSuit());
			cards[count] = c;
			count++;
		}
		
		deckPosition +=  x;		
		return cards;
	}
	
	public Card[] seeXCards(int x){
		Card[] cards = new Card[x];
		int count = 0;
		
		for(int i = deckPosition; i < deckPosition + x; i++){
			Card c = new Card(deck[i].getRank(), deck[i].getSuit());
			//System.out.println("You have a " + deck[i].getRank()+ " of " +  deck[i].getSuit());
			cards[count] = c;
			count++;
		}
			
		return cards;
	}
	
	public Card[] getRestOfDeck(){
		Card[] cards = new Card[deck.length - deckPosition];
		int count = 0;
		
		for(int i = deckPosition; i < deck.length; i++){
			Card c = new Card(deck[i].getRank(), deck[i].getSuit());
			System.out.println("You have a " + deck[i].getRank()+ " of " +  deck[i].getSuit());
			cards[count] = c;
			count++;
		}
		
		//System.out.println(cards.length);
		return cards;
	}
	
	public Card[] findRemaining(){
		Card[] r = new Card[52 - deckPosition];
		int count = 0;

		for (int i = deckPosition; i < r.length + deckPosition; i++){
			String suit = deck[i].getSuit();
			String rank = deck[i].getRank();
			Card c = new Card(rank, suit);
			r[count] = c;
			count++;
		}

		return r;
	}

	public void printAllCards(){
		int countD = 0,countH = 0, countS = 0, countC = 0;

		// display four cards
		for (int i = 0; i < 52; i++){
			String suit = deck[i].getSuit();
			String rank = deck[i].getRank();
			System.out.println("You have the " + rank + " of " + suit);

			if(suit.equals("Hearts")){
				countH++;
			}else if(suit.equals("Diamonds")){
				countD++;
			}else if(suit.equals("Spades")){
				countS++;
			}else{
				countC++;
			}
		}

		System.out.println("H: " + countH);
		System.out.println("D: " + countD);
		System.out.println("S: " + countS);
		System.out.println("C: " + countC);
	}
}