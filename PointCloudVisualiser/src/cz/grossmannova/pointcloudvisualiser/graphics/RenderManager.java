package cz.grossmannova.pointcloudvisualiser.graphics;

import cz.grossmannova.pointcloudvisualiser.models.Model;
import cz.grossmannova.pointcloudvisualiser.utils.ByteUtils;
import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author Pavla
 */
public class RenderManager {

    public final int width = 1040;
    public final int height = 800;

    private float ambientColor[] = {0, 0, 0, 1.0f};
    private int shaderProgram;
    private int vertexShader;
    private int fragmentShader;

    private FloatBuffer ambientColorBuffer;
    private FloatBuffer lightPositionBuffer;
    private FloatBuffer whiteLightBuffer;
    private FloatBuffer whiteLightBuffer2;
    private FloatBuffer matSpecularBuffer;
    private float matSpecular[] = {1, 1, 1, 1};
    private float lightPosition[] = {0, 1, 0, 1};
    private float whiteLight[] = {1, 1, 1, 1};
    private float b[] = {0, 0, 0, 1};
    private FloatBuffer bBuffer;

    public void createDisplay(Canvas canvas) {
        try {
            Display.setParent(canvas);
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle("App");
            Display.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(RenderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inicialization() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(70f, width / (float) height, 0.1f, height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glClearColor(0, 0, 0, 0);

        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

        shaderProgram = GL20.glCreateProgram();
        vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        StringBuilder vertexShaderSource = new StringBuilder();
        StringBuilder fragmentShaderSource = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/shader.vert")));
            String line;
            while ((line = br.readLine()) != null) {
                vertexShaderSource.append(line).append("\n");
            }
            br.close();
        } catch (IOException ex) {
            Display.destroy();
            System.exit(1);
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/shader.frag")));
            String line;
            while ((line = br.readLine()) != null) {
                fragmentShaderSource.append(line).append("\n");
            }
            br.close();
        } catch (IOException ex) {
            Display.destroy();
            System.exit(1);
        }

        GL20.glShaderSource(vertexShader, vertexShaderSource);
        GL20.glCompileShader(vertexShader);
        if (GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Vertex shader compile error!");
        }
        GL20.glShaderSource(fragmentShader, fragmentShaderSource);
        GL20.glCompileShader(fragmentShader);
        if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Fragment shader compile error!");
        }

        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL20.glLinkProgram(shaderProgram);
        GL20.glValidateProgram(shaderProgram);

//        GL11.glEnable(GL11.GL_NORMALIZE);
//
//        lightPositionBufferInicialization();
//        whiteLightBufferInicialization();
//        whiteLightBuffer2Inicialization();
//        matSpecularBufferInicialization();
//        bBufferInicialization();
//        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPositionBuffer);
//        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, whiteLightBuffer);
//        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, bBuffer);
//
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glEnable(GL11.GL_LIGHT0);
//        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
//        colorBufferInicialization();
//        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, ambientColorBuffer);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, asFloatBuffer(new float[]{0.1f, 0.1f, 0.1f, 1.0f}));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, asFloatBuffer(new float[]{0.5f, 0.5f, 0.5f, 1.0f}));
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE);
        GL11.glPointSize(10);

    }

    private static FloatBuffer asFloatBuffer(float[] values) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }

//    public void lightPosition() {
//        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPositionBuffer);
//    }
//
//    public void setLightPosition(float[] pos) {
//        lightPosition = pos;
//        lightPositionBuffer.clear();
//        lightPositionBufferInicialization();
//    }
//
//    public void colorBufferInicialization() {
//        ambientColorBuffer = BufferUtils.createFloatBuffer(4).put(ambientColor);
//        ambientColorBuffer.flip();
//    }
//
//    public void lightPositionBufferInicialization() {
//        lightPositionBuffer = BufferUtils.createFloatBuffer(4).put(lightPosition);
//        lightPositionBuffer.flip();
//    }
//
//    public void whiteLightBufferInicialization() {
//        whiteLightBuffer = BufferUtils.createFloatBuffer(4).put(whiteLight);
//        whiteLightBuffer.flip();
//    }
//
//    public void whiteLightBuffer2Inicialization() {
//        whiteLightBuffer2 = BufferUtils.createFloatBuffer(4).put(whiteLight);
//        whiteLightBuffer2.flip();
//    }
//
//    public void matSpecularBufferInicialization() {
//        matSpecularBuffer = BufferUtils.createFloatBuffer(4).put(matSpecular);
//        matSpecularBuffer.flip();
//    }
//
//    public void bBufferInicialization() {
//        bBuffer = BufferUtils.createFloatBuffer(4).put(b);
//        bBuffer.flip();
//    }
//
//    public void applyLightTransform() {
//        GL11.glTranslatef(lightPositionBuffer.get(0), lightPositionBuffer.get(1), lightPositionBuffer.get(2));
//    }

    public void testError() {
        int err = GL11.glGetError();
        if (err != 0) {
            System.out.println("Error: " + err);
            System.out.println(Util.translateGLErrorString(err));
        }
    }

    public void render(Camera camera, List<Model> models) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
        
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, asFloatBuffer(new float[]{0, 0, 0, 1}));
        
        camera.applyTransformations();
//        GL11.glPushMatrix();
//        camera.applyPositionTransf();
//        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glColor3f(1, 0, 0);
////        camera.getCenterpoint().draw(1, 10, 10);
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glPopMatrix();
        GL11.glPushMatrix();
        for (Model model : models) {
            GL20.glUseProgram(shaderProgram);
            model.draw();
        }
        GL11.glPopMatrix();
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        GL20.glDeleteProgram(shaderProgram);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        Display.destroy();
    }

    public void doScreenshot() {
        ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 3);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, pixels);
        BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        byte[] array = new byte[width * height * 3];
        pixels.get(array);
        int[] intArray = new int[width * height];
        int i = 0;
        int j = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                i = x + width * y;
                j = x + width * (height - y - 1);
                intArray[j] = ByteUtils.colorByteToInt(array[i * 3], array[i * 3 + 1], array[i * 3 + 2]);
            }

        }
        bufImg.setRGB(0, 0, width, height, intArray, 0, width);
        try {
            ImageIO.write(bufImg, "PNG", new File("abcde.png"));
        } catch (IOException ex) {
            Logger.getLogger(RenderManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
