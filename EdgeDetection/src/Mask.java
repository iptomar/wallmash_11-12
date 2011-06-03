/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jCardoso & Gonçalo
 */
public class Mask {
    private double[][] mask;
    private int width;
    private int height;
   
    public Mask (double[][] mask){
        this.mask=mask;
        this.height=mask.length;
        this.width=mask[0].length;
    }
    
    public Mask rotate(Mask mask) {
        int h = mask.getHeight();
        int w = mask.getWidth();
        double[] masktmp = new double[h * w];
        double[][] rotated = new double[w][h];
        int k = 0;
        for (int i = 0; i < w; i++) {
            for (int j = h - 1; j >= 0; j--) {
                masktmp[k++] = mask.getPositionValue(j,i);
            }
        }
        k = 0;
        for (int j = 0; j < w; j++) {
            for (int i = 0; i < h; i++) {
                rotated[j][i] = masktmp[k++];
            }
        }
        return new Mask(rotated);
    }
    
    public double getPositionValue (int h, int w){
        return this.mask[w][h];
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the mask
     */
    public double[][] getMask() {
        return mask;
    }
}
