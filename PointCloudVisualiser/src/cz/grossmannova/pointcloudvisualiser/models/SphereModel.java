/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

/**
 *
 * @author Pavla
 */
public class SphereModel extends Model {

    private int listId = -1;
    private Sphere nodeVisualization = new Sphere();
    private float offset = 0f;//0.5f;

    public SphereModel(Point point) {
        super(point);
    }

    @Override
    public void draw() {
        if(isVisible()){
        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 128f);

        if (listId == -1) {
            listId = GL11.glGenLists(1);
            GL11.glNewList(listId, GL11.GL_COMPILE_AND_EXECUTE);
            GL11.glColor3f(1f, 0.7f, 0.3f);
            GL11.glTranslatef(point.getCoords().getX(), point.getCoords().getY() + offset, point.getCoords().getZ());
            nodeVisualization.draw(0.5f, 10, 10);
            GL11.glTranslatef(-point.getCoords().getX(), -point.getCoords().getY() - offset, -point.getCoords().getZ());
            GL11.glEndList();
        } else {
            GL11.glCallList(listId);
        }

    }
    }
}
