
import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

public class SobelEdgeDetector {

    private BufferedImage sourceImage;
    private BufferedImage edgesImage;
    private int height;
    private int width;
    private double[]mask;

    public SobelEdgeDetector() {
        mask = new double[9];
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
                double Gx = mask[0] * colormatrix[r - 1][c - 1]
                        + mask[3] * colormatrix[r][c - 1]
                        + mask[6] * colormatrix[r + 1][c - 1]
                        + mask[5] * colormatrix[r - 1][c - 1]
                        + mask[2] * colormatrix[r][c]
                        + mask[8] * colormatrix[r + 1][c + 1];
                double Gy = mask[0] * colormatrix[r - 1][c - 1]
                        + mask[3] * colormatrix[r - 1][c]
                        + mask[6] * colormatrix[r - 1][c + 1]
                        + mask[2] * colormatrix[r + 1][c - 1]
                        + mask[5] * colormatrix[r + 1][c]
                        + mask[8] * colormatrix[r + 1][c + 1];
                G[r][c] = (int) Math.sqrt(Gx * Gx + Gy * Gy);
                int k = G[r][c];
                //convert back to array
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

    /**
     * @param array3x3 the array3x3 to set
     */
    public void setArray3x3(double[] array3x3) {
        this.mask = array3x3;
    }
}
