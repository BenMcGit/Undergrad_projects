import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Tree {
	public Node head;
	public DeckOfCards deck;
	public Card[] cards1;
	public Card[] cards2;
	public Card[] table;
	public Card[] rest;

	public Tree(){
		deck = new DeckOfCards();
		cards1 = deck.takeXCards(2);
		cards2 = deck.takeXCards(2);
		table = deck.takeXCards(4);
		rest = deck.findRemaining();
		
		head = new Node(null, true, "head", 1500.0, 1500.0, 500.0, null);
		constructTree(head);
	}

	public void constructTree(Node head){
		List<Node> children = new ArrayList<Node>();

		children.add(addCheck(head));
		children.add(addBet(head));
		children.add(addAllIn(head));

		head.setChildren(children);
	}
	
	public Node getHead(){
		return this.head;
	}

	private Node addCheck(Node node){

		List<Node> children = new ArrayList<Node>();
		Node check = new Node(node, !node.getToggle(),"check", node.getCash1(), node.getCash2(), node.getPot(), null);

		if(node.getName().equals("check")){
			//probability
			Averages average = createAverageNode(node.getCash1(), node.getCash2(), node.getPot());
			Node prob = new Node(node, !node.getToggle(),"prob", node.getCash1(), node.getCash2(), node.getPot(), null, average);
			prob.setChildren(children);
			children.add(prob);
		}else{
			children.add(addCheck(check));
			children.add(addBet(check));
			children.add(addAllIn(check));
		}
		
		//calculate averages
		check.setChildren(children);
		double pot = 0, lose1 = 0, lose2 = 0, count = 0;
		for(Node x: check.children){
			pot += x.getAverages().getWins();
			lose1 += x.getAverages().getLose1();
			lose2 += x.getAverages().getLose2();
			count++;
		}
		
		check.setAverages(new Averages(pot/count, lose1/count, lose2/count));
		
		return check;
	}

	private Node addBet(Node node){

		double cash_p1 = node.getCash1(), cash_p2 = node.getCash2(), pot = node.getPot();
		if(node.getToggle()){
			cash_p1 -= 250;
		}else{
			cash_p2 -= 250;
		}
		pot += 250;

		List<Node> children = new ArrayList<Node>();
		Node bet = new Node(node, !node.getToggle(),"bet", cash_p1, cash_p2, pot, null);

		children.add(addCall(bet));
		children.add(addRaise(bet));
		children.add(addFold(bet));
		children.add(addAllIn(bet));

		bet.setChildren(children);
		
		double pot1 = 0, lose1 = 0, lose2 = 0, count = 0;
		for(Node x: bet.children){
			pot1 += x.getAverages().getWins();
			lose1 += x.getAverages().getLose1();
			lose2 += x.getAverages().getLose2();
			count++;
		}
		
		bet.setAverages(new Averages(pot1/count, lose1/count, lose2/count));
		return bet;
	}

	private Node addRaise(Node node){

		double cash_p1 = node.getCash1(), cash_p2 = node.getCash2(), pot = node.getPot(), add = 0;
		if(node.getName().equals("raise")){
			add = 1000;
		}else{
			add = 500;
		}

		//check to see if all-in is necessary
		if(node.getToggle() && node.getCash1() < add){
			add = node.getCash1();
		}else if(!node.getToggle() && node.getCash2() < add){
			add = node.getCash2();
		}

		if(node.getToggle()){
			cash_p1 -= add;
		}else{
			cash_p2 -= add;
		}
		pot += add;

		List<Node> children = new ArrayList<Node>();
		Node raise = new Node(node, !node.getToggle(), "raise", cash_p1, cash_p2, pot, null);

		if(raise.getCash1() == 0 || raise.getCash2() == 0){
			//probability
			Averages average = createAverageNode(node.getCash1(), node.getCash2(), node.getPot());
			Node prob = new Node(node, !node.getToggle(), "prob", cash_p1, cash_p2, pot, null, average);
			prob.setChildren(children);
			children.add(prob);
		}else{
			children.add(addCall(raise));
			children.add(addRaise(raise));
			children.add(addFold(raise));
			children.add(addAllIn(raise));	
		}

		raise.setChildren(children);
		
		double pot1 = 0, lose1 = 0, lose2 = 0, count = 0;
		for(Node x: raise.children){
			pot1 += x.getAverages().getWins();
			lose1 += x.getAverages().getLose1();
			lose2 += x.getAverages().getLose2();
			count++;
		}
		
		raise.setAverages(new Averages(pot1/count, lose1/count, lose2/count));
		return raise;
	}

	private Node addAllIn(Node node){

		double cash_p1 = node.getCash1(), cash_p2 = node.getCash2(), pot = node.getPot(), add = 0;
		if(node.getToggle() && node.getCash1() < add){
			add = node.getCash1();
		}else if(!node.getToggle() && node.getCash2() < add){
			add = node.getCash2();
		}
		pot +=add;

		List<Node> children = new ArrayList<Node>();
		Node allin = new Node(node, !node.getToggle(),"allin", cash_p1, cash_p2, pot, null);

		if(node.getName().equals("allin")){
			//probability
			Averages average = createAverageNode(node.getCash1(), node.getCash2(), node.getPot());
			Node prob = new Node(node, !node.getToggle(), "prob", cash_p1, cash_p2, pot, null, average);
			prob.setChildren(children);
			children.add(prob);
		}else{
			children.add(addAllIn(allin));
			children.add(addFold(allin));
		}

		allin.setChildren(children);
		
		double pot1 = 0, lose1 = 0, lose2 = 0, count = 0;
		for(Node x: allin.children){
			pot1 += x.getAverages().getWins();
			lose1 += x.getAverages().getLose1();
			lose2 += x.getAverages().getLose2();
			count++;
		}
		
		allin.setAverages(new Averages(pot1/count, lose1/count, lose2/count));
		return allin;
	}

	private Node addFold(Node node){
		double cash_p1 = node.getCash1(), cash_p2 = node.getCash2(), pot = node.getPot(), add = 0;

		List<Node> children = new ArrayList<Node>();
		Node fold = new Node(node, !node.getToggle(),"fold", cash_p1, cash_p2, pot, null);
		
		//never calculate probability
		Averages average = new Averages(cash_p1, cash_p2, pot);
		Node prob = new Node(node, !node.getToggle(), "prob", cash_p1, cash_p2, pot, null, average);
		prob.setChildren(children);
		
		children.add(prob);
		fold.setChildren(children);
		
		double pot1 = 0, lose1 = 0, lose2 = 0, count = 0;
		for(Node x: fold.children){
			pot1 += x.getAverages().getWins();
			lose1 += x.getAverages().getLose1();
			lose2 += x.getAverages().getLose2();
			count++;
		}
		fold.setAverages(new Averages(pot1/count, lose1/count, lose2/count));
		return fold;
	}

	private Node addCall(Node node){ 
		double cash_p1 = node.getCash1(), cash_p2 = node.getCash2(), pot = node.getPot(), diff = node.getParent().getPot() - node.getPot();
		
		if(node.getToggle()){
			cash_p1 -= diff;
		}else{
			cash_p2 -= diff;
		}
		pot += diff;

		List<Node> children = new ArrayList<Node>();
		Node call = new Node(node, !node.getToggle(),"call", cash_p1, cash_p2, pot, null);

		Averages average = createAverageNode(cash_p1, cash_p2, pot);
		Node prob = new Node(node, !node.getToggle(), "prob", cash_p1, cash_p2, pot, null, average);
		prob.setChildren(children);
		children.add(prob);
		
		call.setChildren(children);
		double pot1 = 0, lose1 = 0, lose2 = 0, count = 0;
		for(Node x: call.children){
			pot1 += x.getAverages().getWins();
			lose1 += x.getAverages().getLose1();
			lose2 += x.getAverages().getLose2();
			count++;
		}
		call.setAverages(new Averages(pot1/count, lose1/count, lose2/count));
		return call;
	}
	
	private Averages createAverageNode(double p1, double p2, double pot){
		p1 = 1500 - p1;
		p2 = 1500 - p2;
		
		Ptree tree = new Ptree(cards1, cards2, table, rest, p1);
		double averageWin = tree.getAverage();
		tree = new Ptree(cards1, cards2, table, rest, p2);
		double p1AverageLoss = tree.getAverage();
		tree = new Ptree(cards1, cards2, table, rest, pot);
		double p2AverageLoss = tree.getAverage();
		
		Averages averages = new Averages(averageWin,p1AverageLoss,p2AverageLoss);
		
		return averages;
	}

}
