
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
    private int[] array3x3;

    public SobelEdgeDetector() {
        array3x3 = new int[9];
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
                int Gx = array3x3[0] * colormatrix[r - 1][c - 1]
                        + array3x3[3] * colormatrix[r][c - 1]
                        + array3x3[6] * colormatrix[r + 1][c - 1]
                        + array3x3[5] * colormatrix[r - 1][c - 1]
                        + array3x3[2] * colormatrix[r][c]
                        + array3x3[8] * colormatrix[r + 1][c + 1];
                int Gy = array3x3[0] * colormatrix[r - 1][c - 1]
                        + array3x3[3] * colormatrix[r - 1][c]
                        + array3x3[6] * colormatrix[r - 1][c + 1]
                        + array3x3[2] * colormatrix[r + 1][c - 1]
                        + array3x3[5] * colormatrix[r + 1][c]
                        + array3x3[8] * colormatrix[r + 1][c + 1];
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
    public void setArray3x3(int[] array3x3) {
        this.array3x3 = array3x3;
    }
}