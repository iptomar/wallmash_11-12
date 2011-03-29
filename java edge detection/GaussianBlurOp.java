import java.awt.image.*;
import java.awt.*;

/* Applies Gaussian Blur to object
 */
 
public class GaussianBlurOp{

	/* global variables */
	private Image originalImage;
	private Image finalImage;
	private int pixels[];
	private int height;
	private int width;
	private int intensity;
	private boolean arraygiven;
	
	/* constructor, given source image 
	 */
	public GaussianBlurOp(Image p_originalImage, int p_intensity){
		originalImage = p_originalImage;
		intensity = p_intensity;
		height = originalImage.getHeight(null);
		width = originalImage.getWidth(null);	
		pixels = new int[height * width];
		finalImage = null;
		arraygiven = false;
	}

	public GaussianBlurOp(int[] p_pixels, int p_height, int p_width, int p_intensity){
		intensity = p_intensity;
		height = p_height;
		width = p_width;	
		pixels = new int[height * width];
		pixels = p_pixels;
		finalImage = null;
		arraygiven = true;
	}

	/* called by outside class to get the processed image
	 */
	public Image getImage(){
		System.out.println("IN DA GETIMAGE FUNCTION");
		if(finalImage == null){
			getArray();
			int processedpixels[] = new int[pixels.length];
			gbop(processedpixels, intensity);
			convertToImage(processedpixels);
		}
		return finalImage;
	}

	/* uses pixelGrabber to convert each bit in an image into a pixel and
	   stores it in an array
	 */
	private void getArray(){
		if(!arraygiven){
			PixelGrabber pg = 
			new PixelGrabber(originalImage,0,0,width,height,pixels,0,width);
			try{
				pg.grabPixels();
			} catch(Exception e){
				System.out.println("PixelGrabber failed");
			}
		}
	}
	
