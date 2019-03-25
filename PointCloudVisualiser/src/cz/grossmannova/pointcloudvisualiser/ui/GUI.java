/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.ui;

import cz.grossmannova.pointcloudvisualiser.PointCloudVisualiser;
import cz.grossmannova.pointcloudvisualiser.Service;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Pavla
 */
public class GUI extends javax.swing.JFrame {

    private Thread thread;
    private final PointCloudVisualiser app;
    private Service service;
    private BufferedImage screenshot = null;
    private boolean butPathfindingWasUsed;
    private boolean butStartWasUsed;
    private String path = new String("");

    /**
     * Creates new form GUI
     */
    public GUI(PointCloudVisualiser app) {
        initComponents();
        this.app = app;
        inputContours.setText("");
        inputScale.setText("");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileOpener = new javax.swing.JFileChooser();
        butGroupProjection = new javax.swing.ButtonGroup();
        butGroupPathfinding = new javax.swing.ButtonGroup();
        butGroupPathfndingSpace = new javax.swing.ButtonGroup();
        fileSaver = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        graphicsCanvas = new java.awt.Canvas();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        radCubesOctree = new javax.swing.JRadioButton();
        radPoints = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        radPathLine = new javax.swing.JRadioButton();
        radPathBlocks = new javax.swing.JRadioButton();
        radPathfinCubes = new javax.swing.JRadioButton();
        radPathfinCuboids = new javax.swing.JRadioButton();
        radPathfinCubesOctree = new javax.swing.JRadioButton();
        butPathfinding = new javax.swing.JButton();
        warningLabel = new javax.swing.JLabel();
        butImport = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        radContours = new javax.swing.JRadioButton();
        radTinyCubes = new javax.swing.JRadioButton();
        radCubes = new javax.swing.JRadioButton();
        radCuboids = new javax.swing.JRadioButton();
        txtTinyCubesNum = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCubesNum = new javax.swing.JLabel();
        txtCuboidsNum = new javax.swing.JLabel();
        txtOctreeNum = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTinyCubesTime = new javax.swing.JLabel();
        txtCubesTime = new javax.swing.JLabel();
        txtCuboidsTime = new javax.swing.JLabel();
        txtOctreeTime = new javax.swing.JLabel();
        txtPointsNum = new javax.swing.JLabel();
        txtContoursNum = new javax.swing.JLabel();
        txtContoursTime = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        inputScale = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        inputContours = new javax.swing.JTextField();
        butStart = new javax.swing.JButton();

        fileOpener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileOpenerActionPerformed(evt);
            }
        });

        fileSaver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaverActionPerformed(evt);
            }
        });

        jPanel1.setMaximumSize(new java.awt.Dimension(1040, 800));
        jPanel1.setPreferredSize(new java.awt.Dimension(1040, 800));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1500, 880));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        graphicsCanvas.setMinimumSize(new java.awt.Dimension(1, 1));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        butGroupProjection.add(radCubesOctree);
        radCubesOctree.setText("Krychle skrz oktálová strom");
        radCubesOctree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radCubesOctreeActionPerformed(evt);
            }
        });
        jPanel3.add(radCubesOctree, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 155, -1));

        butGroupProjection.add(radPoints);
        radPoints.setSelected(true);
        radPoints.setText("Body");
        radPoints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPointsActionPerformed(evt);
            }
        });
        jPanel3.add(radPoints, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 155, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));

        butGroupPathfinding.add(radPathLine);
        radPathLine.setText("Cesta čáry");
        radPathLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPathLineActionPerformed(evt);
            }
        });

        butGroupPathfinding.add(radPathBlocks);
        radPathBlocks.setSelected(true);
        radPathBlocks.setText("Cesta bloky");
        radPathBlocks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPathBlocksActionPerformed(evt);
            }
        });

        butGroupPathfndingSpace.add(radPathfinCubes);
        radPathfinCubes.setSelected(true);
        radPathfinCubes.setText("Krychle");
        radPathfinCubes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPathfinCubesActionPerformed(evt);
            }
        });

        butGroupPathfndingSpace.add(radPathfinCuboids);
        radPathfinCuboids.setText("Kvádry");
        radPathfinCuboids.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPathfinCuboidsActionPerformed(evt);
            }
        });

        butGroupPathfndingSpace.add(radPathfinCubesOctree);
        radPathfinCubesOctree.setText("Krychle skrz oktálový strom");
        radPathfinCubesOctree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPathfinCubesOctreeActionPerformed(evt);
            }
        });

        butPathfinding.setText("Demonstrace hledání cesty");
        butPathfinding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPathfindingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(radPathfinCubes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(radPathBlocks, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radPathLine, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(radPathfinCubesOctree, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(radPathfinCuboids, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(warningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(butPathfinding, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 21, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(butPathfinding)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radPathBlocks)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radPathLine)
                .addGap(27, 27, 27)
                .addComponent(radPathfinCubes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radPathfinCuboids)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radPathfinCubesOctree)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(warningLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 431, -1, -1));

        butImport.setText("Nahrát body");
        butImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImportActionPerformed(evt);
            }
        });
        jPanel3.add(butImport, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 410, -1));

        jButton1.setText("Uložit screenshot");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 410, -1));

        butGroupProjection.add(radContours);
        radContours.setText("Kontury");
        radContours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radContoursActionPerformed(evt);
            }
        });
        jPanel3.add(radContours, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 155, -1));

        butGroupProjection.add(radTinyCubes);
        radTinyCubes.setText("Základní krychle");
        radTinyCubes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radTinyCubesActionPerformed(evt);
            }
        });
        jPanel3.add(radTinyCubes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 155, -1));

        butGroupProjection.add(radCubes);
        radCubes.setText("Krychle");
        radCubes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radCubesActionPerformed(evt);
            }
        });
        jPanel3.add(radCubes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 155, -1));

        butGroupProjection.add(radCuboids);
        radCuboids.setText("Kvádry");
        radCuboids.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radCuboidsActionPerformed(evt);
            }
        });
        jPanel3.add(radCuboids, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 155, -1));

        txtTinyCubesNum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(txtTinyCubesNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 70, 21));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Počet");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 70, -1));

        txtCubesNum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(txtCubesNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 70, 21));

        txtCuboidsNum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(txtCuboidsNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 70, 21));

        txtOctreeNum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(txtOctreeNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 70, 21));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Čas v ms");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 97, -1));

        txtTinyCubesTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTinyCubesTime.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(txtTinyCubesTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, 97, 21));

        txtCubesTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtCubesTime.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(txtCubesTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 300, 97, 21));

        txtCuboidsTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtCuboidsTime.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(txtCuboidsTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 340, 97, 21));

        txtOctreeTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtOctreeTime.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(txtOctreeTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, 97, 21));

        txtPointsNum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtPointsNum.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(txtPointsNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 70, 21));

        txtContoursNum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtContoursNum.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(txtContoursNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 70, 21));

        txtContoursTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtContoursTime.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel3.add(txtContoursTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 97, 21));

        jLabel3.setText("Velikost");
        jLabel3.setMaximumSize(new java.awt.Dimension(24, 21));
        jLabel3.setMinimumSize(new java.awt.Dimension(24, 21));
        jLabel3.setPreferredSize(new java.awt.Dimension(24, 21));
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 40, -1));

        inputScale.setMinimumSize(new java.awt.Dimension(40, 21));
        jPanel3.add(inputScale, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 140, -1));

        jLabel4.setText("Kontury (pomocná vzdálenost)");
        jLabel4.setMaximumSize(new java.awt.Dimension(35, 21));
        jLabel4.setMinimumSize(new java.awt.Dimension(35, 21));
        jLabel4.setPreferredSize(new java.awt.Dimension(35, 21));
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 160, -1));

        inputContours.setPreferredSize(new java.awt.Dimension(7, 21));
        jPanel3.add(inputContours, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 140, -1));

        butStart.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        butStart.setText("Start");
        butStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butStartActionPerformed(evt);
            }
        });
        jPanel3.add(butStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, -1, -1));

        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(graphicsCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graphicsCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 811, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(() -> {

                app.inicialization(graphicsCanvas);

            }, "OGL");
            thread.start();

            // loadObject("C:\\Users\\Pavla\\Documents\\bunny.txt");
        }
    }//GEN-LAST:event_formWindowOpened

    private void butImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImportActionPerformed
        fileOpener.showOpenDialog(this);
        butPathfindingWasUsed = false;

    }//GEN-LAST:event_butImportActionPerformed

    private void fileOpenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileOpenerActionPerformed
        if ("ApproveSelection".equals(evt.getActionCommand())) {
            butStartWasUsed = false;
            loadObject(fileOpener.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_fileOpenerActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (service != null && butStartWasUsed) {
            app.setDoScreenshot(true);

            try {
                while ((screenshot = app.getScreenshot()) == null) {
                    Thread.sleep(100);
                }
                fileSaver.showSaveDialog(this);
            } catch (InterruptedException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            app.setScreenshot(null);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void radContoursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radContoursActionPerformed
        if (service != null && butStartWasUsed) {
            if (radContours.isSelected()) {

                service.setVisibility(service.getContoursModel());
            }
        }
    }//GEN-LAST:event_radContoursActionPerformed

    private void radTinyCubesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radTinyCubesActionPerformed
        if (service != null && butStartWasUsed) {
            if (radTinyCubes.isSelected()) {
                service.setVisibility(service.getCubesCloudModel());
            }
        }
    }//GEN-LAST:event_radTinyCubesActionPerformed

    private void radCubesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radCubesActionPerformed
        if (service != null && butStartWasUsed) {
            if (radCubes.isSelected()) {
                service.setVisibility(service.getCubeBlockCloudModel());
            }
        }
    }//GEN-LAST:event_radCubesActionPerformed

    private void radCuboidsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radCuboidsActionPerformed
        if (service != null && butStartWasUsed) {
            if (radCuboids.isSelected()) {
                service.setVisibility(service.getCuboidBlockCloudModel());
            }
        }
    }//GEN-LAST:event_radCuboidsActionPerformed

    private void radCubesOctreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radCubesOctreeActionPerformed
        if (service != null && butStartWasUsed) {
            if (radCubesOctree.isSelected()) {
                service.setVisibility(service.getCubeOctreeBlockCloudModel());
            }
        }
    }//GEN-LAST:event_radCubesOctreeActionPerformed

    private void radPointsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPointsActionPerformed
        if (service != null && butStartWasUsed) {
            if (radPoints.isSelected()) {
                service.setVisibility(service.getModelPointCloud());
            }
        }
    }//GEN-LAST:event_radPointsActionPerformed

    private void radPathLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPathLineActionPerformed
