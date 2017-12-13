package operations;

public abstract class AbstractDigitalImage implements DigitalImage {
	protected int width, height, bands, depth;

	public AbstractDigitalImage(int w, int h, int b){
		width = w;
		height = h;
		bands = b;
		
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getNumberOfBands(){
		return bands;
	}
	public void setDepth(int dp){
		depth= dp;
	}
	public int getDepth(){
		return depth;
	}
	
}
