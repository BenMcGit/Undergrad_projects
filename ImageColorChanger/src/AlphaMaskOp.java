import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

import pixeljelly.ops.PluggableImageOp;
import pixeljelly.scanners.Location;
import pixeljelly.scanners.RasterScanner;


public class AlphaMaskOp extends ImageOperation implements PluggableImageOp{
	int type;

	public AlphaMaskOp(int type){
		this.type = type;
	}

	public AlphaMaskOp(){
		this(3);
	}

	/*
	 * (non-Javadoc)
	 * @see ImageOperation#createCompatibleDestImage(java.awt.image.BufferedImage, java.awt.image.ColorModel)
	 */
	public BufferedImage createCompatibleDestImage(BufferedImage src,
			ColorModel destCM) {
		return new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.image.BufferedImageOp#filter(java.awt.image.BufferedImage, java.awt.image.BufferedImage)
	 */
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if(dest == null){			
			dest = createCompatibleDestImage(src, src.getColorModel());
		}

		if(type == 1){
			dest = type1(src, dest);
		}else if(type == -1){
			dest = type1Inverse(src, dest);
		}else if(type == 2){
			dest = type2(src, dest);
		}else if(type == -2){
			dest = type2Inverse(src, dest);
		}else if(type == 3){
			dest = type3(src, dest);
		}else if(type == -3){
			dest = type3Inverse(src, dest);
		}else{
			System.out.println("Invalid type!");
		}

		return dest;
	}

	/*
	 * This makes an image opaque at the bottom and transparent at the top
	 */
	public BufferedImage type2Inverse(BufferedImage src,BufferedImage dest){
		
		/*populate destination image*/
		for(Location pt: new RasterScanner(src, true)){
			int sample = src.getRaster().getSample(pt.col, pt.row, pt.band);
			dest.getRaster().setSample(pt.col, pt.row, pt.band, sample);
		}
		for(Location pt: new RasterScanner(dest, false)){
			dest.getRaster().setSample(pt.col, pt.row, 3, 255);
		}

		for(Location pt: new RasterScanner(dest, false)){
			double config = ((double)pt.col/(double)dest.getWidth());
			int alpha = (int) (255*config);
			dest.getRaster().setSample(pt.col, pt.row, 3, alpha);
		}

		return dest;	
	}

	/*
	 * This makes an image opaque at the top and transparent at the bottom
	 */
	public BufferedImage type2(BufferedImage src, BufferedImage dest){
		
		/*populate destination image*/
		for(Location pt: new RasterScanner(src, true)){
			int sample = src.getRaster().getSample(pt.col, pt.row, pt.band);
			dest.getRaster().setSample(pt.col, pt.row, pt.band, sample);
		}
		for(Location pt: new RasterScanner(dest, false)){
			dest.getRaster().setSample(pt.col, pt.row, 3, 255);
		}

		for(Location pt: new RasterScanner(dest, false)){
			double config = ((double)255-(double)pt.col/(double)dest.getWidth());
			int alpha = (int) (255*config);
			dest.getRaster().setSample(pt.col, pt.row, 3, alpha);
		}

		return dest;	
	}

	/*
	 * This makes an image trasparte on the left side and opaque at the right
	 */
	public BufferedImage type1Inverse(BufferedImage src,BufferedImage dest){
		
		/*populate destination image*/
		for(Location pt: new RasterScanner(src, true)){
			int sample = src.getRaster().getSample(pt.col, pt.row, pt.band);
			dest.getRaster().setSample(pt.col, pt.row, pt.band, sample);
		}
		for(Location pt: new RasterScanner(dest, false)){
			dest.getRaster().setSample(pt.col, pt.row, 3, 255);
		}

		for(Location pt: new RasterScanner(dest, false)){
			double config = ((double)pt.row/(double)dest.getHeight());
			int alpha = (int) (255*config);
			dest.getRaster().setSample(pt.col, pt.row, 3, alpha);
		}

		return dest;	
	}

