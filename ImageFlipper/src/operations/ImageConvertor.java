package operations;
import java.awt.image.*;


public class ImageConvertor {
	
	public static BufferedImage toBufferedImage(DigitalImage src){
		
		//create a new buffered image
		BufferedImage image = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB); //what should this type be ??
		WritableRaster wRaster = image.getRaster();
		int width = src.getWidth();
		int height = src.getHeight();
		int bands = src.getNumberOfBands();
		int depth = src.getDepth();	
		
		//sets the depth if it wasnt initialized before
		if(depth == 0){
			depth = 255;
			bands = 3;
		}
		
		//loop to the correct pixel
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				for(int b = 0; b < bands; b++){
					int sample = src.getSample(i, j, b);
					
					//rescale by depth
					int mul = 255*sample;
					int div = mul/ depth;
					
					//set sample
					wRaster.setSample(i, j, b, div);
				}
			}
		}
		return image;
		
	}
	
	public static DigitalImage toDigitalImage(BufferedImage src){
		int x = src.getType(); //need to get type in both of these
		
		LinearArrayDigitalImage lImage = null;
		ArrayDigitalImage aImage = null;
		PackedPixelImage pImage = null;

		WritableRaster raster = src.getRaster();
		int height = raster.getHeight();
		int width = raster.getWidth();
		int bands = raster.getNumBands();
				
		//deciding what kind of DigitalImage im going to use
		if(x >= 0 || x <= 4){
			lImage = new LinearArrayDigitalImage(width, height, bands);
		}else if(x >= 5 || x <= 8){
			lImage = new LinearArrayDigitalImage(width, height, bands);
		}else if(x >= 9 || x <= 14){
			aImage = new ArrayDigitalImage(width, height, bands);
		}else if(x >= 15 || x <= 20){
			pImage = new PackedPixelImage(width, height, bands);
		}else{
			aImage = new ArrayDigitalImage(width, height, bands);
		}
		
		//loop to the correct pixel
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				for(int b = 0; b < bands; b++){
					int sample = raster.getSample(i, j, b);
					
					if(x >= 0 || x <= 4){
						lImage.setSample(i, j, b, sample);
					}else if(x >= 5 || x <= 8){
						lImage.setSample(i, j, b, sample);
					}else if(x >= 9 || x <= 14){
						aImage.setSample(i, j, b, sample);
					}else if(x >= 15 || x <= 20){
						pImage.setSample(i, j, b, sample);
					}else
						aImage.setSample(i, j, b, sample);
					
				}
			}
		}
		 
		if(x >= 0 || x <= 4){
			return lImage;
		}else if(x >= 5 || x <= 8){
			return lImage;
		}else if(x >= 9 || x <= 14){
			return aImage;
		}else if(x >= 15 || x <= 20){
			return pImage;
		}else
			return aImage;
		
		
	}
}
