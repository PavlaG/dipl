package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class CubesCloudModel extends Model {

    private int listId = -1;
    //private Vector3f colors;

    public CubesCloudModel(List<Point> pointsList, List<Vector3f> colors) {
      // super(colors);
        super(pointsList, colors);
    }

    @Override
    public void draw() {
        if(isVisible()){
        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 128f);
        GL11.glColor3f(0.7f, 1f, 1f);

        if (listId == -1) {
            listId = GL11.glGenLists(1);
            GL11.glNewList(listId, GL11.GL_COMPILE_AND_EXECUTE);
            GL11.glBegin(GL11.GL_QUADS);
           // for (Point point : pointsList) {
                for (int i = 0; i < pointsList.size(); i++) {
                    
             //   }
                GL11.glColor3f(colors.get(i).x,colors.get(i).y,colors.get(i).z);
                { // TOP
                    GL11.glNormal3f(0, 1, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f, pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, 1, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, 1, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, 1, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f, pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                }
                { // BOTTOM
                    GL11.glNormal3f(0, -1, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, -1, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, -1, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, -1, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                }
                { // LEFT
                    GL11.glNormal3f(-1, 0, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(-1, 0, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(-1, 0, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(-1, 0, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                }
                { // RIGHT
                    GL11.glNormal3f(1, 0, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(1, 0, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f, pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(1, 0, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f, pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(1, 0, 0);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                }
                { // FRONT
                    GL11.glNormal3f(0, 0, 1);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f, pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, 0, 1);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, 0, 1);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                    GL11.glNormal3f(0, 0, 1);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() + 0.5f);
                }
                { // BACK
                    GL11.glNormal3f(0, 0, -1);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, 0, -1);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() - 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, 0, -1);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() - 0.5f, pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                    GL11.glNormal3f(0, 0, -1);
                    GL11.glVertex3f(pointsList.get(i).getCoords().getX() + 0.5f,pointsList.get(i).getCoords().getY() + 0.5f, pointsList.get(i).getCoords().getZ() - 0.5f);
                }
            }
            GL11.glEnd();
            GL11.glEndList();
        } else {
            GL11.glCallList(listId);
        }
    }
    }
}
