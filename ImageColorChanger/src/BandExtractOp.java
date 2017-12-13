import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import pixeljelly.scanners.Location;
import pixeljelly.scanners.RasterScanner;


public class BandExtractOp extends ImageOperation{
	char band;

	public	BandExtractOp(char band){
		this.band = band;
	}
	
	public BandExtractOp(){
		this('H');
	}

	public BufferedImage createCompatibleDestImage(BufferedImage src,
			ColorModel destCM) {
		return new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
	}

	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if(dest == null){			
			dest = createCompatibleDestImage(src, src.getColorModel());
		}

		if(band == 'R' || band == 'G' || band == 'B'){
			dest = extractRGB(src, dest);
		}else if(band == 'Y' || band == 'I' || band == 'Q'){
			dest = extractYIQ(src, dest);
		}else if(band == 'H' || band == 'S' || band == 'V'){
			dest = extractHSV(src, dest);
		}else{
			System.err.println("Not a valid character!");
			return src;
		}

		return dest;
	}

	public BufferedImage extractRGB(BufferedImage src, BufferedImage dest){
		int sample = 0;
		for(Location pt: new RasterScanner(src, false)){
			int rgb = src.getRGB(pt.col, pt.row);
			Color col = new Color(rgb);
			if(band == 'R')
				sample = col.getRed();
			if(band == 'G')
				sample = col.getGreen();
			if(band == 'B')
				sample = col.getBlue();

			dest.getRaster().setSample(pt.col, pt.row, 0, sample);
		}
		return dest;
	}

	public BufferedImage extractYIQ(BufferedImage src, BufferedImage dest){
		int[] convert = new int[3];
		int sample = 0;
		
		/*Convert to YIQ space*/
		for(Location pt: new RasterScanner(src, true)){
			src.getRaster().getPixel(pt.col, pt.row, convert);
			int red = convert[0];
			int green = convert[1];
			int blue = convert[2];
			float y = (float) (red*(0.299)+ green*(0.587) + blue*(0.114));
			float i = (float) (red*(0.596)+ green*(-0.274) + blue*(-0.321));
			float q = (float) (red*(0.211)+ green*(-0.523) + blue*(0.312));
			float[] fArray = {y,i,q};
			src.getRaster().setPixel(pt.col, pt.row, fArray);
		}

		for(Location pt: new RasterScanner(src, false)){
			int rgb = src.getRGB(pt.col, pt.row);
			Color col = new Color(rgb);
			if(band == 'Y')
				sample = col.getRed();
			if(band == 'I')
				sample = col.getGreen();
			if(band == 'Q')
				sample = col.getBlue();

			dest.getRaster().setSample(pt.col, pt.row, 0, sample);
		}
		return dest;
	}

	public BufferedImage extractHSV(BufferedImage src, BufferedImage dest){
		float[] hsb = new float[3];
		int val = 0;
		
		if(band == 'H'){
			val = 0;
		}else if(band == 'S'){
			val = 1;
		}else{
			val = 2;
		}
		
		/*'val' is what controls whether we are extracting h, s, or v*/
		for(Location pt: new RasterScanner(src, false)){
			Color col = new Color(src.getRGB(pt.col, pt.row));
			Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), hsb);
			dest.getRaster().setSample(pt.col, pt.row, 0, hsb[val]*255);

		}
	return dest;
}


public String getAuthorName() {
	return "Ben McAdams";
}

public BufferedImageOp getDefault(BufferedImage src) {
	return new BandExtractOp('H');
}

public void setBand(char band){
	this.band = band;
}

public char getBand(){
	return band;
}

}
