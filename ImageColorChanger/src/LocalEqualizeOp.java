import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.WritableRaster;
import java.lang.Exception;
import pixeljelly.scanners.Location;
import pixeljelly.scanners.RasterScanner;


public class LocalEqualizeOp extends ImageOperation{
	int width;
	int height;
	boolean brightnessband;

	public LocalEqualizeOp(int w, int h, boolean brightnessband){
		if(w < 0 || h < 0){
			System.err.println("Can't use negative numbers!");
			return;
		}
		width = w;
		height = h;
		this.brightnessband = brightnessband;
	}
	
	public LocalEqualizeOp(){
		this(5, 5, true);
	}

	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if(dest == null){			
			dest = createCompatibleDestImage(src, src.getColorModel());
		}

		if(brightnessband){
			dest = equalizeBrightness(src);
		}else{
			dest = equalizeRGB(src, 0);
			dest = equalizeRGB(dest, 1);
			dest = equalizeRGB(dest, 2);
		}
		return dest;
	}

	public BufferedImage equalizeRGB(BufferedImage src, int band){
		
		/*Go through each pixel*/
		for(Location pt: new RasterScanner(src, false)){
			int columns = 0;
			int rows = 0;
			int[] histogram = new int[256];

			BufferedImage overlapImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
			WritableRaster overlapRaster = overlapImage.getRaster();
			WritableRaster srcRaster = src.getRaster();
			
			/*get the height and width around pixel to make histogram*/
			for(int i = pt.col - (overlapRaster.getHeight()/2); rows < height; i++){
				for(int j = pt.row - (overlapRaster.getWidth()/2); columns < width; j++){
					/*checking if out of bounds*/
					try{
						if(i >= 0 || j >= 0 || i <= src.getHeight() || j <= src.getHeight()){
							int r = srcRaster.getSample(i, j, band);
							histogram[r]++;
						}						
					}catch(Exception e){						
					}
					columns++;
				}
				rows++;
				columns = 0;
			}

			//calculate CDF
			double[] norm = normalize(histogram);
			double[] cdf = cdf(norm);
			int samp = srcRaster.getSample(pt.col, pt.row, band);
			
			samp = (int) (cdf[samp]*255);
			srcRaster.setSample(pt.col, pt.row, band, samp);
		}
		return src;
	}
	
	public BufferedImage equalizeBrightness(BufferedImage src){
		for(Location pt: new RasterScanner(src, false)){
			int columns = 0;
			int rows = 0;
			int[] histogram = new int[256];
			float[] hsbvals = new float[3];
			
			BufferedImage overlapImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
			WritableRaster overlapRaster = overlapImage.getRaster();

			for(int i = pt.col - (overlapRaster.getHeight()/2); rows < height; i++){
				for(int j = pt.row - (overlapRaster.getWidth()/2); columns < width; j++){
					try{
						if(i >= 0 || j >= 0 || i <= src.getHeight() || j <= src.getHeight()){
							Color col = new Color(src.getRGB(i, j));
							Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), hsbvals);
							
							//normalize to 255 to fit my array
							float norm = (float)hsbvals[2]*255;
							histogram[(int)norm]++;

						}						
					}catch(Exception e){			
					}
					columns++;
				}
				rows++;
				columns = 0;
			}

			//calculate CDF
			double[] norm = normalize(histogram);
			double[] cdf = cdf(norm);
			
			//get prior HSB value to adjust
			Color col = new Color(src.getRGB(pt.col, pt.row));
			Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), hsbvals);

			//get the value from cdf
			float index = (float)hsbvals[2]*255;
			hsbvals[2] = (float)cdf[(int)index];
			
			//convert back to RGB
			int rgbBright = Color.HSBtoRGB(hsbvals[0], hsbvals[1], hsbvals[2]);
			
			//set RGB
			src.setRGB(pt.col, pt.row, rgbBright);
		}

		return src;
	}

	//Normalize histogram
	public double[] normalize(int[] arr){
		int count = 0;
		double[] percent = new double[arr.length];
		
		//get the amount of colors in array
		for(int i = 0; i<arr.length; i++){
			count = count + arr[i];
		}

		//divide each value by the count to get a decimal (percentage)
		if(count != 0){
			for(int i = 0; i<arr.length; i++){
				percent[i] = (double)arr[i]/count;
			}
		}
		
		return percent;
	}
	
	//cdf
	public double[] cdf(double[] histogram){
		for(int i = 1; i<histogram.length; i++){
			histogram[i] = histogram[i-1] + histogram[i];
		}

		return histogram;

	}

	public int convertRGBtoHSB(){
		return 0;
	}

	public String getAuthorName() {
		return "Ben McAdams";
	}

	public BufferedImageOp getDefault(BufferedImage arg0) {
		return new LocalEqualizeOp(5, 5, true);
	}

	public void setWidth(int w){
		width = w;
	}

	public int getWidth(){
		return width;
	}

	public void setHeight(int h){
		height = h;
	}

	public int getHeight(){
		return height;
	}

	public void setBrightness(boolean brightness){
		brightnessband = brightness;
	}

	public boolean getBrightness(){
		return brightnessband;
	}
}
