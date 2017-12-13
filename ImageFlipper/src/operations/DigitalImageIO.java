package operations;
import java.io.*;
import java.util.*;
import java.awt.Color;


public class DigitalImageIO {

	enum ImageType {INDEXED, PACKED, LINEAR_ARRAY, MULTI_DIM_ARRAY }

	int[] pix = new int[3];
	int r,g,b = 0;

	public static DigitalImage read(File file, ImageType type) throws IOException {
		//set all to null for later use
		IndexedDigitalImage Indeximage = null;
		LinearArrayDigitalImage Linearimage = null;
		ArrayDigitalImage Arrayimage = null;
		PackedPixelImage Pixelimage = null;

		String line;
		StringTokenizer st;

		try {
			//read in the file
			BufferedReader in =
					new BufferedReader(new InputStreamReader(
							new BufferedInputStream(
									new FileInputStream(file))));

			DataInputStream in2 =
					new DataInputStream(
							new BufferedInputStream(
									new FileInputStream(file)));

			// read PPM image header

			// skip comments
			line = in.readLine();
			in2.skip((line+"\n").getBytes().length);
			do {
				line = in.readLine();
				in2.skip((line+"\n").getBytes().length);
			} while (line.charAt(0) == '#');

			// the current line has dimensions
			st = new StringTokenizer(line);
			int width = Integer.parseInt(st.nextToken());
			int height = Integer.parseInt(st.nextToken());

			//set height and width on my digital image
			if(type == ImageType.INDEXED){
				Indeximage = new IndexedDigitalImage(width, height);
			}else if(type == ImageType.LINEAR_ARRAY){
				Linearimage = new LinearArrayDigitalImage(width, height, 3);  // do i hardcode the bands here??
			}else if(type == ImageType.MULTI_DIM_ARRAY){
				Arrayimage = new ArrayDigitalImage(width, height, 3);
			}else if(type == ImageType.PACKED){
				Pixelimage = new PackedPixelImage(width, height, 3);
			}

			// next line has pixel depth
			line = in.readLine();
			in2.skip((line+"\n").getBytes().length);
			st = new StringTokenizer(line);

			//depth is the maximum number that can be used
			int depth = Integer.parseInt(st.nextToken());
			if(type == ImageType.INDEXED){
				Indeximage.setDepth(depth);
			}else if(type == ImageType.LINEAR_ARRAY){
				Linearimage.setDepth(depth);
			}else if(type == ImageType.MULTI_DIM_ARRAY){
				Arrayimage.setDepth(depth);
			}else if(type == ImageType.PACKED){
				Pixelimage.setDepth(depth);
			}
			
			// read pixels now
			int r, g, b, x, y;
			int[] arr = new int[3];
			for (y = 0; y < height; y++){
				line = in.readLine();
				if(line == null){
					System.err.println("The assigned height from the PPM file does not match pixel height");
				}
				st = new StringTokenizer(line);
				r = g = b = 0;

				for (x = 0; x < width; x++){
					// get the r g b values for each pixel
					r = Integer.parseInt(st.nextToken());
					g = Integer.parseInt(st.nextToken());
					b = Integer.parseInt(st.nextToken());
					arr[0] = r;
					arr[1] = g;
					arr[2] = b;

					if(type == ImageType.INDEXED){
						Indeximage.setPixel(x, y, arr);
						Color col = new Color(r, g, b);
						Indeximage.setPaletteColor(width*y + x, col);
					}else if(type == ImageType.LINEAR_ARRAY){
						Linearimage.setPixel(x, y, arr);
					}else if(type == ImageType.MULTI_DIM_ARRAY){
						Arrayimage.setPixel(x, y, arr);
					}else if(type == ImageType.PACKED){
						Pixelimage.setPixel(x, y, arr);
					}
				}
			}


			in.close();
			in2.close();
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Error: image in "+file+" too big");
		} catch(FileNotFoundException e) {
			System.out.println("Error: file "+file+" not found");
		} catch(IOException e) {
			System.out.println("Error: end of stream encountered when reading "+file);
		}

		if(type == ImageType.INDEXED){
			return Indeximage;
		}else if(type == ImageType.LINEAR_ARRAY){
			return Linearimage;
		}else if(type == ImageType.MULTI_DIM_ARRAY){
			return Arrayimage;
		}else if(type == ImageType.PACKED){
			return Pixelimage;
		}
		return null;
	}


	public static void write( File file, DigitalImage image) throws IOException{	

		int height = image.getHeight();
		int width = image.getWidth();
		int bands = image.getNumberOfBands();
		int depth = image.getDepth();
		try {
			//prepare to write to different file
			DataOutputStream out =
					new DataOutputStream(
							new BufferedOutputStream(
									new FileOutputStream(file)));
			//write these every time
			out.writeBytes("P3\n");
			out.writeBytes("#created by Ben McAdams\n");
			out.writeBytes(width+" "+height +"\n"+ depth +"\n");
			
			//search for pixel at given location
			int samp = 0;
			String sample = null;
			for (int y = 0; y < height; y++){
                for (int x = 0; x < width; x++){
                    for (int i = 0; i < bands; i++){
                    	samp = image.getSample(x, y, i);
                    	sample = "" + samp;
                        out.writeBytes(sample);
                        out.writeBytes(" ");
                    }
                    out.writeBytes("    ");

                }
                out.writeBytes("\n");
			}
			out.close();
		} catch(IOException e) {
			System.out.println("ERROR: cannot write output file");
		}
	}

}

