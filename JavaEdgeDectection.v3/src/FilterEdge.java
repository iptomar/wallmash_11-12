
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
    private int treshold=76;

    public FilterEdge(BufferedImage img, double[][] mask, int tresh) {
        this.img = img;
        this.maskx = mask;
        this.treshold=tresh;

    }

    public void start() {
        edgeImg = sobelEdgeDetection(img);
    }

    public double[][] rotate(double[][] mask) {
        int h = mask.length;
        int w = mask[0].length;
        double[] masktmp = new double[h * w];
        double[][] rotated = new double[w][h];
        int k = 0;
        for (int i = 0; i < w; i++) {
            for (int j = h - 1; j >= 0; j--) {
                masktmp[k++] = mask[j][i];
            }
        }
        k = 0;
        for (int j = 0; j < w; j++) {
            for (int i = 0; i < h; i++) {
                rotated[j][i] = masktmp[k++];
            }
        }
        return rotated;
    }

    public int lum(int r, int g, int b) {
        return (r + b + g ) >> 3;
    }

    public int rgb_to_luminance(int rgb) {
        int r = (rgb & 0xff0000) >> 16;
        int g = (rgb & 0xff00) >> 8;
        int b = (rgb & 0xff);
//        System.out.println(r + ", " + g + ", " + b);
        return lum(r, g, b);
        //standard greyscale conversion
        //return (int) (.56 * g + .33 * r + .11 * b);
    }

    public int level_to_greyscale(int level) {
//        if (level >= treshold) {
//            level = 255;
//        } else {
//            level = 0;
//        }
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

        //find mask range
        int auxHx = maskx.length / -2;
        int auxWx = maskx[0].length / -2;

        //adjust range if número for par
        if (maskx.length % 2 == 0) {
            auxHx += 1;
        }

        if (maskx[0].length % 2 == 0) {
            auxWx += 1;
        }

        //find mask range
        int auxHy = masky.length / -2;
        int auxWy = masky[0].length / -2;

        //adjust range if mask width and/or heigth are even numbers
        if (masky.length % 2 == 0) {
            auxHy += 1;
        }

        if (masky[0].length % 2 == 0) {
            auxWy += 1;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int level = 255;

                int sumX = 0;
                for (int yy = 0, mH = auxHx; yy < maskx.length; yy++, mH++) {
                    if (y + mH >= 0 && y + mH < height) {
                        for (int xx = 0, mW = auxWx; xx < maskx[0].length; xx++, mW++) {
                            if (x + mW >= 0 && x + mW < width) {
                                sumX += rgb_to_luminance(image.getRGB(x + mW, y + mH)) * maskx[yy][xx];
                            }
                        }
                    }
                }

                int sumY = 0;
                for (int yy = 0, mH = auxHy; yy < masky.length; yy++, mH++) {
                    if (y + mH >= 0 && y + mH < height) {
                        for (int xx = 0, mW = auxWy; xx < masky[0].length; xx++, mW++) {
                            if (x + mW >= 0 && x + mW < width) {
                                sumY += rgb_to_luminance(image.getRGB(x + mW, y + mH)) * masky[yy][xx];
                            }
                        }
                    }
                }
                
                level = Math.abs(sumX) + Math.abs(sumY);
                if (level < 0) {
                    level = 0;
                } else if (level > 255) {
                    level = 255;
                }

                ret.setRGB(x,y , level_to_greyscale(level));
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
