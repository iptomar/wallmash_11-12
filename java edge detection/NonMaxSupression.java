import java.awt.image.*;
import java.awt.*;

/* This class converts a color image to greyscale 
 */
 
public class NonMaxSupression{

	/* global variables */
	Image originalImage;
	Image finalImage;
	int pixels[];
	int height;
	int width;
	
	/* constructor, given source image 
	 */
	public NonMaxSupression(Image p_originalImage){
		originalImage = p_originalImage;
		height = originalImage.getHeight(null);
		width = originalImage.getWidth(null);
		finalImage = null;
		pixels = new int[width*height];
	}

	
	/* called by outside class to get the processed image
	 */
	public Image getImage(){
		if(finalImage == null){
			getArray();
			int processedpixels[] = new int[pixels.length];
			applyFilter(processedpixels);
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
	
	/* eliminates multi-line edges
	 */
	private void applyFilter(int[] processedpixels){
		//put everything in a matrix to facilitate processing
		int intermatrix[][] = new int[height][width];
		for(int r = 0; r < height; r++){
			for(int c = 0; c < width; c++){
				int p = pixels[r*width + c];
				//extracts the red, green, and blue bits from the pixel
				int r2 = 0xff & (p >> 16);
				int g2 = 0xff & (p >> 8);
				int b2 = 0xff & (p);
				//standard greyscale conversion
				int k = (int) (.56*g2 + .33*r2 + .11*b2);
				intermatrix[r][c] = k;
			}
		}
		int[] finalpixels = new int[height*width];
		//find gradient vectors of each point (starting from 1, 1)
		for(int r = 2; r < height - 2; r++){
			for(int c = 2; c < width - 2; c++){
				//Check this math
				double M_y = (intermatrix[r+1][c] - intermatrix[r-1][c]);///2.0;
				double M_x = (intermatrix[r][c+1] - intermatrix[r][c-1]);///2.0;
				//P(x+1,y-1) - P(x,y-1)
				double M_xplusone = (intermatrix[r-1][c+1] - intermatrix[r-1][c]);
				//P(x,y+1) - P(x-1,y+1)
				double M_xminusone = (intermatrix[r+1][c]-intermatrix[r+1][c-1]);
				//P(x+1,y) - P(x+1,y-1)
				double M_yminusone = (intermatrix[r][c+1] - intermatrix[r-1][c+1]);
				//P(x,y) - P(x,y-1)
				double M_yminusoneB = (intermatrix[r][c] - intermatrix[r-1][c]);
				//p(x-1,y+1) - P(x-1,y)
				double M_yplusone = (intermatrix[r+1][c-1] - intermatrix[r][c-1]);
				//P(x,y+1) - P(x,y)
				double M_yplusoneB = (intermatrix[r+1][c] - intermatrix[r][c]);
				//for M_1
				double M_xplusyminus = Math.sqrt(M_xplusone*M_xplusone + M_yminusone*M_yminusone);
				double M_xyminus = Math.sqrt(M_x*M_x + M_yminusoneB*M_yminusoneB);
				
				//for M_2
				double M_xminusyplus = Math.sqrt(M_xminusone*M_xminusone + M_yplusone*M_yplusone);
				double M_xyplus = Math.sqrt(M_x*M_x + M_yplusoneB*M_yplusoneB);

				double M_1 = M_y/M_x*M_xplusyminus + (M_x - M_y)/M_x*M_xyminus;
				double M_2 = M_y/M_x*M_xminusyplus + (M_x - M_y)/M_x*M_xyplus;
				
				double M_xy = Math.sqrt(M_x*M_x+M_y*M_y);
				
				if(M_xy > M_1 && M_xy > M_2){
					processedpixels[r*width+c] = 
					0xff000000 | (intermatrix[r][c] << 16) | (intermatrix[r][c] << 8) | (intermatrix[r][c]);
					//System.out.println("["+r + "," + c + "]" + processedpixels[r*width+c]);					
				}
			}
		}
		processedpixels = finalpixels;
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