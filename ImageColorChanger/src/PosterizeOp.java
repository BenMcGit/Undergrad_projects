import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

import pixeljelly.scanners.Location;
import pixeljelly.scanners.RasterScanner;


public class PosterizeOp extends ImageOperation{

	public PosterizeOp(){
		//do nothing
	}

	//type byte index
	public BufferedImage createCompatibleDestImage(BufferedImage src,
			ColorModel destCM) {
		
		return new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
	}
	
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if(dest == null){			
			dest = createCompatibleDestImage(src, src.getColorModel());
		}
		//set an array of the possible colors
		Color[] colors = {Color.red, Color.green, Color.blue,
							Color.cyan, Color.magenta, Color.yellow,
							Color.black, Color.white};
		
		//go through each pixel
		for(Location pt: new RasterScanner(src, true)){
			int[] iArray = new int[3];
			src.getRaster().getPixel(pt.col, pt.row, iArray);
			int red = iArray[0];
			int green = iArray[1];
			int blue = iArray[2];
			
			//get a color from the previously initialized array and get its rgb values
			Color smallest = getSmallestL1(red, green, blue, colors);
			iArray[0] = smallest.getRed();
			iArray[1] = smallest.getGreen();
			iArray[2] = smallest.getBlue();
			
			//set the destination image to rgb values
			//dest.getRaster().setPixel(pt.col, pt.row, iArray);
			dest.setRGB(pt.col, pt.row, smallest.getRGB());
		}
		
		return dest;
	}
	
	public Color getSmallestL1(int r, int g, int b, Color[] col){
		//set check to high number that wont be reached
		Color smallest = null;
		int check = 100000000;
		
		//loop through each color in the array 
		for(Color color: col ){
			int red = color.getRed();
			int green = color.getGreen();
			int blue  = color.getBlue();
			
			//find the l1 distance
			int L1 = Math.abs(red - r)+Math.abs(green - g)+ Math.abs(blue - b);
			
			//set the smallest color to eventually return
			if(L1 < check){
				check = L1;
				smallest = color;
			}
		}
		
		return smallest;
	}

	public String getAuthorName() {
		return "Ben McAdams";
	}

	public BufferedImageOp getDefault(BufferedImage arg0) {	
		return new PosterizeOp();
	}

}
