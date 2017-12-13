package operations;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Driver {

	public static void main(String[] args){
		try{
			
			test_basic1();
			test_basic2();
			test_jelly();
			test_dog();
			test_write();
			
			System.out.println("tests complete");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error in main");

		}

	}
	
	/**
	 * Make sure the indexed version can be read from and written to a file 
	 */
	public static void test_basic1(){ 
		try {
			DigitalImage src = DigitalImageIO.read(new File("src/tests/feep.ppm"), DigitalImageIO.ImageType.INDEXED);
			DigitalImageIO.write(new File("src/tests/feep_out.ppm"), src);
			ImageIO.write(ImageConvertor.toBufferedImage(src), "png", 
					new File("src/output/test_basic1.png"));
		} catch(Exception e){
			e.printStackTrace();
			System.err.println("Error in test_basic1");
		}

	}

	/**
	 * Reads feep.ppm and prints to feep_out.ppm
	 */
	public static void test_basic2(){ 
		try {
			DigitalImage src = DigitalImageIO.read(new File("src/tests/feep.ppm"), DigitalImageIO.ImageType.LINEAR_ARRAY);
			DigitalImageIO.write(new File("src/tests/feep_out.ppm"), src);
			ImageIO.write(ImageConvertor.toBufferedImage(src), "png", 
					new File("src/output/test_basic2.png"));
		} catch(Exception e){
			e.printStackTrace();
			System.err.println("Error in test_basic2");
		}
	}
	
	/**
	 * Flips the jellyfish image top down and left to right
	 */
	public static void test_jelly(){
		try{
			ImageOpTopDown TopDown = new ImageOpTopDown();
			BufferedImage jelly = ImageIO.read(new File("src/images/Jellyfish.jpg"));
			BufferedImage td = TopDown.filter(jelly, null);
			DigitalImage src2 = ImageConvertor.toDigitalImage(td);
			DigitalImageIO.write(new File("src/tests/test.ppm"), src2);
			ImageIO.write(ImageConvertor.toBufferedImage(src2), "png", new File("src/output/jelly_output1.png"));
			
			ImageOpLeftRight LeftRight = new ImageOpLeftRight();
			BufferedImage lr = LeftRight.filter(jelly, null);
			DigitalImage src3 = ImageConvertor.toDigitalImage(lr);
			DigitalImageIO.write(new File("src/tests/test.ppm"), src3);
			ImageIO.write(ImageConvertor.toBufferedImage(src3), "png", new File("src/output/jelly_output2.png"));			
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error in test_jelly");
		}

	}
	
	/**
	 * This test can take in any image and turn it 90 clockwise, counter clockwise, and 180.
	 */
	public static void test_dog(){
		try{
			BufferedImage jelly = ImageIO.read(new File("src/images/dog.jpg"));
			
			TurnCounterClockwise cWise = new TurnCounterClockwise();
			BufferedImage cw = cWise.filter(jelly, null);
			DigitalImage b = ImageConvertor.toDigitalImage(cw);
			DigitalImageIO.write(new File("src/tests/test.ppm"), b);
			ImageIO.write(ImageConvertor.toBufferedImage(b), "png", 
					new File("src/output/test_dog1.png"));
			
			TurnClockwise clock = new TurnClockwise();
			BufferedImage c = clock.filter(jelly, null);
			DigitalImage gig = ImageConvertor.toDigitalImage(c);
			DigitalImageIO.write(new File("src/tests/test.ppm"), gig);
			ImageIO.write(ImageConvertor.toBufferedImage(gig), "png", 
					new File("src/output/test_dog2.png"));
			
			Turn180 clo = new Turn180();
			BufferedImage test = clo.filter(jelly, null);
			DigitalImage di = ImageConvertor.toDigitalImage(test);
			DigitalImageIO.write(new File("src/tests/test.ppm"), di);
			ImageIO.write(ImageConvertor.toBufferedImage(di), "png", 
					new File("src/output/test_dog3.png"));
			
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error in test_dog");
		}
	}

	/**
	 * This test converts an image to a digital image 
	 * and writes the PPM file back into a separate text file
	 */
	public static void test_write(){
		try{
			BufferedImage im = ImageIO.read(new File("src/images/jellyfish.jpg"));
			DigitalImage src2 = ImageConvertor.toDigitalImage(im);
			DigitalImageIO.write(new File("src/tests/test.ppm"), src2);
			ImageIO.write(ImageConvertor.toBufferedImage(src2), "jpg", new File("src/output/test_write.png"));
			
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error in test_write");
		}
	}
}
