
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * index.java
 *
 * Created on 17/Abr/2011, 15:03:17
 */
/**
 *
 * @author Goncalo
 */
public class index extends javax.swing.JFrame {

    private File Fileimg;
    private double[] array_3x3;
    private BufferedImage imagemOriginal;
    private BufferedImage imagemPanel;
    double mask[][];

    /** Creates new form index */
    public index() {
        initComponents();
    }

    private void getMatrix() {
    }

    private BufferedImage callSobel(BufferedImage img) {
        BufferedImage edges = null;
        try {
            //create the detector
            SobelEdgeDetector detector = new SobelEdgeDetector();
            double[] array3x3 = new double[9];
            array3x3[0] = Double.parseDouble(txt1.getText());
            array3x3[1] = Double.parseDouble(txt2.getText());
            array3x3[2] = Double.parseDouble(txt3.getText());
            array3x3[3] = Double.parseDouble(txt4.getText());
            array3x3[4] = Double.parseDouble(txt5.getText());
            array3x3[5] = Double.parseDouble(txt6.getText());
            array3x3[6] = Double.parseDouble(txt7.getText());
            array3x3[7] = Double.parseDouble(txt8.getText());
            array3x3[8] = Double.parseDouble(txt9.getText());
            detector.setArray3x3(array3x3);
            //apply it to an image
            detector.setSourceImage(img);
            detector.process();
            edges = detector.getedgesImage();
            imagemPanel = edges;
            ImageIO.write(edges, "PNG", new File("SobelEdge.png"));
        } catch (Exception ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        }
        return edges;
    }

    private BufferedImage callCanny(BufferedImage img) {
        BufferedImage edges = null;
        try {
            //create the detector
            CannyEdgeDetector detector = new CannyEdgeDetector();
            //adjust its parameters as desired
            detector.setLowThreshold(0.5f);
            detector.setHighThreshold(1f);
            //apply it to an image
            detector.setSourceImage(img);
            detector.process();
            edges = detector.getEdgesImage();
            imagemPanel = edges;
            ImageIO.write(edges, "PNG", new File("CannyEdge.png"));
        } catch (Exception ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        }
        return edges;
    }

    private BufferedImage callRoberts(BufferedImage img) {
        BufferedImage edges = null;
        try {
            //create the detector
            RobertsEdgeDectector detector = new RobertsEdgeDectector();
            //apply it to an image
            detector.setSourceImage(img);
            detector.process();
            edges = detector.getedgesImage();
            imagemPanel = edges;
            ImageIO.write(edges, "PNG", new File("RobertsEdge.png"));
        } catch (Exception ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        }
        return edges;
    }

    private BufferedImage callPrewitt(BufferedImage img) {
        BufferedImage edges = null;
        try {
            //create the detector
            PrewittEdgeDectection detector = new PrewittEdgeDectection();
            //apply it to an image
            detector.setSourceImage(img);
            detector.process();
            edges = detector.getedgesImage();
            imagemPanel = edges;
            ImageIO.write(edges, "PNG", new File("PrewittEdge.png"));
        } catch (Exception ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        }
        return edges;
    }

