package operations;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ImageOpLeftRight extends ImageOperation{

	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if(dest == null){			
			dest = createCompatibleDestImage(src, src.getColorModel());
		}

		WritableRaster srcRaster = src.getRaster();
		WritableRaster destRaster = dest.getRaster();
		int height = srcRaster.getHeight();
		int width = srcRaster.getWidth();

        for (int x = 0; x < height; x++){
            for (int y = 0; y < width / 2; y++){
                int[] pix1 = new int[3];
                pix1 = srcRaster.getPixel(y, x, pix1);
                int[] pix2 = new int[3];
                pix2 = srcRaster.getPixel(width - 1 - y,x, pix2);
                destRaster.setPixel(y,x, pix2);
                destRaster.setPixel(width - 1 - y, x, pix1);
            }
        }
		
		return dest;
	}

}