//        service.getBlockModelForPathCubes().setVisible(false);
//        service.getGraphModelForPathCubes().setVisible(true);
//        service.getStart().setVisible(true);
//        service.getFinish().setVisible(true);
        buttonsIfElse();
    }//GEN-LAST:event_radPathLineActionPerformed

    private void butPathfindingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPathfindingActionPerformed
        butPathfindingWasUsed = true;
        warningLabel.setText("");
        if (service != null && butStartWasUsed) {
            if (radCubesOctree.isSelected() || radCuboids.isSelected() || radCubes.isSelected() || radTinyCubes.isSelected()) {
                radPoints.setSelected(true);
                service.setVisibility(service.getModelPointCloud());
            }
            int value = service.graphAndSoOn2();
            if (value == 1) {
                warningLabel.setText("");
                //   if (!service.isSameBlock()) {

                if (radPathfinCubes.isSelected()) {
                    if (radPathBlocks.isSelected()) {
                        service.getBlockModelForPathCubes().setVisible(true);
                        service.getStartCubes().setVisible(false);
                        service.getFinishCubes().setVisible(false);
                    } else {
                        service.getGraphModelForPathCubes().setVisible(true);
                        service.getStartCubes().setVisible(true);
                        service.getFinishCubes().setVisible(true);
                    }
                } else if (radPathfinCuboids.isSelected()) {
                    if (radPathBlocks.isSelected()) {
                        service.getBlockModelForPathCuboids().setVisible(true);
                        service.getStartCuboids().setVisible(false);
                        service.getFinishCuboids().setVisible(false);
                    } else {
                        service.getGraphModelForPathCuboids().setVisible(true);
                        service.getStartCuboids().setVisible(true);
                        service.getFinishCuboids().setVisible(true);
                    }
                } else if (radPathfinCubesOctree.isSelected()) {

                    if (radPathBlocks.isSelected()) {
                        service.getBlockModelForPathCubesOctree().setVisible(true);
                        service.getStartCubesOctree().setVisible(false);
                        service.getFinishCubesOctree().setVisible(false);
                    } else {
                        service.getGraphModelForPathCubesOctree().setVisible(true);
                        service.getStartCubesOctree().setVisible(true);
                        service.getFinishCubesOctree().setVisible(true);
                    }

                }
//                } else {
//                    warningLabel.setText("Start a cíl se nacházají ve stejném bloku, opakujte pokus.");
//                }
            } else if (value == 2) {
                warningLabel.setText("Start a cíl se nacházají ve stejném bloku, opakujte pokus.");
            } else if (value == 0) {
                warningLabel.setText("Cesta mezi těmito bloky neexistuje.");
            }
        }
    }//GEN-LAST:event_butPathfindingActionPerformed

    private void radPathfinCubesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPathfinCubesActionPerformed
        // if (service != null && !butStartWasUsed) {
        buttonsIfElse();
        //   }
    }//GEN-LAST:event_radPathfinCubesActionPerformed

    private void fileSaverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSaverActionPerformed
        if ("ApproveSelection".equals(evt.getActionCommand())) {
            try {
                String s = fileSaver.getSelectedFile().getAbsolutePath();
                if (!s.endsWith(".png") && !s.endsWith(".PNG")) {
                    s = s.concat(".png");
                }
                ImageIO.write(screenshot, "PNG", new File(s));

            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        screenshot = null;
    }//GEN-LAST:event_fileSaverActionPerformed

    private void radPathBlocksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPathBlocksActionPerformed
//        //if(radPathBlocks.isSelected()){
//        service.getGraphModelForPathCubes().setVisible(false);
//        service.getBlockModelForPathCubes().setVisible(true);
//        service.getStart().setVisible(false);
//        service.getFinish().setVisible(false);
//        // }
        buttonsIfElse();
    }//GEN-LAST:event_radPathBlocksActionPerformed

    private void buttonsIfElse() {
        if (service != null && butStartWasUsed) {
            service.setAllPathfindingModelsToUnvisible();
            warningLabel.setText("");
            if (butPathfindingWasUsed) {
                warningLabel.setText("");
                int value = service.getPathFound();
                if (value == 1) {

                    if (radPathfinCubes.isSelected()) {
                        // service.graphAndSoOn(service.getCubeBlocks(), radPathLine.isSelected());
                        if (radPathBlocks.isSelected()) {
                            service.getBlockModelForPathCubes().setVisible(true);
                            service.getStartCubes().setVisible(false);
                            service.getFinishCubes().setVisible(false);
                        } else {
                            service.getGraphModelForPathCubes().setVisible(true);
                            service.getStartCubes().setVisible(true);
                            service.getFinishCubes().setVisible(true);
                        }
                    } else if (radPathfinCuboids.isSelected()) {
                        //service.graphAndSoOn(service.getCuboidBlocks(), radPathLine.isSelected());
                        if (radPathBlocks.isSelected()) {
                            service.getBlockModelForPathCuboids().setVisible(true);
                            service.getStartCuboids().setVisible(false);
                            service.getFinishCuboids().setVisible(false);
                        } else {
                            service.getGraphModelForPathCuboids().setVisible(true);
                            service.getStartCuboids().setVisible(true);
                            service.getFinishCuboids().setVisible(true);
                        }
                    } else if (radPathfinCubesOctree.isSelected()) {

                        if (radPathBlocks.isSelected()) {
                            service.getBlockModelForPathCubesOctree().setVisible(true);
                            service.getStartCubesOctree().setVisible(false);
                            service.getFinishCubesOctree().setVisible(false);
                        } else {
                            service.getGraphModelForPathCubesOctree().setVisible(true);
                            service.getStartCubesOctree().setVisible(true);
                            service.getFinishCubesOctree().setVisible(true);
                        }
                        //service.graphAndSoOn(service.getCubesOctreeBlocks(), radPathLine.isSelected());
                    }
                } else if (value == 2) {
                    warningLabel.setText("Start a cíl se nacházají ve stejném bloku, opakujte pokus.");
                } else if (value == 0) {
                    warningLabel.setText("Cesta mezi těmito bloky neexistuje.");
                }

            }
        }
    }

    private void radPathfinCuboidsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPathfinCuboidsActionPerformed
        buttonsIfElse();
    }//GEN-LAST:event_radPathfinCuboidsActionPerformed

    private void radPathfinCubesOctreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPathfinCubesOctreeActionPerformed
        buttonsIfElse();
    }//GEN-LAST:event_radPathfinCubesOctreeActionPerformed

    private void butStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butStartActionPerformed
        if (!path.isEmpty()) {
            butPathfindingWasUsed = false;
            app.deleteAllModelsFromModelsToDraw();

            int scale;
            if (!inputScale.getText().isEmpty()) {
                try {
                    scale = Integer.parseInt(inputScale.getText());
                    if (scale < 10) {
                        scale = 10;
                        inputScale.setText("10");
                    }

                } catch (NumberFormatException e) {
                    scale = 30;
                    inputScale.setText("30");
                }
            } else {
                scale = 30;
                inputScale.setText("30");
            }

            service = new Service(path, app, scale);
            if (service != null) {
                butStartWasUsed = true;
                int contoursDistanceLimit = 0;
                if (!inputContours.getText().isEmpty()) {
                    try {
                        contoursDistanceLimit = Integer.parseInt(inputContours.getText());
                        if (contoursDistanceLimit < 1) {
                            contoursDistanceLimit = 1;
                            inputContours.setText("1");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("catch");
                         inputContours.setText("10");
                        contoursDistanceLimit = 10;
                    }
                } else {
                    System.out.println("else");
                     inputContours.setText("10");
                    contoursDistanceLimit = 10;
                }

                service.inicialisation(contoursDistanceLimit);

                txtTinyCubesNum.setText(Integer.toString(service.getCubes().size()));
                txtCubesNum.setText(Integer.toString(service.getCubeBlocks().size()));
                txtCuboidsNum.setText(Integer.toString(service.getCuboidBlocks().size()));
                txtOctreeNum.setText(Integer.toString(service.getCubesOctreeBlocks().size()));

                txtTinyCubesTime.setText(Long.toString(service.getCubesMaker().getTime()));
                txtCubesTime.setText(Long.toString(service.getCubeBlockMaker().getTime() + service.getCubesMaker().getTime()));
                txtCuboidsTime.setText(Long.toString(service.getCuboidBlockMaker().getTime() + service.getCubesMaker().getTime()));
                txtOctreeTime.setText(Long.toString(service.getCubesOctreeBlockMaker().getTime() + service.getCubesMaker().getTime()));
                txtPointsNum.setText(Integer.toString(service.getModelPointCloud().getPointsList().size()));
                txtContoursNum.setText(Integer.toString(service.getContourMaker().getAmountOfContours()));
                txtContoursTime.setText(Long.toString(service.getContourMaker().getTime()));
                radPoints.setSelected(true);
                radPathBlocks.setSelected(true);
                radPathfinCubes.setSelected(true);
            }
        }
    }//GEN-LAST:event_butStartActionPerformed

    private void loadObject(String path) {
        app.deleteAllModelsFromModelsToDraw();
        this.path = path;
        // service = new Service(path, app);

//        service.inicialisation();
//
//        txtTinyCubesNum.setText(Integer.toString(service.getCubes().size()));
//        txtCubesNum.setText(Integer.toString(service.getCubeBlocks().size()));
//        txtCuboidsNum.setText(Integer.toString(service.getCuboidBlocks().size()));
//        txtOctreeNum.setText(Integer.toString(service.getCubesOctreeBlocks().size()));
//
//        txtTinyCubesTime.setText(Long.toString(service.getCubesMaker().getTime()));
//        txtCubesTime.setText(Long.toString(service.getCubeBlockMaker().getTime() + service.getCubesMaker().getTime()));
//        txtCuboidsTime.setText(Long.toString(service.getCuboidBlockMaker().getTime() + service.getCubesMaker().getTime()));
//        txtOctreeTime.setText(Long.toString(service.getCubesOctreeBlockMaker().getTime() + service.getCubesMaker().getTime()));
//        txtPointsNum.setText(Integer.toString(service.getModelPointCloud().getPointsList().size()));
//        txtContoursNum.setText(Integer.toString(service.getContourMaker().getAmountOfContours()));
//        txtContoursTime.setText(Long.toString(service.getContourMaker().getTime()));
//        radPoints.setSelected(true);
//        radPathBlocks.setSelected(true);
//        radPathfinCubes.setSelected(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup butGroupPathfinding;
    private javax.swing.ButtonGroup butGroupPathfndingSpace;
    private javax.swing.ButtonGroup butGroupProjection;
    private javax.swing.JButton butImport;
    private javax.swing.JButton butPathfinding;
    private javax.swing.JButton butStart;
    private javax.swing.JFileChooser fileOpener;
    private javax.swing.JFileChooser fileSaver;
    private java.awt.Canvas graphicsCanvas;
    private javax.swing.JTextField inputContours;
    private javax.swing.JTextField inputScale;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton radContours;
    private javax.swing.JRadioButton radCubes;
    private javax.swing.JRadioButton radCubesOctree;
    private javax.swing.JRadioButton radCuboids;
    private javax.swing.JRadioButton radPathBlocks;
    private javax.swing.JRadioButton radPathLine;
    private javax.swing.JRadioButton radPathfinCubes;
    private javax.swing.JRadioButton radPathfinCubesOctree;
    private javax.swing.JRadioButton radPathfinCuboids;
    private javax.swing.JRadioButton radPoints;
    private javax.swing.JRadioButton radTinyCubes;
    private javax.swing.JLabel txtContoursNum;
    private javax.swing.JLabel txtContoursTime;
    private javax.swing.JLabel txtCubesNum;
    private javax.swing.JLabel txtCubesTime;
    private javax.swing.JLabel txtCuboidsNum;
    private javax.swing.JLabel txtCuboidsTime;
    private javax.swing.JLabel txtOctreeNum;
    private javax.swing.JLabel txtOctreeTime;
    private javax.swing.JLabel txtPointsNum;
    private javax.swing.JLabel txtTinyCubesNum;
    private javax.swing.JLabel txtTinyCubesTime;
    private javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables
}
