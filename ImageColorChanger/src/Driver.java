import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Driver {

	public static void main(String[] args) throws IOException {

		BufferedImage hc = ImageIO.read(new File("src/highContrast.jpg"));
		BufferedImage mario = ImageIO.read(new File("src/mario.jpg"));
		BufferedImage fruit = ImageIO.read(new File("src/fruit.jpg"));
		BufferedImage pom = ImageIO.read(new File("src/Pomegranate.jpg"));
		BufferedImage work = ImageIO.read(new File("src/worker.jpg"));
		
		AlphaMaskOp alphamask = new AlphaMaskOp(3);
		BufferedImage alpha = alphamask.filter(pom, null);
		ImageIO.write(alpha, "jpg", new File("src/PomegranateOutput.jpg"));
		
		/*
		HighlightOp highlight = new HighlightOp(new Color(204, 204, 255), 1, 7, true);
		BufferedImage high = highlight.filter(pom, null);
		ImageIO.write(high, "jpg", new File("src/PomegranateOutput.jpg"));
		
		HighlightOp highlight2 = new HighlightOp(new Color(204, 204, 255), 1, 7, true);
		BufferedImage high2 = highlight2.filter(work, null);
		ImageIO.write(high2, "jpg", new File("src/workerOutput.jpg"));
		
		/*ShiftOp shift = new ShiftOp(-.3, 7);
		BufferedImage shifting = shift.filter(pom, null);
		ImageIO.write(shifting, "jpg", new File("src/PomegranateOutput.jpg"));/*	*/
			
		
		/*PosterizeOp posterize = new PosterizeOp();
		BufferedImage post = posterize.filter(pom, null);
		ImageIO.write(post, "jpg", new File("src/PomegranateOutput.jpg"));	*/
	
		
		
		/*BandExtractOp extractBand = new BandExtractOp('V');
		BufferedImage extract = extractBand.filter(pom, null);
		ImageIO.write(extract, "jpg", new File("src/PomegranateOutput.jpg"));	*/
		
		/*LocalEqualizeOp locEqOp = new LocalEqualizeOp(4, 4, true);
		BufferedImage loc = locEqOp.filter(pom, null);
		ImageIO.write(loc, "jpg", new File("src/PomegranateOutput.jpg"));	*/
		System.out.println("END of RUN");
	}

}
