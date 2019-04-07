package cz.grossmannova.pointcloudvisualiser;

import cz.grossmannova.pointcloudvisualiser.graphics.Camera;
import cz.grossmannova.pointcloudvisualiser.graphics.RenderManager;
import cz.grossmannova.pointcloudvisualiser.models.Model;
import cz.grossmannova.pointcloudvisualiser.ui.GUI;
import cz.grossmannova.pointcloudvisualiser.utils.LibUtils;
import java.awt.Canvas;
import java.awt.image.BufferedImage;
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
    private BufferedImage screenshot = null;
    private boolean doScreenshot = false;

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
        camera = new Camera(20, 40);
        Keyboard.enableRepeatEvents(true);
    }

    private void mainLoop() {
        while (!Display.isCloseRequested() && !guiRequestedClose && !Thread.currentThread().isInterrupted()) {
            long frameTime = getFrameTime();
            camera.input(frameTime);
            renderManager.resize();
            renderManager.render(camera, new ArrayList<>(models));
            renderManager.testError();
            if (doScreenshot) {
                screenshot = renderManager.doScreenshot();
                doScreenshot = false;
            }
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

    private long getFrameTime() {
        long currentTime = getTime();
        long delta = currentTime - lastFrame;
        lastFrame = currentTime;
        return delta;
    }

    public void deleteAllPathfindingModelsToDraw(List<Model> modelsToDelete) {
        for (Model model : modelsToDelete) {
            models.remove(model);
        }
    }

    public void sendModelToDraw(Model model) {
        models.add(model);
    }

    public void deleteModelFromModelsToDraw(Model model) {
        models.remove(model);
    }

    public void deleteAllModelsFromModelsToDraw() {
        models.clear();
    }

    public boolean isDoScreenshot() {
        return doScreenshot;
    }

    public void setDoScreenshot(boolean doScreenshot) {
        this.doScreenshot = doScreenshot;
    }

    public BufferedImage getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(BufferedImage screenshot) {
        this.screenshot = screenshot;
    }

}
