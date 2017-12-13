package operations;
import java.awt.Color;

public class IndexedDigitalImage extends AbstractDigitalImage{
	//int[] raster;
	Color[] palette;

	public IndexedDigitalImage(int width, int height){
		super(width, height, 1);
		//raster = new int[width*height];
		palette = new Color[width*height];
		bands = 3;
		
	}

	public IndexedDigitalImage(int width, int height, Color[] palette){
		super(width, height, 1);
		this.palette = palette;
		//where does the palette come to play??
		bands =3;
	}

	public int getSample(int col, int row, int band) {
		int x = 0; 
		Color color = palette[width * row + col];
		if(color == null){
			return 0;
		}

		if(band == 0){
			x = color.getRed();
		}else if(band == 1){
			x = color.getGreen();
		}else if(band == 2){
			x = color.getBlue();
		}

		return x;

	}

	public void setSample(int col, int row, int band, int sample) {
		Color c = null;
		Color color = palette[width * row + col];

		if( color == null ){
			if(band == 0){
				c = new Color(sample, 0, 0);
			}else if(band == 1){
				c = new Color(0, sample, 0);
			}else if(band == 2){
				c = new Color(0, 0, sample);
			}
		}else{
			if(band == 0){
				c = new Color(sample, color.getGreen(), color.getBlue());
			}else if(band == 1){
				c = new Color(color.getRed(), sample, color.getBlue());
			}else if(band == 2){
				c = new Color(color.getRed(), color.getGreen(), sample);
			}

		}
		palette[width * row + col] = c;

		/*int pixelIndex = row * width + col;
		int pixel = raster[pixelIndex];
		int mask = 0xFF << (band * 8);
		sample = sample << (band * 8);

		raster[pixelIndex] = (pixel ^ ((pixel ^ sample) & mask));*/

	}

	public int[] getPixel(int col, int row) {
		int[] result = new int[3];
		Color color = palette[width * row + col];

		result[0] = color.getRed();
		result[1] = color.getGreen();
		result[2] = color.getBlue();

		return result; 


		/*for(int b = 0; b < bands; b++){
			result[b] = getSample(col, row, b);			
		}*/

	}

	public void setPixel(int col, int row, int[] pixel) {
		/*for( int b = 0; b < bands; b++){
			setSample(col, row, b, pixel[b]);
		}*/	
		Color color = new Color(pixel[0], pixel[1], pixel[2]);
		palette[width * row + col] = color;

	}

	public void setPaletteColor(int paletteIndex, Color color){
		palette[paletteIndex] = color;
	}

	public Color getPaletteColor(int paletteIndex){
		return palette[paletteIndex];
	}

}
