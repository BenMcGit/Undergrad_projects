/**
 * Author:Ben McAdams 
 * 
 * This class creates my Huffman tree. It contains a few
 * methods to get the components together and call the binaryHeap class My
 * Huffman tree uses nodes that contain both the character and the frequency to
 * reference the components of my tree There is a separate node class on the
 * bottom
 * **/
public class HuffmanTree {

	public BinaryHeap heap;

	public HuffmanTree() {
		heap = new BinaryHeap();
	}

	public void add(char c, int i) {
		Node node = new Node(c, i);
		heap.insert(node);
	}

	public void createHuff() {
		// when the size is less then 1, stop looping
		while (heap.size() > 1) {

			// get first and second node
			Node n1 = (Node) heap.deleteMin();
			Node n2 = (Node) heap.deleteMin();

			int freq = n1.frequency + n2.frequency;
			Node n = new Node(' ', freq, n1, n2);

			// insert back into the heap
			heap.insert(n);
		}
	}

	public void printHuff() {
		printHuff((Node) heap.findMin(), "");
	}

	/**
	 * recursive printing function
	 * 
	 * @param root
	 *            the current node we are working on
	 * @param path
	 *            the string of the huffman tree
	 */
	private void printHuff(Node root, String path) {

		// if there is nothing in the heap it will immediately return
		if (root == null) {
			return;
		}

		// check if the left and right nodes are null
		if (root != null && root.left == null && root.right == null) {
			System.out.println("Character: " + root.character + " Path: "
					+ path);
			return;
		}

		// checks my left path. Concatenates 1 to my string
		printHuff(root.left, path + "1");

		// checks my right path. Concatenates 0 to my string
		printHuff(root.right, path + "0");
	}

	/**
	 * Implements compareable class so the nodes can be ordered
	 * 
	 * @author Ben
	 *
	 */
	public class Node implements Comparable<Node> {
		public char character;
		public int frequency;
		public Node left;
		public Node right;

		public Node(char c, int i, Node l, Node r) {
			character = c;
			frequency = i;
			left = l;
			right = r;
		}

		public Node(char c, int i) {
			character = c;
			frequency = i;
		}

		/**
		 * Overriding the comparable function Automatically orders the nodes
		 */
		@Override
		public int compareTo(Node o) {
			// compare by frequency
			if (o.frequency < this.frequency) {
				return 1;
			} else if (o.frequency > this.frequency) {
				return -1;
			}
			return 0;
		}
	}

}
