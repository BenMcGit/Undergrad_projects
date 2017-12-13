package operations;

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

public abstract class ImageOperation implements BufferedImageOp{

	public BufferedImage createCompatibleDestImage(BufferedImage src,
			ColorModel destCM) {
		
		return new BufferedImage(destCM, destCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), destCM.isAlphaPremultiplied(), null);
	}

	public Rectangle2D getBounds2D(BufferedImage src) {
		return src.getRaster().getBounds();

	}

	public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
		if(dstPt == null){
			dstPt = (Point2D) srcPt.clone();
		}else{
			dstPt.setLocation(srcPt);
		}
		return dstPt;	
	}

	public RenderingHints getRenderingHints() {
		return null;
	}
	
	
}
