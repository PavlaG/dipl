/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author Pavla
 */
public class GraphModel extends Model {

    private int listId = -1;

    public GraphModel(List<Point> pointsList) {
        super(pointsList);
    }

    @Override
    public void draw() {
        if(isVisible()){
        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 128f);
        GL11.glColor3f(0.6f, 0.6f, 0.6f);
        GL20.glUseProgram(0);
        GL11.glDisable(GL11.GL_LIGHTING);
        if (listId == -1) {
            listId = GL11.glGenLists(1);
            GL11.glNewList(listId, GL11.GL_COMPILE_AND_EXECUTE);

            for (int i = 0; i < pointsList.size() - 1; i += 2) {
                GL11.glBegin(GL11.GL_LINES);
                GL11.glVertex3f(pointsList.get(i).getCoords().x, pointsList.get(i).getCoords().y, pointsList.get(i).getCoords().z);
                GL11.glVertex3f(pointsList.get(i + 1).getCoords().x, pointsList.get(i + 1).getCoords().y, pointsList.get(i + 1).getCoords().z);
                GL11.glEnd();
            }
            GL11.glEndList();
        } else {
            GL11.glCallList(listId);
        }
        GL11.glEnable(GL11.GL_LIGHTING);
    }

}}
