package operations;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class TurnClockwise extends ImageOperation{

	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		if(dest == null){			
			dest = createCompatibleDestImage(src, src.getColorModel());
		}

		AffineTransform tx = new AffineTransform();
		
		//rotates a buffered image 90 degrees
		tx.rotate(Math.PI / 2, src.getWidth() / 2, src.getHeight() / 2);

		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		dest = op.filter(src, null);


		return dest;
	}

}
