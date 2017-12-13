package operations;

public class ArrayDigitalImage extends AbstractDigitalImage{
	private int[][][] raster;
	
	public ArrayDigitalImage(int width, int height, int bands){
		super(width, height, bands);
		raster = new int[height][width][bands];		
	}

	public int getSample(int x, int y, int band) {
		return raster[y][x][band];
	}

	public void setSample(int x, int y, int band, int sample) {
		raster[y][x][band] = sample;
	}
	
	public int[] getPixel(int x, int y) {
		int[] result = new int[bands];
		System.arraycopy(raster[y][x], 0 , result, 0, bands );
		return result;
	}

	public void setPixel(int x, int y, int[] pixel) {
		System.arraycopy(pixel, 0 , raster[y][x], 0, bands );		
	}
		
}
