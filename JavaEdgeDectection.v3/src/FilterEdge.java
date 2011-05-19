
import java.awt.image.BufferedImage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Goncalo
 */
public class FilterEdge {

    private BufferedImage img;
    private BufferedImage edgeImg;
    private double[][] maskx;
    private double[][] masky;
    private int[][] sobelx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    private int[][] sobely = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};

    public FilterEdge(BufferedImage img, double[][] mask) {
        this.img = img;
        this.maskx = mask;
    }

    public void start() {
        edgeImg = sobelEdgeDetection(img);
    }

    public double[][] rotate(double[][] mask) {
        int w = mask.length;
        int h = mask[0].length;
        double[][] masktmp = new double[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                masktmp[i][j] = mask[j][h - i - 1];
            }
        }
        return masktmp;
    }

    public int lum(int r, int g, int b) {
        return (r + r + r + b + g + g + g + g) >> 3;
    }

    public int rgb_to_luminance(int rgb) {
        int r = (rgb & 0xff0000) >> 16;
        int g = (rgb & 0xff00) >> 8;
        int b = (rgb & 0xff);
        //System.out.println(r + ", " + g + ", " + b);
        //return lum(r, g, b);
        //standard greyscale conversion
        return (int) (.56 * g + .33 * r + .11 * b);
    }

    public int level_to_greyscale(int level) {
        if (level >= 76) {
            level = 255;
        } else {
            level = 0;
        }
        return (level << 16) | (level << 8) | level;
    }

    public BufferedImage cloneImage(BufferedImage image) {
        return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
    }

    public BufferedImage sobelEdgeDetection(BufferedImage image) {
        masky = rotate(maskx);
        BufferedImage ret = cloneImage(image);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int level = 255;
                if ((x > 0) && (x < (width - 1)) && (y > 0) && (y < (height - 1))) {
                    int sumX = 0;
                    int sumY = 0;
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            //level += rgb_to_luminance(image.getRGB(x + i, y + j)) * mask[i + 1][j + 1];
                            sumX += rgb_to_luminance(image.getRGB(x + i, y + j)) * maskx[i + 1][j + 1];
                            sumY += rgb_to_luminance(image.getRGB(x + i, y + j)) * masky[i + 1][j + 1];
                            //System.out.println(sumX + " " + sumY);
                        }
                    }
                    level = Math.abs(sumX) + Math.abs(sumY);
                    if (level < 0) {
                        level = 0;
                    } else if (level > 255) {
                        level = 255;
                    }
                }
                ret.setRGB(x, y, level_to_greyscale(level));
            }
        }
        return ret;
    }

    /**
     * @return the edgeImg
     */
    public BufferedImage getEdgeImg() {
        return edgeImg;
    }
}
