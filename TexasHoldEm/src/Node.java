import java.util.List;

public class Node {
	public Node parent;
	public String name;
	public double cash1;
	public double cash2;
	public double pot;
	public boolean toggle;
	public List<Node> children;
	public Averages averages;

	public Node(Node parent, boolean toggle, String name, double cash1, double cash2, double pot, List<Node> children){
		this.parent = parent;
		this.cash1 = cash1;
		this.cash2 = cash2;
		this.pot = pot;
		this.name = name;
		this.children = children;
		this.toggle = toggle;
	}
	
	public Node(Node parent, boolean toggle, String name, double cash1, double cash2, double pot, List<Node> children, Averages averages){
		this.parent = parent;
		this.cash1 = cash1;
		this.cash2 = cash2;
		this.pot = pot;
		this.name = name;
		this.children = children;
		this.toggle = toggle;
		this.averages = averages;
	}

	public Averages getAverages() {
		return averages;
	}

	public void setAverages(Averages averages) {
		this.averages = averages;
	}

	public boolean getToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPot() {
		return pot;
	}

	public void setPot(double pot) {
		this.pot = pot;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public double getCash1() {
		return cash1;
	}

	public void setCash1(double cash1) {
		this.cash1 = cash1;
	}

	public double getCash2() {
		return cash2;
	}

	public void setCash2(double cash2) {
		this.cash2 = cash2;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}
}