    private void insertImage(BufferedImage img, JPanel jpanel) {
        try {
            Graphics g = jpanel.getGraphics();
            g.setColor(jpanel.getBackground());
            g.fillRect(0, 0, jpanel.getWidth(), jpanel.getWidth());
            if ((img.getWidth() > jpanel.getWidth()) || (img.getHeight() > jpanel.getHeight())) {
                g.drawImage(img, 0, 0, jpanel.getWidth(), jpanel.getHeight(), jpanel);
            } else {
                g.drawImage(img, 0, 0, jpanel);
            }
        } catch (Exception ex) {
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pImagem = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btCanny = new javax.swing.JButton();
        btLoadImg = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btSaveImg = new javax.swing.JButton();
        btSobel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txt1 = new javax.swing.JTextField();
        txt4 = new javax.swing.JTextField();
        txt7 = new javax.swing.JTextField();
        txt8 = new javax.swing.JTextField();
        txt5 = new javax.swing.JTextField();
        txt2 = new javax.swing.JTextField();
        txt3 = new javax.swing.JTextField();
        txt6 = new javax.swing.JTextField();
        txt9 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        btCombine = new javax.swing.JButton();
        btRoberts = new javax.swing.JButton();
        btPrewitt = new javax.swing.JButton();
        cbOptions = new javax.swing.JComboBox();
        cbGrey = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMask = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        btReadMask = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pImagem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pImagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pImagemMouseClicked(evt);
            }
        });
        pImagem.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Menu"));

        btCanny.setText("Canny");
        btCanny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCannyActionPerformed(evt);
            }
        });

        btLoadImg.setText("Open Image");
        btLoadImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLoadImgActionPerformed(evt);
            }
        });

        btSaveImg.setText("Save Image");
        btSaveImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveImgActionPerformed(evt);
            }
        });

        btSobel.setText("Sobel");
        btSobel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSobelActionPerformed(evt);
            }
        });

        txt1.setText("1");
        txt1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt1MouseClicked(evt);
            }
        });

        txt4.setText("2");
        txt4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt4MouseClicked(evt);
            }
        });

        txt7.setText("1");
        txt7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt7MouseClicked(evt);
            }
        });

        txt8.setText("0");
        txt8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt8MouseClicked(evt);
            }
        });

        txt5.setText("0");
        txt5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt5MouseClicked(evt);
            }
        });

        txt2.setText("0");
        txt2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt2MouseClicked(evt);
            }
        });

        txt3.setText("-1");
        txt3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt3MouseClicked(evt);
            }
        });

        txt6.setText("-2");
        txt6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt6MouseClicked(evt);
            }
        });

        txt9.setText("-1");
        txt9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt7, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addComponent(txt4, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addComponent(txt1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt8, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(txt5, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(txt2, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt9, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(txt6, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(txt3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btCombine.setText("not working");
        btCombine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCombineActionPerformed(evt);
            }
        });

        btRoberts.setText("Roberts");
        btRoberts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRobertsActionPerformed(evt);
            }
        });

        btPrewitt.setText("Prewitt");
        btPrewitt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPrewittActionPerformed(evt);
            }
        });

        cbOptions.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Default", "Smoothing", "Sharpening", "Raised", "Motion-blur", "Edge Detection" }));
        cbOptions.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbOptionsItemStateChanged(evt);
            }
        });

        cbGrey.setText("Colored?");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btLoadImg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(btSaveImg, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(btCanny, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(btSobel, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(btRoberts, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(btPrewitt, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(btCombine, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbGrey, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbOptions, javax.swing.GroupLayout.Alignment.LEADING, 0, 106, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btLoadImg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSaveImg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCanny)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSobel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btRoberts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btPrewitt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCombine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbGrey)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txtMask.setColumns(20);
        txtMask.setFont(new java.awt.Font("Courier New", 0, 14));
        txtMask.setRows(5);
        txtMask.setText("-1 -1 -1\n0 0 0\n1 1 1\n");
        jScrollPane1.setViewportView(txtMask);

        jLabel1.setText("mask");

        btReadMask.setText("read Mask");
        btReadMask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReadMaskActionPerformed(evt);
            }
        });

        jButton1.setText("apply mask");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("re-apply mask");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(btReadMask, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btReadMask)
                        .addGap(11, 11, 11)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void btCannyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCannyActionPerformed
        insertImage(callCanny(imagemOriginal), pImagem);
    }//GEN-LAST:event_btCannyActionPerformed

    private void btLoadImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLoadImgActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                Fileimg = chooser.getSelectedFile();
                imagemOriginal = ImageIO.read(chooser.getSelectedFile());
            } catch (Exception ex) {
            }
            insertImage(imagemOriginal, pImagem);
        }
    }//GEN-LAST:event_btLoadImgActionPerformed

    private void btSaveImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveImgActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                ImageIO.write(imagemPanel, "PNG", chooser.getSelectedFile());
            } catch (Exception ex) {
            }
        }
    }//GEN-LAST:event_btSaveImgActionPerformed

    private void btSobelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSobelActionPerformed
        insertImage(callSobel(imagemOriginal), pImagem);
    }//GEN-LAST:event_btSobelActionPerformed

    private void btCombineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCombineActionPerformed
        try {
//            // load source images
//            BufferedImage overlay = ImageIO.read(new File("SobelEdge.png"));
//            // create the new image, canvas size is the max. of both image sizes
//            int w = Math.max(image.getWidth(), overlay.getWidth());
//            int h = Math.max(image.getHeight(), overlay.getHeight());
//            BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//            // paint both images, preserving the alpha channels
//            Graphics g = combined.getGraphics();
//            g.drawImage(image, 0, 0, null);
//            g.drawImage(overlay, 0, 0, null);
//            // Save as new image
//            ImageIO.write(combined, "PNG", new File("combined.png"));



            BufferedImage image = ImageIO.read(new File("CannyEdge.png"));
            int[] ori = new int[image.getWidth() * image.getHeight()];
            PixelGrabber pg = new PixelGrabber(image, 0, 0, image.getWidth(), image.getHeight(), ori, 0, image.getWidth());
            try {
                pg.grabPixels();
            } catch (Exception e) {
                System.out.println("PixelGrabber failed");
            }
            BufferedImage image2 = ImageIO.read(new File("SobelEdge.png"));
            int[] ori2 = new int[image2.getWidth() * image2.getHeight()];
            PixelGrabber pg2 = new PixelGrabber(image2, 0, 0, image2.getWidth(), image2.getHeight(), ori2, 0, image2.getWidth());
            try {
                pg2.grabPixels();
            } catch (Exception e) {
                System.out.println("PixelGrabber failed");
            }


            int w = Math.max(image.getWidth(), image2.getWidth());
            int h = Math.max(image.getHeight(), image2.getHeight());

            int[] pixels = new int[w * h];
            for (int j = 0; j < pixels.length; j++) {
                if ((ori[j] & 0x00FFFFFF) != 0) {  //nao é preto, é branco
                    pixels[j] = ori[j];
                } else if ((ori2[j] & 0x00FFFFFF) != 0) { //nao é preto, é branco
                    pixels[j] = ori2[j];
                }
            }
            MemoryImageSource newImage = new MemoryImageSource(w, h, pixels, 0, w);
            Canvas c = new Canvas();
            Image finalImage = c.createImage(newImage);
            //BufferedImage combine = combine.createGraphics();
            imagemPanel.getGraphics().drawImage(finalImage, 0, 0, null);
            insertImage(imagemPanel, pImagem);


            ImageIO.write(imagemPanel, "PNG", new File("combined.png"));
        } catch (Exception ex) {
        }

    }//GEN-LAST:event_btCombineActionPerformed

    private void btRobertsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRobertsActionPerformed
        insertImage(callRoberts(imagemOriginal), pImagem);
    }//GEN-LAST:event_btRobertsActionPerformed

    private void btPrewittActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPrewittActionPerformed
        insertImage(callPrewitt(imagemOriginal), pImagem);
    }//GEN-LAST:event_btPrewittActionPerformed

    private void btReadMaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReadMaskActionPerformed
        String txt = txtMask.getText();
        String[] line = txt.split("\n");
        int lines = line.length;
        mask = new double[lines][];

        for (int y = 0; y < lines; y++) {
            String[] elem = line[y].split(" ");
            mask[y] = new double[elem.length];
            for (int x = 0; x < elem.length; x++) {
                mask[y][x] = Double.parseDouble(elem[x]);
            }
        }
        for (int y = 0; y < mask.length; y++) {
            System.out.println("");
            for (int x = 0; x < mask[y].length; x++) {
                System.out.print(mask[y][x] + "\t");
            }
        }
    }//GEN-LAST:event_btReadMaskActionPerformed

    private void readMask() {
        String str = "";
        txtMask.setText(txtMask.getText().trim());
        mask = new double[txtMask.getLineCount()][];
        try {
            BufferedReader in = new BufferedReader(new StringReader(txtMask.getText()));
            int y = 0;
            while ((str = in.readLine()) != null) {
                String[] elem = str.split(" ");
                mask[y] = new double[elem.length];
                for (int x = 0; x < elem.length; x++) {
                    mask[y][x] = Double.parseDouble(elem[x]);
                }
                y++;
            }
        } catch (Exception e) {
        }
    }

    private void setMask() {
        readMask();
        if (cbGrey.isSelected()) {
            BufferedImage buff = new BufferedImage(imagemOriginal.getWidth(), imagemOriginal.getHeight(), imagemOriginal.getType());
            Kernel kernel = new Kernel     (3, 3, new float[]{
                        Float.parseFloat(txt1.getText()), Float.parseFloat(txt2.getText()), Float.parseFloat(txt3.getText()),
                        Float.parseFloat(txt4.getText()), Float.parseFloat(txt5.getText()), Float.parseFloat(txt6.getText()),
                        Float.parseFloat(txt7.getText()), Float.parseFloat(txt8.getText()), Float.parseFloat(txt9.getText())
                    });
            ConvolveOp op = new ConvolveOp(kernel);
            op.filter(imagemOriginal, buff);
            imagemPanel = buff;
            insertImage(buff, pImagem);
        } else {
            //converte a imagem para preto e branco
            BufferedImage image = new BufferedImage(imagemOriginal.getWidth(), imagemOriginal.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics g = image.getGraphics();
            g.drawImage(imagemOriginal, 0, 0, imagemOriginal.getWidth(), imagemOriginal.getHeight(), null);
            g.dispose();

            BufferedImage buff = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            Kernel kernel = new Kernel(3, 3, new float[]{
                        Float.parseFloat(txt1.getText()), Float.parseFloat(txt2.getText()), Float.parseFloat(txt3.getText()),
                        Float.parseFloat(txt4.getText()), Float.parseFloat(txt5.getText()), Float.parseFloat(txt6.getText()),
                        Float.parseFloat(txt7.getText()), Float.parseFloat(txt8.getText()), Float.parseFloat(txt9.getText())
                    });
            ConvolveOp op = new ConvolveOp(kernel);
            op.filter(image, buff);
            imagemPanel = buff;
            insertImage(buff, pImagem);

        }
    }

//    private void applyMask(int py, int px) {
//        int cx = mask[0].length / 2;
//        int cy = mask.length / 2;
//        //  System.out.print("\n PIXEL " + px + " : " + py + " : ");
//        double sum = 0;
//        for (int y = -cy; y <= cy; y++) {
//            for (int x = -cx; x <= cx; x++) {
//                //garantir que o pixel existe
//                if (px + x < imagemOriginal.getWidth() && px + x >= 0
//                        && py + y < imagemOriginal.getHeight() && py + y >= 0) {
//                    //  System.out.print(" ("+(y+py) + " " + (x+px)+")");
//                    sum += imagemOriginal.getRGB(px + x, py + y) * mask[y + cy][x + cx];
//                }
//            }
//
//        }
//        //colocar na edge
//        imagemOriginal.setRGB(px, py, (int) sum);
//    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        try {
//            imagemOriginal = ImageIO.read(Fileimg);
//            for (int i = 0; i < imagemOriginal.getHeight(); i++) {
//                for (int j = 0; j < imagemOriginal.getWidth(); j++) {
//                    applyMask(i, j);
//                }
//            }
//            insertImage(imagemOriginal, pImagem);
//        } catch (IOException ex) {
//            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
//        }
        setMask();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt1MouseClicked
        txt1.selectAll();
    }//GEN-LAST:event_txt1MouseClicked

    private void txt2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt2MouseClicked
        txt2.selectAll();
    }//GEN-LAST:event_txt2MouseClicked

    private void txt3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt3MouseClicked
        txt3.selectAll();
    }//GEN-LAST:event_txt3MouseClicked

    private void txt4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt4MouseClicked
        txt4.selectAll();
    }//GEN-LAST:event_txt4MouseClicked

    private void txt5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt5MouseClicked
        txt5.selectAll();
    }//GEN-LAST:event_txt5MouseClicked

    private void txt6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt6MouseClicked
        txt6.selectAll();
    }//GEN-LAST:event_txt6MouseClicked

    private void txt7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt7MouseClicked
        txt7.selectAll();
    }//GEN-LAST:event_txt7MouseClicked

    private void txt8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt8MouseClicked
        txt8.selectAll();
    }//GEN-LAST:event_txt8MouseClicked

    private void txt9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt9MouseClicked
        txt9.selectAll();
    }//GEN-LAST:event_txt9MouseClicked

    private void cbOptionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbOptionsItemStateChanged
        int Selection;
        Selection = cbOptions.getSelectedIndex();
        if (Selection == 0) {
            txt1.setText("1");
            txt2.setText("0");
            txt3.setText("-1");
            txt4.setText("2");
            txt5.setText("0");
            txt6.setText("-2");
            txt7.setText("1");
            txt8.setText("0");
            txt9.setText("-1");
        } else if (Selection == 1) {
            txt1.setText("1");
            txt2.setText("1");
            txt3.setText("1");
            txt4.setText("1");
            txt5.setText("2");
            txt6.setText("1");
            txt7.setText("1");
            txt8.setText("1");
            txt9.setText("1");
        } else if (Selection == 2) {
            txt1.setText("-1");
            txt2.setText("-1");
            txt3.setText("-1");
            txt4.setText("-1");
            txt5.setText("9");
            txt6.setText("-1");
            txt7.setText("-1");
            txt8.setText("-1");
            txt9.setText("-1");
        } else if (Selection == 3) {
            txt1.setText("0");
            txt2.setText("0");
            txt3.setText("-2");
            txt4.setText("0");
            txt5.setText("2");
            txt6.setText("0");
            txt7.setText("1");
            txt8.setText("0");
            txt9.setText("0");
        } else if (Selection == 4) {
            txt1.setText("0");
            txt2.setText("0");
            txt3.setText("1");
            txt4.setText("0");
            txt5.setText("0");
            txt6.setText("0");
            txt7.setText("1");
            txt8.setText("0");
            txt9.setText("0");
        } else if (Selection == 5) {
            txt1.setText("-1");
            txt2.setText("-1");
            txt3.setText("-1");
            txt4.setText("-1");
            txt5.setText("8");
            txt6.setText("-1");
            txt7.setText("-1");
            txt8.setText("-1");
            txt9.setText("-1");
        }
    }//GEN-LAST:event_cbOptionsItemStateChanged

    private void pImagemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pImagemMouseClicked
        btLoadImg.doClick();
    }//GEN-LAST:event_pImagemMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        BufferedImage buff = new BufferedImage(imagemPanel.getWidth(), imagemPanel.getHeight(), imagemPanel.getType());
        Kernel kernel = new Kernel(3, 3, new float[]{
                    Float.parseFloat(txt1.getText()), Float.parseFloat(txt2.getText()), Float.parseFloat(txt3.getText()),
                    Float.parseFloat(txt4.getText()), Float.parseFloat(txt5.getText()), Float.parseFloat(txt6.getText()),
                    Float.parseFloat(txt7.getText()), Float.parseFloat(txt8.getText()), Float.parseFloat(txt9.getText())
                });
        ConvolveOp op = new ConvolveOp(kernel);
        op.filter(imagemPanel, buff);
        insertImage(buff, pImagem);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new index().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCanny;
    private javax.swing.JButton btCombine;
    private javax.swing.JButton btLoadImg;
    private javax.swing.JButton btPrewitt;
    private javax.swing.JToggleButton btReadMask;
    private javax.swing.JButton btRoberts;
    private javax.swing.JButton btSaveImg;
    private javax.swing.JButton btSobel;
    private javax.swing.JCheckBox cbGrey;
    private javax.swing.JComboBox cbOptions;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel pImagem;
    private javax.swing.JTextField txt1;
    private javax.swing.JTextField txt2;
    private javax.swing.JTextField txt3;
    private javax.swing.JTextField txt4;
    private javax.swing.JTextField txt5;
    private javax.swing.JTextField txt6;
    private javax.swing.JTextField txt7;
    private javax.swing.JTextField txt8;
    private javax.swing.JTextField txt9;
    private javax.swing.JTextArea txtMask;
    // End of variables declaration//GEN-END:variables
}
