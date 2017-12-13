package operations;

public interface DigitalImage {
	public int getWidth();
	public int getHeight();
	public int getNumberOfBands();
	
	public int[] getPixel(int x, int y);
	public void setPixel(int x, int y, int [] pixel);
	public int getSample(int x, int y, int band);
	public void setSample(int x, int y, int band, int sample);
	public void setDepth(int depth);
	public int getDepth();
}