	/*
	 * this makes an  image opaque at the right and transparent on the left
	 */
	public BufferedImage type1(BufferedImage src, BufferedImage dest){

		/*populate destination image*/
		for(Location pt: new RasterScanner(src, true)){
			int sample = src.getRaster().getSample(pt.col, pt.row, pt.band);
			dest.getRaster().setSample(pt.col, pt.row, pt.band, sample);
		}
		for(Location pt: new RasterScanner(dest, false)){
			dest.getRaster().setSample(pt.col, pt.row, 3, 255);
		}

		for(Location pt: new RasterScanner(dest, false)){
			double config = ((double)255-(double)pt.row/(double)dest.getHeight());
			int alpha = (int) (255*config);
			dest.getRaster().setSample(pt.col, pt.row, 3, alpha);
		}

		return dest;
	}

	/*
	 * Makes an image transparent in the middle at opaque on the corners
	 */
	public BufferedImage type3(BufferedImage src, BufferedImage dest){
		
		int centerHeight = src.getHeight()/2;
		int centerWidth = src.getWidth()/2;
		int edge = 0;
		
		/*determine the edge of the transparency*/
		if(centerHeight > centerWidth){
			edge = centerWidth;
		}else{
			edge = centerHeight;
		}
		
		/*populate destination image*/
		for(Location pt: new RasterScanner(src, true)){
			int sample = src.getRaster().getSample(pt.col, pt.row, pt.band);
			dest.getRaster().setSample(pt.col, pt.row, pt.band, sample);
		}
		for(Location pt: new RasterScanner(dest, false)){
			dest.getRaster().setSample(pt.col, pt.row, 3, 255);
		}

		for(Location pt: new RasterScanner(dest, false)){
			
			/*calculate distances of each point*/ 
			double dist = Math.sqrt(Math.pow(pt.col - centerWidth, 2) + Math.pow(pt.row - centerHeight, 2));
			if(dist > edge){
				dest.getRaster().setSample(pt.col, pt.row, 3, 0);
			}else{
				/*ratio  between middle and edge*/
				double config = ((double)255-(double)dist/(double)edge);
				int alpha = (int) (255*config);
				dest.getRaster().setSample(pt.col, pt.row, 3, alpha);
			}
		}

		return dest;
	}

	/*
	 * Makes an image opaque in the middle and transparent on the corners
	 */
	public BufferedImage type3Inverse(BufferedImage src, BufferedImage dest){

		int centerHeight = src.getHeight()/2;
		int centerWidth = src.getWidth()/2;
		int edge = 0;
		
		/*determine the edge of the transparency*/
		if(centerHeight > centerWidth){
			edge = centerWidth;
		}else{
			edge = centerHeight;
		}
		
		/*populate destination image*/
		for(Location pt: new RasterScanner(src, false)){
			int rgb = src.getRGB(pt.col, pt.row);
			dest.setRGB(pt.col, pt.row, rgb);
		}
		for(Location pt: new RasterScanner(dest, false)){
			dest.getAlphaRaster().setSample(pt.col, pt.row,0, 255f);
		}

		for(Location pt: new RasterScanner(dest, false)){
			
			/*calculate distances of each point*/
			double dist = Math.sqrt(Math.pow(pt.col - centerWidth, 2) + Math.pow(pt.row - centerHeight, 2));
			if(dist > edge){
				dest.getAlphaRaster().setSample(pt.col, pt.row, 0, 255);
			}else{
				/*ratio  between middle and edge*/
				double config = ((double)dist/(double)edge);
				int alpha = (int) (255*config);
				dest.getAlphaRaster().setSample(pt.col, pt.row, 0, alpha);
			}
		}

		return dest;
	}

	public String getAuthorName() {	
		return "Ben McAdams";
	}

	public BufferedImageOp getDefault(BufferedImage arg0) {		
		return new AlphaMaskOp(3);
	}	
}
