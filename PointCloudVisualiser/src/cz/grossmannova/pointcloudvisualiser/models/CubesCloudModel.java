package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class CubesCloudModel extends Model {

    private int listId = -1;

    public CubesCloudModel(List<Point> pointsList) {
        super(pointsList);
    }

    @Override
    public void draw() {
        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 128f);
        GL11.glColor3f(0.6f, 0.6f, 0.6f);

        if (listId == -1) {
            listId = GL11.glGenLists(1);
            GL11.glNewList(listId, GL11.GL_COMPILE_AND_EXECUTE);
            GL11.glBegin(GL11.GL_QUADS);
            for (Point point : pointsList) {
                { // TOP
                    GL11.glNormal3f(0, 1, 0);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, 1, 0);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, 1, 0);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, 1, 0);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() + 0.5f);
                }
                { // BOTTOM
                    GL11.glNormal3f(0, -1, 0);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, -1, 0);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, -1, 0);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, -1, 0);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() - 0.5f);
                }
                { // LEFT
                    GL11.glNormal3f(-1, 0, 0);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(-1, 0, 0);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(-1, 0, 0);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(-1, 0, 0);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() + 0.5f);
                }
                { // RIGHT
                    GL11.glNormal3f(1, 0, 0);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(1, 0, 0);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(1, 0, 0);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(1, 0, 0);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() + 0.5f);
                }
                { // FRONT
                    GL11.glNormal3f(0, 0, 1);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, 0, 1);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, 0, 1);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, 0, 1);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() + 0.5f);
                }
                { // BACK
                    GL11.glNormal3f(0, 0, -1);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, 0, -1);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() - 0.5f, point.getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, 0, -1);
                    GL11.glVertex3f(point.getCoords().getX() - 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, 0, -1);
                    GL11.glVertex3f(point.getCoords().getX() + 0.5f, point.getCoords().getY() + 0.5f, point.getCoords().getZ() - 0.5f);
                }
            }
            GL11.glEnd();
            GL11.glEndList();
        } else {
            GL11.glCallList(listId);
        }
    }

}
