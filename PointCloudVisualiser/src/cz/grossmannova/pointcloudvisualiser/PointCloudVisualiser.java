/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser;

import cz.grossmannova.pointcloudvisualiser.graphics.Camera;
import cz.grossmannova.pointcloudvisualiser.graphics.RenderManager;
import cz.grossmannova.pointcloudvisualiser.models.Model;
import cz.grossmannova.pointcloudvisualiser.ui.GUI;
import cz.grossmannova.pointcloudvisualiser.utils.LibUtils;
import java.awt.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Pavla
 */
public class PointCloudVisualiser {

    private GUI gui;
    private RenderManager renderManager;
    private Camera camera;
    private List<Model> models = new ArrayList<>();
    private boolean guiRequestedClose = false;
    private long lastFrame;
    private int delta;

    public static void main(String[] args) {

        PointCloudVisualiser app = new PointCloudVisualiser();
        app.initLibs();
        app.start();
    }

    public void start() {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PointCloudVisualiser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>
        //java.awt.EventQueue.invokeLater(new Runnable() {
        //    @Override
        //    public void run() {
        gui = new GUI(this);
        gui.setVisible(true);
        //    }
        //});
    }

    public void inicialization(Canvas canvas) {
        renderManager = new RenderManager();
        renderManager.createDisplay(canvas);
        logicInicialization();
        renderManager.inicialization();
        mainLoop();
        renderManager.cleanUp();
    }

    private void logicInicialization() {
        // camera = new Camera((noise.getSizeX() + 2) / 2 - 1, (noise.getSizeZ() + 2) / 2 - 1);
        camera = new Camera((500 + 2) / 2 - 1, (500 + 2) / 2 - 1);
        Keyboard.enableRepeatEvents(true);
    }

    private void mainLoop() {
        lastFrame = getTime();
        while (!Display.isCloseRequested() && !guiRequestedClose && !Thread.currentThread().isInterrupted()) {
            delta = getDelta();
            camera.input(delta);
            renderManager.render(camera, models);
            renderManager.testError();
            Display.update();
            Display.sync(60);
        }
    }

    private void initLibs() {
        try {
            LibUtils.createCopyOfNatives();
            LibUtils.addPathToLibrary(".\\natives");
        } catch (Exception ex) {
            Logger.getLogger(PointCloudVisualiser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }

    public void sendModelToDraw(Model model) {
        models.add(model);
    }
}
