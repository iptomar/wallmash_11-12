import java.awt.image.*;
import java.awt.*;

/* Uses the Simple operator for edge detection
 */
 
public class Simple{

	/* global variables */
	private Image originalImage;
	private Image finalImage;
	static int pixels[];
	static boolean pixelsdeclared = false;
	private int height;
	private int width;
	
	/* constructor, given source image 
	 */
	public Simple(Image p_originalImage){
		originalImage = p_originalImage;
		height = originalImage.getHeight(null);
		width = originalImage.getWidth(null);
		if(!pixelsdeclared){		
			pixels = new int[height * width];
		}
		finalImage = null;
	}

	/* preloads the pixels so that the user doesn't have to wait
	 */
	public int[] loadpixels(){
		getArray();
		return pixels;
	}
	
	/* sets the pixels to reduce loadtime
	 */
	public void setpixels(int p_pixels[]){
		pixels = p_pixels;
	}
	
	/* called by outside class to get the processed image
	 */
	public Image getImage(){
		if(finalImage == null){
			getArray();
			int processedpixels[] = new int[pixels.length];
			edgeAlgorithm(processedpixels);
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
	private void edgeAlgorithm(int[] processedpixels){
		int temppixels[] = new int[height*width];
		for(int i=0; i<width*height; i++){
			int p = pixels[i];
			
			//extracts the red, green, and blue bits from the pixel
			int r = 0xff & (p >> 16);
			int g = 0xff & (p >> 8);
			int b = 0xff & (p);
			//standard greyscale conversion
			int k = (int) (.56*g + .33*r + .11*b);
			temppixels[i] = k;
		}
		//Change to matrix form to facilitate processing
		int colormatrix[][] = new int[height][width];
		for(int r = 0; r < height; r++){
			for(int c = 0; c < width; c++){
				colormatrix[r][c] = temppixels[r*width + c];
			}
		}
		//do roberts cross operator
		int finalmatrix[][] = new int[height][width];
		for(int r = 0; r < height - 1; r++){
			for(int c = 0; c < width - 1; c++){
				  finalmatrix[r][c] = 2*colormatrix[r][c]-colormatrix[r][c+1]-colormatrix[r+1][c];
				int k = Math.abs(finalmatrix[r][c]);//(Math.abs(finalmatrix[r][c] %255)) & 0xff;
				//convert back to array
				int newrgb = (0xff000000 | (k << 16) | (k << 8) | k);
				processedpixels[r*width+c] = newrgb;
				//System.out.print("[" + r + "," + c + "]" + k + "(" + finalmatrix[r][c] + ")   ");
				//if(((newrgb >> 16) & 0xff) != ((newrgb >> 8) & 0xff) || ((newrgb >> 16) & 0xff) != ((newrgb) & 0xff)){
				//	System.out.println("ahhh");
				//}
			}
			//System.out.println();
		}
		//System.out.println(p + "[" + i + "]=(" + r + "," + g + "," + b + ")" + pixels.length);
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