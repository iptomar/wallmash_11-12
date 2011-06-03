
import java.awt.image.BufferedImage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jCardoso
 */
public class RGBImage {
    private int[][] image;
    private int width;
    private int height;
    
    
    public RGBImage (BufferedImage img){
        this.width=img.getWidth();
        this.height=img.getHeight();
        this.image= new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.image[x][y]=img.getRGB(x , y);
            }
        }
    }
    
    public RGBImage cloneRGBImage (RGBImage img){
        RGBImage ret=img;
        return ret; 
    }
    
    public BufferedImage buildImage (RGBImage img){
        int w=img.getWidth();
        int h=img.getHeight();
        BufferedImage ret=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                ret.setRGB(x, y, img.image[x][y]);
            }
        }
        return ret;
    }

    public int getPositionValue (int h, int w){
        return this.image[w][h];
    }
    
    public void setPositionValue (int h, int w, int val){
        this.image[w][h]=val;
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
}
