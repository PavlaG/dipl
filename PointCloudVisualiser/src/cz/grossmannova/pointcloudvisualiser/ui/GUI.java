/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.ui;

import cz.grossmannova.pointcloudvisualiser.PointCloudVisualiser;
import cz.grossmannova.pointcloudvisualiser.io.PointCloudFile;
import cz.grossmannova.pointcloudvisualiser.io.PointCloudFileCSV;
import cz.grossmannova.pointcloudvisualiser.models.Block;
import cz.grossmannova.pointcloudvisualiser.models.BlockCloudModel;
import cz.grossmannova.pointcloudvisualiser.models.CubesCloudModel;
import cz.grossmannova.pointcloudvisualiser.models.Graph;
import cz.grossmannova.pointcloudvisualiser.models.GraphModel;
import cz.grossmannova.pointcloudvisualiser.models.ModelPointCloud;
import cz.grossmannova.pointcloudvisualiser.models.SphereModel;
import cz.grossmannova.pointcloudvisualiser.pathfinding.AStar;
import cz.grossmannova.pointcloudvisualiser.pathfinding.Dijkstra;
import cz.grossmannova.pointcloudvisualiser.pathfinding.Pathfinder;
import cz.grossmannova.pointcloudvisualiser.pointcloud.BlockMaker;
import cz.grossmannova.pointcloudvisualiser.pointcloud.BlockMakerType;
import cz.grossmannova.pointcloudvisualiser.pointcloud.ContourMaker;
import cz.grossmannova.pointcloudvisualiser.pointcloud.CubesMaker;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

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

        butImport.setText("Nahrát body");
        butImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImportActionPerformed(evt);
            }
        });

        jButton1.setText("Uložit screenshot");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Náhodně zvolit start a cíl");
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(butImport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(butImport)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
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

            loadObject("C:\\Users\\Pavla\\Documents\\bunny.txt");
        }
    }//GEN-LAST:event_formWindowOpened

    private void butImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImportActionPerformed
        fileOpener.showOpenDialog(this);
    }//GEN-LAST:event_butImportActionPerformed

    private void fileOpenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileOpenerActionPerformed
        if ("ApproveSelection".equals(evt.getActionCommand())) {
            loadObject(fileOpener.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_fileOpenerActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        app.setDoScreenshot(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//app.setRandomlySetStartAndFinish(true); 

// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void loadObject(String path) {
        PointCloudFile file = new PointCloudFileCSV(path);
        ModelPointCloud modelPointCloud = new ModelPointCloud(file.readPoints());
        app.sendModelToDraw(modelPointCloud);
        //System.out.println(modelPointCloud.pointsList.size());
        ContourMaker contourMaker = new ContourMaker(modelPointCloud.pointsList, modelPointCloud.getMaxY());
        ArrayList<ArrayList<ArrayList<Point>>> contours = contourMaker.generate();
        //System.out.println(contours);
        CubesMaker cubesMaker = new CubesMaker(contours, modelPointCloud.getMaxZ());
        List<Point> cubes = cubesMaker.generate();
        //System.out.println(cubes.size());
        CubesCloudModel cubesCloudModel = new CubesCloudModel(cubes);
        //app.sendModelToDraw(cubesCloudModel); //vykresluje vnitřní krychličky

        //System.out.println("V1 " + cubes.size());
        /*ArrayList<Point> cubesSet = new ArrayList<>();
        for (Point cube : cubes) {
            if (cubesSet.contains(cube)) {
                System.err.println("Duplicate!!!" + cube);
            } else {
                cubesSet.add(cube);
            }
        }
        cubes.clear();
        cubes.addAll(cubesSet);*/
        //System.out.println("V2 " + cubes.size());
        ////zjištění, jestli tam jsou duplicitní body
////        for (int i = 0; i < cubes.size(); i++) {
////            for (int j = 0; j < cubes.size(); j++) {
////                if (i == j) {
////                    continue;
////                }
////                if (cubes.get(i).equals(cubes.get(j))) {
////                    System.err.println("X Duplicate!!!" + cubes.get(i) + cubes.get(j));
////                }
////            }
////        }
//vytváření krychlí: 
        BlockMaker cubeBlockMaker = new BlockMaker(cubes, modelPointCloud.getMaxX(), modelPointCloud.getMaxY(), modelPointCloud.getMaxZ(), BlockMakerType.CUBE_EXPANSION);
        List<Block> cubeBlocks = cubeBlockMaker.generateCubes();
        BlockCloudModel cubeBlockCloudModel = new BlockCloudModel(cubeBlocks);
        //app.sendModelToDraw(cubeBlockCloudModel);

//vytváření kvádrů:
        BlockMaker cuboidBlockMaker = new BlockMaker(cubes, modelPointCloud.getMaxX(), modelPointCloud.getMaxY(), modelPointCloud.getMaxZ(), BlockMakerType.CUBOID_EXPANSION);
        List<Block> cuboidBlocks = cuboidBlockMaker.generateCuboids();

        BlockCloudModel cuboidBlockCloudModel = new BlockCloudModel(cuboidBlocks);
        //app.sendModelToDraw(cuboidBlockCloudModel);

        //vytváření krychlí skrz octree
        BlockMaker cubesOctreeBlockMaker = new BlockMaker(cubes, modelPointCloud.getMaxX(), modelPointCloud.getMaxY(), modelPointCloud.getMaxZ(), BlockMakerType.OCTREE);
        List<Block> cubesOctreeBlocks = cubesOctreeBlockMaker.generateCubesThroughOctree();

        BlockCloudModel cubeOctreeBlockCloudModel = new BlockCloudModel(cubesOctreeBlocks);
        // app.sendModelToDraw(cubeOctreeBlockCloudModel);

        Graph graph = new Graph(cubesOctreeBlocks, modelPointCloud.getMaxX(), modelPointCloud.getMaxY(), modelPointCloud.getMaxZ());
        GraphModel graphModel = new GraphModel(graph.getLineGraph()); //momentálně jen body
        // app.sendModelToDraw(graphModel);
//Dijkstra:
        Pathfinder pathfinder = new Dijkstra(graph);
        pathfinder.randomlySetStartAndFinish();
        if(pathfinder.findPath()){       
        GraphModel graphModelForPath = new GraphModel(pathfinder.getLineGraph());
       // app.sendModelToDraw(graphModelForPath);
        }  else System.out.println("nepodařilo se najít konec");
        
        
      if(pathfinder.findPath()){       
        BlockCloudModel blockModelForPath= new BlockCloudModel(pathfinder.getBlockGraph());
        app.sendModelToDraw(blockModelForPath);
        }  else System.out.println("nepodařilo se najít konec");
    
      //AStar
       Pathfinder pathfinder2 = new AStar(graph);
        pathfinder2.randomlySetStartAndFinish();
        if(pathfinder2.findPath()){       
        GraphModel graphModelForPath2 = new GraphModel(pathfinder2.getLineGraph());
        app.sendModelToDraw(graphModelForPath2);
        }  else System.out.println("nepodařilo se najít konec");
        
      
        SphereModel start = new SphereModel(pathfinder.getStart().getCenter());
        app.sendModelToDraw(start);
        SphereModel finish = new SphereModel(pathfinder.getFinish().getCenter());

        app.sendModelToDraw(finish);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butImport;
    private javax.swing.JFileChooser fileOpener;
    private java.awt.Canvas graphicsCanvas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
