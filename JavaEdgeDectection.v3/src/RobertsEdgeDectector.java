
import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Goncalo
 */
public class RobertsEdgeDectector {

    private BufferedImage sourceImage;
    private BufferedImage edgesImage;
    private int height;
    private int width;

    public RobertsEdgeDectector() {
    }

    public void setSourceImage(BufferedImage image) {
        sourceImage = image;
        edgesImage = image;
    }

    public void process() {
        width = sourceImage.getWidth();
        height = sourceImage.getHeight();
        int[] ori = new int[width * height];
        PixelGrabber pg = new PixelGrabber(sourceImage, 0, 0, width, height, ori, 0, width);
        try {
            pg.grabPixels();
        } catch (Exception e) {
            System.out.println("PixelGrabber failed");
        }

        int temppixels[] = new int[height * width];
        for (int i = 0; i < width * height; i++) {
            int p = ori[i];
            //extracts the red, green, and blue bits from the pixel
            int r = 0xff & (p >> 16);
            int g = 0xff & (p >> 8);
            int b = 0xff & (p);
            //standard greyscale conversion
            int k = (int) (.56 * g + .33 * r + .11 * b);

            temppixels[i] = k;
        }
        int colormatrix[][] = new int[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                colormatrix[r][c] = temppixels[r * width + c];
            }
        }

        int G[][] = new int[height][width];
        int processedpixels[] = new int[width * height];
        for (int r = 1; r < height - 1; r++) {
            for (int c = 1; c < width - 1; c++) {
                int temp1 = Math.abs(colormatrix[r][c] - colormatrix[r + 1][c + 1]);
                int temp2 = Math.abs(colormatrix[r][c + 1] - colormatrix[r + 1][c]);
                colormatrix[r][c] = Math.max(temp1, temp2); //convert back to array
                int k = colormatrix[r][c];
                int newrgb = (0xff000000 | (k << 16) | (k << 8) | k);
                processedpixels[r * width + c] = newrgb;
            }
        }
        MemoryImageSource newImage = new MemoryImageSource(width, height, processedpixels, 0, width);
        Canvas c = new Canvas();
        Image finalImage = c.createImage(newImage);
        edgesImage.getGraphics().drawImage(finalImage, 0, 0, null);
    }

    public BufferedImage getedgesImage() {
        return edgesImage;
    }
}
