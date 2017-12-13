package operations;

public class LinearArrayDigitalImage extends AbstractDigitalImage {
	private int[] raster;
	
	public LinearArrayDigitalImage(int width, int height, int bands){
		super(width, height, bands);
		raster = new int[bands*height*width];
	}
	
	public int getSample(int x, int y, int band) {
		return raster[bands*(x+y*width)+band];
	}

	
	public void setSample(int x, int y, int band, int sample) {
		raster[bands*(x+y*width)+ band] = sample;
		
	}
		
	public int[] getPixel(int x, int y) {
		int[] result = new int[bands];
		System.arraycopy(raster, bands*(x+y*width), result, 0, bands);
		
		return result;
	}

	
	public void setPixel(int x, int y, int[] pixel) {
		System.arraycopy(pixel, 0, raster, bands*(x+y*width), bands);
	}

}

 