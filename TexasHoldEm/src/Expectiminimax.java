import java.util.ArrayList;
import java.util.Stack;


public class Expectiminimax {
	private boolean rational;
	private boolean verbose;
	private Node head;

	public Expectiminimax(boolean rational, boolean verbose, int x){
		this.rational = rational;
		this.verbose = verbose;
		Tree tree = new Tree();
		head = tree.getHead();
		
		Node path = null;
		if(x % 2 == 0){
			//path = max(head);
		}else{
			//path = min(head);
		}
		

	}
	
	public Node max(Node node){
		//if empty graph, return
		if(node.getChildren().size() == 0){
			return node;
		}
		
		//find highest value
		double h = 0;
		Node high = null;
		Node low = null;
		for(Node n: node.getChildren()){
			if(n.getAverages().getWins() > h){
				high = n;
				h = n.getAverages().getWins();
			}
			low = min(high);
		}
		
		
		return low;
	}
	
	public Node min(Node node){
		//if empty graph, return
		
		//find highest value
		double h = 0;
		Node low = null;
		for(Node n: node.getChildren()){
			if(n.getAverages().getWins() < h){
				low = n;
				h = n.getAverages().getWins();
			}
		}
		Node high = max(low);
		
		return high;
	}

	private void printStats(){

		if(verbose){
			System.out.println("Player 1: " + stringify("c1", 2) + " " + stringify("c1", 2) + " " + "$price"  );
			System.out.println("Player 2: " + stringify("c1", 2) + " " + stringify("c1", 2) + " " + "$price"  );
			System.out.println("Table   : " + stringify("c1", 2) + " " + stringify("c2", 2) + " " + stringify("c3", 2) + " " + stringify("c4", 2));
			System.out.println("Pot     : " + "$price");
			System.out.println();
		}
	}

	public static String stringify(String string, int length) {
		return String.format("%1$"+length+ "s", string);
	}

	public String stringify(int digit, int length) {
		return String.format("%1$"+length+ "d", digit);
	}
}
