package operations;

public class Location {
	public int row, col, band;
	
	public Location(int col, int row, int band){
		this.col = col;
		this.row = row;
		this.band = band;	
	}
	
	public Location(){
		this(0,0,0);
	}
}
