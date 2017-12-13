package operations;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ImageOpTopDown extends ImageOperation{

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if(dest == null){			
			dest = createCompatibleDestImage(src, src.getColorModel());
		}

		WritableRaster srcRaster = src.getRaster();
		WritableRaster destRaster = dest.getRaster();
		int height = srcRaster.getHeight();
		int width = srcRaster.getWidth();

        for (int x = 0; x < 0 + width; x++){
            for (int y = 0; y < 0 + height / 2; y++){
                int[] pix1 = new int[3];
                pix1 = srcRaster.getPixel(x, y, pix1);
                int[] pix2 = new int[3];
                pix2 = srcRaster.getPixel(x, 0 + height - 1 - (y - 0), pix2);
                destRaster.setPixel(x, y, pix2);
                destRaster.setPixel(x, 0 + height - 1 - (y - 0), pix1);
            }
        }
		
		return dest;
	}
}
