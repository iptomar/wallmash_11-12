import java.awt.image.*;
import java.awt.*;

/* If an object is above the threshold, set it white, otherwise, set it black
 */
 
public class Threshold{

	/* global variables */
	private Image originalImage;
	private Image finalImage;
	private int pixels[];
	private int height;
	private int width;
	private int threshold;
	/* constructor, given source image 
	 */
	public Threshold(Image p_originalImage, int p_threshold){
		originalImage = p_originalImage;
		height = originalImage.getHeight(null);
		width = originalImage.getWidth(null);
		pixels = new int[width*height];
		finalImage = null;
		threshold = p_threshold;
	}

	/* called by outside class to get the processed image
	 */
	public Image getImage(){
		if(finalImage == null){
			getArray();
			int processedpixels[] = new int[pixels.length];
			thresholdAlgorithm(processedpixels);
			convertToImage(processedpixels);
		}
		return finalImage;
	}

	/* uses pixelGrabber to convert each bit in an image into a pixel and
	   stores it in an array
	 */
	private void getArray(){
		PixelGrabber pg = 
		new PixelGrabber(originalImage,0,0,width,height,pixels,0,width);
		try{
			pg.grabPixels();
		} catch(Exception e){
			System.out.println("PixelGrabber failed");
		}
	}
	
	/* implements simple algorithm
	 */
	private void thresholdAlgorithm(int[] processedpixels){
		int temppixels[] = new int[height*width];
		for(int i=0; i<width*height; i++){
			int p = pixels[i];
			
			//extracts the red, green, and blue bits from the pixel
			int r = 0xff & (p >> 16);
			int g = 0xff & (p >> 8);
			int b = 0xff & (p);
			//standard greyscale conversion
			int k = (int) (.56*g + .33*r + .11*b);
			if(k >= threshold){
				processedpixels[i] = (0xff000000 | (255 << 16) | (255 << 8) | 255); 
				//System.out.println(temppixels[i]);
			}
		}
	}

	/* changes the array back into an image, using MemoryImageSource 
	 * class
	 */
	private void convertToImage(int thepixels[]){
		MemoryImageSource newImage = 
		new MemoryImageSource(width, height, thepixels, 0, width);
		Canvas c = new Canvas();
		finalImage = c.createImage(newImage);	
	}	
}