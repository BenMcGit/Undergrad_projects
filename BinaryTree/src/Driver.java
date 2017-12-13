/**Author: Ben McAdams
 * This is my testing class. It contains 3 examples, one is the one Mao has on her slides just to show it works the way she wants. The
 * next two examples show it works the way i want. It has one where all the frequencies are the same.*/
public class Driver {
	
	public static void main(String[] args){
		new Driver();
	}
	public Driver(){
		
		//Mao's Example 
		HuffmanTree tree = new HuffmanTree();			/**EXPECTED FREQUENCYS(NOTE= print is not sorted same as they are added in**/
		tree.add('e', 103);									//01 (Character: e Path: 01)
		tree.add('a', 66);									//11 (Character: a Path: 11)
		tree.add('i', 58);									//000
		tree.add('r', 46);									//100
		tree.add('d', 35);									//101
		tree.add('l', 32);									//0010
		tree.add('y', 13);									//00110
		tree.add('v', 7);									//00111
		tree.createHuff();
		tree.printHuff();
		System.out.println("");
		
		//Example 1
		HuffmanTree tree2 = new HuffmanTree();
		tree2.add('B', 23);									//11
		tree2.add('E', 23);									//100
		tree2.add('N', 12);									//1010
		tree2.add('M', 70);									//0
		tree2.add('C', 2);									//1011
		tree2.createHuff();
		tree2.printHuff();
		System.out.println("");
		
		//Example 2 (All the same frequencies)
		HuffmanTree tree3 = new HuffmanTree();
		tree3.add('B', 4);									//11
		tree3.add('E', 4);									//100
		tree3.add('N', 4);									//1010
		tree3.add('M', 4);									//0
		tree3.add('C', 4);									//1011
		tree3.createHuff();
		tree3.printHuff();
		System.out.println("");		
	}
}
