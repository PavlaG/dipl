package cz.grossmannova.pointcloudvisualiser.graphics;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private float distance;
    private Vector3f rotation;
    private Vector3f position;
    private Vector3f move;
    private float delta;
    private double sin, cos;
    private final float speed = 0.02f;
    private final float speed2 = 0.08f;
    private Sphere centerpoint;

    public Camera(int x, int z) {
        rotation = new Vector3f(0, 0, 0);
        position = new Vector3f(-x, -50, -z);
        move = new Vector3f(0, 0, 0);
        distance = -10;
        delta = 1;
        centerpoint = new Sphere();
        centerpoint.setOrientation(GLU.GLU_OUTSIDE);
    }

    public void input(long delta) {
        this.delta = delta;
        move.set(0, 0, 0);
        while (Mouse.next()) {
            if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
                Mouse.setGrabbed(!Mouse.isGrabbed());
            }
            mouse();
        }
        keyboard();
        sin = Math.sin(Math.toRadians(rotation.y));
        cos = Math.cos(Math.toRadians(rotation.y));
        position.x += (cos * move.x + sin * move.z) * speed2 * delta;
        position.z += (-cos * move.z + sin * move.x) * speed2 * delta;

    }

    private void mouse() {
        if (Mouse.isGrabbed()) {
            distance += Mouse.getDWheel() / 6 * speed * delta;
            if (distance > 0) {
                distance = 0;
            }
            rotation.y += Mouse.getDX() * speed * delta;
            rotation.x -= Mouse.getDY() * speed * delta;
            if (rotation.x > 90) {
                rotation.x = 90;
            } else if (rotation.x < -90) {
                rotation.x = -90;
            }
        }
    }

    private void keyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            position.y -= 1 * speed2 * delta;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            position.y += 1 * speed2 * delta;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            move.x += 1;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            move.x -= 1;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            move.z -= 1;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            move.z += 1;
        }
    }

    public void applyTransformations() {
        GL11.glTranslatef(0, 0, distance);
        GL11.glRotatef(rotation.x, 1, 0, 0);
        GL11.glRotatef(rotation.y, 0, 1, 0);
        GL11.glRotatef(rotation.z, 0, 0, 1);
        GL11.glTranslatef(position.x, position.y, position.z);
    }

    public void applyPositionTransf() {
        GL11.glTranslatef(-position.x, -position.y, -position.z);
    }

    public Sphere getCenterpoint() {
        return centerpoint;
    }

}
