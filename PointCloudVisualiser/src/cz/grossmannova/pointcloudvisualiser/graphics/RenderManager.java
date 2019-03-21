package cz.grossmannova.pointcloudvisualiser.graphics;

import cz.grossmannova.pointcloudvisualiser.models.Model;
import cz.grossmannova.pointcloudvisualiser.utils.ByteUtils;
import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author Pavla
 */
public class RenderManager {

    //private final int width = 1040;
    //private final int height = 800;

    // private float ambientColor[] = {0, 0, 0, 1.0f};
    private int shaderProgram;
    private int vertexShader;
    private int fragmentShader;
    private Canvas canvas;

//    private FloatBuffer ambientColorBuffer;
//    private FloatBuffer lightPositionBuffer;
//    private FloatBuffer whiteLightBuffer;
//    private FloatBuffer whiteLightBuffer2;
//    private FloatBuffer matSpecularBuffer;
//    private float matSpecular[] = {1, 1, 1, 1};
//    private float lightPosition[] = {0, 1, 0, 1};
//    private float whiteLight[] = {1, 1, 1, 1};
//    private float b[] = {0, 0, 0, 1};
//    private FloatBuffer bBuffer;
    public void createDisplay(Canvas canvas) {
        this.canvas = canvas;
        try {
            Display.setParent(canvas);
            System.out.println("Init Resolution: " + canvas.getWidth() + ":"+ canvas.getHeight());
            Display.setDisplayMode(new DisplayMode(canvas.getWidth(), canvas.getHeight()));
           
            Display.setTitle("App");
            ContextAttribs attribs = new ContextAttribs();
            Display.create(new PixelFormat().withSamples(2), attribs);
            Display.setInitialBackground(0, 0, 0);
        } catch (LWJGLException ex) {
            Logger.getLogger(RenderManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inicialization() {
        GL11.glEnable(GL13.GL_MULTISAMPLE);
        GL11.glViewport(0, 0, canvas.getWidth(), canvas.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(70f, canvas.getWidth() / (float) canvas.getHeight(), 0.1f, canvas.getHeight());
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

    public void testError() {
        int err = GL11.glGetError();
        if (err != 0) {
            System.out.println("Error: " + err);
            System.out.println(Util.translateGLErrorString(err));
        }
    }
    
    public void resize() {
        GL11.glViewport(0, 0, canvas.getWidth(), canvas.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(70f, canvas.getWidth() / (float) canvas.getHeight(), 0.1f, canvas.getHeight());
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    public void render(Camera camera, List<Model> models) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, asFloatBuffer(new float[]{0, 0, 0, 1}));

        camera.applyTransformations();

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
    
    public void prepareForScreenshot() {
        GL11.glViewport(0, 0, 1920, 1080);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(70f, 1920 / (float) 1080, 0.1f, 1080);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    
    public BufferedImage doScreenshotFHD() {
        ByteBuffer pixels = BufferUtils.createByteBuffer(1920 * 1080 * 3);
        GL11.glReadPixels(0, 0, 1920, 1080, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, pixels);
        BufferedImage bufImg = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
        byte[] array = new byte[1920 * 1080 * 3];
        pixels.get(array);
        int[] intArray = new int[1920 * 1080];
        int i = 0;
        int j = 0;
        for (int x = 0; x < 1920; x++) {
            for (int y = 0; y < 1080; y++) {
                i = x + 1920 * y;
                j = x + 1920 * (1080 - y - 1);
                intArray[j] = ByteUtils.colorByteToInt(array[i * 3], array[i * 3 + 1], array[i * 3 + 2]);
            }

        }
        
        bufImg.setRGB(0, 0, 1920, 1080, intArray, 0, 1920);
        return bufImg;
    }

    public BufferedImage doScreenshot() {
        ByteBuffer pixels = BufferUtils.createByteBuffer(canvas.getWidth() * canvas.getHeight() * 3);
        GL11.glReadPixels(0, 0, canvas.getWidth(), canvas.getHeight(), GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, pixels);
        BufferedImage bufImg = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
        byte[] array = new byte[canvas.getWidth() * canvas.getHeight() * 3];
        pixels.get(array);
        int[] intArray = new int[canvas.getWidth() * canvas.getHeight()];
        int i = 0;
        int j = 0;
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                i = x + canvas.getWidth() * y;
                j = x + canvas.getWidth() * (canvas.getHeight() - y - 1);
                intArray[j] = ByteUtils.colorByteToInt(array[i * 3], array[i * 3 + 1], array[i * 3 + 2]);
            }

        }
        
        bufImg.setRGB(0, 0, canvas.getWidth(), canvas.getHeight(), intArray, 0, canvas.getWidth());
        return bufImg;
    }

}