	/* implements gaussian blur algorithm
	 */
	private void gbop(int[] processedpixels, int intensity){
		int redpixels[] = new int[height*width];
		int bluepixels[] = new int[height*width];
		int greenpixels[] = new int[height*width];
		for(int i=0; i<width*height; i++){
			int p = pixels[i];
			
			//extracts the red, green, and blue bits from the pixel
			int r = 0xff & (p >> 16);
			int g = 0xff & (p >> 8);
			int b = 0xff & (p);
			//assign to red, green, and blue arrays
			redpixels[i] = r;
			greenpixels[i] = g;
			bluepixels[i] = b;
		}
		//Change to matrix form to facilitate processing
		int redmatrix[][] = new int[height][width];
		int greenmatrix[][] = new int[height][width];
		int bluematrix[][] = new int[height][width];
		for(int r = 0; r < height; r++){
			for(int c = 0; c < width; c++){
				redmatrix[r][c] = redpixels[r*width + c];
				greenmatrix[r][c] = greenpixels[r*width + c];
				bluematrix[r][c] = bluepixels[r*width + c];
			}
		}
		//do gaussian blur
		int finalmatrix[][] = new int[height][width];
		if(intensity == 3){
			for(int r = 0; r < height; r++){
				for(int c = 0; c < width; c++){
					//if on edge, just copy the values, else, do gaussian blur				
					if(r == 0 || r == height - 1|| c == 0 || c == width - 1){
						processedpixels[r*width+c] = 
						(0xff000000 | (redmatrix[r][c] << 16) | (greenmatrix[r][c] << 8) | bluematrix[r][c]);
					} else{				
						double dividingconst = 1.0/4.92;
						int r2 = (int)
							(.37*redmatrix[r-1][c-1]*dividingconst +
							.61*redmatrix[r-1][c]*dividingconst +	
							.37*redmatrix[r-1][c+1]*dividingconst +

							.61*redmatrix[r][c-1]*dividingconst + 
							1.0*redmatrix[r][c]*dividingconst + 
							.61*redmatrix[r][c+1]*dividingconst + 

							.37*redmatrix[r+1][c-1]*dividingconst +
							.61*redmatrix[r+1][c]*dividingconst +	
							.37*redmatrix[r+1][c+1]*dividingconst);
						int g2 = (int)
							(.37*greenmatrix[r-1][c-1]*dividingconst +
							.61*greenmatrix[r-1][c]*dividingconst +	
							.37*greenmatrix[r-1][c+1]*dividingconst +

							.61*greenmatrix[r][c-1]*dividingconst + 
							1.0*greenmatrix[r][c]*dividingconst + 
							.61*greenmatrix[r][c+1]*dividingconst + 

							.37*greenmatrix[r+1][c-1]*dividingconst +
							.61*greenmatrix[r+1][c]*dividingconst +	
							.37*greenmatrix[r+1][c+1]*dividingconst);	
						int b2 = (int)
							(.37*bluematrix[r-1][c-1]*dividingconst +
							.61*bluematrix[r-1][c]*dividingconst +	
							.37*bluematrix[r-1][c+1]*dividingconst +

							.61*bluematrix[r][c-1]*dividingconst + 
							1.0*bluematrix[r][c]*dividingconst + 
							.61*bluematrix[r][c+1]*dividingconst + 

							.37*bluematrix[r+1][c-1]*dividingconst +
							.61*bluematrix[r+1][c]*dividingconst +	
							.37*bluematrix[r+1][c+1]*dividingconst);							
						//System.out.println("(" + rnew + "," + gnew + "," + bnew + ")" + "k=" + k + "k2=" + k2);
						int newrgb = (0xff000000 | (r2 << 16) | (g2 << 8) | b2);
						processedpixels[r*width+c] = newrgb;
					}
				}
			}
		} else if(intensity == 5){
			for(int r = 0; r < height; r++){
				for(int c = 0; c < width; c++){
					double dividingconst = 1.0/6.1;
					//if on edge, just copy the values, else, do gaussian blur
					if(r <= 1 || r >= height - 2|| c <= 1 || c >= width-2){
						processedpixels[r*width+c] = 
						(0xff000000 | (redmatrix[r][c] << 16) | (greenmatrix[r][c] << 8) | bluematrix[r][c]);
					} else{							
						int r2 = (int)
							(.02*redmatrix[r-2][c-2]*dividingconst +
							.08*redmatrix[r-2][c-1]*dividingconst +
							.14*redmatrix[r-2][c]*dividingconst +
							.08*redmatrix[r-2][c+1]*dividingconst +
							.02*redmatrix[r-2][c+2]*dividingconst +

							.08*redmatrix[r-1][c-2]*dividingconst +
							.37*redmatrix[r-1][c-1]*dividingconst +
							.61*redmatrix[r-1][c]*dividingconst +	
							.37*redmatrix[r-1][c+1]*dividingconst +
							.08*redmatrix[r-1][c+2]*dividingconst +

							.14*redmatrix[r][c-2]*dividingconst + 
							.61*redmatrix[r][c-1]*dividingconst + 
							1.0*redmatrix[r][c]*dividingconst + 
							.61*redmatrix[r][c+1]*dividingconst + 
							.14*redmatrix[r][c+2]*dividingconst + 

							.08*redmatrix[r+1][c-2]*dividingconst +
							.37*redmatrix[r+1][c-1]*dividingconst +
							.61*redmatrix[r+1][c]*dividingconst +	
							.37*redmatrix[r+1][c+1]*dividingconst +
							.08*redmatrix[r+1][c+2]*dividingconst +

							.02*redmatrix[r+2][c-2]*dividingconst +
							.08*redmatrix[r+2][c-1]*dividingconst +
							.14*redmatrix[r+2][c]*dividingconst +
							.08*redmatrix[r+2][c+1]*dividingconst +
							.02*redmatrix[r+2][c+2]*dividingconst);	

						int g2 = (int)
							(.02*greenmatrix[r-2][c-2]*dividingconst +
							.08*greenmatrix[r-2][c-1]*dividingconst +
							.14*greenmatrix[r-2][c]*dividingconst +
							.08*greenmatrix[r-2][c+1]*dividingconst +
							.02*greenmatrix[r-2][c+2]*dividingconst +

							.08*greenmatrix[r-1][c-2]*dividingconst +
							.37*greenmatrix[r-1][c-1]*dividingconst +
							.61*greenmatrix[r-1][c]*dividingconst +	
							.37*greenmatrix[r-1][c+1]*dividingconst +
							.08*greenmatrix[r-1][c+2]*dividingconst +

							.14*greenmatrix[r][c-2]*dividingconst + 
							.61*greenmatrix[r][c-1]*dividingconst + 
							1.0*greenmatrix[r][c]*dividingconst + 
							.61*greenmatrix[r][c+1]*dividingconst + 
							.14*greenmatrix[r][c+2]*dividingconst + 

							.08*greenmatrix[r+1][c-2]*dividingconst +
							.37*greenmatrix[r+1][c-1]*dividingconst +
							.61*greenmatrix[r+1][c]*dividingconst +	
							.37*greenmatrix[r+1][c+1]*dividingconst +
							.08*greenmatrix[r+1][c+2]*dividingconst +

							.02*greenmatrix[r+2][c-2]*dividingconst +
							.08*greenmatrix[r+2][c-1]*dividingconst +
							.14*greenmatrix[r+2][c]*dividingconst +
							.08*greenmatrix[r+2][c+1]*dividingconst +
							.02*greenmatrix[r+2][c+2]*dividingconst);

						int b2 = (int)
							(.02*bluematrix[r-2][c-2]*dividingconst +
							.08*bluematrix[r-2][c-1]*dividingconst +
							.14*bluematrix[r-2][c]*dividingconst +
							.08*bluematrix[r-2][c+1]*dividingconst +
							.02*bluematrix[r-2][c+2]*dividingconst +

							.08*bluematrix[r-1][c-2]*dividingconst +
							.37*bluematrix[r-1][c-1]*dividingconst +
							.61*bluematrix[r-1][c]*dividingconst +	
							.37*bluematrix[r-1][c+1]*dividingconst +
							.08*bluematrix[r-1][c+2]*dividingconst +

							.14*bluematrix[r][c-2]*dividingconst + 
							.61*bluematrix[r][c-1]*dividingconst + 
							1.0*bluematrix[r][c]*dividingconst + 
							.61*bluematrix[r][c+1]*dividingconst + 
							.14*bluematrix[r][c+2]*dividingconst + 

							.08*bluematrix[r+1][c-2]*dividingconst +
							.37*bluematrix[r+1][c-1]*dividingconst +
							.61*bluematrix[r+1][c]*dividingconst +	
							.37*bluematrix[r+1][c+1]*dividingconst +
							.08*bluematrix[r+1][c+2]*dividingconst +

							.02*bluematrix[r+2][c-2]*dividingconst +
							.08*bluematrix[r+2][c-1]*dividingconst +
							.14*bluematrix[r+2][c]*dividingconst +
							.08*bluematrix[r+2][c+1]*dividingconst +
							.02*bluematrix[r+2][c+2]*dividingconst);						
						int newrgb = (0xff000000 | (r2 << 16) | (g2 << 8) | b2);
						processedpixels[r*width+c] = newrgb;
					}
				}
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