import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class State {
	int row, col;
	ArrayList<Integer> carry;
	
	public State(int row, int col, ArrayList<Integer> carry){
		this.row = row;
		this.col = col;
		this.carry = carry;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public ArrayList<Integer> getCarry() {
		return carry;
	}

	public void setCarry(ArrayList<Integer> carry) {
		this.carry = carry;
	}
	
	public String hashCode1(){
		
		String row = "" +this.getRow();
		String col = "" + this.getCol();
		return row + col + Arrays.toString(this.getCarry().toArray());
	}

	public boolean equals(State s){
		if(this.row == s.row && this.col == s.col && this.carry.equals(s.carry)){
			return true;
		}
		
		return false;
	}
}
