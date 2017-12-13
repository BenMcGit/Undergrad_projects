
public class TestCases {
	
	public static void main(String[] args){
		
		Tree tree = new Tree();
		Node head= tree.getHead();
		
		for(Node n: head.getChildren()){
			System.out.println("-------");
			System.out.println(n.getAverages().wins);
			System.out.println(n.getAverages().lose1);
			System.out.println(n.getAverages().lose2);
		}
		
		
		
		
	/*	Tree tree = new Tree();
		Node head = tree.getHead();
		for(Node n: head.getChildren()){
			System.out.println(n.getName());
			for(Node x: n.getChildren()){
				System.out.println("----"+x.getName());
				for(Node p: x.getChildren()){
					System.out.println("--------"+ p.getName());
					for(Node g: p.getChildren()){
						System.out.println("------------"+ g.getName());
						for(Node d: g.getChildren()){
							System.out.println("-----------------"+ d.getName());
							for(Node m: d.getChildren()){
								System.out.println("---------------------"+ m.getName());
							}
						}
					}
				}
			}
		}*/
		
		/*DeckOfCards deck = new DeckOfCards();
		Card[] table = deck.takeXCards(4);
		System.out.println();
		Card[] p1 = deck.takeXCards(2);
		System.out.println();
		Card[] p2 = deck.seeXCards(2);
		System.out.println();
		Card[] rest = deck.getRestOfDeck();*/
		
		
		//System.out.println("p1: " + p1.length);
		//System.out.println("p2: " + p2.length);
		//System.out.println("rest: " + rest.length);
	}
}
