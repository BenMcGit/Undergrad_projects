package operations;

public class PackedPixelImage extends AbstractDigitalImage{
	private int[] raster;
	
	public PackedPixelImage(int width, int height, int bands){
		super(height, width, bands);
		raster = new int[width*height];
		
	}

	public int getSample(int col, int row, int band) {
		return (raster[col + row * width] >> (band * 8)) & 0xFF;
	}

	public void setSample(int col, int row, int band, int sample) {
		int pixelIndex = row * width + col;
		int pixel = raster[pixelIndex];
		int mask = 0xFF << (band * 8);
		sample = sample << (band * 8);
		
		raster[pixelIndex] = (pixel ^ ((pixel ^ sample) & mask));
	}
	
	public int[] getPixel(int col, int row) {
		int[] result = new int[bands];
		for(int b = 0; b < bands; b++){
			result[b] = getSample(col, row, b);			
		}
		return result;
	}

	public void setPixel(int col, int row, int[] pixel) {
		for( int b = 0; b < bands; b++){
			setSample(col, row, b, pixel[b]);
		}		
	}
}
