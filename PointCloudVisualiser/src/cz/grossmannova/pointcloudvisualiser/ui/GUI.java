/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.ui;

import cz.grossmannova.pointcloudvisualiser.PointCloudVisualiser;
import cz.grossmannova.pointcloudvisualiser.io.PointCloudFile;
import cz.grossmannova.pointcloudvisualiser.io.PointCloudFileCSV;
import cz.grossmannova.pointcloudvisualiser.models.ModelPointCloud;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Pavla
 */
public class GUI extends javax.swing.JFrame {

    private Thread thread;
    private final PointCloudVisualiser app;

    /**
     * Creates new form GUI
     */
    public GUI(PointCloudVisualiser app) {
        initComponents();
        this.app = app;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileOpener = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        graphicsCanvas = new java.awt.Canvas();
        butImport = new javax.swing.JButton();

        fileOpener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileOpenerActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        graphicsCanvas.setPreferredSize(new java.awt.Dimension(1040, 800));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(graphicsCanvas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addComponent(graphicsCanvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );

        butImport.setText("Import");
        butImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(butImport)
                .addContainerGap(79, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(butImport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {

                app.inicialization(graphicsCanvas);

            }, "OGL");
            thread.start();

        }        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void butImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImportActionPerformed
        fileOpener.showOpenDialog(this);
    }//GEN-LAST:event_butImportActionPerformed

    private void fileOpenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileOpenerActionPerformed
        if ("ApproveSelection".equals(evt.getActionCommand())) {
            PointCloudFile file = new PointCloudFileCSV(fileOpener.getSelectedFile().getAbsolutePath());
            app.sendModelToDraw(new ModelPointCloud(file.readPoints()));
        }
    }//GEN-LAST:event_fileOpenerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butImport;
    private javax.swing.JFileChooser fileOpener;
    private java.awt.Canvas graphicsCanvas;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
