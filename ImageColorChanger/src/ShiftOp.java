import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import pixeljelly.scanners.Location;
import pixeljelly.scanners.RasterScanner;


public class ShiftOp extends ImageOperation{
	double hueAmount;
	double satAmount;
	
	public ShiftOp(double hueAmount, double satAmount){
		
		//if sat and hue aren't as expected set them to an expected value
		if(satAmount > 1){
			this.satAmount = 1;
		}else if(satAmount < -1){
			this.satAmount = -1;
		}else{
			this.satAmount = satAmount;
		}
		
		if(hueAmount > 1){
			this.hueAmount = 1;
		}else if(hueAmount < -1){
			this.hueAmount = -1;
		}else{
			this.hueAmount = hueAmount;
		}
		
	}
	
	public ShiftOp(){
		this(0, .5);
	}
	
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if(dest == null){			
			dest = createCompatibleDestImage(src, src.getColorModel());
		}
		
		//scan through pixels
		for(Location pt: new RasterScanner(src, true)){
			
			//get red, blue, and green
			int[] c = new int[3];
			src.getRaster().getPixel(pt.col, pt.row, c);
			int red = c[0];
			int green = c[1];
			int blue = c[2];
			
			float[] hsb = Color.RGBtoHSB(red, green, blue, null);
			
			//modify float h,s,v values
			float H = (float) (hsb[0]+ hueAmount);
			float S = (float) (hsb[1]+ satAmount);
			float V	= hsb[2];
			
			//obtain the modified values
			int rgb = Color.HSBtoRGB(H, S, V);
			red = (rgb>>16)&0xFF;
			green = (rgb>>8)&0xFF;
			blue = rgb&0xFF;
			c[0] = red;
			c[1] = green;
			c[2] = blue;

			//set them in the destination image
			dest.getRaster().setPixel(pt.col, pt.row, c);
		}
			
		return dest;
	}

	public String getAuthorName(){
		return "Ben McAdams";
	}

	public BufferedImageOp getDefault(BufferedImage arg0) {
		return new ShiftOp(0, .5);
	}

}
