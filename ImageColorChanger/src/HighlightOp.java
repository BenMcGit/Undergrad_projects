import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import pixeljelly.scanners.Location;
import pixeljelly.scanners.RasterScanner;


public class HighlightOp extends ImageOperation{
	Color target;
	double similarityThreshold;
	double factor; 
	boolean useL1;

	public HighlightOp(Color target, double similarityThreshold, double factor, boolean useL1){
		this.target = target;
		this.similarityThreshold = similarityThreshold;
		this.factor = factor;
		this.useL1 = useL1;
	}

	public HighlightOp(){
		this(new Color(204, 204, 255), .25, 3, false);
	}
	
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if(dest == null){			
			dest = createCompatibleDestImage(src, src.getColorModel());
		}
		boolean equation;
		float H; 
		float S; 
		float V;	
		
		//go through each pixel
		for(Location pt: new RasterScanner(src, false)){
			int[] c = new int[3];
			src.getRaster().getPixel(pt.col, pt.row, c);
			int red = c[0];
			int green = c[1];
			int blue = c[2];

			//convert to hsb values
			float[] hsb = Color.RGBtoHSB(red, green, blue, null);
			
			//determine which equation to use
			if(useL1){
				equation = computeL1(hsb[0], hsb[1], hsb[2]);
			}else{
				equation = computeL2(hsb[0], hsb[1], hsb[2]);
			}
			
			// if the similariltyThreshold is met multiply the saturation by the factor
			if(equation){
				H = (float) (hsb[0]);
				S = (float) (hsb[1]*(factor));
				V = hsb[2];
			}else{
				H = (float) (hsb[0]);
				S = (float) (hsb[1]);
				V = hsb[2];
			}
			
			//shift back to rgb 
			Color color = new Color(Color.HSBtoRGB(H, S, V));
			
			//set pixel in correct spot
			dest.setRGB(pt.col, pt.row, color.getRGB());
		}
		
		return dest;
	}
	
	public boolean computeL1(float h1, float s1, float v1){
		int red = target.getRed();
		int green = target.getGreen();
		int blue = target.getBlue();
	
		//convert to hsb
		float[] hsbvals = new float[3];
		Color.RGBtoHSB(red, green, blue, hsbvals);
		float h2 = hsbvals[0];
		float s2 = hsbvals[1];
		float v2 = hsbvals[2];

		//float the distance with l1
	float L1 =(float) 	(	Math.abs((s1*Math.cos(2*(Math.PI)*h1)) - (s2*Math.cos(2*(Math.PI)*h2))) + 
							Math.abs((s1*Math.sin(2*(Math.PI)*h1)) - (s2*Math.sin(2*(Math.PI)*h2))) + 
							Math.abs(v1 - v2));

		//determine if the distance is within the similarity threshold
		if(L1 <= similarityThreshold){
			return true;
		}
		return false;

	}

	public boolean computeL2(float h1, float s1, float v1){
		int red = target.getRed();
		int green = target.getGreen();
		int blue = target.getBlue();
		float[] hsbvals = new float[3];
		Color.RGBtoHSB(red, green, blue, hsbvals);
		float h2 = hsbvals[0];
		float s2 = hsbvals[1];
		float v2 = hsbvals[2];


		float L2 =(float) (Math.sqrt( 	Math.pow((s1*Math.cos(2*(Math.PI)*h1))-(s2*Math.cos(2*(Math.PI)*h2)), 2.0) + 
										Math.pow((s1*Math.sin(2*(Math.PI)*h1))-(s2*Math.sin(2*(Math.PI)*h2)), 2.0) + 
										Math.pow(v1 - v2, 2.0)));

		if(L2 <= similarityThreshold){
			return true;
		}
		return false;

	}


	@Override
	public String getAuthorName() {
		return "Ben McAdams";
	}

	@Override
	public BufferedImageOp getDefault(BufferedImage arg0) {
		return new HighlightOp(new Color(204, 204, 255), .25, 3, false);
	}

}
