
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
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
 * Created on 29/Mai/2011, 23:40:58
 */
/**
 *
 * @author Goncalo
 */
public class index extends javax.swing.JFrame {

    private BufferedImage imagemOriginal;
    private BufferedImage imagemEdge;
    private double[][] mask;
    private float[] k_mask;

    /** Creates new form index */
    public index() {
        initComponents();
    }

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
            k_mask = new float[mask.length * mask[0].length];
            for (int yy = 0; yy < mask.length; yy++) {
                for (int xx = 0; xx < mask[yy].length; xx++) {
                    k_mask[yy * mask[yy].length + xx] = (int) mask[yy][xx];
                }
            }
        } catch (Exception e) {
        }
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

    public BufferedImage cloneImage(BufferedImage image) {
        return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
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
        jPanel1 = new javax.swing.JPanel();
        btApply = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMask = new javax.swing.JTextArea();
        cbOptions = new javax.swing.JComboBox();
        btLoadImg = new javax.swing.JButton();
        btSaveImg = new javax.swing.JButton();
        btReApply = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        cbGrey = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pImagem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pImagem.setLayout(new java.awt.GridLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Menu"));

        btApply.setText("Apply");
        btApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btApplyActionPerformed(evt);
            }
        });

        txtMask.setColumns(12);
        txtMask.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        txtMask.setLineWrap(true);
        txtMask.setRows(1);
        txtMask.setText("-1 -1 -1\n0 0 0\n1 1 1\n");
        jScrollPane1.setViewportView(txtMask);

        cbOptions.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Default", "Smoothing", "Sharpening", "Raised", "Motion-blur", "Edge Detection" }));
        cbOptions.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbOptionsItemStateChanged(evt);
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

        btReApply.setText("Re-Apply");
        btReApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReApplyActionPerformed(evt);
            }
        });

        cbGrey.setText("Colored?");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbGrey, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(btLoadImg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(btSaveImg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(cbOptions, 0, 119, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(btApply, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(btReApply, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btLoadImg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSaveImg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbGrey)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btApply)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btReApply)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pImagem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbOptionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbOptionsItemStateChanged
        int Selection;
        Selection = cbOptions.getSelectedIndex();
        if (Selection == 0) {
            txtMask.setText("1 0 -1\n" + "2 0 -2\n" + "1 0 -1");
        } else if (Selection == 1) {
            txtMask.setText("1 1 1\n" + "1 2 1\n" + "1 1 1");
        } else if (Selection == 2) {
            txtMask.setText("-1 -1 -1\n" + "-1 9 -1\n" + "-1 -1 -1");
        } else if (Selection == 3) {
            txtMask.setText("0 0 -2\n" + "0 2 0\n" + "1 0 0");
        } else if (Selection == 4) {
            txtMask.setText("0 0 1\n" + "0 0 0\n" + "1 0 0");
        } else if (Selection == 5) {
            txtMask.setText("-1 -1 -1\n" + "-1 8 -1\n" + "-1 -1 -1");
        }
}//GEN-LAST:event_cbOptionsItemStateChanged

    private void btApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btApplyActionPerformed
        try {
            readMask();
            FilterEdge detector = new FilterEdge(cloneImage(imagemOriginal), mask);
            detector.setCheckColor(cbGrey.isSelected());
            detector.start();
            imagemEdge = detector.getEdgeImg();
            insertImage(cloneImage(imagemEdge), pImagem);
            ImageIO.write(imagemEdge, "JPG", new File("egde1111111111111111111111111111.jpg"));
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_btApplyActionPerformed

    private void btLoadImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLoadImgActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                imagemOriginal = ImageIO.read(chooser.getSelectedFile());
            } catch (Exception ex) {
            }
            insertImage(cloneImage(imagemOriginal), pImagem);
        }
}//GEN-LAST:event_btLoadImgActionPerformed

    private void btSaveImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveImgActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                ImageIO.write(imagemEdge, "PNG", chooser.getSelectedFile());
            } catch (Exception ex) {
            }
        }
}//GEN-LAST:event_btSaveImgActionPerformed

    private void btReApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReApplyActionPerformed
        try {
            readMask();
            FilterEdge detector = new FilterEdge(cloneImage(imagemEdge), mask);
            detector.setCheckColor(cbGrey.isSelected());
            detector.start();          
            imagemEdge = detector.getEdgeImg();
            insertImage(cloneImage(imagemEdge), pImagem);
            ImageIO.write(imagemEdge, "JPG", new File("egde1111111111111111111111111111.jpg"));
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_btReApplyActionPerformed

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
    private javax.swing.JButton btApply;
    private javax.swing.JButton btLoadImg;
    private javax.swing.JButton btReApply;
    private javax.swing.JButton btSaveImg;
    private javax.swing.JCheckBox cbGrey;
    private javax.swing.JComboBox cbOptions;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pImagem;
    private javax.swing.JTextArea txtMask;
    // End of variables declaration//GEN-END:variables
}
